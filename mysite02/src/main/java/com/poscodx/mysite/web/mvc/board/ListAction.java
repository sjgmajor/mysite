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

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String spage = request.getParameter("page");
		Long page = 1L;
		if (spage != null && !spage.isEmpty()) {
			page = Long.parseLong(spage);
		}

		Long totalCount = new BoardDao().findTotalCount();
		Long boardDisplay = 5L;
		Long intervalPage = 5L;
		
		Long totalPage;
		if (totalCount % boardDisplay == 0) {
			totalPage = totalCount / boardDisplay;
		} else {
			totalPage = totalCount / boardDisplay + 1;
		}

		Long startGroup = (long) Math.floor((page - 1) / intervalPage);
		Long startPage = startGroup * intervalPage + 1;
		Long endPage = Math.min(startGroup * intervalPage + intervalPage, totalPage);
		
		
		Long listPage = (page - 1) * intervalPage;
		List<BoardVo> list = new BoardDao().findAllByPage(listPage, intervalPage);

		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("intervalPage", intervalPage);
		request.setAttribute("totalCount", totalCount);
		
		WebUtil.forward("board/list", request, response);
	}
}
