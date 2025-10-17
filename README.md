🧩 Sistema de Gerenciamento de Usuários
📝 Descrição

Serviço backend em Spring Boot que oferece uma API RESTful para CRUD de usuários, com regras de negócio e validações na camada de serviço.

✨ Funcionalidades (UsuarioService)
Operação	Método	Descrição	Regras de Negócio
Criar	criar(UsuarioDTO dto)	Cadastra novo usuário	CPF e e-mail únicos, idade mínima 18 anos
Buscar	buscar(Long id)	Busca por ID	Retorna Optional<UsuarioDTO>
Atualizar	atualizar(Long id, UsuarioDTO dto)	Atualiza dados existentes	Impede troca de CPF, e-mail duplicado, e 404 se não encontrado
Listar	listar(String status, int page, int size)	Lista paginada de usuários	Suporte a paginação e filtro por status
Excluir	deletar(Long id)	Remove usuário	404 se não encontrado
---

## 🛠 Tecnologias utilizadas

<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/>
</div>


---

🚀 Execução

Pré-requisitos: JDK 17+, Maven/Git.

git clone [URL_DO_REPO]
cd nome-do-projeto
mvn clean install
java -jar target/nome-do-arquivo.jar


Ou execute via IDE (classe com @SpringBootApplication).
Serviço padrão: http://localhost:8080

🔗 Endpoints
Método	Endpoint	Descrição
GET	/usuarios/{id}	Buscar por ID
GET	/usuarios?page=0&size=10	Listar paginado
POST	/usuarios	Criar novo usuário
PUT	/usuarios/{id}	Atualizar usuário
DELETE	/usuarios/{id}	Excluir usuário
🤝 Contribuição

Faça fork

Crie branch (feature/sua-feature)

Commit (feat: nova funcionalidade)

Push e abra um Pull Request

👨‍💻 Autor

Diego Leite – github.com/diegocbaleite

📄 Licença

Licenciado sob Licença Diego Leite – veja LICENSE.md.
