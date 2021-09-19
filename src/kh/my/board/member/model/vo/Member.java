package kh.my.board.member.model.vo;

import java.sql.Date;

// member.model.vo; 게시판 중에서 회원관리에 대한 모델이고 vo이다.

// 간지나게 vo는 생략
public class Member {
//	  MEMBER_ID VARCHAR2(30) PRIMARY KEY, 
//	  MEMBER_PWD VARCHAR2(30) NOT NULL,
//	  MEMBER_NAME VARCHAR2(30) NOT NULL,
//	  GENDER char(1) CHECK(GENDER IN('M','F')),
//	  EMAIL VARCHAR2(50),
//	  PHONE VARCHAR2(30),
//	  ADDRESS VARCHAR2(100),
//	  AGE NUMBER,
//	  ENROLL_DATE DATE DEFAULT SYSDATE -- 가입일
	private String memberId;
	private String memberPwd;
	private String memberName;
	private char gender;
	// gender는 char(1)이다. 이때는 char로 자료형을 설정한다. --> VO도 char이고 DB도 char인 경우
	// (String이라고 해도 상관은 없다.)
	// char(2) 이상이면 그냥 String을 사용한다.
	private String email;
	private String phone;
	private String address;
	private int age;
	private Date enrollDate;
	private int point;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	public String getMember_id() {
		return memberId;
	}

	public void setMember_id(String member_id) {
		this.memberId = member_id;
	}

	public String getMember_pwd() {
		return memberPwd;
	}

	public void setMember_pwd(String member_pwd) {
		this.memberPwd = member_pwd;
	}

	public String getMember_name() {
		return memberName;
	}

	public void setMember_name(String member_name) {
		this.memberName = member_name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getEnroll_date() {
		return enrollDate;
	}

	public void setEnroll_date(Date enroll_date) {
		this.enrollDate = enroll_date;
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + memberId + ", member_pwd=" + memberPwd + ", member_name=" + memberName
				+ ", gender=" + gender + ", email=" + email + ", phone=" + phone + ", address=" + address + ", age="
				+ age + ", enroll_date=" + enrollDate + ", point=" + point + "]";
	}
	public Member(String member_id, String member_pwd, String member_name, char gender, String email, String phone,
			String address, int age, Date enroll_date) {
		super();
		this.memberId = member_id;
		this.memberPwd = member_pwd;
		this.memberName = member_name;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.age = age;
		this.enrollDate = enroll_date;
	}
	// enroll_date는 뺴도 된다.
}
