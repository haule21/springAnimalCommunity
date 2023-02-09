package haule.raelfarm.jpa.PK;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class BoardMediaFile_PK implements Serializable{
	
	@Column(name="I_BOARD_NUM")
	private String I_BOARD_NUM;
	@Column(name="SEQ")
	private int SEQ;
	
	public BoardMediaFile_PK(String iboardnum, int i) {
		this.I_BOARD_NUM = iboardnum;
		this.SEQ = i;
	}
}
