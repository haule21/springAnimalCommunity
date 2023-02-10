package haule.raelfarm.jpa;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.domain.Persistable;

import haule.raelfarm.jpa.PK.BoardRecommendHistory_PK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_recommend_history_test")
public class BoardRecommendHistory implements Serializable, Persistable<BoardRecommendHistory_PK>{
	
//	@Id
//	@Column(name="I_BOARD_NUM")
//	private String I_BOARD_NUM;
//	
//	@Id
//	@Column(name="USERID")
//	private String USERID;
	
	@EmbeddedId
	private BoardRecommendHistory_PK boardRecommendHistory_PK;
	
	@Column(name="RECOMMEND")
	private String RECOMMEND;
	
	@Builder
	BoardRecommendHistory(BoardRecommendHistory_PK pk, String RECOMMEND){
		this.boardRecommendHistory_PK = pk;
		this.RECOMMEND = RECOMMEND;
	}
	
	@Override
    public BoardRecommendHistory_PK getId() {
        return boardRecommendHistory_PK;
    }

    //prevent Spring Data doing a select-before-insert - this particular entity is never updated
    @Override
    public boolean isNew() {
        return true;
    }
}
