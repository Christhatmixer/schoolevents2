package com.theelitelabel.schoolevents2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.Calendar;

import io.fabric.sdk.android.Fabric;

public class Showinfo extends AppCompatActivity {
    String database;
    String card;
    TextView description;
    TextView name;
    CalendarView calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        description = (TextView)findViewById(R.id.description);
        name = (TextView) findViewById(R.id.name);
        calendar = (CalendarView) findViewById(R.id.calendarView);


        // Get firebase location from MainActivity
        Intent intent = getIntent();
        card = intent.getStringExtra("card");
        database = "https://school-events-3b62e.firebaseio.com/" + card;

        final Firebase cardchoice = new Firebase(database);
        cardchoice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                description.setText((String)dataSnapshot.child("description").getValue());
                name.setText((String)dataSnapshot.child("name").getValue());

                String date = (String)dataSnapshot.child("date").getValue();;
                String parts[] = date.split("/");

                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                Calendar mcalendar = Calendar.getInstance();
                mcalendar.set(Calendar.YEAR, year);
                mcalendar.set(Calendar.MONTH, month);
                mcalendar.set(Calendar.DAY_OF_MONTH, day);

                long milliTime = mcalendar.getTimeInMillis();

                calendar.setDate(milliTime);



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        //Twitter setup
        final TwitterAuthConfig authconfig = new TwitterAuthConfig("WmQL3ZnaKQuXqoPXuQQwRHcNr",
                "JJojHAtS8e9s1yLOmEROswFsvpeHV3VTfRPFNjIvDQioK6XSM4");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fabric.with(view.getContext(),new TwitterCore(authconfig),new TweetComposer());
                final TweetComposer.Builder builder = new TweetComposer.Builder(view.getContext())
                        .text("Ksu is having an event called" + " " +cardchoice.getKey());
                builder.show();

            }
        });



    }

}
