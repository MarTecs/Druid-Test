package com.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.support.logging.Log;
import com.util.DBPoolConnection;

public class Test {
	
	public static void main(String[] args) {
		
		DBPoolConnection connection = DBPoolConnection.getInstance();
		DruidPooledConnection conn = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM goods";
		try {
			conn = connection.getConnection();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs != null)
				while(rs.next()) {
					System.out.println(rs.getString(1) + "\t" + rs.getString(2));
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
