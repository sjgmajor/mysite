package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PagingVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/{page}")
	public String main(@PathVariable Long page,Model model) {
		PagingVo pageVo = boardService.getPagingList(page);
		model.addAttribute("pageVo", pageVo);
		List<BoardVo> list = boardService.getContentsList(pageVo.getPage(), pageVo.getBoardDisplay());
		model.addAttribute("list", list);
		return "board/list";
	}
	
	@RequestMapping(value="/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		boardService.deleteContents(no);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/write/{userNo}", method=RequestMethod.GET)
	public String update(HttpSession session, @PathVariable("userNo") Long userNo, Model model) {
		// Access Control(접근 제어)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////////////
		
		model.addAttribute("userNo", userNo);
		
		return "board/write";
	}

	@RequestMapping(value="/write/{no}", method=RequestMethod.POST)
	public String update(HttpSession session, @PathVariable("no") Long no, BoardVo boardVo) {
		// Access Control(접근 제어)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////////////
		boardVo.setNo(no);
		boardService.writeContents(no);
		
		return "redirect:/board";
	}

}
