package dao;

import java.util.Map;

import models.Entity;

public interface IDao {
    //firme dei metodi CRUD
    Long addEntity(Entity e);
    Map<Long,Entity> readAll();
    Entity readById(Long id);
    void update(Entity e);
    void delete(Long id);
    
}
