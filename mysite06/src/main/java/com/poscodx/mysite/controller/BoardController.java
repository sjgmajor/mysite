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

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, Long userNo,
			@RequestParam(value="no", required=false, defaultValue="") Long no,
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="contents", required=true, defaultValue="") String contents) {
		
		userNo = authUser.getNo();
		boardService.writeContents(no, userNo, title, contents);
		
		return "redirect:/board/1";
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}")
	public String modify(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);

		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, @PathVariable("no") Long no,
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="contents", required=true, defaultValue="") String contents) {
		
		boardService.modifyContents(no, title, contents);
		
		return "redirect:/board/1";
	}	
	
	@Auth
	@RequestMapping(value="/reply/{no}")	
	public String reply( @PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		boardVo.setoNo(boardVo.getoNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
			
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}	
}
