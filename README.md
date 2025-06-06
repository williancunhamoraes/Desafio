# LuizaLabs - Desafio Técnico - Logística

## 💡 Descrição
Sistema desenvolvido como solução para o desafio técnico da LuizaLabs (Vertical Logística). O objetivo é transformar um arquivo `.txt` com estrutura desnormalizada de pedidos em um JSON estruturado e agrupado por usuário, pedidos e produtos.

## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot 3.x
- Lombok
- Maven

## 📁 Estrutura do Projeto
```
src/
├── controller/             # Endpoints REST
├── service/                # Regra de negócio
├── model/                  # DTOs usados na entrada/saída
└── resources/              # Arquivos de configuração
```

## 📦 Executando o Projeto
```bash
# Clonar o projeto
$ git clone https://github.com/seu-usuario/luizalabs-logistica-api.git
$ cd luizalabs-logistica-api

# Compilar e executar
$ mvn clean install
$ mvn spring-boot:run
```

## 📥 Requisição de Processamento
Endpoint: `POST /api/v1/pedidos/processar`

Envia um arquivo `.txt` e recebe um JSON estruturado:
```bash
curl -X POST http://localhost:8080/api/v1/pedidos/processar \
  -F "file=@exemplo.txt" \
  -H "Content-Type: multipart/form-data"
```

## 🔎 Endpoints Disponíveis
- `POST /api/v1/pedidos/processar`: Envia o arquivo `.txt` com os pedidos
- `GET /api/v1/pedidos/filtrar?dataInicio=YYYY-MM-DD&dataFim=YYYY-MM-DD`: Filtra os pedidos por data de compra (intervalo)
- `GET /api/v1/pedidos/pedido/{orderId}`: Retorna o pedido com o `orderId` informado

## ✅ Requisitos Atendidos
- Upload de arquivo via API REST
- Processamento e parsing de linhas fixas
- Agrupamento por usuário → pedidos → produtos
- Cálculo de totais e datas formatadas
- Filtros por data e busca por ID de pedido

## 🔍 Melhorias Futuras
- Persistência em banco de dados (JPA ou NoSQL)
- Paginação e cache para grandes volumes
- Testes unitários e de integração (JUnit, MockMvc)

## 📄 Exemplo de Arquivo de Entrada
```
0000000002 Medeiros00000123450000000111 256.2420201201
0000000001 Zarelli00000001230000000111 512.2420211201
0000000001 Zarelli00000001230000000122 512.2420211201
0000000002 Medeiros00000123450000000122 256.2420201201
```

## 👨‍💻 Autor
Desenvolvido por Willian Moraes.

---
LuizaLabs • Desafio Técnico Logística
