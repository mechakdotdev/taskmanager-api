# Task Manager API

A modular Spring Boot backend that allows users to create and manage tasks, as well as dynamically schedules tasks based on deadlines, priorities, and user capacity using pluggable algorithm strategies.

## Features

- REST API for scheduling tasks using multiple scheduling strategies, which is open to extension with minimal coupling
- Conflict-aware and capacity-based task scheduling strategies
- Pluggable scheduler strategies via Strategy Pattern
- Mock authentication for easy testing (future plan to introduce proper authentication service)


## Technologies

- Java 21
- Spring Boot 3
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- JUnit 5

---

### Scheduling Algorithms
| Algorithm                                     | Functionality	|
|-----------------------------------------------|---------------|
| Conflict Aware (conflict-aware)               | Respects deadline and skips overlapping tasks to avoid conflicts |
| Earliest Deadline First (earliest-deadline) 	 | New algorithms can be added with minimal coupling |
| Latest Deadline First (latest-deadline)       | Sorts by deadline |
| Priority (priority-weighted)                  | Sorts by priority (HIGH > MEDIUM > LOW)	|

---
The 'development' branch is set up to initalise mock data in the local database so functionality can be tested locally
