package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class ModifyFormAction implements Action {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String sno = request.getParameter("no");
		Long no = Long.parseLong(sno);
		
		List<BoardVo> modify = new BoardDao().findAllByNo(no);
		request.setAttribute("modify", modify);
		WebUtil.forward("board/modify", request, response);
	}
}