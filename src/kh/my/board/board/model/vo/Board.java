package kh.my.board.board.model.vo;

import java.sql.Date;

public class Board {

//	CREATE TABLE BOARD_R(
//		    BNO(원본 글 번호)	 NUMBER 		PRIMARY KEY,
//		    TITLE 		 	 VARCHAR2(50)	NOT NULL,
//		    CONTENT	 	 	 VARCHAR2(400)	NOT NULL,
//		    CREATE_DATE	 	 timestamp		DEFAULT SYSDATE,
//		    WRITER 	 		 VARCHAR2(20),
//		    DELETE_YN 	 	 CHAR(2) 		DEFAULT 'N',
//		    BREF 			 NUMBER,
//		    BRE_LEVEL 		 NUMBER,
//		    BRE_STEP 	 	 NUMBER,
//
//		    FOREIGN KEY (WRITER) REFERENCES MEMBER(MEMBER_ID),
//		    CHECK(DELETE_YN IN('Y','N'))
//		);

	private int bno;
	private String title;
	private String content;
	private Date createDate;
	private String writer;
	private String deleteYn;
	private String pw;
	private	int bref; // 몇 번 덩어리의 글이냐
	private int breLevel; // 원본 0, 답글 1, 답답글 2
	private int breStep; // 화면에 보여질 순서. 기준 원본글 1...
	
	public Board(int bno, String title, String content, String writer, int bref, int breLevel, int breStep) {
		super();
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.bref = bref;
		this.breLevel = breLevel;
		this.breStep = breStep;
	}
	
	public Board(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}
	
	public Board() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Board [bno=" + bno + ", title=" + title + ", content=" + content + ", createDate=" + createDate
				+ ", writer=" + writer + ", deleteYn=" + deleteYn + ", pw=" + pw + ", bref=" + bref + ", breLevel="
				+ breLevel + ", breStep=" + breStep + "]";
	}
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getBref() {
		return bref;
	}
	public void setBref(int bref) {
		this.bref = bref;
	}
	public int getBreLevel() {
		return breLevel;
	}
	public void setBreLevel(int breLevel) {
		this.breLevel = breLevel;
	}
	public int getBreStep() {
		return breStep;
	}
	public void setBreStep(int breStep) {
		this.breStep = breStep;
	}}
