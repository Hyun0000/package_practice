package kh.my.board.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;

/**
 * Servlet implementation class SelectDetailBoardServlet
 */
@WebServlet("/boardcontent")
public class BoardContentViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardContentViewServlet() {
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
		
		String bnoStr = request.getParameter("bno");
		int bno = 0;
		if (bnoStr != null && bnoStr != "") {
			bno = Integer.parseInt(bnoStr);
		}
		
		// bno를 가지고 DB에서 해당하는 글의 상세 정보를 읽어 나와야한다.
		// bno는 pk이므로 어차피 결과는 Board 모양 한 개이다.
		Board board = new BoardService().selectDetailBoard(bno);
		
		request.setAttribute("DetailBoard", board);
		request.getRequestDispatcher("detailboard.jsp").forward(request, response);
		// 정보를 들고 전달&URL의 변화를 없게 해줘야하니까 redirect가 아니라 forward를 이용한다.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
