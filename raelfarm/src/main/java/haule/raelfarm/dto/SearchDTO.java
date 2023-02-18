package haule.raelfarm.dto;

import haule.raelfarm.pagination.Pagination;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
	
	int page;
	int recordSize;
	int pageSize;
	String keyword;
	String searchType;
	Pagination pagination;
	
	public SearchDTO(){
		this.page = 1;
		this.recordSize = 20;
		this.pageSize = 10;
	}
	
	public int getOffset() {
		return (page - 1) * recordSize;
	}
	
}
