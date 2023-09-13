package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control(접근제어)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=loginform");
			return;
		}
		///////////////////////////////////////////////////////
		Long userNo = authUser.getNo();

		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		String sno = request.getParameter("no");
		
		BoardVo vo = new BoardVo();
		BoardDao dao = new BoardDao();

		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(userNo);

		if (sno == "") {
			Long maxgNo = dao.findMaxgNo();
			
			if( dao.findMaxgNo() == null) {
				maxgNo = 0L;
			}
			vo.setgNo(maxgNo + 1L);

			dao.insert(vo);
		}

		else {
			Long no = Long.parseLong(sno);

			Long gNo = dao.findAllByNo(no).getgNo();
			Long oNo = dao.findAllByNo(no).getoNo();
			Long depth = dao.findAllByNo(no).getDepth();

			vo.setgNo(gNo);
			vo.setoNo(oNo);
			vo.setDepth(depth);
			
			dao.update(vo);
			dao.insertReply(vo);
		}

		response.sendRedirect(request.getContextPath() + "/board");

	}
}