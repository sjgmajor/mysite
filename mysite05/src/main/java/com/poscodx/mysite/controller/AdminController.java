package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileuploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Auth(Role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteService;

	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping(value="")
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}
	@RequestMapping("main/update")
	public String update(SiteVo siteVo,
						 @RequestParam("file") MultipartFile file) {
		String url = fileuploadService.restore(file);
		siteVo.setProfile(url);
		
		SiteVo site = applicationContext.getBean(SiteVo.class);
		
		siteService.UpdateSite(siteVo);
		servletContext.setAttribute("siteVo", siteVo);
		
//		site.setTitle(siteVo.getTitle());
//		site.setWelcome(siteVo.getWelcome());
//		site.setProfile(siteVo.getProfile());
//		site.setDescription(siteVo.getDescription());
		BeanUtils.copyProperties(siteVo, site);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
