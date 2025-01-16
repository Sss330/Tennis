package dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudTennisDao<T> {

    void save(T entity);

    List<T> findAll() throws SQLException;
}