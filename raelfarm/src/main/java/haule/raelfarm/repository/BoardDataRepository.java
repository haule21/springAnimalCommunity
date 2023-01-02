package haule.raelfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import haule.raelfarm.jpa.BoardData;

public interface BoardDataRepository extends JpaRepository<BoardData, String> {

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE BOARD_DATA_TEST SET VIEWS_COUNT = VIEWS_COUNT + 1 WHERE I_BOARD_NUM = :id", nativeQuery = true)
	void updateViewCount(@Param(value="id") String id);
}
