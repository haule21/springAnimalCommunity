package haule.raelfarm.jpa;

import haule.raelfarm.jpa.PK.BoardRecommendHistory_PK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@IdClass(BoardRecommendHistory_PK.class)
@Table(name = "board_recommend_history_test")
public class BoardRecommendHistory {
	
	@Id
	@Column(name="I_BOARD_NUM")
	private String I_BOARD_NUM;
	
	@Id
	@Column(name="USERID")
	private String USERID;
	
	@Column(name="RECOMMEND")
	private String RECOMMEND;
	
	@Builder
	BoardRecommendHistory(String I_BOARD_NUM, String USERID, String RECOMMEND){
		this.I_BOARD_NUM = I_BOARD_NUM;
		this.USERID = USERID;
		this.RECOMMEND = RECOMMEND;
	}
}
