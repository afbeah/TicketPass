# TicketPass

Sistema de venda e gerenciamento de ingressos desenvolvido como desafio técnico.

O TicketPass permite a descoberta de eventos, reserva de ingressos, processamento de pagamentos simulados, gerenciamento de ingressos do usuário e validação de entradas na portaria.

---

## 🚀 Funcionalidades

### Autenticação

- Cadastro e login de usuários
- Autenticação baseada em JWT
- Rotas protegidas no backend
- Diferenciação entre usuário autenticado e não autenticado

### Eventos

- Listagem de eventos disponíveis no TicketPass
- Busca de eventos externos
- Integração com a API da Ticketmaster
- Busca por evento e cidade

### Reserva de ingressos

- Seleção de ingresso disponível
- Criação de reserva
- Controle de status da reserva
- Expiração da reserva
- Associação da reserva ao usuário

### Pagamento

O fluxo de pagamento é simulado para fins do desafio.

Métodos disponíveis:

- PIX
- Cartão de crédito

Estados de pagamento:

- `PENDING`
- `APPROVED`
- `DECLINED`

Quando um pagamento é aprovado:

1. O pagamento é marcado como aprovado
2. A reserva é confirmada
3. Os ingressos são marcados como vendidos
4. É gerado um identificador de transação

Quando um pagamento é recusado:

1. O pagamento é marcado como recusado
2. A reserva é cancelada

### Meus ingressos

Usuários autenticados podem visualizar seus ingressos confirmados.

São exibidos:

- Evento
- Local
- Data
- Tipo do ingresso
- Valor
- Status
- QR Code

### Compartilhamento de ingresso

O usuário pode gerar um link de compartilhamento para um ingresso.

O sistema utiliza um `shareToken` associado ao ingresso para gerar o link.

### Validação na portaria

O TicketPass possui uma área de validação de ingressos.

A validação verifica:

- Existência do evento
- Existência do ingresso
- Associação do ingresso ao evento
- Status do ingresso
- Utilização anterior do ingresso

Estados utilizados na validação:

- `VALID`
- `INVALID`
- `WRONG_EVENT`
- `ALREADY_USED`

Após uma validação válida, o ingresso pode ser marcado como utilizado, impedindo uma segunda entrada com o mesmo ingresso.

---

## 🏗️ Arquitetura

O projeto está dividido em duas aplicações principais:

```txt
TicketPass
├── backend
└── frontend
```

### Backend

Construído com:

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Maven

A estrutura segue uma separação por responsabilidades:

```txt
backend/
└── src/
└── main/
└── java/
└── com/ticketpass/backend/
├── controller/
├── dto/
├── entity/
├── repository/
├── security/
└── service/
```

### Camadas

**Controller**

Responsável pela exposição dos endpoints HTTP.

**Service**

Concentra as regras de negócio da aplicação.

**Repository**

Responsável pelo acesso aos dados utilizando Spring Data JPA.

**Entity**

Representa as entidades persistidas no banco de dados.

**DTO**

Define os objetos utilizados na comunicação entre API e clientes.

### Frontend

Construído com:

- React
- TypeScript
- Vite
- CSS

Estrutura principal:

```txt
frontend/
└── src/
├── api/
├── pages/
├── types/
├── App.tsx
├── App.css
└── main.tsx
```

O frontend consome a API REST disponibilizada pelo backend.

--- 
## 🔄 Fluxo principal

O fluxo principal da aplicação é:

```txt
Usuário
│
▼
Login
│
▼
Eventos
│
▼
Escolha do ingresso
│
▼
Reserva
│
▼
Pagamento
│
├── DECLINED
│
└── APPROVED
│
▼
Reserva CONFIRMED
│
▼
Ingresso SOLD
│
▼
Meus ingressos
│
├── Compartilhar
│
└── QR Code
│
▼
Portaria
│
▼
Validação
```

---

## 🗄️ Principais entidades

### User

Representa os usuários da plataforma.

### Event

Representa os eventos disponíveis.

### TicketLot

Representa os lotes de ingressos de um evento.

### Ticket

Representa um ingresso individual.

Cada ingresso possui:

- tipo
- preço
- status
- QR Code
- token de compartilhamento

### Reservation

Representa uma reserva realizada pelo usuário.

A reserva possui:

- usuário
- ingressos
- status
- data de criação
- data de expiração

### Payment

Representa o pagamento associado a uma reserva.

---

## 🔐 Segurança

A API utiliza autenticação baseada em JWT.

Após o login, o token é utilizado pelo frontend nas requisições protegidas:

Authorization: Bearer <token>

As rotas que exigem autenticação são protegidas pelo Spring Security.

---

## 🌐 Integração com Ticketmaster

O TicketPass permite buscar eventos externos através da integração com a API da Ticketmaster.

A busca pode ser realizada utilizando:

- termo de pesquisa
- cidade

Os resultados externos são apresentados separadamente dos eventos cadastrados diretamente no TicketPass.

---

## ⚙️ Como executar

### Pré-requisitos

- Java 21
- Node.js
- npm
- PostgreSQL
- Git

### Backend

Entre na pasta:

cd backend

No Windows:

`.\mvnw.cmd spring-boot:run`

No Linux/macOS:

`./mvnw spring-boot:run`

A API estará disponível em:

http://localhost:8080

### Frontend

Entre na pasta:

cd frontend

Instale as dependências:

`npm install`

Execute:

`npm run dev`

O frontend será disponibilizado pelo Vite, normalmente em:

http://localhost:5173

---
## 🧪 Testes e validação

O backend pode ser validado através do Maven:

`.\mvnw.cmd clean test`

O frontend pode ser validado através do build de produção:

`npm run build`

Durante o desenvolvimento foram validados os principais fluxos da aplicação, incluindo:

- autenticação
- consulta de eventos
- reserva
- pagamento
- confirmação da reserva
- disponibilização dos ingressos
- validação na portaria
- prevenção de reutilização de ingresso
- compartilhamento de ingresso

---

## 📡 Principais endpoints

### Autenticação

`POST /api/auth/register`

`POST /api/auth/login`

### Eventos

`GET /api/events/local`

### Reservas

`POST /api/reservations`

### Pagamentos

`POST /api/payments`

`PUT /api/payments/{paymentId}/approve`

`PUT /api/payments/{paymentId}/decline`

### Ingressos

`GET /api/tickets/my`

`POST /api/tickets/{ticketId}/share`

`GET /api/tickets/share/{shareToken}`

### Portaria

`POST /api/gate/validate`

---

## 🧠 Decisões técnicas

### Reserva antes do pagamento

O ingresso passa por uma etapa de reserva antes do pagamento. Isso permite separar o processo de seleção do ingresso do processamento do pagamento.

### Estados explícitos

Reservas, pagamentos e ingressos utilizam enums para representar seus estados.

Isso evita o uso de strings arbitrárias nas regras de negócio e facilita a manutenção.

### Transações

Operações críticas, como criação e processamento de pagamentos, utilizam transações para manter a consistência entre as alterações relacionadas.

### Separação de responsabilidades

As regras de negócio ficam concentradas nos services, enquanto os controllers ficam responsáveis pela camada HTTP.

### Compartilhamento de ingressos

O compartilhamento utiliza um `shareToken` associado ao ingresso. O token permite gerar um link específico para o ingresso sem expor diretamente sua identificação como mecanismo de compartilhamento.

---

## 🤖 Uso de Inteligência Artificial

A Inteligência Artificial foi utilizada como ferramenta de apoio durante o desenvolvimento do projeto, atuando como uma espécie de apoio técnico e simulação de uma perspectiva de Tech Lead.

O uso incluiu:

- apoio na análise de requisitos;
- discussão de decisões de arquitetura;
- auxílio na identificação de problemas;
- revisão de código;
- apoio na implementação e evolução de funcionalidades;
- suporte durante debugging;
- apoio na criação e revisão da documentação.

As decisões finais, implementação, execução dos testes e validação do sistema foram realizadas durante o desenvolvimento do projeto.

A IA foi utilizada como ferramenta de desenvolvimento e apoio à tomada de decisão, e não como substituição da validação técnica.

---

## ⚠️ Limitações conhecidas

O projeto foi desenvolvido dentro do tempo e escopo disponíveis para o desafio.

Algumas funcionalidades permanecem simplificadas:

- O pagamento é simulado e não realiza transações financeiras reais.
- A validação de ingresso utiliza o código informado pelo operador, sem leitura de câmera.
- O compartilhamento utiliza um token associado ao ingresso.
- A aplicação não possui integração com um gateway de pagamento real.
- O projeto não implementa recursos avançados de gestão de eventos ou mapa de assentos.

Essas decisões priorizaram a implementação e validação do fluxo principal de compra, gerenciamento e validação de ingressos.

---

## 📌 Status

Projeto desenvolvido e validado como desafio técnico.

Principais fluxos implementados:

- autenticação
- eventos
- busca de eventos
- reserva
- pagamento
- confirmação da compra
- meus ingressos
- QR Code
- compartilhamento
- validação na portaria
- controle de ingresso já utilizado

## 👩‍💻 Desenvolvido por

**Beatriz França**

Desenvolvido como parte de um desafio técnico para demonstração de conhecimentos em desenvolvimento de software, APIs REST, arquitetura, banco de dados, autenticação e desenvolvimento frontend.


