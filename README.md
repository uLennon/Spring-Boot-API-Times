<details id="pt">
  <summary>PT/BR:brazil:</summary>
  
  #Spring-Boot-API-Times

## :page_with_curl:Sobre
- Projeto, criado como parte dos meus estudos, tem como principal propósito a aplicação prática dos meus conhecimentos no desenvolvimento back-end. O sistema permite cadastrar time, alterar, deletar e listar.


## :bookmark_tabs:Dependências

Este projeto é construido usando:

- Java 17
- Spring Boot 3.0.2
- Spring Data
- Lombok
- MapStruct
- Swagger
- Junit 5, AssertJ, Mockito - H2 para o repositório de teste
- MySql DataBase
- Docker - docker compose


## :earth_americas:LINUX
## :whale:Run
```console
docker-compose up
```
## :hammer:Build
```console
docker-compose build

```
---  

## :mag_right:Endpoints

|Método | 	Url		| 	Descrição |
|-------| ------- | ----------- |
|DELETE|/times/{id}| Excluir um time pelo id|
|PUT|/times| 	Altera um time|
|POST|/times| 	Salva um time|
|GET| /times| 	Lista todos os times paginado|
|GET|/times/{id}| 	Procura o time pelo id|
|GET|/times/find| 	Procura o time pelo nome|
|GET|/times/by-id/{id}| 	Procura o time pelo id, necessita de autorização|
|GET|/times/all| 	Lista todos os time|


![Screenshot_2021-05-19 Swagger UI](https://user-images.githubusercontent.com/99137194/219903458-0827fcbc-ce9c-4670-8e7e-5169c922a195.png)
>Swagger

## :unlock:Licença 

Este software foi criado apenas para fins de estudo. Sinta-se à vontade para experimentar. 

</details>
