package models;

public abstract class Entity implements IMappable{

    private Long id;

    public Entity() {
    }

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[id=" + id + "]";
    }

    //prova2 modifica per github 
    
    
    
}
