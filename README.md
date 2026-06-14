# AutoManager - Microserviço Spring Boot

Sistema de gerenciamento de serviços automotivos com autenticação JWT e autorização por perfil.

## 📋 Descrição

AutoManager é uma aplicação Spring Boot que fornece APIs REST para gerenciar:
- **Mercadorias**: Peças e componentes automotivos
- **Serviços**: Serviços de manutenção e reparo
- **Veículos**: Cadastro de veículos dos clientes
- **Vendas**: Registro de transações de vendas
- **Empresas**: Dados das empresas
- **Usuários**: Gestão de usuários com perfis

**Segurança**: Autenticação JWT com autorização baseada em perfis (CLIENTE, FUNCIONARIO, FORNECEDOR).

---

## 🚀 Pré-requisitos

- **Java 17+** (ou superior)
- **Maven 3.6+**
- **MySQL 5.7+** rodando em `localhost:3306`
- **Git** (opcional)

### Verificar versões instaladas

```bash
java -version
mvn -version
mysql --version
```

---

## 🔧 Instalação e Setup

### 1. Clonar o repositório

```bash
git clone <seu-repositorio>
cd automanager
```

### 2. Configurar banco de dados

A aplicação criará o banco automaticamente se não existir.

**Arquivo**: `src/main/resources/application.properties`

```properties
# Conexão MySQL (banco será criado automaticamente se não existir)
spring.datasource.url=jdbc:mysql://localhost:3306/base?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=MATPET2007a@

# Hibernat será auto-criar as tabelas
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=SuperChaveSecretaMuitoLongaEComplexaParaHS2561234567890
jwt.expiration=3600000
```

### 3. Compilar o projeto

```bash
mvn clean install
```

---

## ▶️ Como Rodar

### Opção 1: Maven (CLI)

```bash
cd automanager
mvn spring-boot:run
```

### Opção 2: VS Code

1. Clique em **Run** (Play icon) ou use `F5`
2. Selecione **AutomanagerApplication**
3. A aplicação inicia em `http://localhost:8080`

### Opção 3: IDE (IntelliJ/Eclipse)

1. Abra a classe `AutomanagerApplication.java`
2. Clique com botão direito → **Run**

### Verificar se está rodando

```bash
curl http://localhost:8080/auth/login
```

Você deve receber um erro 405 (Method Not Allowed), o que confirma que o servidor está online.

---

## 🔐 Autenticação e Login

### 1. Fazer Login (Obter JWT Token)

**URL**: `POST http://localhost:8080/auth/login`

**Headers**:
```
Content-Type: application/json
```

**Body** (JSON):
```json
{
  "nomeUsuario": "dompedrofuncionario",
  "senha": "123456"
}
```

**Resposta (sucesso)**:
```json
{
  "jwttoken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb21wZWRyb2Z1bmNpb25hcmlvIiwiaWF0IjoxNzgxNDAyNjQ2LCJleHAiOjE3ODE0MDYyNDZ9.Tb2sPyKvTOgzeuSq-4YQPF7mOUYuZBYEdIrDfXUVWio"
}
```

### 2. Usar o Token nas Requisições

Copie o token retornado e adicione em todas as requisições subsequentes:

**Header obrigatório**:
```
Authorization: Bearer <TOKEN_AQUI>
```

**Exemplo completo**:
```bash
curl -X GET http://localhost:8080/mercadorias \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Accept: application/json"
```

---

## 👥 Usuários de Teste

Três usuários são criados automaticamente no startup:

| Username | Senha | Perfil | Pode acessar |
|----------|-------|--------|-------------|
| `dompedrofuncionario` | `123456` | FUNCIONARIO | Todos endpoints |
| `dompedrofornecedor` | `123456` | FORNECEDOR | Mercadorias, Veículos |
| `dompedrocliente` | `123456` | CLIENTE | Veículos |

---

## 📡 Endpoints Disponíveis

### Autenticação
- `POST /auth/login` - Fazer login (sem autorização)

### Mercadorias (Peças)
- `GET /mercadorias` - Listar todas
- `GET /mercadorias/{id}` - Buscar por ID
- `POST /mercadorias` - Criar (requer FUNCIONARIO ou FORNECEDOR)
- `PUT /mercadorias/{id}` - Atualizar (requer FUNCIONARIO ou FORNECEDOR)
- `DELETE /mercadorias/{id}` - Deletar (requer FUNCIONARIO ou FORNECEDOR)

### Serviços
- `GET /servicos` - Listar
- `GET /servicos/{id}` - Buscar por ID
- `POST /servicos` - Criar (requer FUNCIONARIO)
- `PUT /servicos/{id}` - Atualizar (requer FUNCIONARIO)
- `DELETE /servicos/{id}` - Deletar (requer FUNCIONARIO)

### Veículos
- `GET /veiculos` - Listar
- `GET /veiculos/{id}` - Buscar por ID
- `POST /veiculos` - Criar (requer CLIENTE ou FUNCIONARIO)
- `PUT /veiculos/{id}` - Atualizar (requer CLIENTE ou FUNCIONARIO)
- `DELETE /veiculos/{id}` - Deletar (requer CLIENTE ou FUNCIONARIO)

### Vendas
- `GET /vendas` - Listar
- `GET /vendas/{id}` - Buscar por ID
- `POST /vendas` - Criar (requer FUNCIONARIO)
- `PUT /vendas/{id}` - Atualizar (requer FUNCIONARIO)
- `DELETE /vendas/{id}` - Deletar (requer FUNCIONARIO)

### Empresas
- `GET /empresas` - Listar
- `GET /empresas/{id}` - Buscar por ID
- `POST /empresas` - Criar (requer FUNCIONARIO)
- `PUT /empresas/{id}` - Atualizar (requer FUNCIONARIO)
- `DELETE /empresas/{id}` - Deletar (requer FUNCIONARIO)

### Usuários
- `GET /usuarios` - Listar
- `GET /usuarios/{id}` - Buscar por ID
- `POST /usuarios` - Criar (requer FUNCIONARIO)
- `PUT /usuarios/{id}` - Atualizar (requer FUNCIONARIO)
- `DELETE /usuarios/{id}` - Deletar (requer FUNCIONARIO)

---

## 📝 Exemplos de Requisições

### 1. Login e Copiar Token

**PowerShell**:
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/auth/login" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"nomeUsuario":"dompedrofuncionario","senha":"123456"}'

$token = $response.jwttoken
Write-Host "Token: $token"
```

**Bash/curl**:
```bash
TOKEN=$(curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"nomeUsuario":"dompedrofuncionario","senha":"123456"}' | jq -r '.jwttoken')

echo "Token: $TOKEN"
```

### 2. Listar Mercadorias com Token

**PowerShell**:
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/mercadorias" `
  -Headers @{ Authorization = "Bearer $token" } `
  -Method Get | ConvertTo-Json | Write-Host
```

**Bash/curl**:
```bash
curl -X GET http://localhost:8080/mercadorias \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json" | jq
```

### 3. Criar Nova Mercadoria

**PowerShell**:
```powershell
$body = @{
    nome = "Pneu Michelin"
    preco = 250.00
    quantidade = 10
    descricao = "Pneu de alta qualidade"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/mercadorias" `
  -Headers @{ Authorization = "Bearer $token" } `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

**Bash/curl**:
```bash
curl -X POST http://localhost:8080/mercadorias \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pneu Michelin",
    "preco": 250.00,
    "quantidade": 10,
    "descricao": "Pneu de alta qualidade"
  }' | jq
```

---

## 🧪 Testar com Insomnia ou Postman

### 1. Importar Coleção (Opcional)

Você pode criar uma coleção no Insomnia/Postman com as requisições acima.

### 2. Configurar Environment

Crie uma variável de ambiente:

**Name**: `Token`  
**Value**: Cole o token obtido no login

Depois use `{{Token}}` em todas as requisições:

```
Authorization: Bearer {{Token}}
```

---

## 🛠️ Solução de Problemas

### Erro: "Connection refused" (Porta 8080)

A aplicação já está rodando em outro processo. Matá-lo:

**Windows (PowerShell)**:
```powershell
Get-Process java | Stop-Process -Force
```

**Linux/Mac**:
```bash
lsof -ti:8080 | xargs kill -9
```

### Erro: "Access denied" ao banco MySQL

Verifique as credenciais em `application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=MATPET2007a@
```

Se a senha é diferente, altere conforme sua configuração local.

### Erro: "LazyInitializationException" ao fazer requisições

Isso foi resolvido adicionando `@Transactional` no serviço de carregamento de usuários. Se persistir, verifique se a aplicação foi reiniciada após as correções.

### Token expirado (401 Unauthorized)

O token dura **1 hora** (3600000 ms). Faça login novamente:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"nomeUsuario":"dompedrofuncionario","senha":"123456"}'
```

---

## 📚 Arquitetura

```
automanager/
├── src/main/java/com/autobots/automanager/
│   ├── controle/          # REST Controllers
│   ├── services/          # Business logic
│   ├── repositorios/      # Data access (JPA)
│   ├── entitades/         # Entity models
│   ├── dto/               # Data transfer objects
│   ├── assembler/         # HATEOAS response builders
│   ├── security/          # JWT & authentication
│   ├── enumeracoes/       # Enums
│   └── AutomanagerApplication.java  # Main + seed data
├── src/main/resources/
│   └── application.properties  # Config
└── pom.xml                # Maven dependencies
```

---

## 🔒 Segurança

- **Autenticação**: JWT (JSON Web Token)
- **Algoritmo**: HS256
- **Expiração**: 1 hora
- **Senha**: BCrypt (hashed)
- **Autorização**: Role-based (@PreAuthorize)

---

## 📦 Dependências Principais

- Spring Boot 2.6.7
- Spring Security
- Spring Data JPA
- Hibernate 5.6.8
- JJWT 0.11.5 (JWT)
- MySQL Connector 8.0
- Lombok
- HATEOAS

---

## 📄 Licença

Este projeto é fornecido como parte de uma avaliação acadêmica.

---

## ✉️ Suporte

Para dúvidas ou problemas, verifique os logs da aplicação:

```bash
tail -f console.log
```

Ou habilite debug logging em `application.properties`:

```properties
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG
```

---

**Última atualização**: 13 de junho de 2026
