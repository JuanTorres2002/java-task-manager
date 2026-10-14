package com.portfolio.tasks;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository(Path.of("tasks.txt"));
        List<Task> tasks;
        try {
            tasks = new ArrayList<>(repository.load());
        } catch (IOException ex) {
            System.err.println("Could not load tasks: " + ex.getMessage());
            return;
        }

        int nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        System.out.println("Java Task Manager — type 'help' for commands.");

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) break;
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] parts = input.split("\\s+", 2);
                String command = parts[0].toLowerCase();
                String argument = parts.length > 1 ? parts[1].trim() : "";

                switch (command) {
                    case "add" -> {
                        if (argument.isBlank()) {
                            System.out.println("Usage: add <title>");
                            break;
                        }
                        tasks.add(new Task(nextId, argument, false));
                        System.out.println("Created task #" + nextId++);
                        save(repository, tasks);
                    }
                    case "list" -> {
                        if (tasks.isEmpty()) {
                            System.out.println("No tasks yet.");
                        } else {
                            for (Task task : tasks) {
                                System.out.printf("%s #%d %s%n",
                                    task.isCompleted() ? "[x]" : "[ ]",
                                    task.getId(), task.getTitle());
                            }
                        }
                    }
                    case "done" -> {
                        try {
                            int id = Integer.parseInt(argument);
                            Task found = tasks.stream()
                                .filter(task -> task.getId() == id)
                                .findFirst().orElse(null);
                            if (found == null) {
                                System.out.println("No task found with id " + id);
                            } else {
                                found.markCompleted();
                                System.out.println("Task #" + id + " completed");
                                save(repository, tasks);
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("Usage: done <id>");
                        }
                    }
                    case "help" -> System.out.println(
                        "Commands: add <title>, list, done <id>, help, exit");
                    case "exit", "quit" -> running = false;
                    default -> System.out.println("Unknown command. Type 'help'.");
                }
            }
        }
    }

    private static void save(TaskRepository repository, List<Task> tasks) {
        try {
            repository.save(tasks);
        } catch (IOException ex) {
            System.err.println("Could not save tasks: " + ex.getMessage());
        }
    }
}
