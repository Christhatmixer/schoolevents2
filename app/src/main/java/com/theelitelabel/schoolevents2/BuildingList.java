package com.theelitelabel.schoolevents2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class BuildingList extends AppCompatActivity {
    String database;
    private DatabaseReference mDatabase;

    HashMap<String, ArrayList<Integer>> buildings = new HashMap<String, ArrayList<Integer>>();
    String[] buildingsList ={"Bailey Athletic Facility",
            "Bailey Performance Hall", "Baseball Field","Burruss", "Campus Green", "Campus Services", "Chastain Pointe",
            "Clendenin", "Convocation Center", "Education Classroom Facility", "English Building",
            "Gazebo", "House 48 - ASap", "House 49 - Cox Family Enterprise", "House 51 - TBD",
            "House 52 - Clinic", "House 54 - CETL", "House 55 - MEBUS",
            "House 56 - Alumni", "House 57 - Center for Elections",
            "House 58 - Distance Learning", "House 59 - ATOMS",
            "J.M Wilson", "Jolley Lodge", "Kennesaw Hall", "KSU Center",
            "Library", "Math and Statistics", "Music", "Office Annex", "Owl's Nest", "Pilcher",
            "Prillaman Health Sciences", "Public Safety", "Rec Fields",
            "Science", "Science Laboratory", "Student Recreation & Activities Center",
            "Social Sciences", "Softball Field",
            "Sports and Recreation Park", "Student Athlete Success", "Student Center/Bookstore",
            "Tech Annex", "Technology Services", "The Commons", "Town Point",
            "University College", "Visual Arts",
            "Willingham Hall", "Wilson Annex", "Zuckerman Museum",

    "Austin Residence Complex",
            "University Village",
            "KSU Place Apartments"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buildings");
        Firebase.setAndroidContext(this);
        database = "https://school-events-3b62e.firebaseio.com/Buildings";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Buildings");
        final Firebase cardchoice = new Firebase(database);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buildingsList);
        final ListView listview = (ListView) findViewById(R.id.buildingListView);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                int itemposition = position;
                final String itemvalue = (String) listview.getItemAtPosition(position);
                DatabaseReference buildingChoice = mDatabase.child(itemvalue);
                String building = "https://school-events-3b62e.firebaseio.com/Buildings/" + itemvalue;

                buildingChoice.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String lat = (String)dataSnapshot.child("lat").getValue();
                        String longitude = (String) dataSnapshot.child("longitude").getValue();
                        System.out.print(lat);
                        System.out.print(longitude);
                        Intent addressintent = new Intent(view.getContext(),map.class);
                        addressintent.putExtra("lat",Float.valueOf(lat));
                        addressintent.putExtra("longitude",Float.valueOf(longitude));
                        addressintent.putExtra("name",itemvalue);
                        startActivity(addressintent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 1);

            //check for null
            if (address == null) {
                return;
            }

            //Lets take first possibility from the all possibilities.
            Address location = address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            //Put marker on map on that LatLng



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
