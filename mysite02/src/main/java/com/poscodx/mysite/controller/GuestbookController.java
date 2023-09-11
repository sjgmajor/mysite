package com.poscodx.mysite.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;


public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");
		
		if ("insert".equals(action)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String message = request.getParameter("content");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String reg_Date = dateFormat.format(new Date());

			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(message);
			vo.setRegDate(reg_Date);
			
			new GuestbookDao().insert(vo);

			response.sendRedirect("/mysite02/guestbook");

		} else if ("deleteform".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);
			
		} else if ("delete".equals(action)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");

			GuestbookDao dao = new GuestbookDao();
			dao.guestbookDeleteByPassWord(no, password);

			response.sendRedirect("/mysite02/guestbook");
			
		} else {
			List<GuestbookVo> list = new GuestbookDao().guestbookfindAll();
			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/index.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
