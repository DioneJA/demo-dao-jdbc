package models.dao;

import java.util.List;

import models.entities.Department;
import models.entities.Seller;

public interface SellerDAO {
	public void insert(Seller seller);
	public void update(Seller Seller);
	public void deleteById(Integer id);
	public Seller findById(Integer id);
	public List<Seller> findByDepartment(Department dep);
	public List<Seller> findAll();
}
