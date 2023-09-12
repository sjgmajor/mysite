<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>MySite</h1>
			<ul>
				<li><a href="">ë¡ê·¸ì¸</a><li>
				<li><a href="">íìê°ì</a><li>
				<li><a href="">íìì ë³´ìì </a><li>
				<li><a href="">ë¡ê·¸ìì</a><li>
				<li>ë ìëíì¸ì ^^;</li>
			</ul>
		</div>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="ì°¾ê¸°">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>ë²í¸</th>
						<th>ì ëª©</th>
						<th>ê¸ì´ì´</th>
						<th>ì¡°íì</th>
						<th>ìì±ì¼</th>
						<th>&nbsp;</th>
					</tr>				
					<tr>
						<td>3</td>
						<td><a href="">ì¸ ë²ì§¸ ê¸ìëë¤.</a></td>
						<td>ìëí</td>
						<td>3</td>
						<td>2015-10-11 12:04:20</td>
						<td><a href="" class="del">ì­ì </a></td>
					</tr>
					<tr>
						<td>2</td>
						<td><a href="">ë ë²ì§¸ ê¸ìëë¤.</a></td>
						<td>ìëí</td>
						<td>3</td>
						<td>2015-10-02 12:04:12</td>
						<td><a href="" class="del">ì­ì </a></td>
					</tr>
					<tr>
						<td>1</td>
						<td><a href="">ì²« ë²ì§¸ ê¸ìëë¤.</a></td>
						<td>ìëí</td>
						<td>3</td>
						<td>2015-09-25 07:24:32</td>
						<td><a href="" class="del">ì­ì </a></td>
					</tr>
				</table>
				<div class="bottom">
					<a href="" id="new-book">ê¸ì°ê¸°</a>
				</div>				
			</div>
		</div>
		<div id="navigation">
			<ul>
				<li><a href="">ìëí</a></li>
				<li><a href="">ë°©ëªë¡</a></li>
				<li><a href="">ê²ìí</a></li>
			</ul>
		</div>
		<div id="footer">
			<p>(c)opyright 2015, 2016, 2017, 2018</p>
		</div>
	</div>
</body>
</html>