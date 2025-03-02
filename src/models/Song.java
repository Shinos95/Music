package models;

public class Song extends Entity{

    private String name;
    private double duration;
    
    public Song() {
    }

    public Song(Long id, String name, double duration) {
        super(id);
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song" + super.toString() + "name=" + name + ", duration=" + duration + "\n";
    }

    @Override
    public Entity clone() {
      return this.clone();
    }

    

}
