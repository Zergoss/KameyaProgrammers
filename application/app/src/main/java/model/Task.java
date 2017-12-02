package model;

import java.util.Date;

public class Task {
    private int id;
    private int points;
    private boolean available;
    private String name;
    private String description;
    private Date dueDate;
    private User creator;
    private User assignedUser;
    //Ressource List
    //GroupTache

    //Default
    public Task() {}

    //Minimum Constructor
    public Task (int points, String name, String description, Date dueDate, User creator) {
        this.points = points;
        this.available = false;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.creator = creator;
    }

    //Assign
    public Task (int points, boolean available, String name, String description, Date dueDate, User creator) {
        this.points = points;
        this.available = available;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.creator = creator;
    }
    //Full parameter
    public Task (int points, String name, String description, Date dueDate, User creator, User assignedUser) {
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


    public Date getDueDate(){
        if(dueDate != null){
            return (Date) this.dueDate.clone();
        }
        return null;
    }
    public void setDueDate(Date dueDate){
            this.dueDate = (Date) dueDate.clone();
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

    public long getDateLong(Date date) {
        return date.getTime();
    }
    public Date getDate(long date) {
        return new Date(date);
    }
}

/*
Calendar cal = Calendar.getInstance();
cal.set(Calendar.YEAR, 1988);
cal.set(Calendar.MONTH, Calendar.JANUARY);
cal.set(Calendar.DAY_OF_MONTH, 1);
cal.getTimeInMillis()


Calendar calendar = Calendar.getInstance();
calendar.setTimeInMillis(timeStamp);

System.out.println(cal.getTimeInMillis());

Calendar calendar = Calendar.getInstance();
//calendar.setTimeInMillis(timeStamp);

System.out.println(calendar.get(mYear));
System.out.println(calendar.get(mMonth));
System.out.println(calendar.get(mDay));
 */