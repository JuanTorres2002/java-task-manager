package com.portfolio.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void trimsTitleAndStoresCompletion() {
        Task task = new Task(1, "  Learn Java  ", false);
        assertEquals("Learn Java", task.getTitle());
        task.markCompleted();
        assertTrue(task.isCompleted());
    }

    @Test
    void rejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(1, "  ", false));
    }

    @Test
    void taskCanRoundTripThroughStorageFormat() {
        Task original = new Task(3, "Practice SQL", true);
        Task restored = Task.fromStorageLine(original.toStorageLine());
        assertEquals(original.getId(), restored.getId());
        assertEquals(original.getTitle(), restored.getTitle());
        assertEquals(original.isCompleted(), restored.isCompleted());
    }
}
