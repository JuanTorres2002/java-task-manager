package com.portfolio.tasks;

public class Task {
    private final int id;
    private final String title;
    private boolean completed;

    public Task(int id, String title, boolean completed) {
        if (id < 1) {
            throw new IllegalArgumentException("Task id must be positive.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        this.id = id;
        this.title = title.trim();
        this.completed = completed;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public void markCompleted() { completed = true; }

    public String toStorageLine() {
        String safeTitle = title.replace("\\", "\\\\").replace("\t", " ").replace("\n", " ");
        return id + "\t" + completed + "\t" + safeTitle;
    }

    public static Task fromStorageLine(String line) {
        String[] parts = line.split("\t", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid task record.");
        }
        return new Task(Integer.parseInt(parts[0]), parts[2], Boolean.parseBoolean(parts[1]));
    }
}
