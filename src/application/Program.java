package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import db.DB;
import db.DBException;
import models.entities.Department;

public class Program {

	public static void main(String[] args) {
		List<Department> dep = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs=null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM department");
			
			while(rs.next()) {
				dep.add(new Department(rs.getInt(1),rs.getString(2)));
			}
	
		}catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}
		dep.forEach(System.out::println);
	}

}
