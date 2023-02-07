package haule.raelfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import haule.raelfarm.jpa.Board;
import haule.raelfarm.jpa.PK.Board_PK;

@Repository
public interface BoardRepository extends JpaRepository<Board, Board_PK>{
	
	@Query(value="SELECT WRITER FROM BOARD_TEST WHERE CATEGORY_NUM = :categorynum AND BOARD_NUM = :boardnum", nativeQuery=true)
	String SelectWriter(@Param(value="categorynum") int categorynum, @Param(value="boardnum") int boardnum);
}
