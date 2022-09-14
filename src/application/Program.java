package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.dao.DaoFactory;
import models.dao.SellerDAO;
import models.entities.Department;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
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
		
		System.out.println("\n==== TESTE 5: seller insert =====");
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse("03/20/2001");
		Seller seller4 = new Seller(0, "Bruno Alves", "bruno@gmail.com", date, 2500.00, new Department(2, "Electronics"));
		sellerDao.insert(seller4);
		System.out.println("Sucefull inserction! Id: " + seller4.getId());
		
		System.out.println("\n==== TESTE 6: seller update =====");
		Seller seller5 = sellerDao.findById(1);
		seller5.setName("ATUALIZADO");
		sellerDao.update(seller5);
		System.out.println("Sucefull updated!");
	}
}
