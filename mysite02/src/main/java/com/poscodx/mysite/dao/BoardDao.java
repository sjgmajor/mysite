package com.poscodx.mysite.dao;

import java.sql.Connection;
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
			conn = UserDao.getConnection();

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
			conn = UserDao.getConnection();

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
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = " insert into board" + " values(null, ?, ?, 0, now(), ?, 1, 1, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getgNo());
			pstmt.setLong(4, vo.getUserNo());

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

	public Boolean update(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = " update board set o_no = o_no + 1" + "  where g_no=? and o_no >= ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getgNo());
			pstmt.setLong(2, vo.getoNo() + 1L);

			pstmt.executeUpdate();

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

	public Boolean insertReply(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = " insert into board" + " values(null, ?, ?, 0, now(), ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getgNo());
			pstmt.setLong(4, vo.getoNo() + 1L);
			pstmt.setLong(5, vo.getDepth() + 1L);
			pstmt.setLong(6, vo.getUserNo());

			pstmt.executeUpdate();

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

	public Long findMaxgNo() {

		Long maxgNo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			conn = UserDao.getConnection();

			String sql = " select ifnull((select MAX(g_no) from board), 0)" + "   from board";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				maxgNo = rs.getLong(1);
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

		return maxgNo;
	}

	public BoardVo findAllByNo(Long no) {
		BoardVo bvo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = UserDao.getConnection();

			String sql = " select b.user_no, b.title, a.name, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.no, b.contents"
					+ "   from user a, board b" + "  where a.no = b.user_no" + "    and b.no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

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
				Long vno = rs.getLong(9);
				String contents = rs.getString(10);

				bvo = new BoardVo();

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

		return bvo;
	}

	public boolean modify(BoardVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = UserDao.getConnection();

			String sql = " update board" + "    set title = ?, contents = ?" + "  where no = ?";

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

	public boolean updateByNo(Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = "update board set hit = hit + 1" +
					     "  where no=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			pstmt.executeUpdate();

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
}
