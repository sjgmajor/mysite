package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poscodx.mysite.vo.UserVo;

public class UserDao {
	
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = BoardDao.getConnection();
			String sql =
				"select no, name" +
				"  from user" +
				" where email=?" +
				"   and password=password(?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			//5. 결과 처리
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
			}
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return userVo;
	}
	
	public UserVo findByNo(Long no) {
		UserVo userVo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = BoardDao.getConnection();
			
			String sql =
				"select no, name, email, gender" +
				"  from user" +
				" where no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			//5. 결과 처리
			if(rs.next()) {
				Long number = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				userVo = new UserVo();
				userVo.setNo(number);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return userVo;
	}
	
	public Boolean insert(UserVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = BoardDao.getConnection();
			
			String sql =
				" insert" +
				"   into user" +
				" values(null, ?, ?, password(?), ?, current_date())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			int count = pstmt.executeUpdate();
			
			//5. 결과 처리
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public Boolean update(UserVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = BoardDao.getConnection();
			
			if("".equals(vo.getPassword())) {
			    String sql1 =
			            " update user" +
			            " set name = ?," +
			            " gender = ?" +
			            " email = ?" +
			            " where no=? ";
			        
			        pstmt = conn.prepareStatement(sql1);
			        pstmt.setString(1, vo.getName());
			        pstmt.setString(2, vo.getGender());
			        pstmt.setLong(3, vo.getNo()); 
			}
			else {
			    String sql2 =
			        " update user" +
			        " set password = password(?)," +
			        " name = ?," +
			        " gender = ?" +
			        " where no=? ";
			    
			    pstmt = conn.prepareStatement(sql2);
			    pstmt.setString(1, vo.getPassword());
			    pstmt.setString(2, vo.getName());
			    pstmt.setString(3, vo.getGender());
			    pstmt.setLong(4, vo.getNo());  
			}
			
			int count = pstmt.executeUpdate();
			
			//5. 결과 처리
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}