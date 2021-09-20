package kh.my.board.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;
import kh.my.board.common.Command;

public class BoardContentViewCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bnoStr = request.getParameter("bno");
		int bno = 0;
		if (bnoStr != null && bnoStr != "") {
			bno = Integer.parseInt(bnoStr);
		}
		Board board = new BoardService().selectDetailBoard(bno);
		
		request.setAttribute("DetailBoard", board);
	}
}
