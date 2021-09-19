package kh.my.board.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;

/**
 * Servlet implementation class BoardRewriteServelt
 */
@WebServlet("/boardrewrite")
public class BoardRewriteServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardRewriteServelt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		
		Board originVo = null; // 원본글에 대한 정보를 가져온다.
		// 답글은 원본글이 존재해야 달 수 있는 것이다.
		// 따라서 답글을 작성하기 전에 답글을 달기위한 원본글의 정보를 알아야한다.
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = (String)request.getSession().getAttribute("memberLoginInfo");
		if (writer == null || writer.equals("")) { writer = "user01"; } // 로그인이 안 됐을때를 대비해 기본 계정 설정
		
		String bnoStr = request.getParameter("bno"); // 현재 보고 있는 글의 번호
		int bno = 0;
		
		if (bnoStr == null || bnoStr.equals("")) {
			originVo = new Board();
			// 현재 보고 있는 글이 없는데 글을 작성하려고 한다. --> 새로운 글(원본글)을 작성하기 위한 것으로 판단
			// 아예 새로운 글(원본글)에 대한 정보를 넣을 수 있는 비어 있는 Board 객체를 주는 것이다.
		} else {
			try { bno = Integer.parseInt(bnoStr); } catch (Exception e) { e.printStackTrace(); }
			originVo = new BoardService().getBoard(bno);
			// 답글(답답글)을 달려고 하는 원본글의 정보를 가져온다. --> 정보 = (bno, bref, bre_level, bre_step)
		}
		
		
//		out.print("title : " + title + "<br>");
//		out.print("content : " + content);
		
		// originVo = new BoardService().getBoard(bno);를 통해
		// 원본글에 대한 (bno, bref, bre_level, bre_step) 정보를 가져왔다.
		// 이때 originVo.getBno() = 0 --> bno = 0 --> 새로운 원본글을 작성하겠다는 얘기
		
		// 원본글이 됐던 답글이 됐던 새로운 글을 작성하기 위한 정보를 Controller에 넘긴다.
		// 여기서 눈여겨봐야 할 점은
		// BoardWriteServlet에서는 아예 새로운 원본글을 작성하는 것이 당연하기에 아래의 정보만 Controller에 넘기면 된다.
		// Board board = new Board(title, content, writer);
		
		// 그러나 BoardRewriteServelt에서는 새로운 원본글을 작성할 수도 있지만 그것이 아닌
		// 답글(답답글)을 작성할 경우도 있기에 (bno, bref, bre_level, bre_step)에 대한 정보도 같이 넘기는 것이다.
		Board board = new Board(originVo.getBno(), title, content, writer, originVo.getBref(), originVo.getBreLevel(), originVo.getBreStep());
		int result = new BoardService().insertBoard(board);

//		if (result == 0) {
//			out.print("<br>게시글이 등록되지 않았습니다. 작성한 글에 비속어가 포함돼 있습니다. 다시 작성해주세요");
//		} else {
//			out.print("<br>게시글이 등록됐다.");
//		}
		
		// 글을 쓴후 다시 리스트화면으로 이동하고 싶을때는 어떻게 해야할까?
//		request.getRequestDispatcher("BoardList").forward(request, response);
		
		//URL에 이상하게 보여지지 않게
		response.sendRedirect("BoardList");
		// 데이터를 들고가지는 못한다.
		// 페이지를 이동시켜주는 역할만한다.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
