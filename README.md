# EstanteDigital - Testes de API

Projeto de testes de API com Java, RestAssured, Cucumber e Maven.

## Requisitos

- JDK 21 ou superior
- Maven 3.9 ou superior
- API em execução para executar cenários reais

## Estrutura

```text
src/test/java/br/com/estantedigital/api/
	client/       Cliente HTTP reutilizável baseado em RestAssured
	config/       Leitura de propriedades e variáveis de ambiente
	context/      Estado isolado por cenário Cucumber
	runner/
	  RegressionRunnerTest.java Runner geral dos cenários marcados @regression
	  books/      Runner de validação da API de livros
	  customers/  Runner de validação da API de clientes
	  employees/  Runner de validação da API de funcionários
	  health/     Runner de validação de disponibilidade
	  sales/      Runner de validação da API de vendas
	steps/
	  common/     Steps genéricos compartilhados por todas as APIs
	  books/      Steps exclusivos da API de livros, quando necessários
	  customers/  Steps exclusivos da API de clientes, quando necessários
	  employees/  Steps exclusivos da API de funcionários, quando necessários
	  sales/      Steps exclusivos da API de vendas, quando necessários
src/test/resources/
	config/       Configuração padrão
	features/
	  books/      Cenários da API de livros
	  customers/  Cenários da API de clientes
	  employees/  Cenários da API de funcionários
	  health/     Cenários de disponibilidade
	  sales/      Cenários da API de vendas
	payloads/     Corpos JSON das requisições, organizados por recurso
```

O fluxo é: feature Gherkin -> step definition -> `ApiClient` -> API. O `ScenarioContext` mantém a resposta isolada para cada cenário. Configure autenticação e endereço fora dos steps para que os cenários continuem legíveis.

## Configuração

O endereço padrão está em `src/test/resources/config/api.properties`. Pode ser sobrescrito por variável de ambiente ou propriedade do Maven:

```powershell
$env:API_BASE_URL = "http://localhost:8080"
$env:API_TOKEN = "seu-token"
mvn test
```

Alternativamente:

```powershell
mvn test "-Dapi.base-url=http://localhost:8080" "-Dapi.token=seu-token"
```

Variáveis de ambiente têm precedência sobre propriedades Java, que têm precedência sobre o arquivo padrão. Não salve tokens no repositório.

## Executar

Inicie a API em outro terminal:

```powershell
Set-Location C:\Projeto\EstanteDigital\APIs
.\mvnw.cmd '-Dmaven.test.skip=true' spring-boot:run
```

Com a API disponivel em `http://localhost:8080`, execute os cenarios:

```powershell
mvn clean test
```

Para selecionar cenários por tag:

```powershell
mvn test "-Dcucumber.filter.tags=@smoke"
```

Para executar somente os cenários de regressão:

```powershell
mvn test "-Dtest=RegressionRunnerTest"
```

Para executar apenas uma API, selecione seu runner:

```powershell
mvn test "-Dtest=BooksValidationRunnerTest"
mvn test "-Dtest=CustomersValidationRunnerTest"
mvn test "-Dtest=EmployeesValidationRunnerTest"
mvn test "-Dtest=HealthValidationRunnerTest"
mvn test "-Dtest=SalesValidationRunnerTest"
```

## Convenções

- Mantenha detalhes HTTP e headers no cliente, não nos steps.
- Mantenha corpos JSON em `src/test/resources/payloads/` e referencie-os pelo caminho relativo nas features.
- Nomeie features e cenários pelo comportamento observado pela pessoa usuária da API.
- Mantenha steps reutilizáveis em `steps/common/CommonSteps.java`; crie steps nas pastas da API apenas quando expressarem comportamento específico daquele recurso.
- Identifique os cenários com códigos sequenciais `CT001`, `CT002` e assim por diante.
- Cada runner seleciona somente as features do recurso correspondente; todos reutilizam o glue em `steps/common`.
- Use tags como `@smoke` e `@regression` para organizar a execução.
- Não compartilhe estado entre cenários; o PicoContainer cria dependências por cenário.