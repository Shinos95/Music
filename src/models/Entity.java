package models;

public abstract class Entity implements IMappable, Cloneable{

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

    public Entity clone(){
        try {
            return (Entity)super.clone();
        } catch (CloneNotSupportedException e) {
           System.out.println("clonazione non avvenuta");
           return null;
        }
    }
    
    
}
