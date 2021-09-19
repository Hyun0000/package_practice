package kh.my.board.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kh.my.board.member.model.Service.MemberService;

/**
 * Servlet implementation class LoginMemberServlet
 */
@WebServlet("/LoginMember")
public class LoginMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginMemberServlet() {
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
		
		String memberId = request.getParameter("id");
		String memberPwd = request.getParameter("pw");
		// LoginMember?id=user01&pw=pass01
		
		int result = new MemberService().loginMember(memberId, memberPwd);
		
		// result = 0 로그인 실패 혹은 오류 / result = 1 로그인 성공
		if (result == 1) {
			// 로그인이 됐으니 세션을 만들어보자.(세션에 만들어 넣는 작업)
			request.getSession().setAttribute("memberLoginInfo", memberId);
			// 세션에 저장할때도 이름을 정해서 저장한다.
			// "memberLoginInfo"가 Session의 Attribute의 이름인 것이다.
			// 현재 "memberLoginInfo"라는 이름에 memberId 값이 들어있는 상태가 된 것이다.
			// 설정한 Session 정보를 글 등록 Servelt에서 이용해보자
			// (로그인을 했으니까 게시판에 글을 쓸 수 있는것이다.)
			out.println(memberId + "님이 로그인하셨습니다. 게시판 이용이 가능합니다.");
		} else {
			out.println("아이디 또는 패스워드가 잘못입력되었습니다. 다시 확인해 주세요.");
		}
		
		response.sendRedirect("BoardList");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
