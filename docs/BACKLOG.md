# TicketPass — Backlog

## 1. Priorização

### Must Have
Funcionalidades necessárias para cumprir o fluxo principal do desafio e entregar uma experiência ponta a ponta funcional.

### Should Have
Funcionalidades importantes para completar a experiência e demonstrar qualidade de produto e engenharia.

### Could Have
Funcionalidades complementares que podem ser implementadas caso o fluxo principal esteja concluído e haja tempo disponível.

---

# 2. Setup e Fundação

## Must Have

- [ ] Criar estrutura inicial do projeto.
- [ ] Configurar frontend.
- [ ] Configurar backend.
- [ ] Configurar banco de dados.
- [ ] Configurar variáveis de ambiente.
- [ ] Configurar integração entre frontend e backend.
- [ ] Configurar controle de acesso por perfil.
- [ ] Definir estratégia de tratamento de erros.
- [ ] Configurar documentação inicial do projeto.

---

# 3. Autenticação e Perfis

## Must Have

- [ ] Implementar autenticação.
- [ ] Implementar perfil Cliente.
- [ ] Implementar perfil Organizador.
- [ ] Implementar perfil Portaria.
- [ ] Restringir acesso às funcionalidades conforme o perfil do usuário.

---

# 4. Catálogo Externo

## Must Have

- [ ] Integrar catálogo externo de eventos.
- [ ] Permitir busca no catálogo.
- [ ] Exibir resultados do catálogo.
- [ ] Permitir seleção de um show ou filme.
- [ ] Utilizar os dados selecionados como base para criação do evento.

---

# 5. Eventos — Organizador

## Must Have

- [ ] Criar evento a partir de item selecionado no catálogo.
- [ ] Configurar data.
- [ ] Configurar horário.
- [ ] Configurar local.
- [ ] Configurar capacidade.
- [ ] Configurar lotes de ingressos.
- [ ] Configurar preços de ingressos.
- [ ] Revisar informações antes da publicação.
- [ ] Publicar evento.
- [ ] Listar eventos do organizador.
- [ ] Visualizar evento.
- [ ] Editar evento publicado.
- [ ] Exigir confirmação antes de aplicar alterações em evento publicado.
- [ ] Impedir redução da capacidade de evento publicado.
- [ ] Permitir aumento da capacidade.
- [ ] Permitir criação de novos lotes.
- [ ] Impedir redução de preço após início das vendas.
- [ ] Permitir cancelamento do evento.
- [ ] Exigir confirmação antes do cancelamento.

---

# 6. Eventos — Cliente

## Must Have

- [ ] Listar eventos publicados.
- [ ] Buscar eventos.
- [ ] Visualizar detalhes do evento.
- [ ] Exibir data e horário.
- [ ] Exibir local.
- [ ] Exibir disponibilidade.
- [ ] Exibir informações dos lotes e preços.
- [ ] Exibir aviso sobre possíveis alterações de data, horário e local.

---

# 7. Inventário e Ingressos

## Must Have

- [ ] Criar ingressos individuais de acordo com a capacidade do evento.
- [ ] Controlar estado individual dos ingressos.
- [ ] Implementar estado `AVAILABLE`.
- [ ] Implementar estado `RESERVED`.
- [ ] Implementar estado `PAID`.
- [ ] Implementar estado `USED`.
- [ ] Implementar expiração de reserva.
- [ ] Retornar ingressos expirados para `AVAILABLE`.
- [ ] Impedir que um ingresso seja reservado simultaneamente por clientes diferentes.
- [ ] Garantir integridade do inventário em situações de concorrência.

---

# 8. Reserva

## Must Have

- [ ] Permitir seleção da quantidade de ingressos.
- [ ] Permitir seleção do tipo de ingresso: meia ou inteira.
- [ ] Criar reserva antes do pagamento.
- [ ] Associar ingressos à reserva.
- [ ] Bloquear os ingressos durante a reserva.
- [ ] Definir duração da reserva em 30 minutos.
- [ ] Exibir tempo restante da reserva.
- [ ] Permitir cancelamento voluntário da reserva.
- [ ] Liberar ingressos quando a reserva for cancelada.
- [ ] Expirar automaticamente reservas após 30 minutos.
- [ ] Liberar ingressos de reservas expiradas.

---

# 9. Pagamento

## Must Have

- [ ] Exibir opções de pagamento.
- [ ] Permitir preenchimento dos dados de pagamento.
- [ ] Exibir tela de confirmação dos dados.
- [ ] Permitir desistência antes da confirmação.
- [ ] Processar pagamento simulado.
- [ ] Simular pagamento aprovado.
- [ ] Simular pagamento recusado.
- [ ] Manter reserva ativa após pagamento recusado.
- [ ] Permitir nova tentativa durante o período da reserva.
- [ ] Alterar ingresso de `RESERVED` para `PAID` após aprovação.
- [ ] Encaminhar o cliente para "Meus ingressos" após pagamento aprovado.

---

# 10. Meus Ingressos

## Must Have

- [ ] Listar ingressos do cliente.
- [ ] Exibir informações do evento.
- [ ] Exibir tipo do ingresso.
- [ ] Exibir status do ingresso.
- [ ] Permitir selecionar um ingresso.
- [ ] Exibir QR Code do ingresso.
- [ ] Diferenciar ingresso disponível de ingresso já utilizado.
- [ ] Exibir ingresso relacionado a evento cancelado.
- [ ] Exibir ingresso cancelado quando aplicável.

---

# 11. Compartilhamento

## Should Have

- [ ] Gerar link de compartilhamento para o ingresso.
- [ ] Permitir acesso ao ingresso através do link.
- [ ] Exibir QR Code através do ingresso compartilhado.
- [ ] Evitar exposição desnecessária de identificadores internos.

---

# 12. Portaria

## Must Have

- [ ] Criar área específica para Portaria.
- [ ] Permitir leitura do QR Code pela câmera.
- [ ] Disponibilizar digitação manual do código como alternativa.
- [ ] Validar existência do ingresso.
- [ ] Validar vínculo do ingresso com o evento.
- [ ] Validar status do ingresso.
- [ ] Identificar ingresso já utilizado.
- [ ] Impedir reutilização do ingresso.
- [ ] Alterar ingresso de `PAID` para `USED` após validação válida.
- [ ] Exibir resultado de validação para o operador.

---

# 13. Cancelamento de Evento

## Must Have

- [ ] Permitir cancelamento pelo organizador.
- [ ] Exigir confirmação antes do cancelamento.
- [ ] Alterar estado do evento para cancelado.
- [ ] Impedir utilização de ingressos de evento cancelado.
- [ ] Invalidar ingressos pagos relacionados ao evento.
- [ ] Exibir aviso de evento cancelado em "Meus ingressos".

## Should Have

- [ ] Permitir solicitação de reembolso pelo cliente.
- [ ] Permitir solicitação de crédito para outro evento.
- [ ] Registrar a solicitação realizada pelo cliente.
- [ ] Representar o processo de reembolso/crédito de forma simulada.

---

# 14. UX e Design System

## Should Have

- [ ] Definir identidade visual do TicketPass.
- [ ] Definir paleta de cores.
- [ ] Definir tipografia.
- [ ] Definir espaçamentos.
- [ ] Definir componentes principais.
- [ ] Definir estados de componentes.
- [ ] Definir estados de sucesso, erro, alerta e informação.
- [ ] Garantir consistência visual entre os três perfis.
- [ ] Garantir feedback visual para ações críticas.
- [ ] Garantir confirmação para operações potencialmente destrutivas.

---

# 15. Segurança e Integridade

## Must Have

- [ ] Garantir autorização por perfil.
- [ ] Impedir acesso de usuários a funcionalidades de outros perfis.
- [ ] Garantir que um ingresso não seja vendido duas vezes.
- [ ] Garantir que uma reserva não seja adquirida simultaneamente por dois clientes.
- [ ] Garantir que um ingresso pago não possa ser reutilizado.
- [ ] Validar ingresso e evento durante a entrada.
- [ ] Garantir que o QR Code não possa ser utilizado para forjar um ingresso válido.

---

# 16. Testes

## Must Have

- [ ] Testar criação de evento.
- [ ] Testar publicação de evento.
- [ ] Testar reserva de ingresso.
- [ ] Testar expiração de reserva.
- [ ] Testar cancelamento de reserva.
- [ ] Testar pagamento aprovado.
- [ ] Testar pagamento recusado.
- [ ] Testar nova tentativa de pagamento.
- [ ] Testar concorrência na reserva.
- [ ] Testar validação de ingresso.
- [ ] Testar tentativa de utilização duplicada.
- [ ] Testar ingresso de evento diferente.
- [ ] Testar alteração de capacidade.
- [ ] Testar redução indevida de capacidade.
- [ ] Testar alteração de preço.
- [ ] Testar cancelamento de evento.

---

# 17. Documentação

## Must Have

- [ ] Atualizar README com descrição do projeto.
- [ ] Documentar requisitos para execução.
- [ ] Documentar variáveis de ambiente.
- [ ] Documentar usuários de teste.
- [ ] Documentar principais decisões técnicas.
- [ ] Documentar limitações conhecidas.

## Should Have

- [ ] Documentar uso de IA durante o desenvolvimento.
- [ ] Registrar decisões relevantes tomadas durante a implementação.
- [ ] Registrar diferenças entre o escopo planejado e o escopo entregue.

---

# 18. Deploy

## Should Have

- [ ] Preparar aplicação para ambiente de produção.
- [ ] Configurar deploy do frontend.
- [ ] Configurar deploy do backend.
- [ ] Configurar banco de dados de produção.
- [ ] Configurar variáveis de ambiente de produção.
- [ ] Validar fluxo completo em ambiente publicado.

---

# 19. Could Have

Funcionalidades que só devem ser consideradas após a conclusão do fluxo principal:

- [ ] Melhorias adicionais de filtros e busca.
- [ ] Dashboard com métricas para o organizador.
- [ ] Histórico detalhado de alterações do evento.
- [ ] Melhorias adicionais na experiência de compartilhamento.
- [ ] Funcionalidades adicionais de acessibilidade além dos requisitos essenciais.