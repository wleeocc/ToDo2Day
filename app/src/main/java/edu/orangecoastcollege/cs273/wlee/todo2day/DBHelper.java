package edu.orangecoastcollege.cs273.wlee.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "ToDo2Day";
    static final String DATABASE_TABLE = "Tasks";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    // Create a method to add a brand new task to the database;
    public void addTask (Task newTask){
        // Step 1) Create a reference to our database:
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2) make a key-value pair for each value  you want to insert
        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone());

        // Step 3) Insert values into our database
        db.insert(DATABASE_TABLE, null, values);

        // Step 4) close the database
        db.close();

    }

    // Create a method to get all the tasks in the database
    public ArrayList<Task> getAllTasks(){

        SQLiteDatabase db = this.getReadableDatabase(); // as above

        // Step 2) Make a new ArrayList:
        ArrayList<Task> allTasks = new ArrayList<>();

        // Step 3) Query the database for all records (all rows) and all fields (all columns)
        // The return type of a query is Cursor
        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null); // new String[] {KEY_FIELD_ID} --> to be specific, on second null

        // Step 4) Loop through the results, create Task objects, add to the ArrayList
        if (results.moveToFirst()) // false if non exist
        {
            do {
                int id = results.getInt(0);
                String description = results.getString(1);
                int isDone = results.getInt(2);
                allTasks.add(new Task(id, description, isDone));
            } while (results.moveToNext());
        }

        db.close();
        return allTasks;
    }
}
