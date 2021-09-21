package kh.my.board.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;
import kh.my.board.common.Command;

public class BoardListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final int PAGE_SIZE = 5; // 한 페이지 당 보여질 글 수
		final int PAGE_BLOCK = 3; // 한 화면에 나타날 페이지 링크 수
		int bCount = 0 ; // 총 글 개수
		int pageCount = 0; // 총 페이지 수
		int startPage = 1; // 화면에 나타날 시작 페이지 링크
		int endPage = 1; // 화면에 나타날 마지막 페이지 링크
		int startRnum = 1; // 화면에 나타날 글 목록 중 처음
		int endRnum = 1; // 화면에 나타날 글 목록 중 마지막
		int currentPage = 1; // 애초에 아무것도 안 누르면 1페이지 부터 보여준다.
		
		String pageNum = request.getParameter("pagenum"); 
		
		if (pageNum != null) { // true --> 사용자가 누른 페이지가 있음
			currentPage = Integer.parseInt(pageNum); //눌려진 페이지
		}
		
		bCount = new BoardService().getBoradCount();
		
		pageCount = bCount / PAGE_SIZE;
		pageCount = (bCount / PAGE_SIZE == 0) ? pageCount : pageCount + 1; 
		
		startRnum = (currentPage - 1) * PAGE_SIZE + 1; // 등차수열 적용

		endRnum = startRnum + PAGE_SIZE -1 ;
		
		if (endRnum > bCount) { endRnum = bCount; }
		
		if (startRnum < 1) { startRnum = 1; }
		
		ArrayList<Board> selectBoradList = new BoardService().selectBoradList(startRnum, endRnum);
		
		if(currentPage % PAGE_BLOCK == 0) {
			startPage = (currentPage / PAGE_BLOCK - 1) * PAGE_BLOCK + 1;
		} else {
			startPage = (currentPage / PAGE_BLOCK) * PAGE_BLOCK + 1;
		}
		
		endPage = startPage + PAGE_BLOCK - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		request.setAttribute("boardList", selectBoradList);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
	}
}