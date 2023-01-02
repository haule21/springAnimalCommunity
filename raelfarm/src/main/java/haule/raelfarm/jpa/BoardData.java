package haule.raelfarm.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "board_data_test")
public class BoardData {
	
	@Id
	@Column(name="I_BOARD_NUM")
	private String I_BOARD_NUM;
	
	@Column(name="VIEW_COUNT")
	private int VIEW_COUNT;
	
	@Column(name="RECOMMEND_COUNT")
	private String RECOMMEND_COUNT;
	
	@Column(name="NO_RECOMMEND_COUNT")
	private String NO_RECOMMEND_COUNT;
	
}
