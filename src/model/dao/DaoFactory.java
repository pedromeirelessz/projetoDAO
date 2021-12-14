package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

// Classe auxiliar respons�vel por instanciar os dados
public class DaoFactory {

	// A minha classe vai expor um m�todo que retorna o tipo da interface, mas
	// internamente ela ir� retornar uma implementa��o
	
	// � tamb�m uma forma de fazer uma inje��o de depend�ncia sem explicitar a implementa��o
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}