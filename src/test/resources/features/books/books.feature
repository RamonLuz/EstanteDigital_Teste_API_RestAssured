Feature: Gerenciamento de livros
  @smoke
  Scenario: CT002 - Cadastrar um livro
    When envio uma requisicao POST para "/books" com o payload "books/create-book.json"
    Then o status da resposta deve ser 201
    And o campo JSON "id" deve ser um numero positivo
    And o campo JSON "title" deve ser "Livro BDD ${unique}"

  @smoke
  Scenario: CT003 - Consultar um livro por ID
    When envio uma requisicao POST para "/books" com o payload "books/create-book.json"
    When envio uma requisicao GET para "/books/${id}"
    Then o status da resposta deve ser 200
    And o campo JSON "title" deve ser "Livro BDD ${unique}"
    And o campo JSON "author" deve ser "Autor de Teste"

  @smoke
  Scenario: CT004 - Listar livros cadastrados
    When envio uma requisicao POST para "/books" com o payload "books/create-book.json"
    When envio uma requisicao GET para "/books"
    Then o status da resposta deve ser 200
    And a lista JSON deve conter "Livro BDD ${unique}" no campo "title"

  @regression
  Scenario: CT005 - Rejeitar livro com preco invalido
    When envio uma requisicao POST para "/books" com o payload "books/invalid-book.json"
    Then o status da resposta deve ser 400
    And o campo JSON "message" deve ser "Required fields were not provided"