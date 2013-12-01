package org.ganaccity.interfaces.dao;

import java.util.List;

public interface GenericDAO<T extends Object> {
    
    int create(T o);
    
    T readById(int id);
    
    List<T> readAll();
    
    void update(T o);
    
    void merge(T o);
    
    void delete(T o);
}
