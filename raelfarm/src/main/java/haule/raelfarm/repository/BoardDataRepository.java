package haule.raelfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import haule.raelfarm.jpa.BoardData;
import jakarta.transaction.Transactional;

@Repository
public interface BoardDataRepository extends JpaRepository<BoardData, String> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE BOARD_DATA_TEST SET VIEW_COUNT = VIEW_COUNT + 1 WHERE I_BOARD_NUM = :id", nativeQuery = true)
	void updateViewCount(@Param(value="id") String id);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE BOARD_DATA_TEST SET RECOMMEND_COUNT = RECOMMEND_COUNT + 1 WHERE I_BOARD_NUM = :id", nativeQuery = true)
	void updateRecommendCount(@Param(value="id") String id);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE BOARD_DATA_TEST SET NO_RECOMMEND_COUNT = NO_RECOMMEND_COUNT + 1 WHERE I_BOARD_NUM = :id", nativeQuery = true)
	void updateNoRecommendCount(@Param(value="id") String id);
}
