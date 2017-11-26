package ca.uottawa.cohab;

import java.util.Date;

/**
 * Created by Administrateur on 2017-11-23.
 */

public class Task {
    private int points;  //Real???
    private boolean available;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date dueDate;
    private User creator;

    //Ressource List
    //GroupTache

    //Minimum Constructor
    public Task (int points, String name, String description, Date dueDate, User creator) {
        this.points = points;
        this.available = false;
        this.name = name;
        this.description = description;
        this.startDate = null;
        this.endDate = null;
        this.dueDate = dueDate;
        this.creator = creator;
    }
    //With start/end Date
    public Task (int points, String name, String description, Date startDate, Date endDate, Date dueDate, User creator) {
        this.points = points;
        this.available = false;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.creator = creator;
    }
    //Assign
    public Task (int points, boolean available, String name, String description, Date dueDate, User creator) {
        this.points = points;
        this.available = available;
        this.name = name;
        this.description = description;
        this.startDate = null;
        this.endDate = null;
        this.dueDate = dueDate;
        this.creator = creator;
    }
    //With start/end Date & assign
    public Task (int points, boolean available, String name, String description, Date startDate, Date endDate, Date dueDate, User creator) {
        this.points = points;
        this.available = available;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.creator = creator;
    }

    //getters & setters
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

    public User getCreator(){
        return (User) this.creator.clone();
    }
    public void setDueDate(User creator){
        this.creator = (User) creator.clone();
    }


    public Task clone() {
        Task aClone = new Task(this.points, this.available, this.name, this.description, this.startDate, this.endDate, this.dueDate, this.creator);
        return aClone;
    }

}
