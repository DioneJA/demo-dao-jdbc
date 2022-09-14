package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DBException;
import models.dao.SellerDAO;
import models.entities.Department;
import models.entities.Seller;

public class SellerDAOimplJDBC implements SellerDAO {
	private Connection conn;

	public SellerDAOimplJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"INSERT INTO seller(name,email,birthdate,basesalary,departmentid)" + " values(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDep().getId());

			int rows = ps.executeUpdate();
			if (rows > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
					DB.closeResultSet(rs);
				}
			} else {
				throw new DBException("ERROR! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"UPDATE seller SET name = ?, email=?, birthdate=?,basesalary=?,departmentid=? Where id = ?");
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDep().getId());
			ps.setInt(6, seller.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
			// TODO: handle exception
		} finally {
			DB.closeStatement(st);
		}
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
			if (rs.next()) {
				Department dep = instanciateDepartment(rs);
				Seller sl = instanciateSeller(rs, dep);
				return sl;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException("ERROR! Caused by " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select seller.*, department.name as depname from seller Join"
					+ " department ON department.id = departmentid");
			rs = st.executeQuery();

			Map<Integer, Department> map = new HashMap<>();
			List<Seller> sl = new ArrayList<Seller>();
			while (rs.next()) {
				Department dep = map.get(rs.getInt("departmentid"));
				if (dep == null) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("departmentid"), dep);

				}
				Seller seller = instanciateSeller(rs, dep);
				sl.add(seller);
			}
			return sl;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}
	}

	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select seller.*, department.name as 'depname' from seller join"
					+ " department on seller.departmentid = department.id where department.id = ? order by name");
			ps.setInt(1, dep.getId());
			rs = ps.executeQuery();

			List<Seller> seller = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				/* Controlando a já existencia do department */
				Department dep2 = map.get(rs.getInt("departmentid"));
				if (dep2 == null) {
					dep2 = instanciateDepartment(rs);
					map.put(rs.getInt("departmentid"), dep2);
				}
				Seller sl = instanciateSeller(rs, dep2);
				seller.add(sl);
			}
			return seller;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DBException("ERROR! Caused by " + e.getMessage());
		}
	}

	private Seller instanciateSeller(ResultSet rs, Department dep) throws SQLException {
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
}
