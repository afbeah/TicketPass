# TicketPass — Architecture

## 1. Visão Arquitetural

O TicketPass será desenvolvido como um **Monólito Modular**, organizado por domínios de negócio.

A aplicação será composta por:

- Frontend web em React + TypeScript;
- Backend REST em Java + Spring Boot;
- PostgreSQL como banco de dados relacional;
- Integração com API externa para catálogo de shows e filmes;
- Docker para padronização do ambiente de desenvolvimento.

A escolha por um monólito modular busca equilibrar:

- simplicidade de desenvolvimento;
- separação clara de responsabilidades;
- consistência transacional;
- facilidade de execução e deploy;
- possibilidade de evolução futura para serviços independentes caso o domínio exija.

O projeto não utilizará microsserviços neste momento, pois a complexidade operacional adicional não é necessária para o escopo atual.

---

## 2. Stack

### Frontend

- React
- TypeScript
- Vite

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security

### Banco de Dados

- PostgreSQL

### Infraestrutura

- Docker
- Docker Compose

### Integrações

- API externa de catálogo de shows/filmes
- Serviço simulado de pagamento

### Documentação e versionamento

- Git
- GitHub
- Markdown

---

## 3. Estrutura do Projeto

O projeto utilizará um monorepo:

```text
TicketPass/
├── backend/
├── frontend/
├── docs/
│   ├── VISION.md
│   ├── BACKLOG.md
│   ├── ARCHITECTURE.md
│   └── DESIGN-SYSTEM.md
│
├── .gitignore
└── README.md
```

O diretório ```docs``` concentra a documentação do produto e da arquitetura.

O ```frontend``` contém a aplicação web.

O ```backend``` contém a API e as regras de negócio.

## 4. Arquitetura do Backend

O backend será estruturado como um Monólito Modular.

Os módulos serão organizados de acordo com os principais contextos do domínio:

```text
backend/
└── src/
    └── main/
        └── java/
            └── ...
                └── modules/
                    ├── auth/
                    ├── event/
                    ├── reservation/
                    ├── ticket/
                    ├── payment/
                    └── refund/
```

Cada módulo será responsável pelo seu próprio contexto de negócio.

A separação interna seguirá uma estrutura simplificada:

```text
module/
├── domain/
├── application/
├── infrastructure/
└── presentation/
```

### Domain

Contém:

- entidades;
- regras de negócio;
- estados;
- objetos de domínio;
- contratos essenciais do módulo.

### Application

Contém:

- casos de uso;
- orquestração das regras;
- comandos e resultados;
- interfaces necessárias para comunicação com infraestrutura.

### Infrastructure

Contém:

- persistência;
- integrações externas;
- implementações de interfaces;
- configurações técnicas.

### Presentation

Contém:

- controllers REST;
- DTOs;
- validações de entrada;
- respostas HTTP.

## 5. Módulos de Domínio

### Auth

Responsável por:

- autenticação;
- usuários;
- papéis;
- autorização.

Papéis principais:

```text
CLIENT
ORGANIZER
GATEKEEPER
```
### Event

Responsável por:

- criação de eventos;
- dados do evento;
- catálogo externo;
- publicação;
- edição;
- capacidade;
- lotes;
- preços;
- cancelamento.

### Reservation

Responsável por:

- criação de reservas;
- associação de ingressos à reserva;
- duração da reserva;
- expiração;
- cancelamento voluntário;
- liberação dos ingressos;
- controle de concorrência.

### Ticket

Responsável por:

- ingressos individuais;
- estados dos ingressos;
- associação com cliente e evento;
- QR Code;
- validação;
- utilização do ingresso;
- compartilhamento.

### Payment

Responsável por:

- dados do pagamento;
- processamento simulado;
- aprovação;
- recusa;
- associação do resultado à reserva.

### Refund

Responsável por:

- solicitações de reembolso;
- solicitações de crédito;
- associação da solicitação ao ingresso/evento;
- acompanhamento do estado da solicitação.

## 6. Arquitetura do Frontend

O frontend será desenvolvido como uma aplicação React utilizando TypeScript.

A aplicação será organizada por funcionalidades e contextos do produto.

Estrutura inicial:

```text
frontend/
└── src/
    ├── app/
    ├── components/
    ├── features/
    │   ├── auth/
    │   ├── events/
    │   ├── reservations/
    │   ├── checkout/
    │   ├── tickets/
    │   ├── organizer/
    │   ├── gatekeeper/
    │   └── refunds/
    │
    ├── services/
    ├── hooks/
    ├── types/
    └── styles/
```

```components```

Componentes reutilizáveis da interface.

```features```

Funcionalidades específicas do produto.

```services```

Comunicação com a API e integrações necessárias.

```hooks```

Hooks reutilizáveis e lógica relacionada à interface.

```types```

Tipos compartilhados do frontend.

```styles```

Estilos globais e tokens relacionados ao Design System.

## 7. Comunicação Frontend / Backend

O frontend se comunicará com o backend através de uma API REST.

Fluxo geral:

```text
Cliente
   ↓
React
   ↓
REST API
   ↓
Spring Boot
   ↓
Domínio
   ↓
PostgreSQL
```

Integrações externas seguirão o fluxo:

```text
Backend
   ↓
External API
   ↓
Catálogo de shows/filmes
```

O frontend não acessará diretamente a API externa de catálogo.

A responsabilidade pela integração e normalização dos dados externos ficará no backend.

## 8. Modelo de Domínio

As principais entidades do sistema serão:

```text
User
Event
Ticket
TicketBatch
Reservation
Payment
RefundRequest
```

### User

Representa um usuário autenticado da plataforma.

Principais informações:

- identificador;
- nome;
- e-mail;
- credenciais;
- papel.

### Event

Representa um evento publicado ou gerenciado pelo organizador.

Principais informações:

- identificador;
- título;
- descrição;
- imagem;
- data;
- horário;
- local;
- capacidade;
- status;
- organizador;
- referência ao conteúdo do catálogo externo.

### TicketBatch

Representa um lote de ingressos.

Principais informações:

- identificador;
- evento;
- tipo de ingresso;
- preço;
- quantidade;
- ordem do lote;
- status.

Tipos de ingresso:

```text
FULL
HALF
```

### Ticket

Representa uma unidade individual de ingresso.

Principais informações:

- identificador;
- evento;
- lote;
- cliente;
- reserva;
- código;
- QR Code;
- status.

Estados principais:

```text
AVAILABLE
RESERVED
PAID
USED
CANCELLED
```

### Reservation

Representa a reserva temporária de um ou mais ingressos.

Principais informações:

- identificador;
- cliente;
- ingressos;
- data de criação;
- data de expiração;
- status.

Estados principais:

```text
ACTIVE
CANCELLED
EXPIRED
COMPLETED
```

### Payment

Representa uma tentativa de pagamento.

Principais informações:

- identificador;
- reserva;
- valor;
- método;
- status;
- data de processamento.

Estados principais:

```text
PENDING
APPROVED
DECLINED
```

### RefundRequest

Representa uma solicitação realizada pelo cliente após o cancelamento de um evento.

Tipos:

```text
REFUND
CREDIT
```

## 9. Estados dos Eventos

Os eventos possuirão um ciclo de vida próprio.

```text
DRAFT
   ↓
PUBLISHED
   ↓
CANCELLED
```

### DRAFT

Evento criado pelo organizador, mas ainda não publicado.

### PUBLISHED

Evento disponível para clientes.

### CANCELLED

Evento cancelado pelo organizador.

Eventos cancelados não podem receber novas reservas ou pagamentos.

## 10. Inventário e Reserva

A capacidade do evento será representada por ingressos individuais.

Exemplo:

```text
Evento
Capacidade: 3

Ticket 01 → AVAILABLE
Ticket 02 → AVAILABLE
Ticket 03 → AVAILABLE
```

Quando o cliente realiza uma reserva:

```text
Ticket 01 → RESERVED
Ticket 02 → RESERVED
```

A reserva terá duração de 30 minutos.

Após o pagamento aprovado:

```text
Ticket 01 → PAID
Ticket 02 → PAID
```

Após validação na portaria:

```text
Ticket 01 → USED
```

## 11. Concorrência de Reservas

A reserva é o mecanismo responsável por garantir a preferência do primeiro cliente.

Quando dois clientes tentarem reservar o mesmo ingresso simultaneamente, apenas uma operação poderá adquirir o ingresso.

Fluxo:

```text

             Ticket
               ↓
          AVAILABLE
           ↙       ↘
     Cliente A   Cliente B
          ↓          ↓
       Reserva     Reserva
          ↓          ↓
       sucesso     rejeitada
```

A operação de reserva deverá utilizar mecanismos transacionais do banco de dados para impedir que o mesmo ingresso seja reservado simultaneamente por clientes diferentes.

A integridade do inventário será tratada no backend, e não apenas pelo frontend.

## 12. Expiração de Reservas

Toda reserva possui um horário de expiração.

```text
Reserva criada
      ↓
30 minutos
      ↓
Reserva expira
      ↓
Ingressos → AVAILABLE
```

A expiração deve ser processada de forma segura para evitar que um ingresso seja liberado enquanto ainda estiver associado a uma reserva válida ou pagamento aprovado.

O mecanismo específico de execução da expiração será definido durante a implementação.

## 13. Pagamento

O pagamento será simulado.

O fluxo será:

```text
RESERVED
   ↓
Dados do pagamento
   ↓
Confirmação
   ↓
Processamento
   ├── APPROVED
   │      ↓
   │   Ticket → PAID
   │
   └── DECLINED
          ↓
       Ticket → RESERVED
```

Um pagamento recusado não libera automaticamente os ingressos.

O cliente poderá tentar novamente enquanto a reserva estiver dentro do período de 30 minutos.

O pagamento aprovado será responsável por transformar a reserva em uma compra efetivamente confirmada.

## 14. QR Code

Cada ingresso possuirá um identificador que poderá ser representado através de QR Code.

O QR Code será acessado pelo cliente através de:

```text
Meus ingressos
      ↓
Selecionar ingresso
      ↓
Visualizar QR Code
```

O QR Code não será necessário para a recuperação do ingresso.

O ingresso permanece persistido no sistema após a conclusão do pagamento, permitindo que o cliente saia da tela de checkout e posteriormente acesse o ingresso novamente.

A validação será realizada pelo backend.

## 15. Validação de Ingressos

A portaria poderá validar um ingresso através de:

- leitura do QR Code;
- digitação manual do código.

O backend verificará:

1. se o ingresso existe;
2. se pertence ao evento correto;
3. se está pago;
4. se ainda não foi utilizado;
5. se o evento está válido.

Fluxo de sucesso:

```text
PAID
 ↓
Validação
 ↓
USED
```

Uma validação válida deve alterar o estado do ingresso de forma atômica para impedir que o mesmo ingresso seja utilizado duas vezes em uma situação de concorrência.

## 16. Compartilhamento

O cliente poderá gerar um link de compartilhamento para seu ingresso.

Fluxo:

```text
Meus ingressos
      ↓
Selecionar ingresso
      ↓
Compartilhar
      ↓
Gerar link
      ↓
Página pública do ingresso
      ↓
QR Code
```

O link compartilhado não deverá expor identificadores internos desnecessários.

A validação do ingresso continuará sendo responsabilidade do backend.

## 17. Catálogo Externo

O organizador não criará manualmente todo o conteúdo do evento.

O fluxo será:

```text
Organizador
    ↓
Criar evento
    ↓
Buscar catálogo
    ↓
API externa
    ↓
Resultados
    ↓
Selecionar show/filme
    ↓
Configurar evento
```

A integração será realizada pelo backend.

O sistema deverá manter apenas as informações necessárias para representar o evento dentro do TicketPass.

A escolha da API externa será definida durante a implementação, considerando as opções permitidas pelo desafio e a disponibilidade de uma API pública adequada.

## 18. Lotes e Preços

Os ingressos poderão ser organizados em lotes.

Exemplo:

```text
Lote 1
R$ 50
100 ingressos

        ↓

Lote 2
R$ 70
200 ingressos

        ↓

Lote 3
R$ 90
Restante
```

O lote vigente determinará o preço aplicado à nova compra.

O preço de uma compra já confirmada não será alterado posteriormente.

Eventos publicados não poderão reduzir o preço de venda após o início das vendas.

## 19. Alterações em Eventos Publicados

Eventos publicados poderão ser editados pelo organizador.

Alterações relevantes deverão passar por uma confirmação explícita.

Fluxo:

```text
Organizador
     ↓
Editar evento
     ↓
Alterar informação
     ↓
Sistema apresenta alerta
     ↓
Confirmar?
   ├── Não → descartar alteração
   └── Sim → aplicar alteração
```

O alerta deverá considerar o impacto potencial sobre clientes que já possuem ingressos.

O evento exibirá o aviso:

"Datas, horários e local podem sofrer alterações conforme a organização do evento."

## 20. Capacidade

Após a publicação, a capacidade de um evento não poderá ser reduzida.

É permitido aumentar a capacidade.

```text
500 → 800 → 1200
```

é permitido.

```text
500 → 400
```

é bloqueado.

O aumento de capacidade deverá gerar novos ingressos disponíveis para venda.

A implementação deverá garantir que o aumento não altere ou invalide ingressos já reservados ou pagos.

## 21. Cancelamento de Eventos

O organizador poderá cancelar um evento publicado.

O cancelamento exige confirmação explícita.

Após o cancelamento:

```text
Event → CANCELLED
```

Novas reservas e pagamentos serão bloqueados.

Ingressos pagos relacionados ao evento deixam de ser válidos para entrada.

O cliente será informado em Meus ingressos e poderá solicitar:

```text
REFUND
ou
CREDIT
```

O organizador não executará diretamente o reembolso.

O sistema registrará uma solicitação para representar esse processo.

## 22. Autenticação e Autorização

A aplicação terá três papéis:

```text
CLIENT
ORGANIZER
GATEKEEPER
```

O backend será responsável por autorização.

Exemplos:

```text
CLIENT
→ comprar ingressos
→ consultar seus ingressos

ORGANIZER
→ criar eventos
→ gerenciar seus eventos

GATEKEEPER
→ validar ingressos
```

A interface não será considerada mecanismo de segurança.

Mesmo que uma funcionalidade não esteja disponível visualmente para determinado usuário, o backend deverá validar o papel e a permissão antes de executar a operação.

## 23. Persistência

O PostgreSQL será utilizado como banco de dados principal.

A escolha do banco relacional está relacionada à necessidade de:

- relacionamentos entre entidades;
- integridade referencial;
- transações;
- controle de concorrência;
- consistência do inventário;
- rastreabilidade das operações.

As alterações de banco serão versionadas através de migrations.

## 24. Transações

Operações críticas de negócio deverão ser executadas dentro de transações.

Exemplos:

- criação de reserva;
- confirmação de pagamento;
- expiração de reserva;
- validação de ingresso;
- cancelamento de evento.

A reserva e a validação de ingresso são especialmente sensíveis à concorrência.

A consistência será garantida no backend e no banco de dados.

## 25. Tratamento de Erros

A API deverá utilizar respostas HTTP coerentes com o resultado da operação.

Exemplos:

```text
400 → dados inválidos
401 → não autenticado
403 → sem permissão
404 → recurso não encontrado
409 → conflito de estado ou concorrência
422 → regra de negócio não permitida
500 → erro inesperado
```

Erros de negócio deverão retornar informações suficientes para que o frontend apresente uma mensagem compreensível ao usuário.

Exemplo:

```text
Não foi possível reservar este ingresso.
Ele foi reservado por outro cliente.
```

## 26. Testes

A estratégia de testes será organizada por nível.

### Testes unitários

Serão utilizados para validar regras de negócio isoladas.

Exemplos:

- expiração de reserva;
- alteração de preço;
- alteração de capacidade;
- cancelamento de evento;
- transições de estado.

### Testes de integração

Serão utilizados para validar:

- persistência;
- transações;
- reserva;
- pagamento;
- validação de ingresso;
- integração entre módulos.

### Testes de API

Serão utilizados para validar os principais fluxos REST.

### Cenários críticos

Os seguintes cenários deverão possuir cobertura:

- dois clientes tentando reservar o mesmo ingresso;
- reserva expirando;
- pagamento recusado;
- nova tentativa de pagamento;
- pagamento aprovado;
- ingresso utilizado duas vezes;
- ingresso de outro evento;
- redução de capacidade;
- alteração de preço;
- cancelamento de evento.

## 27. Deploy

A aplicação será preparada para execução em ambiente publicado.

Componentes:

```text
Frontend
    ↓
Backend
    ↓
PostgreSQL
```

O ambiente local será reproduzível através de Docker.

O deploy deverá disponibilizar:

- frontend acessível publicamente;
- backend acessível pelo frontend;
- banco de dados persistente;
- variáveis de ambiente configuradas;
- integração com a API externa funcionando.

A estratégia e os provedores específicos de deploy serão definidos durante a implementação.

## 28. Observabilidade e Logs

O backend deverá registrar informações relevantes para diagnóstico.

Exemplos:

- criação de reserva;
- expiração de reserva;
- pagamento aprovado ou recusado;
- validação de ingresso;
- cancelamento de evento;
- erros de integração externa.

Informações sensíveis, especialmente dados de pagamento, não deverão ser registradas nos logs.

## 29. Segurança

A aplicação deverá considerar:

- autenticação;
- autorização por papel;
- validação de entrada;
- proteção de endpoints;
- não exposição de dados sensíveis;
- controle de acesso aos ingressos;
- validação de ingressos no backend;
- proteção contra reutilização de ingressos;
- controle de concorrência no inventário.

O QR Code será tratado como mecanismo de identificação do ingresso, e não como substituto da validação realizada pelo backend.

## 30. Decisões Arquiteturais

### Monólito Modular

Escolhido para manter o sistema simples de desenvolver e operar dentro do prazo do desafio, preservando separação entre os principais contextos do domínio.

### PostgreSQL

Escolhido devido à necessidade de consistência transacional, relacionamentos e controle de concorrência.

### React + TypeScript + Vite

Escolhido para construir uma aplicação web moderna, componentizada e alinhada ao requisito do desafio.

### Java + Spring Boot

Escolhido para implementar a API e as regras de negócio com uma estrutura adequada para domínio, transações, segurança e integração.

### Docker

Escolhido para padronizar o ambiente de desenvolvimento e facilitar a execução dos serviços.

### REST

Escolhido como mecanismo de comunicação entre frontend e backend pela simplicidade e adequação ao escopo da aplicação.

### Sem Microsserviços

Microsserviços não serão utilizados nesta versão.

A complexidade operacional de múltiplos serviços não oferece benefício proporcional ao tamanho atual do sistema.

A separação modular permite uma futura extração de serviços caso necessidades reais do produto justifiquem essa evolução.

## 31. Evolução Futura

A arquitetura deverá permitir evolução sem exigir uma reescrita completa do sistema.

Possíveis evoluções:

- extração de módulos para serviços independentes;
- integração com gateway de pagamento real;
- serviço dedicado de notificações;
- processamento assíncrono de eventos;
cache;
- observabilidade avançada;
- escalabilidade horizontal;
- integração com diferentes catálogos externos.

Essas possibilidades não fazem parte do escopo atual e não devem adicionar complexidade desnecessária à primeira versão.