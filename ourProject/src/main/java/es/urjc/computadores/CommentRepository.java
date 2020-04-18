package es.urjc.computadores;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="ourProject")
public interface CommentRepository extends JpaRepository<Comment,Long>{
	
	
	@CacheEvict(allEntries=true)
	Comment save(Comment comment);
	
	@CacheEvict(allEntries=true)
	void delete(Comment comment);
	/***
	 * Filtra los comentarios por nombre de Usuario y Nombre del proyecto
	 * @param nickname
	 * @param projectname
	 * @return Lista de comentarios
	 */
	
	//@Cacheable
	List<Comment> findAll();
	
	//@Cacheable
	List<Comment> findByUserNicknameAndProjectProjectName(String nickname, String projectname);
	
	/***
	 * Filtra los comentarios por nombre de Usuario y Nombre del proyecto
	 * @param nickname
	 * @param projectname
	 * @return Pagina de comentarios
	 */
	//@Cacheable
	Page<Comment> findByUserNicknameAndProjectProjectName(String nickname, String projectname, Pageable page);
	
	/***
	 * Filtra los comentarios por nombre de Usuario
	 * @param nickName
	 * @return Lista de comentarios
	 */
	//@Cacheable
	List<Comment> findByUserNickname(String nickName);
	
	/***
	 * Filtra los comentarios por nombre de Usuario
	 * @param nickName
	 * @return Pagina de comentarios
	 */ 
	//@Cacheable
	Page<Comment> findByUserNickname(String nickName,Pageable page);
	
	/***
	 * Filtra los comentarios por nombre de Proyecto
	 * @param projectName
	 * @return Lista de comentarios
	 */
	//@Cacheable
	List<Comment> findByProjectProjectName(String projectName);
	
	/***
	 * Filtra los comentarios por nombre de Proyecto
	 * @param projectName
	 * @param page
	 * @return Pagina de comentarios
	 */
	//@Cacheable
	Page<Comment> findByProjectProjectName(String projectName,Pageable page);

}
