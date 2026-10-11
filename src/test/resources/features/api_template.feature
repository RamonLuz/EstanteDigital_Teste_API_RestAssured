@template
Feature: Modelo de teste de API
  Como pessoa responsavel pela qualidade da EstanteDigital
  Quero validar respostas HTTP da API
  Para verificar o contrato acordado com o servico

  Scenario: Consultar um endpoint de disponibilidade
    When envio uma requisicao GET para "/health"
    Then o status da resposta deve ser 200