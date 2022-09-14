package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DBException;
import models.dao.SellerDAO;
import models.entities.Department;
import models.entities.Seller;

public class SellerDAOimplJDBC implements SellerDAO{
	private Connection conn;
	
	public SellerDAOimplJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller Seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		ps = conn.prepareStatement("select seller.*, department.name as 'depname' from seller join department"
					+ " on seller.departmentid = department.id where seller.id = ?");
		ps.setInt(1, id);
		rs = ps.executeQuery();
		if(rs.next()) {
			Department dep = instanciateDepartment(rs);
			Seller sl = instanciateSeller(rs,dep);
			return sl;
		}
		return null;
		}catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	private Seller instanciateSeller(ResultSet rs,Department dep) throws SQLException {
		Seller sl = new Seller();
		sl.setId(rs.getInt("id"));
		sl.setName(rs.getString("name"));
		sl.setEmail(rs.getString("email"));
		sl.setBirthDate(rs.getDate("birthdate"));
		sl.setBaseSalary(rs.getDouble("basesalary"));
		sl.setDep(dep);
		return sl;
	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("departmentid"));
		dep.setName(rs.getString("depname"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
