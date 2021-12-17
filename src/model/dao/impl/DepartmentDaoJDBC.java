package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					  "INSERT INTO department "
					  + "(Name) "
					  + "VALUES (?)",
					  Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getName());

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					  "UPDATE department "
					  + "SET Name = ? "
					  + "WHERE Id = ?");

			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getId());
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
					  "DELETE FROM department "
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
	public Department findById(Integer id) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					  "SELECT * "
					  + "FROM department "
					  + "WHERE Id = ?");

			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Department dep = createDepartment(rs);
				return dep;
			}
			return null;

		} catch (SQLException DB) {
			throw new DbException(DB.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	public Department createDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public List<Department> findAll() {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					  "SELECT *"
					  + "FROM department");
			
			rs = ps.executeQuery();
			List<Department> list = new ArrayList<>();

			while (rs.next()) {
				Department dep = createDepartment(rs);
				list.add(dep);
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
