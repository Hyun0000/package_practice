package kh.my.board.member.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import static kh.my.board.common.JDBCTemplate.*;
import kh.my.board.member.model.dao.MemberDao;
import kh.my.board.member.model.vo.Member;

// controller(servlet)과 DAO 사이의 메소드 작성
public class MemberService {
//=======================================================================================================================
	public MemberService() {
		// TODO Auto-generated constructor stub
	}
//=======================================================================================================================
	public ArrayList<Member> searchAllMember() {
		Connection conn = getConnection();
		ArrayList<Member> searchAllMember = new MemberDao().searchAllMember(conn);
		close(conn);
		return searchAllMember;
	}
//=======================================================================================================================
	public ArrayList<Member> searchMember(String member_name) {
		Connection conn = getConnection();
		ArrayList<Member> searchMember = new MemberDao().searchMember(conn, member_name);
		close(conn);
		return searchMember;
	}
//=======================================================================================================================
	public int insertMember(Member member) {

		int result = -1; int result2 = -1;
		
		Connection conn = getConnection();
		setAutoCommit(conn, false);
		// false --> AutoCommit off
		// true --> AutoCommit on
		
		// 1단계 : ID중복 체크
		// 1단계에서의 result값 정리
		// result = 2 ID중복 (기존 회원 존재), 해당 값 Controller로 return
		// result = 0 ID중복 없음, 회원가입 2단계로 진행
		// result = -1 오류 발생, 해당 값 Controller로 return
		result = new MemberDao().checkDuplicatedMember(conn, member);
		
		if (result == 0) {
			// 2단계 : 회원 가입 시도
			/*
			 * result값 정리
			 * result = -1; 오류발생
			 * result = 0; 회원가입 실패, ID중복도 없고 SQL문도 정상수행 됐지만 다른 문제로 인해 insert문이 수행되지 않았다.
			 * result = 1 회원가입 성공
			 * result = 2 중복 ID존재 
			 */
			result = new MemberDao().insertMember(conn, member);
			
			// 회원가입시 이벤트로 point 500를 준다.
			result2 = new MemberDao().updatePointMember(conn, member.getMember_id(), 500);
			
			// Service에서 수행하는 트랙잭션 기능 구현 예시(commit, rollBack 모두 포함)
			// 모든 DML작업 --> 회원가입 때의 insert 작업 / 포인트 증가의 update 작업
			// 모든 작업이 끝나면 commit을 해주는 코드가 아래의 코드이다.
			// 이때 [ setAutoCommit(conn, false); ] 상태여야 한다.(AutoCommit off)
			if (result > 0 && result2 > 0) {
					commit(conn);
				} else {
					rollBack(conn);
					// 모든 DML 작업을 없는 상태로 만들어준다.
					// 한 작업 단위인 insert와 update 작업중에서 하나의 작업이라도 잘못 되면 
					// 전체 작업이 모두 rollBack 되는 것이다. 반대로 모든 작업이 잘되면 commit으로 마무리된다.
					// 선제조건 --> setAutoCommit(conn, false); --> AutoCommit 기능이 꺼져있어야한다.
					// 만약 AutoCommit이 on상태이면 insert 작업후 자동 commit이 되어 회원가입 작업이 완료돼버린다.
					
					// DML문 하나만 이용하는 작업 단위는 위와 같은 트랜잭션 관련 코드가 필요없다.
					// 어차피 오류가 일어나면 반영이 안 되니까 rollBack을 따로 설정할 필요가 없다.
					// 또한 close(conn)될때 자동으로 commit이 된다.
					// 2개 이상의 DML문 작업부터 commit & rollBack 작업을 하면 된다.
				}
		} 
		close(conn);
		return result;
		// cf) 0xFF --> 가장 큰 수를 의미  / 0xFFFFFFFF (이게 가장 큰수)
	}
//=======================================================================================================================
	public int deleteMember(String member_id) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, member_id);
		close(conn);
		return result;
	}
//=======================================================================================================================
	public int updateMemberAge(String member_id) {
		int result = -1;
		Connection conn = getConnection();
		result = new MemberDao().updateMemberAge(conn, member_id, 1);
		close(conn);
		return result;
	}
//=======================================================================================================================
	// 멤버 Login
	public int loginMember(String memberId, String memberPwd) {
		Connection conn = getConnection();
		int result = new MemberDao().loginMember(conn, memberId, memberPwd);
		close(conn);
		return result;
	}
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
}