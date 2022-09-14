package application;

import java.util.List;

import models.dao.DaoFactory;
import models.dao.SellerDAO;
import models.entities.Department;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDAO sellerDao = DaoFactory.createSellerDao();

		System.out.println("==== TESTE 1: seller findById =====");
		Seller seller = sellerDao.findById(5);
		System.out.println(seller);

		System.out.println("\n==== TESTE 2: seller findByDepartment =====");
		List<Seller> seller2 = sellerDao.findByDepartment(new Department(2, "Electronics"));
		seller2.forEach(System.out::println);

		System.out.println("\n==== TESTE 3: seller findByAll =====");
		List<Seller> seller3 = sellerDao.findAll();
		seller3.forEach(System.out::println);
	}
}
