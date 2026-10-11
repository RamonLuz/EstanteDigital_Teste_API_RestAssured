Feature: Registro de vendas
  @smoke
  Scenario: CT011 - Registrar venda com cliente, funcionario e livro existentes
    When envio uma requisicao POST para "/books" com o payload "sales/create-sale-book.json"
    Then o status da resposta deve ser 201

    When envio uma requisicao POST para "/customers" com o payload "sales/create-sale-customer.json"
    Then o status da resposta deve ser 201

    When envio uma requisicao POST para "/employees" com o payload "sales/create-sale-employee.json"
    Then o status da resposta deve ser 201

    When envio uma requisicao POST para "/sales" com o payload "sales/create-sale.json"
    Then o status da resposta deve ser 201
    And o campo JSON "id" deve ser um numero positivo
    And o campo JSON "customer.cpf" deve ser "${unique}"
    And o campo JSON "employee.registrationNumber" deve ser "${registration}"
    And o campo JSON "book.title" deve ser "Livro de Venda ${unique}"