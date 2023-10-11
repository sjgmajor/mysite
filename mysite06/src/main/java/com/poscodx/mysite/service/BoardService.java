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
	
	public void writeContents(Long no, Long userNo, String title, String contents) {
		BoardVo boardVo = new BoardVo();
		
		boardVo.setContents(contents);
		boardVo.setTitle(title);
		boardVo.setUserNo(userNo);
		if(no == null) {
		boardRepository.insert(boardVo);
		}
		else {
			BoardVo parentBoard = boardRepository.findAllByNo(no);

			if (parentBoard != null) {
				boardVo.setgNo(parentBoard.getgNo());
		        boardVo.setoNo(parentBoard.getoNo() + 1L);
		        boardVo.setDepth(parentBoard.getDepth() + 1L);
		        boardRepository.update(boardVo);
		        boardRepository.insert(boardVo);
			}
		}
	}

	public BoardVo getContents(Long no) {
		return boardRepository.findAllByNo(no);
	}

	public void modifyContents(Long no, String title, String contents) {
		BoardVo boardVo = new BoardVo();
		
		boardVo.setContents(contents);
		boardVo.setTitle(title);
		boardVo.setNo(no);
		
		boardRepository.modify(boardVo);
		
	}

	public void updatehit(Long no) {
		boardRepository.updateByNo(no);
	}
}
