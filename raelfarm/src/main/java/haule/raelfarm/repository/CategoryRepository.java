package haule.raelfarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import haule.raelfarm.dto.CategorySelectDTO;
import haule.raelfarm.jpa.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query(value = "SELECT CATEGORY_NAME FROM CATEGORY_BOARD_TEST WHERE CATEGORY_NUM = :id", nativeQuery = true)
	String ViewCategoryName(@Param(value="id") int id);
	
	@Query(value = 
			"SELECT CONCAT(CATEGORY_NUM,\";\",CATEGORY_NAME) FROM CATEGORY_BOARD_TEST WHERE CATEGORY_NUM BETWEEN :st AND :ed", nativeQuery = true)
	List<String> ViewCategorysName(@Param(value="st") int st, @Param(value="ed") int ed);
	
}
