package com.example.demo.memento;

import java.util.HashMap;
import java.util.Map;

/**
 * Caretaker class that manages multiple memento objects.
 */
public class Caretaker {
    private final Map<String, Object> mementoMap = new HashMap<>();

    /**
     * Saves a memento object with a unique key.
     *
     * @param key The key to identify the memento.
     * @param memento The memento object to save.
     */
    public void saveMemento(String key, Object memento) {
        mementoMap.put(key, memento);
    }

    /**
     * Retrieves a memento object by its key.
     *
     * @param key The key to identify the memento.
     * @return The memento object, or null if not found.
     */
    public Object getMemento(String key) {
        return mementoMap.get(key);
    }

    /**
     * Clears all saved mementos.
     */
    public void clearMementos() {
        mementoMap.clear();
    }
}
