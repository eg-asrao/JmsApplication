
# Spring Boot JMS Group Management System

This project demonstrates a simple group creation and deletion messaging system using **Spring Boot**, **ActiveMQ**, and **Java Messaging Service (JMS)**. It allows sending and receiving group-related messages with support for message filtering, dead-letter queues, and unit testing.

---

## 📌 Features

- 📤 Group creation & deletion via REST API
- 📥 JMS-based message sending using `JmsTemplate`
- 📬 Listeners for `CREATE` and `DELETE` operations using JMS selectors
- 🧼 Clean code structured around **SOLID principles**
- 🧪 Unit tests with **Mockito** and **Spring Boot Test**
- 🧾 JavaDoc comments for core components
- ☠️ Dead Letter Queue (DLQ) handling
- ⚙️ Configurable concurrency for listeners
- 🕒 Timestamping messages with `LocalDateTime`

---

## 🧱 Project Structure

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

## 🔧 Setup Instructions

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

## 🔄 Key Components & Changes

### ✅ Controller - `GroupController`
- Refactored to follow **SRP (Single Responsibility Principle)**
- Extracted message construction logic into a private method
- Added **JavaDocs** for all methods

### ✅ DTO - `GroupMessageDTO`
- Implements `Serializable`
- Fields: `groupId`, `parentGroupId`, `operation`, `timestamp`

### ✅ JMS Producer - `GroupMessageProducer`
- Sends messages to `group-events` queue
- Attaches custom `operation` property to support message filtering
- Uses `JmsTemplate` with JSON message conversion

### ✅ Listeners
- `CreateGroupListener` listens for `operation='CREATE'`
- `DeleteGroupListener` listens for `operation='DELETE'`
- `DeadLetterQueueListener` listens to `ActiveMQ.DLQ`

### ✅ Configuration - `JmsConfig`
- Configures in-memory ActiveMQ broker (`vm://localhost`)
- Enables JSON message conversion with support for `LocalDateTime`
- Adds concurrency settings and error handler

### ✅ Testing
- **Mockito** used for mocking dependencies
- Tests written for:
  - `GroupController`
  - `GroupMessageProducer`
- Uses `@WebMvcTest`, `@MockBean`, and `@ExtendWith(MockitoExtension.class)`

---

## ❗ Troubleshooting

- If `mvn test` fails with `cannot find symbol`:
  - Ensure constructors are correctly defined in `GroupMessageDTO`
  - Ensure `@Slf4j` is present for logging
  - Check imports and correct mocking setup

- If `log.info(...)` causes error:
  - Ensure `@Slf4j` is on the class
  - Ensure Lombok is enabled in your IDE

- If JMS listener fails:
  - Validate `@EnableJms` is present in `JsmDemoApplication`
  - Ensure correct queue name (`group-events`) and selector (`operation = '...'`)

---

## 📚 Documentation

JavaDoc comments have been added to:

- `GroupController.java`
- `GroupMessageProducer.java`
- `JmsConfig.java`
- Listener classes (`Create`, `Delete`, `DLQ`)
- DTO class (`GroupMessageDTO`)

Use the IDE feature or Maven plugin to generate docs if needed.

---

## 📤 Sample API Usage

### Create Group
```bash
curl -X POST "http://localhost:8080/groups/create?groupId=G1&parentGroupId=Root"
```

### Delete Group
```bash
curl -X DELETE "http://localhost:8080/groups/delete?groupId=G1&parentGroupId=Root"
```

---

## ✅ License

This project is for demonstration purpose.
