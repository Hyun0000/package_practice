package kh.my.board.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardWriteViewServlet
 */
@WebServlet("/boardWriteview")
public class BoardWriteViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteViewServlet() {
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
		
//		String bnoStr = request.getParameter("bno");
//		request.setAttribute("bno", bnoStr);
		// 여기서 안 꺼낼 것이다. 꺼낸걸 다시 담을 필요가 없다.
		
		// URL에 boardWriteview가 들어오면 "boardwrite.jsp" 파일로 나한테 들어왔던 모든 request와 response를 가지고 이동해줘
		// (forward 방식으로)
		// 그러면 "boardwrite.jsp"로 이동하게 되고 화면이 띄어진다.
		request.getRequestDispatcher("boardwrite.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
