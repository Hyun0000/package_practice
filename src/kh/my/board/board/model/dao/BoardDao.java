package kh.my.board.board.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static kh.my.board.common.JDBCTemplate.*;

import kh.my.board.board.model.vo.Board;

public class BoardDao {
//========================================================================================================================
	// 전체 조회
	public ArrayList<Board> selectBoradList(Connection conn) {
		ArrayList<Board> selectBoradList = null;
		String sql= "select * from BOARD_R";
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				selectBoradList =  new ArrayList<Board>();
				do {
					Board board = new Board();
					board.setBno(rs.getInt("bno"));
					board.setTitle(rs.getString("title"));
					board.setContent(rs.getString("content"));
					board.setCreateDate(rs.getDate("create_date"));
					board.setWriter(rs.getString("writer"));
					board.setDeleteYn(rs.getString("delete_yn"));
					// 추가한 vo 관련 해서 코드 추가
					board.setBref(rs.getInt("bref"));
					board.setBreLevel(rs.getInt("bre_Level"));
					board.setBreStep(rs.getInt("bre_Step"));
					selectBoradList.add(board);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return selectBoradList;
	}
//========================================================================================================================
	// 페이징 처리 (오버로딩 이용)
	public ArrayList<Board> selectBoradList(Connection conn, int start, int end) {
		ArrayList<Board> selectBoradList = null;
		String sql= "select * from (select rownum rown, t1.* from (select * from BOARD_R order by bref desc, bre_step asc)t1)t2 where rown between ? and ?" ;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				selectBoradList =  new ArrayList<Board>();
				do {
					Board board = new Board();
					board.setBno(rs.getInt("bno"));
					board.setTitle(rs.getString("title"));
					board.setContent(rs.getString("content"));
					board.setCreateDate(rs.getDate("create_date"));
					// 타임스탬프로 자료형을 바꿨지만 getDate로 커버가 가능하다.
					board.setWriter(rs.getString("writer"));
					board.setDeleteYn(rs.getString("delete_yn"));
					// 추가한 vo 관련 해서 코드 추가
					board.setBref(rs.getInt("bref"));
					board.setBreLevel(rs.getInt("bre_Level"));
					board.setBreStep(rs.getInt("bre_Step"));
					selectBoradList.add(board);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return selectBoradList;
	}
//========================================================================================================================
	// 총 글 개수 by EJ
	public int getBoradCount(Connection conn) {
		int result = 0;
		String sql = "select count(*) from BOARD_R";
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(pstmt);
		}
		return result;
	}
//========================================================================================================================
	// 삭제(Delete) --> 기준은 ID * TITLE
	public int deleteBorad(Connection conn, int bno, String title) {
		int result = 0;
		String sql = "delete from BOARD_R where bno = ? and title = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			//흠....주석 테스트
			pstmt.setInt(1, bno);
			pstmt.setString(2, title);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
//========================================================================================================================
	// title update
	public int boardUpdate(Connection conn, String bno, String writer, String title) {
		int result = 0;
		String sql = "update BOARD_R set title = ? where bno = ? and writer = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, bno);
			pstmt.setString(3, writer);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
//========================================================================================================================
	public Board getBoard(Connection conn, int bno) {
		Board originVo = null;
		String sql = "select bno, bref, bre_level, bre_step from BOARD_R where bno = ?";
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
				originVo = new Board();
				originVo.setBno(resultSet.getInt(1));
				originVo.setBref(resultSet.getInt(2));
				originVo.setBreLevel(resultSet.getInt(3));
				originVo.setBreStep(resultSet.getInt(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(pstmt);
		}
		return originVo;
	}
//========================================================================================================================
	public int insertBorad(Connection conn, Board board) {
		int result = -1;
		// 답글은 반드시 Update를 먼저하고 Insert를 해야한다.
		String sqlUpdate = "update board_r set bre_step = bre_step + 1 where bref= ? and bre_step > ? ";
		
		// 답글을 작성할 때의 Insert문 
		String sqlInsert = "INSERT INTO  board_r (BNO, TITLE, CONTENT, WRITER, bref, bre_level, bre_step)" + 
				"VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		// SEQ_BOARD.nextval을 미리 받아와 보자(직접 하드코딩 하지 말고)
		String sqlSqlNextval = "select SEQ_BOARD.nextval from dual";
		
		ResultSet rs = null;
		int nextVal = 0; // 시퀀스는 1부터 시작(옵션 없다는 가정하에)이니까 초기값을 '0'으로 잡았다.
		
		int bref = 0;
		int bre_level = 0; // 새 원본글을 작성할 때를 대비해 '0'으로 설정
		int bre_step = 1; // 새 원본글을 작성할 때를 대비해 '1'로 설정
		
		PreparedStatement pstmt = null;
		// (board.getBno() != 0) --> 답글(답답글)을 작성하는 것이다.
		// (board.getBno() == 0) --> 새로운 원본글을 작성하는 것이다.
		
		try {
			pstmt = conn.prepareStatement(sqlSqlNextval); // SEQ_BOARD.nextval의 값을 먼저 가져온다.
			rs = pstmt.executeQuery();
			if (rs.next()) {
				nextVal = rs.getInt(1); // 없으면 (SEQ_BOARD.nextval = 0)
			}
			close(rs); close(pstmt);
			
			if (board.getBno() != 0) { // 답글(답답글)을 쓰는 상태
				bref = board.getBref();
				bre_step = board.getBreStep();
				pstmt = conn.prepareStatement(sqlUpdate); // update
				pstmt.setInt(1, bref);
				pstmt.setInt(2, bre_step);
				result = pstmt.executeUpdate();
				close(pstmt);
				
				// insert문 때문에 증가
				bre_level = board.getBreLevel() + 1; // 레벨증가
				bre_step++;
			}	
				//close 했으니 다시 연다.
				pstmt = conn.prepareStatement(sqlInsert); // insert
				if (board.getBno() != 0) {
					pstmt.setInt(5, bref); // 답글(답답글)쓰기
				} else {
					pstmt.setInt(5, nextVal); // 새글 쓰기
				}
				pstmt.setInt(1, nextVal);
				pstmt.setString(2, board.getTitle());
				pstmt.setString(3, board.getContent());
				pstmt.setString(4, board.getWriter());
				pstmt.setInt(6, bre_level);
				pstmt.setInt(7, bre_step);
				result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			// 위의 코드의 if (rs.next()) { nextVal = rs.getInt(1); } 부분에서
			// 오류가 발생하면 catch 다음에 바로 이곳으로 오기에 정상적으로 close(rs)을 수행할 수 없다.
			// 따라서 close(rs) 부분을 여기서도 작성해 NullPointException을 방지한다.
			close(pstmt);
		}
		return result;
	}
//========================================================================================================================
	// 글 세부사항 출력
	public Board selectDetailBoard(Connection conn, int bno) {
		Board board = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select title, content, create_Date, writer, delete_yn, bno, bref, bre_level, bre_step from BOARD_R where bno = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				board = new Board();
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setCreateDate(rs.getDate("create_Date"));
				board.setWriter(rs.getString("writer"));
				board.setDeleteYn(rs.getString("delete_Yn"));
				board.setBno(rs.getInt("bno"));
				board.setBref(rs.getInt("bref"));
				board.setBreLevel(rs.getInt("bre_Level"));
				board.setBreStep(rs.getInt("bre_Step"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return board;
	}
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
}