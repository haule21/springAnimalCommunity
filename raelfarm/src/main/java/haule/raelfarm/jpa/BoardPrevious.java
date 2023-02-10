package haule.raelfarm.jpa;

import haule.raelfarm.jpa.PK.BoardMediaFile_PK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "board_previous_content_test")
public class BoardPrevious {
	
//	@Id
//	@Column(name="I_BOARD_NUM")
//	private String I_BOARD_NUM;
//	
//	@Id
//	@Column(name="SEQ")
//	private int SEQ;
	
	@EmbeddedId
	BoardMediaFile_PK boardMediaFile_PK;
	
	@Column(name="MODIFIED_DATE")
	private String MODIFIED_DATE;
	
	@Column(name="CONTENT")
	private String CONTENT;

}
