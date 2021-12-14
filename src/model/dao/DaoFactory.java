package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

// Classe auxiliar responsável por instanciar os dados
public class DaoFactory {

	// A minha classe vai expor um método que retorna o tipo da interface, mas
	// internamente ela irá retornar uma implementação
	
	// É também uma forma de fazer uma injeção de dependência sem explicitar a implementação
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}