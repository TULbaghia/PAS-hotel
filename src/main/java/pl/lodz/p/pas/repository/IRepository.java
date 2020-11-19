package pl.lodz.p.pas.repository;

import java.util.List;

public interface IRepository<T> {

    void add(T item);

    T get(String id);

    List<T> getAll();

    void update(String id, T item);

    void delete(String id);

}
