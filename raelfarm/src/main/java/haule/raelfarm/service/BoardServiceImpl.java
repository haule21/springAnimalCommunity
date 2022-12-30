package haule.raelfarm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardMapper boardMapper;
	
	@Override
	public List<MainSelectDTO> SelectMainDatas() {
		List<MainSelectDTO> datas = boardMapper.SelectMainDatas();
		return datas; 
	}
}
