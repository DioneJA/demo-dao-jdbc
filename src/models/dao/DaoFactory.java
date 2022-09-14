package models.dao;

import models.dao.impl.SellerDAOimplJDBC;

public class DaoFactory {
	
	public static SellerDAO createSellerDao() {
		return new SellerDAOimplJDBC();
	}
}
