package haule.raelfarm.jpa;

import haule.raelfarm.jpa.PK.Board_PK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@IdClass(Board_PK.class)
@Getter
@Table(name = "board_test")
public class Board {
	@Id
	@Column(name="CATEGORY_NUM")
	private int CATEGORY_NUM;
	
	@Id
	@Column(name="BOARD_NUM")
	private int BOARD_NUM;
	
	@Column(name="REGISTERED_DATE")
	private String REGISTERED_DATE;
	
	@Column(name="TITLE")
	private String TITLE;
	
	@Column(name="WRITER")
	private String WRITER;
	
	@Column(name="MODIFIED_DATE")
	private String MODIFIED_DATE;
	
	@Column(name="DELETED")
	private String DELETED;
	
	@Column(name="EXIST_IMGFILE")
	private String EXIST_IMGFILE;
	
	@Column(name="CONTENT")
	private String CONTENT;
	
}
