package haule.raelfarm.jpa;

import haule.raelfarm.jpa.PK.BoardMediaFile_PK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(BoardMediaFile_PK.class)
@Table(name = "board_previous_content_test")
public class BoardPrevious {
	@Id
	@Column(name="I_BOARD_NUM")
	private String I_BOARD_NUM;
	
	@Id
	@Column(name="SEQ")
	private int SEQ;
	
	@Column(name="MODIFIED_DATE")
	private String MODIFIED_DATE;
	
	@Column(name="CONTENT")
	private String CONTENT;

}
