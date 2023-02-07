package haule.raelfarm.jpa.PK;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardMediaFile_PK implements Serializable{
	private static final long serialVersionUID = -6859646763506182144L;
	private String I_BOARD_NUM;
	private int SEQ;
}
