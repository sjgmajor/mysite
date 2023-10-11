package com.poscodx.mysite.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	
	@Autowired
	private SiteService siteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 값 가져오기
		ServletContext sc = request.getServletContext();
		SiteVo siteVo = (SiteVo) sc.getAttribute("siteVo");
		
		// DB에서 가져오기
		if(siteVo == null) {
			siteVo = siteService.getSite();
			sc.setAttribute("siteVo", siteVo);
		}

		return true;
	}
	
}
