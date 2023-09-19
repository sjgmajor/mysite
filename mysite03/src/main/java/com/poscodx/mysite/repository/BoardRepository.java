package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAllByPage(Long page, Long boardDisplay) {
		page = (page - 1) * boardDisplay;
		Map<String, Object> map = new HashMap<>();
		map.put("boardDisplay", boardDisplay);
		map.put("page", page);
		List<BoardVo> list = sqlSession.selectList("board.findAllByPage", map);
		return list;
	}
	
	public Long findTotalCount() {
		return sqlSession.selectOne("board.findTotalCount");
	}
	
	public Boolean deleteByNo(Long no) {
		int count = sqlSession.delete("board.deleteByNo", no);
		return count == 1;
	}
	
	public Boolean insert(BoardVo boardVo) {
		int count = sqlSession.insert("board.insert", boardVo);
		return count == 1;
	}
	
	public BoardVo findAllByNo(Long no) {
		return sqlSession.selectOne("board.findAllByNo", no);
	}
	
	public Long findMaxgNo() {
		return sqlSession.selectOne("board.findMaxgNo");
	}

	public Boolean update(BoardVo boardVo) {
		int count = sqlSession.update("board.update", boardVo);
		return count == 1;
	}

	public Boolean insertReply(BoardVo boardVo) {
		int count = sqlSession.insert("board.insertReply", boardVo);
		return count == 1;
	}

	public Boolean modify(BoardVo boardVo) {
		int count = sqlSession.update("board.modify", boardVo);
		return count == 1;
	}

	public boolean updateByNo(Long no) {
		int count = sqlSession.update("board.updateByNo", no);
		return count == 1;
	}
}
