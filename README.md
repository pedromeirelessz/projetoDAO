# Padrão de projeto DAO (Data Acess Object)

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

