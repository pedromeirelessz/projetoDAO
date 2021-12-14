package model.dao;

import java.util.List;

import model.entities.Department;

// Por que usamos interface? Pois o acesso pode mudar, você pode usar o banco de dados MySQL e depois pode migrar para outra tecnologia, 
// a tecnologia especifica pode mudar e para que o sistema fique flexivel e para que preserve o contrato dos objetos de acesso a dados 
// nos vamos definir eles por meio de interfaces
public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
