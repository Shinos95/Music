package dao;

import java.util.Map;

import models.Entity;

public interface IDao<T extends Entity> {
    //firme dei metodi CRUD
    Long addEntity(T e);
    Map<Long,Entity> readAll();
    Entity readById(Long id);
    void update(T e);
    void delete(Long id);
    
}
