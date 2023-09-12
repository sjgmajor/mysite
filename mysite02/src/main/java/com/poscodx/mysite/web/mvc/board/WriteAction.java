package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String gNo = request.getParameter("gNo");
		Long sgNo = Long.parseLong(gNo);
		String userNo = request.getParameter("userNo");
		Long suserNo = Long.parseLong(userNo);
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setgNo(sgNo);
		vo.setUserNo(suserNo);
		
		new BoardDao().insert(vo);
		response.sendRedirect(request.getContextPath() + "/board");
		
	}
}