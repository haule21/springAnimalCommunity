package haule.raelfarm.jpa.PK;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class Board_PK implements Serializable{

	private int CATEGORY_NUM;
	private int BOARD_NUM;
}
