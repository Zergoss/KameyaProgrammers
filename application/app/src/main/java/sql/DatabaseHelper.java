package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Task;
import model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DataManager.db";

    // Table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_TASK = "task";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_POINTS = "user_points";
    private static final String COLUMN_USER_NUMBERTASK = "user_numberTask";
    private static final String COLUMN_USER_USERNAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_TASK = "user_listTask";

    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_POINTS = "task_points";
    private static final String COLUMN_TASK_AVAILABLE = "task_available";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";
    private static final String COLUMN_TASK_STARTDATE = "task_startDate";
    private static final String COLUMN_TASK_ENDDATE = "task_endDate";
    private static final String COLUMN_TASK_DUEDATE = "task_dueDate";
    private static final String COLUMN_TASK_CREATOR = "task_creator";
    private static final String COLUMN_TASK_ASSIGNEDUSED = "task_assignedUser";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_POINTS + " INTEGER,"
            + COLUMN_USER_NUMBERTASK + " INTEGER,"
            + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";
            //+ COLUMN_USER_TASK + " LISTTASK"+


    private String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
            + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TASK_POINTS + " INTEGER,"
            + COLUMN_TASK_AVAILABLE + " NUMERIC,"
            + COLUMN_TASK_NAME + " TEXT,"
            + COLUMN_TASK_DESCRIPTION + " TEXT,"
            + COLUMN_TASK_STARTDATE + " NUMERIC,"
            + COLUMN_TASK_ENDDATE + " NUMERIC,"
            + COLUMN_TASK_DUEDATE + " NUMERIC" + ")";
            //+ COLUMN_TASK_CREATOR + " USER,"
            //+ COLUMN_TASK_ASSIGNEDUSED + " USER"

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TABLE_TASK;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TASK_TABLE);

        // Create tables again
        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_NUMBERTASK, user.getNumberTask());
        values.put(COLUMN_USER_POINTS, user.getPoints());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        //values.put(COLUMN_USER_TASK, user.getTask());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_POINTS, task.getPoints());
        values.put(COLUMN_TASK_AVAILABLE, task.isAvailable());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_STARTDATE, task.getStartDate().getTime());
        values.put(COLUMN_TASK_ENDDATE, task.getEndDate().getTime());
        values.put(COLUMN_TASK_DUEDATE, task.getDueDate().getTime());
        //add creator and assign

        // Inserting Row
        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    //Return User, Task
    public User getUser(String username) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_POINTS,
                COLUMN_USER_NUMBERTASK,
                COLUMN_USER_USERNAME,
                COLUMN_USER_PASSWORD
                //COLUMN_USER_TASK
        };

        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();

        String query =("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = " + username);
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
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
        // array of columns to fetch
        String[] columns = {
                COLUMN_TASK_ID,
                COLUMN_TASK_POINTS,
                COLUMN_TASK_AVAILABLE,
                COLUMN_TASK_NAME,
                COLUMN_TASK_DESCRIPTION,
                COLUMN_TASK_STARTDATE,
                COLUMN_TASK_ENDDATE,
                COLUMN_TASK_DUEDATE
                //COLUMN_TASK_CREATOR
                //COLUMN_TASK_ASSIGNEDUSED
        };

        SQLiteDatabase db = this.getReadableDatabase();
        Task task = new Task();
        Date aDate;

        String query =("SELECT * FROM LIST WHERE " + COLUMN_TASK_NAME + " = " + name);
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            task.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_ID))));
            task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
            task.setAvailable(((cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_AVAILABLE)))) == 1);
            task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
            aDate = new Date(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
            task.setStartDate(aDate);
            aDate = new Date(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
            task.setEndDate(aDate);
            aDate = new Date(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
            task.setDueDate(aDate);
            // Adding user record to list
        }
        cursor.close();
        db.close();

        return task;
    }

    //Return list of all user
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_POINTS,
                COLUMN_USER_NUMBERTASK,
                COLUMN_USER_USERNAME,
                COLUMN_USER_PASSWORD
                //COLUMN_USER_TASK
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
                userList.add(user);
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
                COLUMN_TASK_STARTDATE,
                COLUMN_TASK_ENDDATE,
                COLUMN_TASK_DUEDATE
                //COLUMN_TASK_CREATOR
                //COLUMN_TASK_ASSIGNEDUSED
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
                aDate = new Date(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                task.setStartDate(aDate);
                aDate = new Date(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                task.setEndDate(aDate);
                aDate = new Date(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                task.setDueDate(aDate);
                // Adding user record to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return taskList;
    }

    //Update user records
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_POINTS, user.getPoints());
        values.put(COLUMN_USER_NUMBERTASK, user.getNumberTask());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        //listTask

        // updating row
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
        values.put(COLUMN_TASK_STARTDATE, task.getStartDate().getTime());
        values.put(COLUMN_TASK_ENDDATE, task.getEndDate().getTime());
        values.put(COLUMN_TASK_DUEDATE, task.getDueDate().getTime());
        //CREATOR AND ASSIGN

        // updating row
        db.update(TABLE_TASK, values, COLUMN_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    //Delete user or task
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TASK, COLUMN_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    //User or task return 1 if exist & if password is good
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

        Cursor cursor = db.query(TABLE_TASK, columns, selection, selectionArgs, null, null,null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
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
                    break;
                }
            } while(cursor.moveToNext());
        }

        return b;
    }

}