package Db;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DbCollection<T extends Identifiable> {
    private static int nextId = 1;
    private Hashtable<Integer, T> table = new Hashtable<>();

    public T get(int id) {
        if (!table.containsKey(id))
            throw new IllegalArgumentException("No such id in the DB.");

        T original = table.get(id);
        return original;
    }

    public void remove(int id) {
        if (!table.containsKey(id))
            throw new IllegalArgumentException("No such id in the DB.");

        table.remove(id);

    }

    public int add(T element) {
        if (table.values().stream().anyMatch(e -> e == element)) {
            throw new IllegalArgumentException(
                    "The original element is already present in the DB. Create a new one and copy the fields.");
        }

        int id = nextId();
        element.setId(id);
        table.put(id, element);

        return id;
    }

    public List<T> search(Predicate<T> p) {
        return table.values().stream().filter(p).collect(Collectors.toList());
    }

    public T getFirstOrNull(Predicate<T> p) {
        Optional<T> result = table.values().stream().filter(p).findFirst();
        return result.orElse(null);
    }

    private int nextId() {
        return ++nextId;
    }
}
