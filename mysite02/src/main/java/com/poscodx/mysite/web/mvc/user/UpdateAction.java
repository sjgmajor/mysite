package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");

		HttpSession session = request.getSession();
        UserVo authUser  = (UserVo) session.getAttribute("authUser");
        
	    UserVo userVo = new UserVo();
	    
	    userVo.setNo(authUser.getNo());
	    userVo.setName(name);
	    userVo.setPassword(password);
	    userVo.setGender(gender);

	    new UserDao().update(userVo);

    	UserVo updatedUserVo = new UserDao().findByNo(authUser.getNo());

	    session.setAttribute("authUser", updatedUserVo);
	    WebUtil.forward("/user/updateform", request, response);	
    }
}