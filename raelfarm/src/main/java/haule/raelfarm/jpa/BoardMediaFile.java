package haule.raelfarm.jpa;

import haule.raelfarm.jpa.PK.BoardMediaFile_PK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(BoardMediaFile_PK.class)
@Table(name = "board_mediafile_test")
public class BoardMediaFile {
	@Id
	@Column(name="I_BOARD_NUM")
	private String I_BOARD_NUM;
	
	@Id
	@Column(name="SEQ")
	private int SEQ;
	
	@Column(name="CONTENT_TYPE")
	private String CONTENT_TYPE;
	
	@Column(name="FILE_NAME")
	private String FILE_NAME;
	
	@Column(name="FILE_PATH")
	private String FILE_PATH;
	
	@Column(name="DELETED")
	private String DELETED;
	
	@Builder
	public BoardMediaFile(String iboardnum, int seq, String contenttype, String filename, String filepath) {
		this.I_BOARD_NUM = iboardnum;
		this.SEQ = seq;
		this.CONTENT_TYPE = contenttype;
		this.FILE_NAME = filename;
		this.FILE_PATH = filepath;
		this.DELETED = "N";
	}
	
	public BoardMediaFile changeDeletedtoY() {
		this.DELETED = "Y";
		return this;
	}
}

