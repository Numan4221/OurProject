package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>{
	
	List<Comment> findByUserNicknameAndProjectProjectName(String nickname, String projectname);
	
	List<Comment> findByUserNickname(String nickName);
	
	List<Comment> findByProjectProjectName(String projectName);

}
