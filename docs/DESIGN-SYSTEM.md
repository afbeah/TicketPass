# TicketPass — Design System

## 1. Visão geral

O Design System do TicketPass define os princípios visuais e padrões utilizados na construção da interface da aplicação.

Seu objetivo é garantir:

- consistência visual entre as telas;
- reutilização de padrões;
- clareza na comunicação com o usuário;
- facilidade de manutenção;
- experiência coerente entre os diferentes fluxos da aplicação.

O Design System foi construído utilizando CSS próprio, sem dependência de uma biblioteca externa de componentes.

---

## 2. Princípios visuais

O TicketPass utiliza uma linguagem visual:

- dark-first;
- minimalista;
- moderna;
- orientada à experiência do usuário;
- com alto contraste;
- com destaque visual para ações importantes.

A interface busca reduzir elementos desnecessários e priorizar a informação e a ação principal de cada tela.

---

## 3. Paleta de cores

### Background principal

```text
#0D0D12
```

Utilizado como fundo principal da aplicação.

### Surface

```text
#16161D
```

Utilizado em cards, formulários e áreas de conteúdo.

### Surface secundária

```txt
#1D1B29
```

Utilizado em seções de destaque, pagamentos e elementos complementares.

### Bordas

```text
#30303A
```

Utilizado para delimitar cards, inputs e elementos estruturais.

### Bordas secundárias

```text
#24242C
```

Utilizado principalmente em divisores e elementos de navegação.

### Texto principal

```txt
#FFFFFF
```

Utilizado para títulos e informações de maior importância.

### Texto secundário

```txt
#A5A5B0
```
Utilizado para descrições, textos auxiliares e navegação.

### Texto terciário

```txt
#8F8F9B
```

Utilizado para informações complementares.

### Cor de destaque

```txt
#A78BFA
```
Utilizada como cor principal de ação e destaque visual.

É aplicada em:

botões;
links;
estados ativos;
elementos de destaque;
eyebrow labels.

### Erros

```txt
#FCA5A5
```

Utilizada para mensagens de erro e feedback negativo.

### Sucesso

```txt
#193D2A
```

Background utilizado para mensagens de sucesso.

```txt
#8FF0B0
```

Cor utilizada para o texto de sucesso.

## 4. Tipografia

A família tipográfica principal é:

`Inter`

Com fallback para fontes disponíveis no sistema:

```txt
system-ui
-apple-system
BlinkMacSystemFont
"Segoe UI"
sans-serif
```

### Títulos

Os títulos utilizam:

- peso elevado;
- tamanho maior;
- espaçamento negativo entre letras;
- alto contraste.

O objetivo é criar hierarquia visual clara.

#### Texto

Textos de descrição utilizam tamanhos menores e cores secundárias para preservar a hierarquia sem competir com os títulos.

## 5. Espaçamento

O layout utiliza espaçamentos consistentes entre os elementos.

Os principais padrões utilizados incluem:

- 8px para pequenos espaçamentos;
- 10px para agrupamentos próximos;
- 16px para espaçamento entre controles;
- 18px para conteúdos relacionados;
- 20px para padding de cards;
- 24px para áreas de maior separação;
- 32px para seções internas;
- 48px ou mais para separação entre grandes áreas.

O espaçamento é utilizado para criar hierarquia e evitar excesso de informação visual.

## 6. Bordas e arredondamento

Os componentes utilizam bordas discretas para separar visualmente as áreas da aplicação.

Padrão de borda:

`1px solid #30303A`
`
O border-radius utilizado varia de acordo com o componente, com os principais padrões:

```txt
8px
12px
14px
```


Botões e inputs utilizam principalmente `8px`.

Cards utilizam principalmente `12px`.

Cards de autenticação utilizam `14px`.

## 7. Botões

Os botões possuem como característica principal:

- altura mínima consistente;
- bordas arredondadas;
- destaque visual;
- texto com peso elevado;
- cursor indicando interação.

### Botão principal

```txt
background: #A78BFA
color: #15151B
```
Utilizado para ações principais como:

- buscar eventos;
- comprar ingresso;
- realizar pagamento;
- compartilhar ingresso;
- validar ingresso.

### Estados

O botão possui feedback visual através de:

- alteração de brilho no hover;
- redução de opacidade quando desabilitado;
- cursor not-allowed quando indisponível.

## 8. Inputs

Os campos de entrada utilizam:
```txt
background: #0D0D12
border: 1px solid #30303A
color: #FFFFFF

```

Os inputs possuem:

- altura mínima consistente;
- padding horizontal;
- bordas arredondadas;
- placeholder com menor contraste.

### Estado de foco

Ao receber foco, o input utiliza a cor principal do Design System:

```txt
border-color: #A78BFA
```

Isso fornece indicação visual clara de qual campo está ativo.

## 9. Cards

Cards são utilizados para representar:

- eventos;
- ingressos;
- informações relacionadas à compra.

Características principais:

```
background: #16161D
border: 1px solid #30303A
border-radius: 12px
```

Os cards possuem padding interno para separar visualmente título, descrição, informações e ações.

## 10. Eventos

Os eventos possuem uma hierarquia visual composta por:

1. imagem ou representação visual;
2. data;
3. título;
4. localização;
5. descrição;
6. ação principal.

A data utiliza a cor de destaque:

```
#A78BFA
```

O título utiliza o texto principal:

```
#FFFFFF
```

Informações secundárias utilizam:
```
#8F8F9B
```
## 11. Feedback e estados

O sistema utiliza mensagens visuais para comunicar o resultado das operações.

Sucesso

Utiliza background escuro esverdeado e texto verde claro.

Exemplo:
```
Pagamento aprovado!
Seu ingresso foi confirmado.
```

Erro

Utiliza texto em tom avermelhado.

Exemplo:

```
Não foi possível carregar seus ingressos.
```

Loading

Durante operações assíncronas, o texto do botão ou da interface é alterado para indicar processamento.

Exemplos:

```
Buscando...
Reservando...
Processando...
Carregando seus ingressos...
``` 

Isso evita que o usuário fique sem feedback durante uma operação.

## 12. Portaria

A área de Portaria utiliza os mesmos padrões visuais do restante da aplicação.

O fluxo de validação apresenta estados distintos para comunicar o resultado da operação.

Estados previstos:

```
VALID
INVALID
WRONG_EVENT
ALREADY_USED
```

A interface deve comunicar claramente se o ingresso pode ou não ser utilizado.

## 13. Responsividade

O layout utiliza CSS Grid e Media Queries para adaptar a interface a diferentes tamanhos de tela.

O layout principal possui breakpoints para:

- desktop;
- tablet;
- dispositivos móveis.

Em telas menores:

- a navegação é simplificada;
- o grid de eventos passa para uma coluna;
- os campos de busca passam a ocupar linhas separadas;
- os títulos utilizam tamanhos adaptáveis.

A interface utiliza clamp() em títulos para permitir adaptação fluida da tipografia.

## 14. Acessibilidade

Algumas decisões de acessibilidade consideradas no desenvolvimento incluem:

- alto contraste entre texto e background;
- estados de foco visíveis nos inputs;
- textos alternativos para imagens;
- utilização de elementos HTML semânticos;
- indicação textual dos estados das operações;
- tamanho adequado das áreas de interação;
- feedback visual para estados de erro, sucesso e loading.

A acessibilidade é considerada como parte da experiência do usuário e não apenas como uma etapa posterior.

## 15. Componentes e padrões reutilizados

Os principais padrões visuais utilizados na aplicação incluem:

- Header;
- navegação;
- Hero;
- Section Header;
- Search;
- Event Card;
- Ticket Card;
- Login Card;
- Payment Section;
- Success Message;
- Error Message;
- Loading Message;
- Primary Button;
- Input.

Esses padrões formam a base visual das diferentes funcionalidades do TicketPass.

## 16. Evolução futura

O Design System pode evoluir para uma estrutura de componentes mais formal caso o projeto cresça.

Possíveis evoluções:

- criação de tokens CSS centralizados;
- biblioteca de componentes reutilizáveis;
- documentação visual dos componentes;
- definição formal de estados;
- testes de acessibilidade;
- Storybook;
- expansão dos tokens de espaçamento e tipografia.

No escopo atual, a prioridade foi estabelecer uma linguagem visual consistente e reutilizável sem adicionar complexidade desnecessária ao projeto.