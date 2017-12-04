package model;

public class Resource {
    private String name;
    private String description;
    private User owner;

    public Resource(){
        name = "";
        description = "";
        owner = new User();
    }

    public Resource(String name, String description, User user){
       this.name = name;
       this.description = description;
       this.owner = user;
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
