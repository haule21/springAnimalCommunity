package haule.raelfarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import haule.raelfarm.jpa.BoardMediaFile;
import haule.raelfarm.jpa.PK.BoardMediaFile_PK;

@Repository
public interface BoardMediaFileRepository extends JpaRepository<BoardMediaFile, BoardMediaFile_PK>{
	
	@Query(value = "SELECT CONCAT(SEQ, \"/summernoteImage/\",FILE_PATH,\"/\",FILE_NAME) FROM BOARD_MEDIAFILE_TEST WHERE DELETED = \"N\" AND I_BOARD_NUM = :id", nativeQuery = true)
	List<String> SelectBoardMediaData( @Param(value="id") String iboardnum);
	
	@Query(value = "SELECT IFNULL(MAX(SEQ),0) FROM BOARD_MEDIAFILE_TEST WHERE I_BOARD_NUM = :id", nativeQuery = true)
	int SelectSEQNumber(@Param(value="id") String iboardnum);
	
}
