Feature: Gerenciamento de clientes
  @smoke
  Scenario: CT006 - Cadastrar um cliente
    When envio uma requisicao POST para "/customers" com o payload "customers/create-customer.json"
    Then o status da resposta deve ser 201
    And o campo JSON "id" deve ser um numero positivo
    And o campo JSON "cpf" deve ser "${unique}"

  @smoke
  Scenario: CT007 - Consultar um cliente por ID
    When envio uma requisicao POST para "/customers" com o payload "customers/create-customer.json"
    When envio uma requisicao GET para "/customers/${id}"
    Then o status da resposta deve ser 200
    And o campo JSON "status" deve ser "ACTIVE"

  @regression
  Scenario: CT008 - Rejeitar cliente com CPF invalido
    When envio uma requisicao POST para "/customers" com o payload "customers/invalid-customer.json"
    Then o status da resposta deve ser 400
    And o campo JSON "message" deve ser "CPF must have 11 digits"