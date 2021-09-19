package kh.my.board.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.member.model.Service.MemberService;
import kh.my.board.member.model.vo.Member;

/**
 * Servlet implementation class EnrollMemberServlet
 */
@WebServlet("/enroll") //회원가입
public class EnrollMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EnrollMemberServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8"); // 내용이 text이고 포맷이 html이다.
		response.setCharacterEncoding("UTF-8");
	    /*
	     * 회원가입 시나리오
	     * 1. 입력받은 ID가 기존 회원의 ID와 중복인지 확인 --> DB한번 갔다와야한다.
	     * 이때 사용할 수 있는 SQL문은 아래와 같다.
	     * 
	     * select count(*) from member where member_id = ?
	     * - 3순위 : 제일 느리다. 일일이 다 헤아려야하기 때문이다.
	     * 
	     * select * from member where member_id = ?
	     * - 2순위
	     * 
	     * select member_id	from member where member_id = ?
	     * - 1순위 : 가장 빠르다.(가장 적은양을 전달)
	     * 
	     * 2. 입력받은 값들로 회원가입
	     * insert into member values (?, ?, ?, ?, ?, ?, ?, ?, sysdate);
	     */
		PrintWriter out = response.getWriter();
		
//		LocalDate onlyDate = LocalDate.now();
//		Date d = Date.valueOf(onlyDate);
		
		// html(화면)로 부터 넘어온 데이터(사용자 입력 데이터)
		String member_id = "user17"; 
		String member_pwd = "pass17";
		String member_name = "흑곰";
		char gender = 'M'; // char는 작은 따옴표 
		String email = "hoon_gd@kh.or.kr";
		String phone = "010-5555-6666";
		String address = "짱구네 문열고 왼쪽 흰둥이집";
		int age = 20;
		// Date enroll_date;
		// enroll_date는 사용자가 직접 입력하는 값이 아니므로 아예 변수로 둘 필요가 없다.
		
		// 화면 데이터를 member에 싣기
		Member member = new Member(member_id, member_pwd, member_name, gender, email, phone, address, age, null);
		
		// member를 가지고 회원가입하러 dao로 출발
		int result = new MemberService().insertMember(member);
		
		/*
		 * result값 정리
		 * result = -1; 오류발생
		 * result = 0; 회원가입 실패, ID중복도 없고 SQL문도 정상수행 됐지만 다른 문제로 인해 insert문이 수행되지 않았다.
		 * result = 1 회원가입 성공
		 * result = 2 중복 ID존재 
		 */
		if (result == 1) {
			out.print(member_id + " 회원가입 완료");
		} else if (result == 2) {
			out.print("기존 ID 존재");
		} else { // 오류 발생 = -1 및 가입 실패 = 0, 그 외 경우
			out.print("예기치 못한 오류 발생, 다시 시도해 주세요");
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
