package kh.my.board.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import kh.my.board.member.model.vo.Member;
import static kh.my.board.common.JDBCTemplate.*;


public class MemberDao {
	// CRUD 메소드 작성 --> 딜리트 업데이트
//=======================================================================================================================
	// 전체 member 조회
	// select
	public ArrayList<Member> searchAllMember(Connection conn) {
		ArrayList<Member> searchAllMember = null; // 문제 없어
		String sql = "select * from MEMBER";
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				searchAllMember = new ArrayList<Member>();  // 문제 없어
				// 이미 rs 한 줄을 읽었으니까 do-while문으로 실행한다.
				// while로 하면 또 (rs.next()) 부터 비교해야 하니까 
				do {
					Member member = new Member();
					member.setMember_id(rs.getString("member_id"));
					member.setMember_pwd(rs.getString("member_pwd"));
					member.setMember_name(rs.getString("member_name"));
					member.setGender(rs.getString("gender").charAt(0));
					// getChar() method는 없다.
					// CharacterStream은 있지만 어쨌든 한 글자만 읽는것은 없다.
					// charAt()을 이용해 String  -->  Character로 변환한다.
					member.setEmail(rs.getString("email"));
					member.setPhone(rs.getString("phone"));
					member.setAddress(rs.getString("address"));
					member.setAge(rs.getInt("age"));
					member.setEnroll_date(rs.getDate("enroll_date"));
					member.setPoint(rs.getInt("point")); // 이게 빠졌다.
					searchAllMember.add(member);
					// 그럼 채우는데 문제가 있겠네 
					// 지금 보니까 포인트 부분이 없다.
					// 없으니까 기본 셋팅값 0이 들어가는 것이다.
					
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				// close() 작업이 위 작업의 연장선상이라는 의미에서 finally에 담는다.
				// close()만 따로 떼서 아예 새로운 try-catch 구문에 담아도 상관은 없다.
				// 이때 참조변수 rs와 stmt는 try{ } 구문의 지역변수로 담으면 안 된다.
				// (지역변수가 되면 finally 구문에서 참조변수 rs와 stmt를 찾을 수 없다.)
				
				// 이럴거면 그냥 try{ } 구문안에 close()를 담으면 되지 않냐?
				// close() 전에 있는 작업중에서 Exception이 발생하면 close()가 정상적으로 수행되지 않기에  
				// finally 구문으로 따로 뺀 것이다.
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return searchAllMember;
	}
//=======================================================================================================================
	// 이름으로 검색해 특정 member 조회
	// select search
	public ArrayList<Member> searchMember(Connection conn, String member_name){
		String sql = "select * from MEMBER where MEMBER_NAME = ?";
		ArrayList<Member> searchMember = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_name);
			rs = pstmt.executeQuery();			
			
			if (rs.next()) {
				searchMember = new ArrayList<Member>();
				do {
					Member member = new Member();
					member.setMember_id(rs.getString("member_id"));
					member.setMember_pwd(rs.getString("member_pwd"));
					member.setMember_name(rs.getString("member_name"));
					member.setGender(rs.getString("gender").charAt(0));
					member.setEmail(rs.getString("email"));
					member.setPhone(rs.getString("phone"));
					member.setAddress(rs.getString("address"));
					member.setAge(rs.getInt("age"));
					member.setEnroll_date(rs.getDate("enroll_date"));
					member.setPoint(rs.getInt("point")); // 이게 빠졌다.
					searchMember.add(member);
				} while (rs.next());
			}
			rs.close(); pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return searchMember;
	}
//=======================================================================================================================
	// Dao의 method는 보통 하나의 method가 하나의 SQL문만 수행한다.
	// (보통이라는 것이지 무조건 이래야 한다는 것은 아니다.)
	// 하나의 작업이지만 Dao에서 method를 기능별로 분리 했다면 Service에서도 똑같이 
	// 나누어서 각각의 작업을 수행해야 한다.
	
	// 회원가입 1단계
	// ID 중복체크
	public int checkDuplicatedMember(Connection conn, Member member) {
		int result = -1;
		String sql = "select member_id from member where member_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMember_id());
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				result = 2; //기존 회원 있음
			} else {
				result = 0; //기존 회원 없음
			}
		} catch (Exception e) {
			e.printStackTrace(); // result = -1
		} finally {
			// 주구장창 finally에서 try-catch로 묶는게 귀찮으니 JdbcTemplate 이용
			close(rset);
			close(pstmt);
		}
		return result;
	}
//=======================================================================================================================
	// 회원가입 2단계
	// 회원 가입하기
	public int insertMember(Connection conn, Member member) {
		int result = -1;
		String sql= "insert into"
				// 앞에 공백이 반드시 있어야한다. 줄 바꿈할때 공백 체크 중요
				+ " member"
				+ " (Member_id, Member_pwd, Member_name, Gender, Email, Phone, Address, Age, enroll_date) "
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		// default로 셋팅한 값이 insert때 들어가게 하려면
		// insert문 작성시 넣을 데이터값만이 아니라 컬럼명까지 모두 작성해야한다.
		// 단, 기본값이 들어갈 컬럼은 컬렴명 & value 부분을 작성하지 않는다.
		// (위의 SQL문에서는 point의 컬럼명과 값을 작성하지 않았다.)
		
		PreparedStatement pstmt = null;
		
		try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMember_id());
				pstmt.setString(2, member.getMember_pwd());
				pstmt.setString(3, member.getMember_name());
				pstmt.setString(4, String.valueOf(member.getGender()));
				// pstmt.setString(4, (String)member.getGender()); --> 이게 아니다.
				// 위와 같은 형변환은 상속관계, interface, 기본자료형인 경우에 가능하다.
				// String은 참조자료형이고 Character는 기본 자료형이다.
				// (String과 Character는 아무 관계도 없다.)
				
				// DBMS에서 char와 varchar2는 모두 똑같은 문자(String)이다.
				// 쉽게 말해 DB는 char와 String을 개별적으로 가리는게 아니라 다 똑같은 문자로 취급한다는 것이다.
				// (고정된 공간을 차지하냐 안 하냐의 차이는 있다.)
				pstmt.setString(5, member.getEmail());
				pstmt.setString(6, member.getPhone());
				pstmt.setString(7, member.getAddress());
				pstmt.setInt(8, member.getAge());
				
				result = pstmt.executeUpdate();
				// 회원가입 성공 result = 1
		} catch (Exception e) {
			e.printStackTrace(); // result = -1
		} finally {
			close(pstmt);
		}
		return result;
	}
//=======================================================================================================================
	// 포인트
	// 회원가입시 이벤트로 point 500를 준다.
	public int updatePointMember(Connection conn, String member_id, int point/*증감하고 싶은 포인트*/) {
		int result = -1;
		String sql= "update member set point = point + ? where member_id = ?";
		// 포인트의 기본값을 null로 설정하면 (+500)이 계산되지 않는다.
		// 이런 오류를 방지하기 위해
		// 1. NVL() 함수 사용
		// 2. point column의 default 값을 '0'으로 설정한다. & not null 제약조건 설정
		// 이때
		// 데이터가 이미 point column에 들어있는 상태이다.
		// 또한 그 중에 이미 null값이 들어가 있으므로 not null 조건을 추가할 수 없다.
		// 따라서 null값을 변경 한 후(by update) default값을 설정한다.
		
		// 1. UPDATE MEMBER SET POINT = 0 WHERE POINT IS NULL;
		// 2. ALTER TABLE MEMBER ADD POINT NUMBER DEFAULT 0;
		// 3. ALTER TABLE MEMBER MODIFY POINT NOT NULL;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setString(2, member_id);
			
			result = pstmt.executeUpdate(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
//=======================================================================================================================
	public int deleteMember(Connection conn, String member_id) {
		int result = -1;
		String sql = "delete from MEMBER where member_id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
//=======================================================================================================================
	public int updateMemberAge(Connection conn, String member_id, int age) {
		int result = -1;
		String sql= "update member set age = age + ? where member_id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setString(2, member_id);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
//=======================================================================================================================
	public int loginMember(Connection conn, String memberId, String memberPwd) {
		// result = 0 로그인 실패 혹은 오류 / result = 1 로그인 성공
		int result = 0;
		String sql= "select MEMBER_ID from member where MEMBER_ID = ? and MEMBER_PWD = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = 1; // result = 1 로그인 성공
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
//=======================================================================================================================
}
