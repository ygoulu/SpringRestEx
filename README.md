# spring-rest-ex

A rest API made example application made with spring,

Stack:
*Java 17
*SpringBoot
*Gradle
*PostgreSQL

Run the application:
There is to ways to run the application, the first is with docker, for this:
1) Install docker
2) Open your terminal and go to de projects root folder.
3) Execute the following commands:
    docker build -t restex-app -f Dockerfile .
    docker-compose up

The second way is running the application localy, for this:
1) Install java 17
2) Install PostgreSQL
3) Open your terminal and go to de projects root folder.
4) Create a database for the project.
 4.1) Set your PostgreSQL credentials (user and password), database name and direction with port in src/main/resources/application.properties
5) Run migrations inside db/migrations folder
6) Execute the app with ./gradle bootRun

