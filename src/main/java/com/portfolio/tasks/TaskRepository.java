package com.portfolio.tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskRepository {
    private final Path file;

    public TaskRepository(Path file) {
        this.file = file;
    }

    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(file)) {
            return tasks;
        }
        for (String line : Files.readAllLines(file)) {
            if (!line.isBlank()) {
                try {
                    tasks.add(Task.fromStorageLine(line));
                } catch (IllegalArgumentException ex) {
                    throw new IOException("Invalid task record in " + file + ": " + line, ex);
                }
            }
        }
        tasks.sort(Comparator.comparingInt(Task::getId));
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        List<String> lines = tasks.stream().map(Task::toStorageLine).toList();
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.write(file, lines);
    }
}
