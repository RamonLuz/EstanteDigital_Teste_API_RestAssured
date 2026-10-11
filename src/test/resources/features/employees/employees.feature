Feature: Gerenciamento de funcionarios
  @smoke
  Scenario: CT009 - Cadastrar um funcionario
    When envio uma requisicao POST para "/employees" com o payload "employees/create-employee.json"
    Then o status da resposta deve ser 201
    And o campo JSON "registrationNumber" deve ser "${registration}"

  @smoke
  Scenario: CT010 - Atualizar o status de um funcionario
    When envio uma requisicao POST para "/employees" com o payload "employees/create-employee.json"
    When envio uma requisicao PUT para "/employees/${id}" com o payload "employees/update-employee.json"
    Then o status da resposta deve ser 200
    And o campo JSON "status" deve ser "INACTIVE"