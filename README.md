# Task Manager API

A modular Spring Boot backend that allows users to create and manage tasks, as well as dynamically schedules tasks based on deadlines, priorities, and user capacity using pluggable algorithm strategies.


## Features

- REST API for scheduling tasks using multiple algorithms
- Conflict-aware and capacity-based task scheduling
- Pluggable scheduler strategies via Strategy Pattern
- Mock authentication for easy testing (future plan to introduce proper authentication service)



## Technologies

- Java 21
- Spring Boot 3
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- JUnit 5

--

### Scheduling Algorithms

- Conflict Aware (conflict-aware)       - Respects deadline and skips overlapping tasks to avoid conflicts
- Earliest Deadline First (earliest)    - Sorts by deadline
- Latest Deadline First (latest)        - Sorts by deadline in reverse order
- Priority (priority)                   - Sorts by priority (HIGH > MEDIUM > LOW)
