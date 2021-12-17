# Padrão de projeto DAO (Data Acess Object)

[![LICENSE](https://img.shields.io/npm/l/react)](https://github.com/phmeyreles/projetoDAO/blob/master/LICENSE) 

## Ideia geral do padrão DAO
* Para cada entidade, haverá um objeto responsável por fazer o acesso a dados relacionado a esta entidade. Por exemplo:
  * Cliente: ClienteDAO
  * Produto: ProdutoDAO
  * Pedido: PedidoDAO
* Cada DAO será definido por uma interface
* A injeção de dependência pode ser feita por meio do padrão de projeto Factory

![ModeloDAO](https://user-images.githubusercontent.com/81655895/145939055-b5a4cdbd-a04a-42dd-b3ce-af4d5ce82e6b.PNG)

## Department entity class
### Entity class checklist:
* Attributes
* Constructors
* Getters/Setters
* hashCode and equals
* toString
* Implements Serializable

![ModeloDAO2](https://user-images.githubusercontent.com/81655895/146043053-867d49e2-0b0a-42e3-ab76-6fc28c096dd7.PNG)
 
## findById implementation
 
### SQL Query: 
SELECT seller.*,department.Name as DepName <br>
FROM seller INNER JOIN department <br>
ON seller.DepartmentId = department.Id  <br>
WHERE seller.Id = ?

![ModeloDAO3](https://user-images.githubusercontent.com/81655895/146043744-88991442-c203-4a79-9231-be331e5e64ca.PNG)



## Reusing instantiation
```Java

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

```
```Java
private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
}

```

## findByDepartment implementation

### SQL Query:
SELECT seller.*,department.Name as DepName<br>
FROM seller INNER JOIN department<br>
ON seller.DepartmentId = department.Id<br>
WHERE DepartmentId = ?<br>
ORDER BY Name

![ModeloDAO3](https://user-images.githubusercontent.com/81655895/146099788-75adebe5-354f-416a-a5d0-9027f8a60698.PNG)

## findAll implementation

### SQL Query:
SELECT seller.*,department.Name as DepName<br>
FROM seller INNER JOIN department<br>
ON seller.DepartmentId = department.Id<br>
ORDER BY Name

## insert implementation

### SQL Query:
INSERT INTO seller<br>
(Name, Email, BirthDate, BaseSalary, DepartmentId)<br>
VALUES<br>
(?, ?, ?, ?, ?)

## update implementation

### SQL Query:
UPDATE seller<br>
SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?<br>
WHERE Id = ?

## delete implementation

### SQL Query:
DELETE FROM seller<br>
WHERE Id = ?

## DepartmentDao implementation and test
### Checklist:
* DepartmentDaoJDBC
* DaoFactory
* Program2

## Artigos relacionados
* [https://www.devmedia.com.br/dao-pattern-persistencia-de-dados-utilizando-o-padrao-dao/30999](https://www.devmedia.com.br/dao-pattern-persistencia-de-dados-utilizando-o-padrao-dao/30999 "Artigo relacionado")
* [https://www.oracle.com/java/technologies/dataaccessobject.html](https://www.oracle.com/java/technologies/dataaccessobject.html "Artigo relacionado")
