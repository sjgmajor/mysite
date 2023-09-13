package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class WriteFromAction implements Action {

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
				
		String sno = request.getParameter("no");
		String suserNo = request.getParameter("userNo");

		if(sno == null) {
			Long userNo = Long.parseLong(suserNo);
			request.setAttribute("userNo", userNo);
		}
		else {
			Long no = Long.parseLong(sno);
			request.setAttribute("no", no);
		}
		
		WebUtil.forward("board/write", request, response);
	}

}
