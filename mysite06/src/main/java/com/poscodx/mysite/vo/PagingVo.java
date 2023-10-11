package com.poscodx.mysite.vo;

public class PagingVo {
	private Long boardDisplay = 7L;
	private Long intervalPage = 3L;
	private Long page = 1L;
	private Long totalCount;
	
	private Long totalPage;
	private Long startPage;
	private Long endPage;
	
	public void PaingVo(Long boardDisplay, Long intervalPage, Long page, Long totalCount, Long totalPage, Long startPage, Long endPage) {
		this.boardDisplay = boardDisplay;
		this.intervalPage = intervalPage;
		this.page = page;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.startPage = startPage;
		this.endPage = endPage;
	}
	
	public Long getPage() {
		return page;
	}
	
	public void setPage(Long page) {
		this.page = page;
	}
	
	public Long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		
		pageInfo();
	}
	public Long getBoardDisplay() {
		return boardDisplay;
	}
	public void setBoardDisplay(Long boardDisplay) {
		this.boardDisplay = boardDisplay;
	}
	public Long getIntervalPage() {
		return intervalPage;
	}
	public void setIntervalPage(Long intervalPage) {
		this.intervalPage = intervalPage;
	}
	public Long getStartPage() {
		return startPage;
	}
	public void setStartPage(Long startPage) {
		this.startPage = startPage;
	}
	public Long getEndPage() {
		return endPage;
	}
	public void setEndPage(Long endPage) {
		this.endPage = endPage;
	}
	@Override
	public String toString() {
		return "PageVo [page=" + page + ", totalPage=" + totalPage + ", totalCount=" + totalCount + ", boardDisplay="
				+ boardDisplay + ", intervalPage=" + intervalPage + ", startPage=" + startPage + ", endPage=" + endPage
				+ "]";
	}

	public void pageInfo() {
		
		if (totalCount % boardDisplay == 0) {
			totalPage = totalCount / boardDisplay;
		} else {
			totalPage = totalCount / boardDisplay + 1;
		}
		
		Long startGroup = (long) Math.floor((page - 1) / intervalPage);
		startPage = startGroup * intervalPage + 1;
		endPage = Math.min(startGroup * intervalPage + intervalPage, totalPage);
	}

}



