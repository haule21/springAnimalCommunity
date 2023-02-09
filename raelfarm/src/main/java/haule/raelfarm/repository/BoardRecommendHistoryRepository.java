package haule.raelfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import haule.raelfarm.jpa.BoardRecommendHistory;
import haule.raelfarm.jpa.PK.BoardRecommendHistory_PK;

public interface BoardRecommendHistoryRepository extends JpaRepository<BoardRecommendHistory, BoardRecommendHistory_PK>{

}
