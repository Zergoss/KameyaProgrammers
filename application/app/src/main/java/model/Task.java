package model;

import java.util.Date;

public class Task {
    private int id;
    private int points;
    private boolean available;
    private String name;
    private String description;
    private String dueDate;
    private User creator;
    private User assignedUser;
    //Ressource List
    //GroupTache

    //Default
    public Task() {}

    //Minimum Constructor
    public Task (int points, String name, String description, String dueDate, User creator) {
        this.points = points;
        this.available = false;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.creator = creator;
    }

    //Assign
    public Task (int points, boolean available, String name, String description, String dueDate, User creator) {
        this.points = points;
        this.available = available;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.creator = creator;
    }
    //Full parameter
    public Task (int points, String name, String description, String dueDate, User creator, User assignedUser) {
        this.points = points;
        this.available = false;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.creator = creator;
        this.assignedUser = assignedUser;
    }

    //getters & setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPoints(){
        return this.points;
    }
    public void setPoints(int points){
        this.points = points;
    }

    public boolean isAvailable(){
        return this.available;
    }
    public void setAvailable(boolean available){
        this.available = available;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.name = description;
    }

    public String getDueDate(){
            return this.dueDate;
    }
    public void setDueDate(String dueDate){
            this.dueDate = dueDate;
    }

    public User getCreator(){
        return this.creator;
    }
    public void setCreator(User creator){
        this.creator = creator;
    }

    public User getAssignedUser(){
        return this.assignedUser;
    }
    public void setAssignedUser(User assignedUser){
        this.assignedUser = assignedUser;
    }

}