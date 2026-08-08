# TicketPass — Product Vision

## 1. Visão do Produto

O **TicketPass** é uma plataforma de eventos e ingressos que conecta organizadores, clientes e equipes de portaria em um fluxo integrado de criação, venda, pagamento e validação de ingressos.

A plataforma permite que um organizador crie eventos a partir de informações obtidas de um catálogo externo de shows ou filmes, configure sua oferta de ingressos e publique o evento.

Clientes podem descobrir eventos, reservar ingressos, realizar um pagamento simulado e acessar seus ingressos digitais com QR Code.

Na entrada do evento, a portaria pode validar o ingresso por leitura do QR Code ou por digitação manual do código.

O produto prioriza um fluxo completo de ponta a ponta, regras de negócio claras e uma experiência simples para cada perfil de usuário.

## 2. Problema

A plataforma deve representar, de forma integrada, o ciclo de vida de um ingresso:

```text
Evento
  ↓
Disponibilidade
  ↓
Reserva
  ↓
Pagamento
  ↓
Ingresso
  ↓
Compartilhamento
  ↓
Validação
```

O principal desafio de produto está em garantir que esse ciclo aconteça de forma consistente, especialmente em situações como:

- concorrência pela disponibilidade de ingressos;
- reservas temporárias;
- pagamentos recusados;
- expiração de reservas;
- alterações realizadas pelo organizador;
- cancelamento de eventos;
- validação de um ingresso mais de uma vez.

## 3. Usuários

### 3.1 Cliente

Responsável por:

- navegar pelos eventos publicados;
- consultar detalhes do evento;
- selecionar ingressos;
- escolher quantidade e tipo de ingresso;
- reservar ingressos;
- realizar o pagamento;
- consultar seus ingressos;
- visualizar o QR Code;
- compartilhar o ingresso;
- solicitar reembolso ou crédito em caso de cancelamento do evento.

### 3.2 Organizador

Responsável por:

- criar eventos;
- selecionar conteúdos do catálogo externo;
- configurar data, horário, local, capacidade e preços;
- publicar eventos;
- gerenciar eventos publicados;
- alterar informações permitidas;
- criar novos lotes de ingressos;
- aumentar a capacidade do evento;
- cancelar eventos.

### 3.3 Portaria

Responsável por:

- acessar a área de validação;
- ler o QR Code do ingresso;
- digitar manualmente o código como alternativa;
- validar o ingresso;
- identificar ingressos inválidos, já utilizados ou pertencentes a outro evento.

## 4. Objetivos do Produto

### Objetivo principal

Entregar uma experiência completa de compra e validação de ingressos, contemplando o fluxo desde a descoberta do evento até a entrada do cliente.

### Objetivos específicos

- Garantir que um ingresso não seja vendido para mais de um cliente.
- Garantir que reservas tenham duração limitada.
- Permitir novas tentativas após pagamentos recusados.
- Disponibilizar o ingresso digital após a confirmação do pagamento.
- Permitir compartilhamento seguro do ingresso.
- Impedir que um ingresso válido seja utilizado mais de uma vez.
- Permitir que organizadores gerenciem seus eventos.
- Permitir alterações controladas em eventos publicados.
- Tratar o cancelamento de eventos e as solicitações decorrentes pelos clientes.

## 5. Fluxos Principais

### 5.1 Fluxo do Cliente

```text
Autenticação
      ↓
Escolha do evento
      ↓
Detalhes do evento
      ↓
Seleção dos ingressos
      ├── Quantidade
      └── Tipo: meia / inteira
      ↓
Reserva
      ↓
Forma de pagamento
      ↓
Dados do pagamento
      ↓
Confirmação dos dados
      ├── Evento
      ├── Quantidade
      ├── Tipo
      ├── Valor
      └── Dados do pagamento
      │
      ├── Desistir
      │      ↓
      │   Reserva cancelada
      │      ↓
      │   Ingressos disponíveis
      │
      └── Confirmar
             ↓
        Processamento
          ├── Negado
          │     ↓
          │  Reserva permanece ativa
          │     ↓
          │  Nova tentativa
          │
          └── Aprovado
                 ↓
              PAID
                 ↓
          Meus ingressos
                 ↓
          Selecionar ingresso
                 ↓
              QR Code
```

### 5.2 Fluxo do Organizador

Autenticação
      ↓
Área do organizador
      ↓
Criar evento
      ↓
Catálogo externo
      ↓
Selecionar show / filme
      ↓
Configuração do evento
      ├── Data
      ├── Horário
      ├── Local
      ├── Capacidade
      └── Lotes / preços
      ↓
Revisão
      ↓
Publicação
      ↓
Evento publicado
```
Após a publicação:

```text
Meus eventos
      ↓
Selecionar evento
      ├── Visualizar
      ├── Editar
      └── Cancelar
```
Alterações realizadas após a publicação devem passar por uma etapa adicional de confirmação antes de serem aplicadas.

O sistema deve alertar o organizador quando uma alteração puder impactar clientes que já possuem ingressos.

### 5.3 Fluxo da Portaria

```text
Autenticação
      ↓
Área da portaria
      ↓
Ler QR Code
      OU
Digitar código
      ↓
Validar ingresso
      ↓
Resultado
```
Possíveis resultados:

- Ingresso válido;
- Ingresso inválido;
- Ingresso já utilizado;
- Ingresso pertencente a outro evento.

Quando um ingresso válido é utilizado, seu estado passa a representar que a entrada foi realizada.

## 6. Regras de Negócio

### 6.1 Inventário de Ingressos

A capacidade de um evento representa a quantidade total de ingressos disponibilizados.

Cada ingresso é tratado individualmente.

Estados principais:

```text
AVAILABLE
    ↓
RESERVED
    ↓
PAID
    ↓
USED
```

Uma reserva expirada segue:

```text
RESERVED
    ↓
EXPIRED
    ↓
AVAILABLE
```

### 6.2 Reservas

- A reserva acontece antes do pagamento.
- A reserva funciona como um "ingresso no carrinho".
- Uma reserva possui duração de 30 minutos.
- O primeiro cliente que reservar determinado ingresso possui preferência sobre ele.
- Enquanto a reserva estiver ativa, o ingresso não fica disponível para outro cliente.
- Se o cliente desistir antes do pagamento, a reserva é cancelada e os ingressos retornam à disponibilidade.
- Se o pagamento for recusado, a reserva permanece ativa.
- O cliente pode tentar realizar o pagamento novamente durante os 30 minutos.
- Caso os 30 minutos terminem sem pagamento aprovado, a reserva expira e os ingressos retornam à disponibilidade.

### 6.3 Pagamento

O pagamento é simulado.

O fluxo contempla:

```text
Reserva
   ↓
Dados do pagamento
   ↓
Confirmação
   ↓
Processamento
   ├── Aprovado
   └── Negado
```
Pagamento aprovado:

```text
RESERVED → PAID
```

Pagamento negado:

```text
RESERVED → RESERVED
```

O ingresso não depende da tela de pagamento para ser recuperado. Após a aprovação, o cliente é direcionado para Meus ingressos, onde poderá selecionar o ingresso e visualizar seu QR Code.

### 6.4 Ingressos

O ingresso é criado durante a reserva e permanece associado ao cliente e ao evento durante seu ciclo de vida.

O QR Code é disponibilizado ao cliente através da área Meus ingressos.

Somente ingressos com pagamento aprovado podem ser utilizados para entrada no evento.

Um ingresso validado não pode ser utilizado novamente.

### 6.5 Compartilhamento

O cliente poderá compartilhar um ingresso através de um link gerado pela aplicação.

O link permite acessar uma representação do ingresso e seu QR Code, possibilitando sua apresentação na entrada do evento.

## 7. Regras de Eventos

### 7.1 Criação

O organizador cria o evento a partir de informações provenientes de um catálogo externo de shows ou filmes.

Ao criar o evento, configura:

- data;
- horário;
- local;
- capacidade;
- lotes e preços dos ingressos.

### 7.2 Alterações após publicação

Um evento publicado pode ser alterado pelo organizador.

Alterações realizadas após a publicação devem passar por uma confirmação explícita antes de serem aplicadas.

O sistema deve alertar o organizador quando uma alteração puder impactar clientes que já possuem ingressos.

O evento apresenta o aviso:

> **"Datas, horários e local podem sofrer alterações conforme a organização do evento."**

### 7.3 Capacidade

A capacidade de um evento publicado **não pode ser reduzida**.

Ela pode ser aumentada, por exemplo, quando o evento é transferido para um local com maior capacidade.

Exemplo:

```text
500 → 800 → 1200

é permitido.

```text
500 → 400
```

não é permitido.

### 7.4 Preços e Lotes

O evento pode possuir diferentes lotes de ingressos.

A estratégia de preços permite que ingressos sejam vendidos por valores progressivamente maiores conforme novos lotes são disponibilizados.

Exemplo:

```text
Lote 1 → R$ 50
Lote 2 → R$ 70
Lote 3 → R$ 90
```

O preço não pode ser reduzido após o início das vendas.

Uma redução posterior que faça clientes comprarem o mesmo tipo de ingresso por um preço menor não é permitida.

Clientes que já realizaram uma compra mantêm o preço originalmente pago.

## 8. Cancelamento de Evento

O organizador pode cancelar um evento publicado.

O cancelamento exige confirmação explícita antes de ser aplicado.

Após o cancelamento:

```text
Evento → CANCELADO

Ingressos pagos relacionados ao evento deixam de ser válidos.

Na área Meus ingressos, o cliente visualizará uma mensagem informando o cancelamento e poderá solicitar:

- reembolso; ou
- crédito para outro evento.

A solicitação é realizada pelo cliente e não diretamente pelo organizador.

O pagamento é simulado e, portanto, o processo de reembolso também será representado de forma simulada pela aplicação.

## 9. Estados Visíveis em "Meus Ingressos"

O cliente poderá encontrar diferentes situações para seus ingressos.

### Ingresso disponível

O ingresso foi pago e ainda não foi utilizado.

Permite acesso ao QR Code.

### Ingresso validado

O ingresso já foi utilizado para entrada no evento.

### Ingresso cancelado

O ingresso deixou de ser válido.

### Evento cancelado

O evento relacionado ao ingresso foi cancelado.

Nesse caso, o cliente poderá acessar as opções de solicitação de reembolso ou crédito.

## 10. Escopo

### In Scope

- Autenticação com três papéis;
- Catálogo externo de shows ou filmes;
- Criação e gerenciamento de eventos;
- Publicação de eventos;
- Busca e navegação de eventos;
- Inventário individual de ingressos;
- Reservas temporárias;
- Pagamento simulado;
- Ingressos de meia e inteira;
- Lotes de ingressos;
- Meus ingressos;
- QR Code;
- Compartilhamento por link;
- Validação por QR Code;
- Digitação manual do código;
- Validação única do ingresso;
- Alterações em eventos publicados;
- Aumento de capacidade;
- Cancelamento de eventos;
- Solicitação de reembolso ou crédito.

### Out of Scope

Para manter o escopo compatível com o prazo do desafio, não fazem parte do produto:

- Nota fiscal;
- Revenda de ingressos entre usuários;
- Aplicativo nativo;
- Recuperação de senha;
- Envio de ingresso por e-mail;
- Transações financeiras reais.

## 11. Critérios de Sucesso

O TicketPass será considerado funcional quando for possível percorrer o fluxo completo:

```text
Organizador
    ↓
Cria e publica evento
    ↓
Cliente encontra evento
    ↓
Reserva ingresso
    ↓
Realiza pagamento
    ↓
Recebe ingresso
    ↓
Acessa QR Code
    ↓
Portaria valida
    ↓
Ingresso passa a ser utilizado
```

Também devem funcionar corretamente os principais cenários alternativos:

- pagamento recusado;
- nova tentativa de pagamento;
- expiração da reserva;
- desistência da compra;
- ingresso já utilizado;
- ingresso inválido;
- ingresso de evento diferente;
- alteração de evento;
- aumento de capacidade;
- mudança de preço por lote;
- cancelamento do evento.

## 12. Princípios do Produto

### Fluxo completo antes de complexidade

Priorizar uma experiência ponta a ponta funcional antes de adicionar funcionalidades secundárias.

### Regras de negócio explícitas

Comportamentos importantes do sistema devem ser definidos e previsíveis.

### Segurança e integridade do ingresso

O sistema deve impedir venda duplicada e reutilização de ingressos.

### Experiência do usuário

Alterações que possam impactar clientes devem ser apresentadas com clareza e exigir confirmação quando necessário.