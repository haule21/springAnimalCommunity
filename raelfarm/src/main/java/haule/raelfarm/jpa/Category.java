package haule.raelfarm.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category_board_test")
public class Category {
	
	@Id
	@Column(name = "CATEGORY_NUM")
	private int CATEGORY_NUM;
	
	@Column(name = "CATEGORY_NAME")
	private String CATEGORY_NAME;
}
