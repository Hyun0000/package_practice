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
 * Servlet implementation class UpdateMemberAgeServlet
 */
@WebServlet("/UpdateMemberAge")
public class UpdateMemberAgeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberAgeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8"); // 내용이 text이고 포맷이 html이다.
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String member_id = "user07";
		
		int result = new MemberService().updateMemberAge(member_id);
		
		if (result == 1) {
			out.print("떡국 한 그릇 추가요");
		} else if (result == 0) {
			out.print("SQL문은 정상이나 update되지 않았습니다.");
		} else if (result == -1) {
			out.print("오류 발생");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
