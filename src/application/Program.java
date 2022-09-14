package application;

import models.dao.DaoFactory;
import models.dao.SellerDAO;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		Seller seller = sellerDao.findById(5);
		
		System.out.println(seller);
	}	
}
