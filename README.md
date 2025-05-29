
# JMS Application

This project demonstrates a simple group creation and deletion messaging system using **Spring Boot**, **ActiveMQ**, and **Java Messaging Service (JMS)**. It allows sending and receiving group-related messages with support for message filtering, dead-letter queues, and unit testing.

---

## 📌 Features

-  Group creation & deletion via REST API
-  JMS-based message sending using `JmsTemplate`
-  Listeners for `CREATE` and `DELETE` operations using JMS selectors
-  Unit tests with **Mockito** and **Spring Boot Test**
-  JavaDoc comments for core components
-  Dead Letter Queue (DLQ) handling
-  Configurable concurrency for listeners
-  Timestamping messages with `LocalDateTime`

---

##  Project Structure

```
src
└── main
    ├── java/org/example/jsmdemo
    │   ├── config                     # JMS configuration
    │   ├── controller                 # REST controller for group APIs
    │   ├── dto                        # Data Transfer Objects
    │   ├── listener                   # JMS listeners (create, delete, DLQ)
    │   ├── producer                   # JMS message producer
    │   └── JmsApplication.java    # Main Spring Boot app
    └── test
        └── java/org/example/jmsapplication/JmsApplicationTests  # Unit tests
```

---

##  Setup Instructions

### Prerequisites
- Java 17+
- Maven
- (Optional) ActiveMQ installed or use in-memory broker

### Run the application

```bash
mvn spring-boot:run
```

### Run tests

```bash
mvn test
```

---
##  Documentation

JavaDoc comments have been added to:

- `GroupController.java`
- `GroupMessageProducer.java`
- `JmsConfig.java`
- Listener classes (`Create`, `Delete`, `DLQ`)
- DTO class (`GroupMessageDTO`)

Use the IDE feature or Maven plugin to generate docs if needed.

---

##  Sample API Usage

### Create Group
```bash
curl -X POST "http://localhost:8080/groups/create?groupId=G1&parentGroupId=Root"
```

### Delete Group
```bash
curl -X DELETE "http://localhost:8080/groups/delete?groupId=G1&parentGroupId=Root"
```

---

##  License

This project is for demonstration purpose.
