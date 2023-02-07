package haule.raelfarm.jpa.PK;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Board_PK implements Serializable{

	private static final long serialVersionUID = -5535853000547357102L;
	private int CATEGORY_NUM;
	private int BOARD_NUM;
}
