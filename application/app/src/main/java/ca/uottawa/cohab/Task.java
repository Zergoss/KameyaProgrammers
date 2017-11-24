package ca.uottawa.cohab;

import java.util.Date;

/**
 * Created by Administrateur on 2017-11-23.
 */

public class Task {
    private String name;
    private String description;
    private int points;  //Real???
    private Date startDate;
    private Date endDate;
    private Date dueDate;
    private boolean available;
    private User creator;
    //Ressource List
    //GroupTache

    //Minimum Constructor
    public Task (String name, String description, int points, Date dueDate, User creator) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.startDate = null;
        this.endDate = null;
        this.dueDate = dueDate;
        this.available = false;
        this.creator = creator;
    }
    //With start/end Date
    public Task (String name, String description, int points, Date startDate, Date endDate, Date dueDate, User creator) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.available = false;
        this.creator = creator;
    }
    //Assign
    public Task (String name, String description, int points, Date dueDate, boolean status, User creator) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.startDate = null;
        this.endDate = null;
        this.dueDate = dueDate;
        this.available = status;
        this.creator = creator;
    }
    //With start/end Date & assign
    public Task (String name, String description, int points, Date startDate, Date endDate, Date dueDate, boolean status, User creator) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.available = status;
        this.creator = creator;
    }

    //getters & setters
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

    public int getPoints(){
        return this.points;
    }
    public void setPoints(String points){
        this.name = points;
    }

    public Date getStartDate(){
        return (Date) this.startDate.clone();
    }
    public void setStartDate(Date startDate){
        this.startDate = (Date) startDate.clone();
    }

    public Date getEndDate(){
        return (Date) this.endDate.clone();
    }
    public void setEndDate(Date endDate){
        this.endDate = (Date) endDate.clone();
    }

    public Date getDueDate(){
        return (Date) this.dueDate.clone();
    }
    public void setDueDate(Date dueDate){
        this.dueDate = (Date) dueDate.clone();
    }

    public boolean isAvailable(){
        return this.available;
    }
    public void setAvailable(boolean available){
        this.available = available;
    }

    public User getCreator(){
        return (User) this.creator.clone();
    }
    public void setDueDate(User creator){
        this.creator = (User) creator.clone();
    }

}
