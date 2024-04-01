package by.artem.je.jdbc.dao.dao_classes;

import java.util.List;
import java.util.Optional;

public interface Dao<K, V> {
    V create(V v);
    boolean update(V v);

    List<V> findAll();

    Optional<V> findById(K k);

    boolean delete(K k);
}
