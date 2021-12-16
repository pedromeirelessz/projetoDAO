package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	// Vou ter que criar uma conexão no findById? Não, pois o SellerDaoJDBC vai ter
	// uma dependência com a conexão
	private Connection conn;

	// Para forçar a injeção de dependência aqui dentro, eu irei utilizar um
	// construtor, então terei o objeto conn a disposição em qualquer lugar na minha
	// classe SellerDaoJDBC
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			
			// executeUpdate retorna um número inteiro
			int rowsAffected = ps.executeUpdate();
			
			// verificar se retornou algo
			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected");
			}
		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			ps.setInt(6, obj.getId());
			ps.executeUpdate();
			
		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}
	
	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"DELETE FROM seller "
					+ "WHERE Id = ?");
			
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}
	
	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			ps.setInt(1, id);
			// Quando eu acabo de fazer uma consulta SQL e vem o resulto no ResultSet, esse
			// ResultSet está apontando para posição 0, a posição 0 não contém objeto,
			// apenas na posição 1
			rs = ps.executeQuery();
			// testar se veio algum resultado
			if (rs.next()) {
				// Nos estamos programando orientado a objetos, nossa classe DaoJDBC, pega os
				// dados do banco de dados relacional e transforma em objetos associados, então
				// irei criar um objeto seller com os dados do vendedor e associado a ele vai
				// ter outro objeto do tipo department com os dados do departamento dele.
				Department dep = instantiateDepartment(rs);

				Seller obj = instantiateSeller(rs, dep);
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

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			rs = ps.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			// Pegando o id do departamento
			ps.setInt(1, department.getId());

			rs = ps.executeQuery();

			List<Seller> list = new ArrayList<>();
			// Vou guardar dentro desse map qualquer departamento que eu instanciar
			Map<Integer, Department> map = new HashMap<>();
			// Nesse irei usar um while pois o ResultSet pode retornar mais de 1 resultado
			while (rs.next()) {

				// Toda vez que passar no while, irei verificar se o departamento já foi
				// instanciado, eu uso a operação get do map verificar se já foi instanciado um
				// departamento com esse id, se não exitir o map irá retornar null, então irei
				// instanciar ele.
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					// Vou salvar o departamento dentro do meu map, para que no proximo o if possa
					// ser verificado que ele já foi instanciado com essa chave
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}
}