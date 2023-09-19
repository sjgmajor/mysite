package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PagingVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public List<BoardVo> getContentsList(Long page, Long boardDisplay) {
		return boardRepository.findAllByPage(page, boardDisplay);
	}
	
	public PagingVo getPagingList(Long page){
		PagingVo pagingVo = new PagingVo();
		pagingVo.setPage(page);
		pagingVo.setTotalCount(boardRepository.findTotalCount());
		
		return pagingVo;
	}
	
	public void deleteContents(Long no) {
		boardRepository.deleteByNo(no);
	}
	
	public void writeContents(Long no) {
		
		BoardVo boardVo = new BoardVo();

		if (no == null) {
			Long maxgNo = boardRepository.findMaxgNo();
			boardVo.setgNo(maxgNo + 1L);
			boardRepository.insert(boardVo);
		}
		else {
		   	  boardVo  = boardRepository.findAllByNo(no);
			  boardRepository.update(boardVo);
			  boardRepository.insertReply(boardVo);
		 	 }
	}
	
}
