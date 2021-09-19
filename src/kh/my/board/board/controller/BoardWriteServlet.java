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
 * Servlet implementation class BoardService
 */
@WebServlet("/BoardWrite")
// 이런 파일은 없지만 url을 이렇게 정해 해당파일로 오게 한 것이다.
public class BoardWriteServlet extends HttpServlet {
	static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardWriteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 사용자가 입력한 값이 request에 실려서 이곳으로 오게된다.
		// 화면입력 전달되어 옴 by request --> parameter (==변수명)에 실어서 받아온다.
		// title라는 파라미터면 거기에는 타이틀이 , content면 그 안에는 콘텐트가 있을것이다.

		PrintWriter out = response.getWriter();

		// WAS가 받아온 파라미터를 꺼내보자
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		String writer = (String)request.getSession().getAttribute("memberLoginInfo");
		// 멤버가 로그인을 하면 writer 부분은 알아서 등록된다. by 세션(Session)
		// 즉, 멤버의 아이디(writer명)를 세션(Session)에서 꺼내오는 것이다.
		// ("memberLoginInfo"라는 이름을 읽어서 거기에 들어있는 값을 가져오는 것이다.)
		// 이것이 가능한 이유는 세션의 기능도 있지만 Board table에서 FK인 WRITER column이 참조하는 PK가
		// 바로 Member table의 MEMBER_ID column이다.
		// FOREIGN KEY (WRITER) REFERENCES MEMBER(MEMBER_ID)
		// 단, 브라우저 창을 싹다 닫아버리면 세션 정보는 사라진다.

		// getAttribute() method는 Object가 반환(return) 타입이다.
		// 따라사 맨 마지막 method가 반환하는게 진짜배기이므로 얘를 기준으로 형변환을 해야한다.
		// String writer이므로 자료형을 맞추기 위해 (String)을 앞에 두어 형변환을 해야한다.(다운 캐스팅)
		
		// 로그인 세션 정보가 없을때 사용할 임시 코드
		if (writer == null) {
			writer = "user01";
		}
		out.print("title : " + title + "<br>");
		out.print("content : " + content);

		Board board = new Board(title, content, writer);
		int result = new BoardService().insertBoard(board);

		if (result == 0) {
			out.print("<br>게시글이 등록되지 않았습니다. 작성한 글에 비속어가 포함돼 있습니다. 다시 작성해주세요");
		} else {
			out.print("<br>게시글이 등록됐다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
