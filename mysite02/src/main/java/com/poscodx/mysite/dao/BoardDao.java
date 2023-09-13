package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "   select b.user_no, b.title, a.name, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.no"
					+ "     from user a, board b" + "    where a.no = b.user_no" + " order by b.g_no desc, b.o_no asc";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long userNo = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long no = rs.getLong(9);

				BoardVo bvo = new BoardVo();

				bvo.setUserNo(userNo);
				bvo.setTitle(title);
				bvo.setName(name);
				bvo.setHit(hit);
				bvo.setRegDate(regDate);
				bvo.setgNo(gNo);
				bvo.setoNo(oNo);
				bvo.setDepth(depth);
				bvo.setNo(no);

				result.add(bvo);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public Boolean deleteByNo(Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public Boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		Long maxgNo = null;

		try {
			conn = getConnection();
			String sql1 = " select ifnull((select MAX(g_no) from board), 0) + 1" +
						  "   from board";

			pstmt1 = conn.prepareStatement(sql1);

			rs1 = pstmt1.executeQuery();

			if (rs1.next()) {
				maxgNo = rs1.getLong(1);
			}
			
			String sql2 = " insert into board" +
						  " values(null, ?, ?, 0, now(), ?, 1, 1, ?)";

			pstmt2 = conn.prepareStatement(sql2);

			pstmt2.setString(1, vo.getTitle());
			pstmt2.setString(2, vo.getContents());
			pstmt2.setLong(3, maxgNo);
			pstmt2.setLong(4, vo.getUserNo());

			pstmt2.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (pstmt1 != null) {
					pstmt1.close();
				}

				if (pstmt2 != null) {
					pstmt2.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<BoardVo> findAllByNo(Long no) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql1 = "update board" + "   set hit = hit + 1" + " where no = ?";

			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setLong(1, no);

			pstmt1.executeUpdate();

			String sql2 = " select b.user_no, b.title, a.name, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.no, b.contents"
					+ "   from user a, board b" + "  where a.no = b.user_no" + "    and b.no = ?";

			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setLong(1, no);

			rs = pstmt2.executeQuery();
			while (rs.next()) {
				Long userNo = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long vno = rs.getLong(9);
				String contents = rs.getString(10);

				BoardVo bvo = new BoardVo();

				bvo.setUserNo(userNo);
				bvo.setTitle(title);
				bvo.setName(name);
				bvo.setHit(hit);
				bvo.setRegDate(regDate);
				bvo.setgNo(gNo);
				bvo.setoNo(oNo);
				bvo.setDepth(depth);
				bvo.setNo(vno);
				bvo.setContents(contents);

				result.add(bvo);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt1 != null) {
					pstmt1.close();
				}

				if (pstmt2 != null) {
					pstmt1.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean modify(BoardVo vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();

			String sql =  " update board" + 
						  "    set title = ?, contents = ?" +
						  "  where no = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.183:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

}
