package model;

public class Recompenses {
    private String name;
    private String description;
    private User owner;

    public Recompenses(){
        name = "";
        description = "";
    }

    public Recompenses(String name, String description){
       this.name = name;
       this.description = description;
    }

    public String getDescription(){
        return  description;
    }
    public void setDescription(String value){
        description = value;
    }

    public String getName(){
        return name;
    }
    public void setName (String nom){
        name = nom;
    }

    public User getUser(){
        return this.owner;
    }
    public void setUser(User user){
        this.owner = user;
    }

}



