package kh.my.board.board.model.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import kh.my.board.board.model.dao.BoardDao;
import kh.my.board.board.model.vo.Board;
import static kh.my.board.common.JDBCTemplate.*;

public class BoardService {
//=======================================================================================================================
	public ArrayList<Board> selectBoradList() {
		ArrayList<Board> selectBoradList = null;
		Connection conn = getConnection();
		selectBoradList = new BoardDao().selectBoradList(conn);
		close(conn);
		return selectBoradList;
	}
//========================================================================================================================
	// 페이징 처리(오버로딩)
	public ArrayList<Board> selectBoradList(int start, int end) {
		ArrayList<Board> selectBoradList = null;
		Connection conn = getConnection();
		selectBoradList = new BoardDao().selectBoradList(conn, start, end);
		close(conn);
		return selectBoradList;
	}
//========================================================================================================================
	public int insertBoard(Board board) {
		int result = -1;
		Connection conn = getConnection();
		result = new BoardDao().insertBorad(conn, board);
		close(conn);
		return result;
		// 한개짜리 DML 작업이기에 커밋 롤백 신경쓸 필요없다.
		// 잘 되면 결과 출력
		// 안 되면 오류 출력되고 땡
	}
//========================================================================================================================
	// 총 글 개수
	public int getBoradCount() {
		int totalCount = 0;
		Connection conn = getConnection();
		totalCount = new BoardDao().getBoradCount(conn);
		close(conn);
		return totalCount;
	}
//========================================================================================================================
	// 삭제
	public int deleteBoard(int bno, String title) {
		int result = 0;
		Connection conn = getConnection();
		result = new BoardDao().deleteBorad(conn, bno, title);
		close(conn);
		return result;
	}
//========================================================================================================================
	// title update
	public int boardUpdate(String bno, String writer, String title) {
		int result = 0; // 오류난 상황을 사용자에게 알리고 싶지 않아서
		Connection conn = getConnection();
		result = new BoardDao().boardUpdate(conn, bno, writer, title);
		close(conn);
		return result;
	}
//========================================================================================================================
	public Board getBoard(int bno) {
		Board originVo = null;
		Connection conn = getConnection();
		originVo = new BoardDao().getBoard(conn, bno);
		close(conn);
		return originVo;
	}
//========================================================================================================================
	// 글 세부사항 출력
	public Board selectDetailBoard(int bno) {
		Board board = new Board();
		Connection conn = getConnection();
		board = new BoardDao().selectDetailBoard(conn, bno);
		close(conn);
		return board;
	}
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
//========================================================================================================================
}
