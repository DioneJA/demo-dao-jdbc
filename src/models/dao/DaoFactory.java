package models.dao;

import db.DB;
import models.dao.impl.SellerDAOimplJDBC;

public class DaoFactory {
	
	public static SellerDAO createSellerDao() {
		return new SellerDAOimplJDBC(DB.getConnection());
	}
}
