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
	runner/       Entrada da suíte Cucumber para JUnit Platform
	steps/        Definições dos passos Gherkin
src/test/resources/
	config/       Configuração padrão
	features/     Cenários de negócio em Gherkin
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

```powershell
mvn clean test
```

O cenário de exemplo em `features/api_template.feature` usa `/health` apenas como modelo e está marcado com `@template`, excluído da execução padrão. Quando o contrato real estiver definido, ajuste a rota e a expectativa e remova essa tag.

Para selecionar cenários por tag:

```powershell
mvn test "-Dcucumber.filter.tags=@smoke"
```

## Convenções

- Mantenha detalhes HTTP e headers no cliente, não nos steps.
- Nomeie features e cenários pelo comportamento observado pela pessoa usuária da API.
- Use tags como `@smoke`, `@regression` e `@template` para organizar a execução.
- Não compartilhe estado entre cenários; o PicoContainer cria dependências por cenário.