# Padrão de projeto DAO (Data Acess Object)

# Ideia geral do padrão DAO
* Para cada entidade, haverá um objeto responsável por fazer o acesso a dados relacionado a esta entidade. Por exemplo:
  * Cliente: ClienteDAO
  * Produto: ProdutoDAO
  * Pedido: PedidoDAO
* Cada DAO será definido por uma interface
* A injeção de dependência pode ser feita por meio do padrão de projeto Factory

![ModeloDAO](https://user-images.githubusercontent.com/81655895/145939055-b5a4cdbd-a04a-42dd-b3ce-af4d5ce82e6b.PNG)
 
