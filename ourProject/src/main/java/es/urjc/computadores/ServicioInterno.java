package es.urjc.computadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class ServicioInterno {

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private GoalRepository goalRepo;

	@Autowired
	private ContractRepository contractRepo;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;

	public boolean deleteProject(String projectName, String developerNickname) {
		Project proj = projectRepo.findFirstByProjectNameAndDeveloperNickname(projectName, developerNickname);
		if (proj != null) {
			List<Goal> goals = goalRepo.findByProjectProjectNameAndProjectDeveloperName(projectName, developerNickname);
			// List<Goal> goals = goalRepo.findByProjectProjectName(projectName);
			goalRepo.deleteAll(goals);
			List<Contract> contracts = contractRepo.findByProjectProjectName(projectName);
			contractRepo.deleteAll(contracts);
			List<Comment> comments = commentRepo.findByProjectProjectName(projectName);
			commentRepo.deleteAll(comments);
			projectRepo.delete(proj);
			return true;
		} else {
			return false;
		}
	}

	public boolean convertirseDesarrollador(String userName, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		User user = userRepo.findFirstByNickname(userName);
		
		System.out.println("print 1");
		
		if (user != null) {
			if (user.getClass() != Developer.class) {

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				Collection<GrantedAuthority> extraAuths = new ArrayList<>(); // calculate the extra authorities
				extraAuths.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));
				MidSessionAuthenticationWrapper midSessionAuthentication = new MidSessionAuthenticationWrapper(
						authentication, extraAuths);
				SecurityContextHolder.getContext().setAuthentication(midSessionAuthentication);

				System.out.println("print 2");
				
				List<Project> financedProjects = user.getFinancedProjects();
				List<Contract> contracts = user.getMyContracts();
				List<Comment> comments = user.getMyComments();
				Developer dev = new Developer(user.nickname, user.getPassword(), user.email, user.name, user.surname);
				dev.setAccountID(user.getAccountID());
				dev.setMyPaymentMethod(user.getMyPaymentMethod());
				dev.addRole();

				System.out.println("print 3");

				userRepo.save(dev);
				for (Project pro : financedProjects) {
					List<User> contributors = pro.getContributors();
					contributors.remove(user);
					contributors.add(dev);
					pro.setContributors(contributors);
					projectRepo.save(pro);
				}
				for (Contract cont : contracts) {
					Contract cont2 = new Contract(dev, cont.project, cont.contractContent, cont.contribution);

					// cont.contributor = dev;
					// contractRepo.delete(cont);
					contractRepo.save(cont2);
				}
				for (Comment com : comments) {
					com.user = dev;
					commentRepo.save(com);
				}
				userRepo.delete(user);
				/*
				MidSessionAuthenticationWrapper midSessionAuthentication2 = (MidSessionAuthenticationWrapper) SecurityContextHolder
						.getContext().getAuthentication();
				Authentication authentication2 = midSessionAuthentication2.getWrapped();
				SecurityContextHolder.getContext().setAuthentication(authentication2);
				*/
				System.out.println("print 4");
				return true;
			}
		}

		return false;

	}

}
