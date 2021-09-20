package kh.my.board.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;

/**
 * Servlet implementation class BoardList
 */
@WebServlet("/BoardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
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

		final int PAGE_SIZE = 15; // 한 페이지 당 보여질 글 수
		final int PAGE_BLOCK = 3; // 한 화면에 나타날 페이지 링크 수
		// 헷갈려서 내가 작성
		out.print("한 페이지 당 보여질 글 수 : " + PAGE_SIZE + "<br>");
		out.print("한 화면에 나타날 페이지 링크 수 : " + PAGE_BLOCK + "<br>");
		
		int bCount = 0 ; // 총 글 개수
		int pageCount = 0; // 총 페이지 수
		
		// (1 2 3)으로 나올지 (4 5 6)으로 나올지
		int startPage = 1; // 화면에 나타날 시작 페이지 링크
		int endPage = 1; // 화면에 나타날 마지막 페이지 링크
		// startPage, endPage를 알기 위해서는 지금 눌려진 페이지를 알아야한다.
		
		int startRnum = 1; // 화면에 나타날 글 목록 중 처음
		int endRnum = 1; // 화면에 나타날 글 목록 중 마지막
		
		// 이전 화면에서 사용자가 몇 페이지를 누른 상태에서 넘어온 것인지 알아야 한다.
		// currentPage = 바로 이 누른 값
		int currentPage = 1; // 애초에 아무것도 안 누르면 1페이지 부터 보여준다.
		
		String pageNum = request.getParameter("pagenum"); 
		
		if (pageNum != null) { // true --> 사용자가 누른 페이지가 있음
			currentPage = Integer.parseInt(pageNum); //눌려진 페이지
			// html은 모든 데이터를 문자(String)형식으로 받으므로 Int로 형변환을 해야한다. 
			// = WAS가 숫자, 문자를 가리지 않는다는 것이다.(그냥 다 String으로 받는다.)
		}
		
		// 총 글 개수
		bCount = new BoardService().getBoradCount();
		out.print("총 글의 개수(bCount) : " + bCount + "<br>");
		
		// 총 페이지 개수 by EJ
		// % --> 나머지 값을 구하는 연산자
		// pageCount = (bCount / PAGE_SIZE) + (bCount%PAGE_SIZE == 0 ? 0 : 1);
		pageCount = bCount / PAGE_SIZE;
		pageCount = (bCount / PAGE_SIZE == 0) ? pageCount : pageCount + 1; 
		// 위의 삼항연산자가 눈에 좀 더 잘 들어온다.
		out.print("총 페이지 수(pageCount) : " + pageCount + "<br>");
		
		// 화면에 나타날 글 (Rownum 조건 계산)
		startRnum = (currentPage - 1) * PAGE_SIZE + 1; // 등차수열 적용
		// 1 6 11 16 21
		endRnum = startRnum + PAGE_SIZE -1 ;
		// 5 10 15 20 25
		
		// 마지막 페이지를 고려해서 작성
		// 66-70읽고 내가 원하는건 다음에 71-72만읽어야하는데 71-75로 읽는것을 방지하기 위해
		if (endRnum > bCount) { endRnum = bCount; }
		
		if (startRnum < 1) { startRnum = 1; }
		
		// 전체 리스트 뿌리기
		ArrayList<Board> selectBoradList = new BoardService().selectBoradList(startRnum, endRnum);
		
		// (1 2 3) (4 5 6) (7 8 9) 같은 부분 작성
		// ex) currentPage = 8이면 (7 8 9)가 나와야한다.
		// 8 / 3 * 3 + 1 --> 스타트 패이지
		// (스타트 패이지) + 3 - 1 --> 엔드 페이지
		out.print("누른 페이지(currentPage) : " + currentPage + "<br>");
		if(currentPage % PAGE_BLOCK == 0) {
			startPage = (currentPage / PAGE_BLOCK - 1) * PAGE_BLOCK + 1;
		} else {
			startPage = (currentPage / PAGE_BLOCK) * PAGE_BLOCK + 1;
			// currentPage = 2
			// startPage = (2 / 3) * 3 + 1
			// 2 + 3 = 2
		}
		
		endPage = startPage + PAGE_BLOCK - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		request.setAttribute("boardList", selectBoradList);
		// 1. Data 전달을 위해 request에 값을 Set한다.
		// setAttribute() --> request에 넣고 싶은 값이 있다면 Attribute의 이름(String)을 정한 다음에 그곳에다가 값을 넣어라.  
		// 그러면 request에 값을 실어가지고 HTTP를 통해 전달을 해주겠다.
		// (이때 값은 Object 형태이기에 모든 형태의 값을 넣을 수 있다.)
		// 단, 꺼낼 때는 넣었던 형태에 맞춰서 꺼내야한다.(형변환하라는 의미)
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.getRequestDispatcher("/boardlist.jsp").forward(request, response);
		// 2. 위의 단계에서는 Data를 request에 넣기만 한 것이다. 이제 전달을 해보자
		// getRequestDispatcher(path) --> 이동할 페이지를 적는다.
		// forward() --> forward 방식으로 ("/boardlist.jsp")로 이동할 것이다. 
		// 이때 처음 doGet() method에 들어왔던 request와 response와 selectBoradList 데이터를 가지고 함께 이동할 것이다.
//========================================================================================================================
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
