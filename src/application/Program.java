package application;

import models.dao.DaoFactory;
import models.dao.SellerDAO;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("==== TESTE 1: seller findById =====");
		Seller seller = sellerDao.findById(5);
		System.out.println(seller);
	}	
}
