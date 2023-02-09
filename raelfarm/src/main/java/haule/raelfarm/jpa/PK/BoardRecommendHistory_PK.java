package haule.raelfarm.jpa.PK;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardRecommendHistory_PK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4181179647049911501L;
	private String I_BOARD_NUM;
	private String USERID;
}
