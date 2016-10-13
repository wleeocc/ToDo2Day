package edu.orangecoastcollege.cs273.wlee.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    private EditText taskEditText;
    private ListView taskListView; // associate with adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR NOW, delete the old database, then create a new one
        //this.deleteDatabase(DBHelper.DATABASE_TABLE);

        // Let's make a DBHelper reference:
        database = new DBHelper(this);
        /*
        DBHelper db = new DBHelper(this);

        // Let's make a new task and add to database:
        db.addTask(new Task(1, "Study for CS273 Midterm", 0));
        db.addTask(new Task(2, "Study for CS200 ", 0));
        db.addTask(new Task(3, "Study for MATH 285", 0));
        db.addTask(new Task(4, "Be productive", 0));
        db.addTask(new Task(5, "Clean your room", 0));

        // Let's get all the tasks from database and print them with Log.i()
        ArrayList<Task> allTasks = db.getAllTasks();

        // Loop through each task, print to Log.i
        for (Task singleTask : allTasks)
            Log.i("DATABASE TASK", singleTask.toString());
*/
        // Let's add one dummy task
        //database.addTask(new Task("Dummy task", 1));

        // Let's fill the list with Tasks from the database
        taskList = database.getAllTasks();

        // Let's create our custom task list adapter
        // (We want to associate the adapter with context, the layout and the List
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        // Connect the ListView with our layout
        taskListView = (ListView) findViewById(R.id.taskListView); // different form OCMusicEv

        // Associate the adapter with the list view
        taskListView.setAdapter(taskListAdapter);

        // Connect the edit text with out layout
        taskEditText = (EditText) findViewById(R.id.taskEditText);

    }

    public void addTask(View view)
    {
        String description = taskEditText.getText().toString();
        if (description.isEmpty())
        {
            Toast.makeText(this,"Task Description cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Make a new task
            Task newTask = new Task(description, 0);
            // Add task to the list adapter
            taskListAdapter.add(newTask); // otherwise(just taskList) layout wont change
            // Add the task to the database
            database.addTask(newTask);

            taskEditText.setText("");
        }
    }

    public void changeTaskStatus(View view)
    {
        // if (view instanceof CheckBox) {
        CheckBox selectedCheckBox = (CheckBox) view;
        Task selectedTask = (Task) selectedCheckBox.getTag();
        selectedTask.setIsDone(selectedCheckBox.isChecked()? 1 : 0);
        // Update the selectedTask in the database
        database.updateTask(selectedTask);

        // }
    }

    public void clearAllTasks(View view)
    {
        // Clear the List
        taskList.clear();
        // Delete all records in the database
        database.deleteAllTasks();
        // Tell the TaskListAdapter to update itself
        taskListAdapter.notifyDataSetChanged();

    }
}
