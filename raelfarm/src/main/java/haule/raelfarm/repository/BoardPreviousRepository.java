package haule.raelfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import haule.raelfarm.jpa.BoardPrevious;
import haule.raelfarm.jpa.PK.BoardMediaFile_PK;

@Repository
public interface BoardPreviousRepository extends JpaRepository<BoardPrevious, BoardMediaFile_PK>{

	@Query(value = "SELECT IFNULL(MAX(SEQ),0) FROM BOARD_PREVIOUS_CONTENT_TEST WHERE I_BOARD_NUM = :id", nativeQuery = true)
	int SelectSEQNumber(@Param(value="id") String iboardnum);
}
