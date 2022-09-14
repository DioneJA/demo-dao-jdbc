package models.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import db.DB;
import db.DBException;
import models.dao.DepartmentDAO;
import models.entities.Department;

public class DepartmentDAOimplJDBC implements DepartmentDAO {
	Connection conn;

	public DepartmentDAOimplJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dep) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("insert into dep(name) values(?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, dep.getName());
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected>0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()){
					dep.setId(rs.getInt(1));
					DB.closeResultSet(rs);
				}
			}
		}catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}finally {
			DB.closeStatement(ps);

		}

	}

	@Override
	public void update(Department dep) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("update department set name=? where id=?)");
			ps.setString(1, dep.getName());
			ps.setInt(2, dep.getId());
			
			ps.executeUpdate();
			
		}catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("Delete from department where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps=conn.prepareStatement("Select * from department where id=?");
			rs=ps.executeQuery();
			if(rs.next()) {
				Department dp = instanciateDepartment(rs);
				return dp;
			}else {
				return null;
			}
		}catch (Exception e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select * from department");
			rs = ps.executeQuery();
			List<Department> dep = new ArrayList<Department>();
			while(rs.next()) {
				Department aux = instanciateDepartment(rs);
				dep.add(aux);
			}
			return dep;
		}catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("departmentid"));
		dep.setName(rs.getString("depname"));
		return dep;
	}
}
