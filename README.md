\# Microservices Quiz App



A scalable Quiz Application built using Java Spring Boot and Microservices architecture. The system separates question management from quiz creation and uses a Service Registry for discovery.



\## Architecture



The project consists of four main modules:



1\.  \*\*Service Registry (Eureka Server)\*\*

&nbsp;   \* Acts as a discovery server for all microservices.

&nbsp;   \* Runs on port `8761`.

2\.  \*\*Question Service\*\*

&nbsp;   \* Responsible for creating, retrieving, and managing the database of questions.

&nbsp;   \* Runs on port `8080` (Default).

3\.  \*\*Quiz Service\*\*

&nbsp;   \* Manages the creation of specific quizzes.

&nbsp;   \* Communicates with the `Question Service` using \*\*Spring Cloud OpenFeign\*\*.

&nbsp;   \* Runs on port `8090`.

4\.  \*\*API Gateway\*\*

&nbsp;   \* \*\*Entry Point:\*\* The single entry point for all client requests.

&nbsp;   \* \*\*Routing:\*\* Dynamically routes traffic to services based on the URL path using Eureka Service Discovery.

&nbsp;   \* \*\*Port:\*\* `8765`.



```mermaid

flowchart LR

&nbsp;   subgraph Client

&nbsp;       A\[User Request]

&nbsp;   end



&nbsp;   A --> G\[API Gateway\\nPort 8765]



&nbsp;   subgraph Eureka\[Service Registry\\nEureka Server\\nPort 8761]

&nbsp;       E\[(Eureka Server)]

&nbsp;   end



&nbsp;   G --> E



&nbsp;   subgraph QuestionService\[Question Service\\nPort 8080]

&nbsp;       QN1\[Question Controller]

&nbsp;       QDB\[(PostgreSQL)]

&nbsp;       QN1 --> QDB

&nbsp;   end



&nbsp;   subgraph QuizService\[Quiz Service\\nPort 8090]

&nbsp;       QS1\[Quiz Controller]

&nbsp;       QS1 --> QN1

&nbsp;   end



&nbsp;   E --> QuestionService

&nbsp;   E --> QuizService



&nbsp;   G --> QuestionService

&nbsp;   G --> QuizService

```



\## Tech Stack



\* \*\*Java 17+\*\*

\* \*\*Spring Boot\*\* (Web, Data JPA)

\* \*\*Spring Cloud\*\* (Netflix Eureka, OpenFeign, Gateway)

\* \*\*PostgreSQL\*\*

\* \*\*Lombok\*\*

\* \*\*Maven\*\*



\## Getting Started



\### Prerequisites

\* Java JDK

\* Maven

\* PostgreSQL



\### Installation \& Running



1\.  \*\*Clone the repository\*\*

&nbsp;   ```bash

&nbsp;   git clone https://github.com/modhtom/quiz-app-microservices.git

&nbsp;   ```



2\.  \*\*Start the Service Registry\*\*

&nbsp;   \* Navigate to `/service-registry`

&nbsp;   \* Run: `mvn spring-boot:run`

&nbsp;   \* Verify Eureka is running at `http://localhost:8761`



3\.  \*\*Start the Question Service\*\*

&nbsp;   \* Navigate to `/question-service`

&nbsp;   \* Update `application.properties` with your database credentials.

&nbsp;   \* Run: `mvn spring-boot:run`



4\.  \*\*Start the Quiz Service\*\*

&nbsp;   \* Navigate to `/quiz-service`

&nbsp;   \* Run: `mvn spring-boot:run`



5\.  \*\*Start the API Gateway\*\*

&nbsp;   \* Navigate to `/api-gateway`

&nbsp;   \* Run: `mvn spring-boot:run`



\## API Endpoints (Via Gateway)



Instead of calling services directly, use the Gateway URL: `http://localhost:8765`.



The Gateway uses the service name (both lowercased and uppercased ) to route requests.



\### Question Service Endpoints



\- GET /question-service/question/allQuestions



&nbsp; - Retrieves all questions from the database.



\- GET /question-service/question/category/{category}



&nbsp; - Retrieves questions filtered by a specific category.



\- POST /question-service/question/add



&nbsp; - Adds a new question to the database.



\### Quiz Service Endpoints



\- POST /quiz-service/quiz/create



&nbsp; - Creates a new quiz with a specific title and number of questions.



\- POST /quiz-service/quiz/get/{id}



&nbsp; - Fetches the question details for a specific quiz ID.



\- POST /quiz-service/quiz/submit/{id}



&nbsp; - Submits user answers and calculates the final score.





\## Feign Client Configuration



The Quiz Service communicates with the Question Service via the `QuizInterface`.



It creates a proxy that talks to the `QUESTION-SERVICE` registered in Eureka, ensuring load balancing and loose coupling.

