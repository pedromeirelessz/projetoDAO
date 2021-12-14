package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	// Vou ter que criar uma conex�o no findById? N�o, pois o SellerDaoJDBC vai ter
	// uma depend�ncia com a conex�o
	private Connection conn;

	// Para for�ar a inje��o de depend�ncia aqui dentro, eu irei utilizar um
	// construtor, ent�o terei o objeto conn a disposi��o em qualquer lugar na minha
	// classe SellerDaoJDBC
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
		            + "FROM seller INNER JOIN department "	
		            + "ON seller.DepartmentId = department.Id " 
		            + "WHERE seller.Id = ?");

			ps.setInt(1, id);
			// Quando eu acabo de fazer uma consulta SQL e vem o resulto no ResultSet, esse
			// ResultSet est� apontando para posi��o 0, a posi��o 0 n�o cont�m objeto,
			// apenas na posi��o 1
			rs = ps.executeQuery();
			// testar se veio algum resultado
			if (rs.next()) {
				// Nos estamos programando orientado a objetos, nossa classe DaoJDBC, pega os
				// dados do banco de dados relacional e transforma em objetos associados, ent�o
				// irei criar um objeto seller com os dados do vendedor e associado a ele vai
				// ter outro objeto do tipo department com os dados do departamento dele.
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));

				Seller obj = new Seller();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
				obj.setBaseSalary(rs.getDouble("BaseSalary"));
				obj.setBirthDate(rs.getDate("BirthDate"));
				obj.setDepartment(dep);
				return obj;
			} else {
				return null;
			}
		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
