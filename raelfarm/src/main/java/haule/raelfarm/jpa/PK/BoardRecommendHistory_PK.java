package haule.raelfarm.jpa.PK;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BoardRecommendHistory_PK implements Serializable{

	private String I_BOARD_NUM;
	private String USERID;
}
