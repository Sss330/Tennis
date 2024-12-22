package dao;

import model.Match;

import java.util.List;

public interface crudTennisDao<T> {

    void add(T entity);

    void add(Match entity);

    List<T> findAll();
}