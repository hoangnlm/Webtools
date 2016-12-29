package dao;

import java.util.List;

/**
 * DAO Interface
 * @created on Dec 29, 2016, 8:40:43 AM
 * @author HoangNLM
 * @param <T>
 */
public interface IDao<T> {

    List<T> get();

    long insert(T model);

    boolean update(T model);

    boolean delete(T model);

}
