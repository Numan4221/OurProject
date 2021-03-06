package es.urjc.computadores;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="ourProject")
public interface ContractRepository extends JpaRepository<Contract,Long>{
	
	
	@CacheEvict(allEntries=true)
	Contract save(Contract contract);
	
	@CacheEvict(allEntries=true)
	void delete(Contract contract);
	
	
	List<Contract> findAll();
	
	/***
	 * Filtra los contratos por el nombre del contribuidor y el nombre del proyecto
	 * @param nickname
	 * @param projectname
	 * @return Lista de contratos
	 */

	List <Contract> findByContributorNicknameAndProjectProjectName(String nickname, String projectname);
	
	/***
	 * Filtra los contratos por el nombre del contribuidor y el nombre del proyecto
	 * @param nickname
	 * @param projectname
	 * @param page
	 * @return Pagina de contratos
	 */

	Page <Contract> findByContributorNicknameAndProjectProjectName(String nickname, String projectname, Pageable page);
	
	/***
	 * Filtra los contratos por el nombre del contribuidor
	 * @param nickName
	 * @return Lista de contratos
	 */

	List <Contract> findByContributorNickname(String nickName);
	
	/***
	 * Filtra los contratos por el nombre del contribuidor
	 * @param nickName
	 * @param page
	 * @return Pagina de contratos
	 */

	Page <Contract> findByContributorNickname(String nickName,Pageable page);
	
	/***
	 * Filtra los contratos por el nombre del proyecto
	 * @param projectName
	 * @return Lista de contratos
	 */

	List <Contract> findByProjectProjectName(String projectName);
	
	/***
	 * Filtra los contratos por el nombre del proyecto
	 * @param projectName
	 * @param page
	 * @return Lista de contratos
	 */

	Page <Contract> findByProjectProjectName(String projectName, Pageable page);
	
	
}
