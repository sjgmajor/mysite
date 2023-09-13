package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;

public class ModifyAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		String sNo = request.getParameter("no");
		Long no = Long.parseLong(sNo);
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setNo(no);
		
		new BoardDao().modify(vo);
		
		response.sendRedirect(request.getContextPath() + "/board");
		
	}
}