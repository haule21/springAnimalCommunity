package haule.raelfarm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CategorySelectDTO {
	
	int categorynum;
	String categoryname;
	
	@Builder
	public CategorySelectDTO(int categorynum, String categoryname){
		this.categorynum = categorynum;
		this.categoryname = categoryname;
	}
}
