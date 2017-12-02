package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Recompenses;
import model.Task;
import model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "DataManager.db";

    // Table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_TASK = "task";
    private static final String TABLE_REWARD = "recompences";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_POINTS = "user_points";
    private static final String COLUMN_USER_NUMBERTASK = "user_numberTask";
    private static final String COLUMN_USER_USERNAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_POINTS = "task_points";
    private static final String COLUMN_TASK_AVAILABLE = "task_available";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";
    private static final String COLUMN_TASK_DUEDATE = "task_dueDate";
    private static final String COLUMN_TASK_CREATOR = "task_creator";
    private static final String COLUMN_TASK_ASSIGNEDUSER = "task_assignedUser";

    private static final String COLUMN_REWARD_ID = "reward_id";
    private static final String COLUMN_REWARD_NAME = "reward_name";
    private static final String COLUMN_REWARD_DESCRIPTION = "reward_description";
    private static final String COLUMN_REWARD_ASSIGNEDUSER = "reward_assignedUser";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_POINTS + " INTEGER,"
            + COLUMN_USER_NUMBERTASK + " INTEGER,"
            + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";


    private String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
            + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TASK_POINTS + " INTEGER,"
            + COLUMN_TASK_AVAILABLE + " NUMERIC,"
            + COLUMN_TASK_NAME + " TEXT,"
            + COLUMN_TASK_DESCRIPTION + " TEXT,"
            + COLUMN_TASK_DUEDATE + " NUMERIC,"
            + COLUMN_TASK_CREATOR + " INTEGER,"
            + COLUMN_TASK_ASSIGNEDUSER + " INTEGER,"
            + "FOREIGN KEY (" + COLUMN_TASK_CREATOR + ") REFERENCES " + TABLE_USER +"(" + COLUMN_USER_ID + "),"
            + "FOREIGN KEY (" + COLUMN_TASK_ASSIGNEDUSER + ") REFERENCES " + TABLE_USER +"(" + COLUMN_USER_ID + "))";

    private String CREATE_REWARD_TABLE = "CREATE TABLE " + TABLE_REWARD + "("
            + COLUMN_REWARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REWARD_NAME + " TEXT,"
            + COLUMN_REWARD_DESCRIPTION + " TEXT,"
            + COLUMN_REWARD_ASSIGNEDUSER + " INTEGER,"
            + "FOREIGN KEY (" + COLUMN_REWARD_ASSIGNEDUSER + ") REFERENCES " + TABLE_USER +"(" + COLUMN_USER_ID + "))";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TABLE_TASK;
    private String DROP_REWARD_TABLE = "DROP TABLE IF EXISTS " + TABLE_REWARD;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_REWARD_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TASK_TABLE);
        db.execSQL(DROP_REWARD_TABLE);

        // Create tables again
        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NUMBERTASK, user.getNumberTask());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_POINTS, user.getPoints());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_POINTS, task.getPoints());
        values.put(COLUMN_TASK_AVAILABLE, task.isAvailable());
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DUEDATE, checkDate(task.getDueDate()));
        values.put(COLUMN_TASK_CREATOR, task.getCreator().getId());
        values.put(COLUMN_TASK_ASSIGNEDUSER, checkUser(task.getAssignedUser()));

        // Inserting Row
        db.insert(TABLE_TASK, null, values);
        db.close();
    }
    public void addReward(Recompenses reward){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REWARD_DESCRIPTION, reward.getName());
        values.put(COLUMN_REWARD_DESCRIPTION, reward.getDescription());
        values.put(COLUMN_REWARD_ASSIGNEDUSER, reward.getUser().getId());

        // Inserting Row
        db.insert(TABLE_REWARD, null, values);
        db.close();
    }
    private int checkUser (User user) {
        int i = -1;
        if (user != null){
            i = user.getId();
        }
        return i;
    }
    private long checkDate(Date date) {
        long l = 0;
        if (date != null){
            l = date.getTime();
        }
        return l;
    }

    //Return User, Task
    public User getUser(String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();

        String query =("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = '" + username + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            //user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
            user.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS)));
            user.setNumberTask(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_NUMBERTASK)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        }
        user.setListTask(getTaskOf(user.getId()));
        user.setListReward(getRewardOf(user.getId()));

        cursor.close();
        db.close();

        return user;
    }
    public User getUser(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();

        String query =("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS)));
            user.setNumberTask(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_NUMBERTASK)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            // Adding user record to list
        }
        cursor.close();
        db.close();

        return user;
    }
    public Task getTask(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Task task = new Task();
        Date aDate;

        String query =("SELECT * FROM TASK WHERE " + COLUMN_TASK_NAME + " = '" + name + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
            task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
            task.setAvailable((cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_AVAILABLE))) == 1);
            task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));

            aDate = new Date(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_DUEDATE)));
            task.setDueDate(aDate);

            task.setAssignedUser(null);
            task.setCreator(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));
        }
        cursor.close();
        db.close();

        return task;
    }
    public Recompenses getReward(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Recompenses reward = new Recompenses();

        String query =("SELECT * FROM " + TABLE_REWARD + " WHERE " + COLUMN_USER_ID + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            reward.setName(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_NAME)));
            reward.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_DESCRIPTION)));
            reward.setUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_REWARD_ASSIGNEDUSER))));
        }
        cursor.close();
        db.close();

        return reward;
    }

    //Return list of all user
    public List<User> getAllUser(User profile) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_POINTS,
                COLUMN_USER_NUMBERTASK,
                COLUMN_USER_USERNAME,
                COLUMN_USER_PASSWORD
        };

        String sortOrder =
                COLUMN_USER_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS)));
                user.setNumberTask(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_NUMBERTASK)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                if(user.getId()!=profile.getId()){
                    userList.add(user);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }
    public List<Task> getAllTask() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_TASK_ID,
                COLUMN_TASK_POINTS,
                COLUMN_TASK_AVAILABLE,
                COLUMN_TASK_NAME,
                COLUMN_TASK_DESCRIPTION,
                COLUMN_TASK_DUEDATE,
                COLUMN_TASK_CREATOR,
                COLUMN_TASK_ASSIGNEDUSER
        };
        // sorting orders
        String sortOrder =
                COLUMN_TASK_NAME + " ASC";
        List<Task> taskList = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();
        Date aDate;

        // query the user table
        Cursor cursor = db.query(TABLE_TASK, columns, null, null, null, null, sortOrder);


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_ID))));
                task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
                task.setAvailable(((cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_AVAILABLE)))) == 1);
                task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));

                aDate = new Date(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_DUEDATE)));
                task.setDueDate(aDate);

                task.setCreator(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));
                task.setAssignedUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ASSIGNEDUSER))));

                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return taskList;
    }
    public List<Task> getTaskOf(int id) {

        List<Task> taskList = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();
        Date aDate;

        String query =("SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_ASSIGNEDUSER + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
                task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
                task.setAvailable(((cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_AVAILABLE)))) == 1);
                task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));

                aDate = new Date(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_DUEDATE)));
                task.setDueDate(aDate);

                task.setCreator(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));
                task.setAssignedUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ASSIGNEDUSER))));

                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return taskList;
    }
    public List<Recompenses> getRewardOf(int id) {

        List<Recompenses> rewardList = new ArrayList<Recompenses>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query =("SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_ASSIGNEDUSER + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recompenses reward = new Recompenses();
                //reward.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
                reward.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                reward.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                reward.setUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ASSIGNEDUSER))));

                rewardList.add(reward);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return reward list
        return rewardList;
    }

    //Update user records
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NUMBERTASK, user.getNumberTask());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_POINTS, user.getPoints());

        // Update row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_POINTS, task.getPoints());
        values.put(COLUMN_TASK_AVAILABLE, task.isAvailable());
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DUEDATE, task.getDueDate().getTime());
        values.put(COLUMN_TASK_CREATOR , task.getCreator().getId());
        values.put(COLUMN_TASK_ASSIGNEDUSER , task.getAssignedUser().getId());

        // updating row
        db.update(TABLE_TASK, values, COLUMN_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    //Delete user or task
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TASK, COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }
    public void deleteReward(Recompenses reward) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_REWARD, COLUMN_REWARD_NAME + " = ?", new String[]{String.valueOf(reward.getName())});
        db.close();
    }


    //User or task return 1 if exist & password if it's good
    public boolean checkUser(String username) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_USERNAME + " = ?";

        String[] selectionArgs = {username};

        ///SELECT user_id FROM name WHERE user_name = 'username';
        try {
            Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public boolean checkTask(String name) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_TASK_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_TASK_NAME + " = ?";

        // selection arguments
        String[] selectionArgs = {name};

        try {
            Cursor cursor = db.query(TABLE_TASK, columns, selection, selectionArgs, null, null, null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public String checkPassword(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + " FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if(a.equals(username)) {
                    b = cursor.getString(1);
                    //break;
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return b;
    }

}