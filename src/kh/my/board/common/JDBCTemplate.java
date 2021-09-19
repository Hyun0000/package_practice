package kh.my.board.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBCTemplate {
	private JDBCTemplate() {}
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/mylocaloracle");
			
			conn = ds.getConnection();
			
			if (conn != null) { System.out.println("DBCP JNDI 연결성공"); }
			else { System.out.println("DBCP JNDI 연결성공"); } 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
//============================================================================================================
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//=======================================================================================================================	
	// PreparedStatement도 여기에 넣을 수 있다. (by 다형성)
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//=======================================================================================================================
	public static void close(ResultSet rest) {
		try {
			if (rest != null && !rest.isClosed()) {
			// 위의 과정을 거치지 않으면 널포인트Exception을 발생시킨다.
				rest.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//============================================================================================================
	//commit과 rollBack 또한 Connection을 이용한다.
	public static void commit(Connection conn) {
		try { // DB 에서 일어나는 트랜잭션 커밋 처리
			if (conn != null && !conn.isClosed())
				conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//============================================================================================================
	public static void rollBack(Connection conn) {
		try { // DB 에서 일어나는 트랜잭션 커밋 처리
			if (conn != null && !conn.isClosed())
				conn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//============================================================================================================
	public static void setAutoCommit(Connection conn, boolean onOff) {
		try {
			conn.setAutoCommit(onOff);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
