package models.dao;

import java.util.List;

import models.entities.Department;

public interface DepartmentDAO {
	public void insert(Department dep);

	public void update(Department dep);

	public void deleteById(Integer id);

	public Department findById(Integer id);

	public List<Department> findAll();
}
