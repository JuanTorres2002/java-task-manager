# Java Task Manager

A small command-line task manager demonstrating object-oriented programming, input validation, file persistence and unit testing.

## Features

- Add tasks with a title.
- List tasks with completion status.
- Mark a task as complete.
- Save tasks to a local CSV-like data file.
- Basic JUnit tests for task behavior.

## Requirements

- Java 17+
- Maven 3.8+

## Run

```bash
mvn test
mvn package
java -jar target/java-task-manager-1.0.0.jar
```

The application stores data in `tasks.txt` in the current working directory. The format is intentionally simple for this educational project.

## Commands

- `add <title>` — create a task
- `list` — show all tasks
- `done <id>` — mark a task as complete
- `help` — show help
- `exit` — quit

Example:

```text
> add Review Linux permissions
Created task #1
> list
[ ] #1 Review Linux permissions
> done 1
Task #1 completed
```

## Design notes

The project separates the `Task` domain model, a file-backed `TaskRepository`, and the command-line entry point. For a larger application, persistence should use a database and CSV parsing should use a robust serialization library.

## Possible improvements

- Add SQLite persistence.
- Add due dates and priorities.
- Add integration tests for repository behavior.
- Add a REST API with Spring Boot.
