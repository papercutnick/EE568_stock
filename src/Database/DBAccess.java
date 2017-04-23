package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccess {
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName( "org.postgresql.Driver" ).newInstance();
			String url = "jdbc:postgresql://54.148.123.99:5432/ece568" ;
			conn=DriverManager.getConnection(url, "postgres" , "postgres" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	       
        return conn;
	}
	
	public static String getLatestPrice(String code){
		Connection conn = getConnection();
		PreparedStatement ps=null;
		String sql = "select stockcode, time, date, price from realtime_price where time=(select max(time) from (select time from realtime_price where date=(select max(date) from realtime_price where stockcode=?)) as temp) and stockcode=?";
		ResultSet rs=null;
		String cnt="";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			ps.setString(2, code);
			rs = ps.executeQuery();
			if(rs.next()){
				cnt = rs.getString("price");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			closeResultSet(rs);
			closeStatement(ps);
			closeConnection(conn);
		}
       return cnt;
	}
	
	public static String getHighestPriceInNDays(String code, int t){
		Connection conn = getConnection();
		PreparedStatement ps=null;
		String sql = "SELECT MAX(temp.high) FROM(SELECT *  FROM history_price WHERE stockcode=? ORDER BY date DESC FETCH FIRST ? ROWS ONLY) as temp".toLowerCase();
		ResultSet rs=null;
		String cnt="";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			ps.setInt(2, t);
			rs = ps.executeQuery();
			if(rs.next()){
				cnt = rs.getString("high");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			closeResultSet(rs);
			closeStatement(ps);
			closeConnection(conn);
		}
       return cnt;
	}
	
	public static int insertRealtimeData(String stockCode, String time, double price, int volume, String date){
		Connection conn = getConnection();
		PreparedStatement ps=null;
		String getAggregatedVolume = "select sum(volume) volumesum from realtime_price where stockcode=? and date=?";
		String sql = "insert into realtime_price(stockcode, time, price, volume, date) values(?,?,?,?,?)";
		int result=0;
		ResultSet rs=null;
		int volumesum=0;
		try {
			ps = conn.prepareStatement(getAggregatedVolume);
			ps.setString(1, stockCode);
			ps.setString(2, date);
			rs = ps.executeQuery();
			if(rs.next()){
				volumesum = rs.getInt("volumesum");
			}
			closeResultSet(rs);
			closeStatement(ps);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, stockCode);
			ps.setString(2, time);
			ps.setDouble(3, price);
			ps.setInt(4, volume-volumesum);
			ps.setString(5, date);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=0;
		}  finally{
			closeStatement(ps);
			closeConnection(conn);
		}
       return result;
	}
	
	public static boolean checkExsistence(String stockCode, String date){
		Connection conn = getConnection();
		PreparedStatement ps=null;
		String sql = "select count(1) cnt from history_price where stockcode=? and date=?";
		ResultSet rs=null;
		int cnt=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, stockCode);
			ps.setString(2, date);
			rs = ps.executeQuery();
			if(rs.next()){
				cnt = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			closeResultSet(rs);
			closeStatement(ps);
			closeConnection(conn);
		}
       return cnt!=0;
	}
	
	public static int insertHistoryData(String stockCode, String date, double open, double high, double low, double close, int volume){
		Connection conn = getConnection();
		PreparedStatement ps=null;
		String sql = "insert into history_price(stockcode, date, open, high, low, close, volume) values(?,?,?,?,?,?,?)";
		int result=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, stockCode);
			ps.setString(2, date);
			ps.setDouble(3, open);
			ps.setDouble(4, high);
			ps.setDouble(5, low);
			ps.setDouble(6, close);
			ps.setInt(7, volume);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=0;
		}  finally{
			closeStatement(ps);
			closeConnection(conn);
		}
       return result;
	}
	
	public static int deleteFromHistory(String stockCode, String from, String to){
		Connection conn = getConnection();
		PreparedStatement ps=null;
		String sql = "delete from history_price where stockcode=? and date>? and date<?";
		int result=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, stockCode);
			ps.setString(2, from);
			ps.setString(3, to);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=0;
		}  finally{
			closeStatement(ps);
			closeConnection(conn);
		}
       return result;
	}
	
	public static void closeConnection(Connection conn) {
	    try {
	        if(conn!=null){
	     	   conn.close();
	        }
	     } catch (SQLException e) {  
	         e.printStackTrace();  
	     }
	}

	public static void closeStatement(PreparedStatement pst){
		 try {
		       if(pst!=null){
		       		pst.close();
		       }
		    } catch (SQLException e) {  
		        e.printStackTrace();  
		    }  
	}
	
	public static void closeResultSet(ResultSet rs){
		 try {
		       if(rs!=null){
		    	   rs.close();
		       }
		    } catch (SQLException e) {  
		        e.printStackTrace();  
		    }  
	}
}
