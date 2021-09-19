package kh.my.board.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;

/**
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/BoardDelete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String bnoStr = request.getParameter("bno");
		int bno = Integer.parseInt(bnoStr);
		String title = request.getParameter("title");
		
		
		int result = new BoardService().deleteBoard(bno, title);
		
		request.setAttribute("deleteResult", result);
		request.getRequestDispatcher("/deleteboard.jsp").forward(request, response);
		
		/*
		 * result = 1 정상 삭제
		 * result = 0 정상 but 지워진게 없다.
		 * result = -1 오류
		 */
//		if (result == 1) {
//			out.print("삭제 완료");
//		} else if (result == 0) {
//			out.print("정상이긴하나 삭제한게 없다.");
//		} else {
//			out.print("오류 발생");
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
