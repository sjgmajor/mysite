package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		return "redirect:/board/1";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		boardService.updatehit(no);
		model.addAttribute("boardVo", boardVo);
		return "board/view";
	}	
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		// Access Control(접근 제어)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////////////
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, Long userNo,
			@RequestParam(value="no", required=false, defaultValue="") Long no,
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="contents", required=true, defaultValue="") String contents) {
		// Access Control(접근 제어)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////////////
		userNo = authUser.getNo();
		System.out.println(no);
		boardService.writeContents(no, userNo, title, contents);
		
		return "redirect:/board/1";
	}
	
	@RequestMapping(value="/modify/{no}")
	public String modify(HttpSession session, @PathVariable("no") Long no, Model model) {
		// Access Control(접근 제어)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////////////
		BoardVo boardVo = boardService.getContents(no);

		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(HttpSession session, @PathVariable("no") Long no,
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="contents", required=true, defaultValue="") String contents) {
		// Access Control(접근 제어)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////////////
		
		boardService.modifyContents(no, title, contents);
		
		return "redirect:/board/1";
	}	
	
	@RequestMapping(value="/reply/{no}")	
	public String reply(
		HttpSession session, 
		@PathVariable("no") Long no,
		Model model) {
		// Access Control(접근 제어)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////////////
		
		BoardVo boardVo = boardService.getContents(no);
		boardVo.setoNo(boardVo.getoNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
			
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}	
}
