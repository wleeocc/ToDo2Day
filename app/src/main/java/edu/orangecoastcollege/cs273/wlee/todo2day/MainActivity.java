package edu.orangecoastcollege.cs273.wlee.todo2day;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Let's make a DBHelper reference:
        DBHelper db = new DBHelper(this);

        // Let's make a new task and add to database:
        db.addTask(new Task(1, "Study for CS273 Midterm", 0));
        db.addTask(new Task(2, "Study for CS200 ", 0));
        db.addTask(new Task(3, "Study for MATH 285", 0));
        db.addTask(new Task(4, "Be productive", 0));
        db.addTask(new Task(5, "Clean your room", 0));



    }
}
