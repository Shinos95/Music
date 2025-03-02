package dao;

import java.util.Map;

import models.Entity;
/*Questa è un'interfaccia generica che definisce un DAO (Data Access Object), 
un pattern utilizzato per l'accesso ai dati in applicazioni che interagiscono con un database.
L'uso dei generics (<T extends Entity>) 
consente a questa interfaccia di operare su qualsiasi classe che estenda Entity. */
public interface IDao<T extends Entity> {
//L'interfaccia utilizza generics per dichiarare T, che deve essere una sottoclasse di Entity.
//Questo vincolo (T extends Entity) garantisce che il tipo passato a IDAO sia sempre un'entità valida.
//Questa interfaccia IDAO definisce le operazioni CRUD (Create, Read, Update, Delete) 
//per entità di tipo T, garantendo che T sia sempre una sottoclasse di Entity(cioè una figlia).
//L'uso dei Generics permette di creare DAO specifici per diverse entità 
//(es. DaoSong, DaoAlbum...) senza duplicare il codice.


    //firme dei metodi CRUD
    Long addEntity(T e);//Accetta un oggetto T (cioè un’entità concreta FIGLIA di ENTITY).
    //quindi il metodo addEntity accetta in input o Song o Album
    Map<Long,Entity> readAll();
    Entity readById(Long id);
    void update(T e);
    void delete(Long id);
    
}
