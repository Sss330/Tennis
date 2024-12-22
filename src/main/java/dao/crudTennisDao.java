package dao;

import java.util.List;

public interface crudTennisDao<T> {

    void add(T entity);

    List<T> findAll();
}