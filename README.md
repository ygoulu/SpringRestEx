# Spring-Rest-Ex

A REST API example application made with Spring


## Stack:

- Java 17
- SpringBoot
- Gradle
- PostgreSQL



## Run the application:

There is two ways to run the application:

### Option 1, with docker:

1. Install [docker](https://www.docker.com/).
2. Open your terminal on the project root folder.
3. Execute the following commands:

   ```docker build -t restex-app -f Dockerfile .```

   ```docker-compose up```

### Option 2, locally:
1. Install java 17
2. Install PostgreSQL
3. Open your terminal and go to de projects root folder.
4. Create a database for the project.
5. Set your PostgreSQL credentials (user and password), database name and direction with port in src/main/resources/application.properties

```
spring.datasource.url=jdbc:postgresql://localhost:{db_port}/{your_database}  
spring.datasource.username={your_DB_user}  
spring.datasource.password={your_DB_password}  
spring.datasource.driver-class-name=org.postgresql.Driver  
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect  
spring.jpa.hibernate.ddl-auto=update

spring.mvc.throw-exception-if-no-handler-found=true  
spring.web.resources.add-mappings=false
```

Ensure that the provided user has read and write permissions on the selected database.

6. Run migrations inside db/migrations folder
7. Execute the app with ./gradlew bootRun

## Using the app

The application has two endpoints:

1. GET `sum`: the endpoint sum will take two parameters, `first` and `second`, both double values, and will return the sum with a plus random percentage.
2. GET `history`: is a paginated endpoint (params for paginated endpoints like sort are allowed) that will return the history of calls to the api.

Inside `postman` folder is a collection made to use the application, just import in postman and enjoy!

### This application was made following the next statements (in spanish)
```
Los requerimientos son los siguientes:

Debes desarrollar una API REST en Spring Boot utilizando java 11 o superior, con las siguientes funcionalidades:

Debe contener un servicio llamado por api-rest que reciba 2 números, los sume, y le aplique una suba de un porcentaje que debe ser adquirido de un servicio externo (por ejemplo, si el servicio recibe 5 y 5 como valores, y el porcentaje devuelto por el servicio externo es 10, entonces (5 + 5) + 10% = 11). Se deben tener en cuenta las siguientes consideraciones:

El servicio externo puede ser un mock, tiene que devolver el % sumado.

Dado que ese % varía poco, podemos considerar que el valor que devuelve ese servicio no va cambiar por 30 minutos.

Si el servicio externo falla, se debe devolver el último valor retornado. Si no hay valor, debe retornar un error la api.

Si el servicio falla, se puede reintentar hasta 3 veces.

Historial de todos los llamados a todos los endpoint junto con la respuesta en caso de haber sido exitoso. Responder en Json, con data paginada. El guardado del historial de llamadas no debe sumar tiempo al servicio invocado, y en caso de falla, no debe impactar el llamado al servicio principal.

La api soporta recibir como máximo 3 rpm (request / minuto), en caso de superar ese umbral, debe retornar un error con el código http y mensaje adecuado.

El historial se debe almacenar en una database PostgreSQL.

Incluir errores http. Mensajes y descripciones para la serie 4XX.


Se deben incluir tests unitarios.

Esta API debe ser desplegada en un docker container. Este docker puede estar en un dockerhub público. La base de datos también debe correr en un contenedor docker. Recomendación usar docker compose

Debes agregar un Postman Collection o Swagger para que probemos tu API

Tu código debe estar disponible en un repositorio público, junto con las instrucciones de cómo desplegar el servicio y cómo utilizarlo.

Tener en cuenta que la aplicación funcionará de la forma de un sistema distribuido donde puede existir más de una réplica del servicio funcionando en paralelo.
```
