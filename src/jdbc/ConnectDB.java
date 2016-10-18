package jdbc;
import java.sql.*;
import configurations.*;
public class ConnectDB {  
	private String dbDriver = Configuration.DBDriver;
	private String url =Configuration.URL;
	private String userName =Configuration.USERName; 
	private String password = Configuration.PASSWORD; 
	private Connection con = null; 
	
	//建立连接
	public ConnectDB() {
		try { 			
			Class.forName(dbDriver).newInstance();
			con = DriverManager.getConnection(url, userName,password); 
			con.setAutoCommit(true);
		} catch (Exception ex) {
			System.out.println("false in :src/configurations/ConnectDB/ConnectDB()");
		}
		
	}

	//执行更新语句，更新了返回true,未更新返回false
	public boolean executeUpdate(String sql) {
		//System.out.println("执行src/jdbc/ConnectDB/executeUpdate()");
		try {
			Statement stmt = con.createStatement();		
			stmt.executeUpdate(sql); 			
			return true; 
		} catch (SQLException e) {		
			System.out.println("false in :src/jdbc/ConnectDB/executeUpdate()");
			System.out.println("   错误信息："+e);
			return false; 
		}
	}
	
	//执行查询语句，返回查询结果，如果没有查询到任何结果返回null
	public ResultSet executeQuery(String sql) {
		//System.out.println("执行src/jdbc/ConnectDB/executeQuery()");
		
		ResultSet rs; 
		try {		

			Statement stmt = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql); 
		} catch (SQLException e) {		
			System.out.println("false in :src/jdbc/ConnectDB/executeQuery()");
			System.out.println("   错误信息："+e);
			return null;
		}
		return rs; 
	}
	
	//关闭连接
	public void close() {
		if (con != null) {
			try {
				con.close();				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("false in :src/jdbc/ConnectDB/close()");
			} finally {
				con = null;
			}
		}
	}

}
