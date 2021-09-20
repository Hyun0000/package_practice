package kh.my.board.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.common.Command;

/**
 * Servlet implementation class BoardControl
 */
@WebServlet("/board/*")
// URL에 'board/~~~' 값이 들어오면 현재 servlet(페이지)으로 진입하겠다는 의미
public class BoardControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	// board 관련 페이지를 전체적으로 관리하는 method
	// URL에 'board/~~~' 값이 들어오면 아래의 method로 url 값이 들어오게 된다.
	// 따라서 기존 Servlet 파일들에 있던 URL 값에도 앞에 '/board'를 추가해야한다.
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ // 오류가 발생하면 나도 throws 하겠다.
		response.setContentType("text/html; charset = UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		// uri 값을 알려준다. --> /newmvc/board/BoardList
		// (newmvc = 현재 프로젝트의 context-root)
		// cf) URL은 'http:// ~' 부분부터 다 있는 것이다.
		String contextPath = request.getContextPath();
		// 현재 프로젝트의 context-root를 알려준다. --> /newmvc
		String com = uri.substring(contextPath.length());
		// uri값에서 contextPath를 뺀 값   by substring() method
		// /newmvc/board/BoardList - /newmvc = /board/BoardList
		// contextpath.length() 길이 다음부터의 문자만 남는다.
		
		System.out.println("uri :" + uri);
		System.out.println("contextpath :" + contextPath);
		System.out.println("com :" + com);
		// log찍기 ex)
		// uri :/newmvc/board/BoardList
		// contextpath :/newmvc
		// com :/board/BoardList
		
		String viewPage = null;
		Command cmd = null;
		
		if (com.equals("/board/BoardList")) {
			// BoardList는 DB에 갖다와야 결과물을 화면에 뿌릴 수 있으므로 DB 관련 코드가 있어야한다.
			// DB에 갖다오는 코드
			BoardListCommand boardListCommand = new BoardListCommand();
			boardListCommand.execute(request, response);
			viewPage = "/WEB-INF/board/boardlist.jsp";
			
		} else if (com.equals("/board/boardcontent")) {
			BoardContentViewCommand boardContentViewCommand = new BoardContentViewCommand();
			boardContentViewCommand.execute(request, response);
			viewPage = "/WEB-INF/board/detailboard.jsp";
			
		} else if (com.equals("/board/applpe")) {
			viewPage = "/WEB-INF/board/template.jsp";
		}

		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}