package kh.my.board.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;
import kh.my.board.common.Command;

public class BoardWriteCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Board originVo = null; // 원본글에 대한 정보를 가져온다.
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = (String)request.getSession().getAttribute("memberLoginInfo");
		if (writer == null || writer.equals("")) { writer = "user01"; } // 로그인이 안 됐을때를 대비해 기본 계정 설정
		
		String bnoStr = request.getParameter("bno"); // 현재 보고 있는 글의 번호
		int bno = 0;
		
		if (bnoStr == null || bnoStr.equals("")) {
			originVo = new Board();
		} else {
			try { bno = Integer.parseInt(bnoStr); } catch (Exception e) { e.printStackTrace(); }
			originVo = new BoardService().getBoard(bno);
		}
		
		Board board = new Board(originVo.getBno(), title, content, writer, originVo.getBref(), originVo.getBreLevel(), originVo.getBreStep());
		int result = new BoardService().insertBoard(board);
	}
}
