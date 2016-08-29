package com.theelitelabel.schoolevents2;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "WmQL3ZnaKQuXqoPXuQQwRHcNr"; //DO NOT SHARE
    private static final String TWITTER_SECRET = "JJojHAtS8e9s1yLOmEROswFsvpeHV3VTfRPFNjIvDQioK6XSM4"; //DO NOT SHARE

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Event, FirebaseHolder> mAdapter;
    //private ArrayList<Boolean> pressStates;
    Boolean[] pressStates;
    boolean firstSession;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("All Events");
        Firebase.setAndroidContext(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        firstSession = true;



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Firebase database = new Firebase("https://school-events-3b62e.firebaseio.com/");
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference mstorage = storage.getReferenceFromUrl("gs://school-events-3b62e.appspot.com");



        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        System.out.println("minute is" + minutes);

        final Query votes = ref.orderByChild("votes"); //order by votes

        final Handler handler = new Handler();


        final Runnable r = new Runnable() {
            public void run() { //not in use




            }
        };

        handler.postDelayed(r, 3000);



        //votes.keepSynced(false);
        Intent intent = getIntent();
        final String user = intent.getStringExtra("user");//get username from login page
        System.out.println(user);
        mAdapter = new FirebaseRecyclerAdapter<Event, FirebaseHolder>(Event.class,R.layout.event,FirebaseHolder.class,votes) {

            @Override
            protected void populateViewHolder(final FirebaseHolder viewHolder, final Event model, final int position) {
                if (model.getCategory().contains("basketball")){
                    mstorage.child("basketball/colorbasketball.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());
                        }
                    });
                    System.out.println(mstorage.child("basketball/basketball.jpg").getDownloadUrl().toString());


                }
                if(model.getCategory().contains("academic")){
                    mstorage.child("academic" +"/"+ model.getPicture().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());

                        }
                    });

                }
                if(model.getCategory().contains("greek")){
                    mstorage.child("greek"+"/"+ model.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());
                        }
                    });

                }
                if(model.getCategory().contains("networking")){
                    mstorage.child("networking" +"/"+ model.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());

                        }
                    });

                }
                viewHolder.mView.findViewById(R.id.address).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent addressintent = new Intent(view.getContext(),map.class);
                        addressintent.putExtra("lat",model.getLat());
                        addressintent.putExtra("longitude",model.getLongitude());
                        addressintent.putExtra("name",model.getLocation_name());
                        System.out.println(model.getLongitude());
                        System.out.println(model.getLat());
                        startActivity(addressintent);
                    }
                });
                viewHolder.setName(model.getName());
                viewHolder.setVotes(Math.abs(model.getVotes()));
                viewHolder.setDescription(model.getDescription());
                if (model.getDescription().length() > 8){
                    viewHolder.setDescription(model.getDescription().substring(0,8) + "...");
                    viewHolder.mView.findViewById(R.id.description).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder full = new AlertDialog.Builder(view.getContext());
                            full.setMessage(model.getDescription());
                            full.show();
                        }
                    });
                }
                viewHolder.setTime(model.getDate());
                viewHolder.setCategory(model.getCategory());
                viewHolder.mView.findViewById(R.id.uparrow).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        String voters = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() + "/voters";
                        final Firebase voterdatabase = new Firebase(voters);
                        voterdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(user).exists()){ //if user has voted for this
                                    System.out.println(dataSnapshot.child(user) + "has voted");
                                    String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                    Firebase truedatabase = new Firebase(database);
                                    truedatabase.runTransaction(new Transaction.Handler() {
                                        @Override
                                        public Transaction.Result doTransaction(MutableData mutableData) {
                                            if (mutableData.getValue() == null) {
                                                mutableData.setValue(-1);

                                            } else {
                                                mutableData.setValue((long) mutableData.getValue() + 1); //take away vote

                                            }
                                            return Transaction.success(mutableData);


                                        }

                                        @Override
                                        public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                            voterdatabase.child(user).removeValue();


                                            //notifyDataSetChanged();


                                        }

                                    });

                                }else{ //user hasnt voted for this
                                    System.out.println("no user");
                                    final String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                    final Firebase truedatabase = new Firebase(database);
                                    truedatabase.runTransaction(new Transaction.Handler() {
                                        @Override
                                        public Transaction.Result doTransaction(MutableData mutableData) {
                                            if (mutableData.getValue() == null) {
                                                mutableData.setValue(-1);

                                            } else {
                                                mutableData.setValue((long) mutableData.getValue() - 1); //add vote



                                            }
                                            return Transaction.success(mutableData);

                                        }

                                        @Override
                                        public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {

                                            //final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            // ref = database.getReference("https://school-events-3b62e.firebaseio.com/" + getItem(position).getName().toString() + "/voters");
                                            //Map<String, Object> voterUpdates = new HashMap<String, Object>();
                                            //voterUpdates.put(user, "voted");

                                            //ref.updateChildren(voterUpdates);

                                            Map<String, Object> nickname = new HashMap<String, Object>();
                                            nickname.put(user, "voted");
                                            voterdatabase.updateChildren(nickname);


                                            //Firebase ref = voterdatabase.push();
                                            //ref.setValue("voted");
                                            //notifyDataSetChanged();
                                            //view.setPressed(true);

                                        }

                                    });

                                }



                                /*for (DataSnapshot votersnapshot: dataSnapshot.getChildren()){
                                    if (votersnapshot.getKey().contains(user)){
                                        System.out.println(votersnapshot.getKey().toString() + "has voted");
                                        String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() + 1);

                                                }
                                                return Transaction.success(mutableData);


                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {

                                                notifyDataSetChanged();


                                            }

                                        });
                                        break;
                                    }
                                    else{
                                        //allowing button press
                                        System.out.println("no user");
                                        final String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        final Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() - 1);

                                                }
                                                return Transaction.success(mutableData);

                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                //final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                // ref = database.getReference("https://school-events-3b62e.firebaseio.com/" + getItem(position).getName().toString() + "/voters");
                                                //Map<String, Object> voterUpdates = new HashMap<String, Object>();
                                                //voterUpdates.put(user, "voted");

                                                //ref.updateChildren(voterUpdates);

                                                Map<String, Object> nickname = new HashMap<String, Object>();
                                                nickname.put(user, "voted");
                                                voterdatabase.updateChildren(nickname);

                                                //Firebase ref = voterdatabase.push();
                                                //ref.setValue("voted");
                                                //notifyDataSetChanged();
                                                //view.setPressed(true);

                                            }

                                        });
                                        break;
                                    }
                                    //end of else statement
                                }*/


                            }


                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });







                    }
                });
                viewHolder.mView.findViewById(R.id.person1_card).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { //goes to detailed card
                        String card = model.getName();
                        Intent intent = new Intent(view.getContext(),Showinfo.class);
                        intent.putExtra("card",card); //send info class card name
                        startActivity(intent);

                    }
                });



            }


        };
        mAdapter.setHasStableIds(true);
        mRecyclerView.setItemAnimator(null);

        mRecyclerView.setAdapter(mAdapter);







    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all) { //shows all events
            Intent intent = getIntent();
            final String user = intent.getStringExtra("user");
            System.out.println(user);
            // Handle the camera action
            getSupportActionBar().setTitle("All Events");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            final FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference mstorage = storage.getReferenceFromUrl("gs://school-events-3b62e.appspot.com");
            Query votes = ref.orderByChild("votes"); //order information by votes
            mAdapter = new FirebaseRecyclerAdapter<Event, FirebaseHolder>(Event.class,R.layout.event,FirebaseHolder.class,votes) {
                @Override
                protected void populateViewHolder(final FirebaseHolder viewHolder, final Event model, final int position) {
                    mstorage.child("greek" +"/"+model.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());
                        }
                    });
                    viewHolder.setName(model.getName());
                    viewHolder.setVotes(Math.abs(model.getVotes()));
                    viewHolder.setDescription(model.getDescription());
                    if (model.getDescription().length() > 8){ //if description is longer than 8 characters. shorten it
                        viewHolder.setDescription(model.getDescription().substring(0,8) + "...");
                        viewHolder.mView.findViewById(R.id.description).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder full = new AlertDialog.Builder(view.getContext());
                                full.setMessage(model.getDescription());
                                full.show();
                            }
                        });
                    }
                    viewHolder.setTime(model.getDate());
                    viewHolder.setCategory(model.getCategory());
                    viewHolder.mView.findViewById(R.id.uparrow).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String voters = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() + "/voters";
                            final Firebase voterdatabase = new Firebase(voters);
                            voterdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(user).exists()){
                                        System.out.println(dataSnapshot.child(user) + "has voted");
                                        String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() + 1);

                                                }
                                                return Transaction.success(mutableData);


                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                voterdatabase.child(user).removeValue();

                                                notifyDataSetChanged();


                                            }

                                        });

                                    }else{
                                        System.out.println("no user");
                                        final String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        final Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() - 1);

                                                }
                                                return Transaction.success(mutableData);

                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) { //add user to voted list
                                                //final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                // ref = database.getReference("https://school-events-3b62e.firebaseio.com/" + getItem(position).getName().toString() + "/voters");
                                                //Map<String, Object> voterUpdates = new HashMap<String, Object>();
                                                //voterUpdates.put(user, "voted");

                                                //ref.updateChildren(voterUpdates);

                                                Map<String, Object> nickname = new HashMap<String, Object>();
                                                nickname.put(user, "voted");
                                                voterdatabase.updateChildren(nickname);

                                                //Firebase ref = voterdatabase.push();
                                                //ref.setValue("voted");
                                                //notifyDataSetChanged();
                                                //view.setPressed(true);

                                            }

                                        });

                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    });
                    viewHolder.mView.findViewById(R.id.person1_card).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //goes to detailed card
                            String card = model.getName();
                            Intent intent = new Intent(view.getContext(),Showinfo.class);
                            intent.putExtra("card",card); //send info class card name
                            startActivity(intent);

                        }
                    });


                }
            };
            mRecyclerView.setAdapter(mAdapter);
        } else if (id == R.id.greek) { //show all greek events
            getSupportActionBar().setTitle("Greek Events");
            Intent intent = getIntent();
            final String user = intent.getStringExtra("user");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference mstorage = storage.getReferenceFromUrl("gs://school-events-3b62e.appspot.com");
            Query greek = ref.orderByChild("category").equalTo("greek"); //organize by greek events
            mAdapter = new FirebaseRecyclerAdapter<Event, FirebaseHolder>(Event.class,R.layout.event,FirebaseHolder.class,greek) {
                @Override
                protected void populateViewHolder(final FirebaseHolder viewHolder, final Event model, final int position) {


                    mstorage.child("greek" +"/"+model.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());
                        }
                    });
                    viewHolder.setName(model.getName());
                    viewHolder.setVotes(Math.abs(model.getVotes()));
                    if (model.getDescription().length() > 8){
                        viewHolder.setDescription(model.getDescription().substring(0,8) + "...");
                        viewHolder.mView.findViewById(R.id.description).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder full = new AlertDialog.Builder(view.getContext());
                                full.setMessage(model.getDescription());
                                full.show();
                            }
                        });
                    }

                    viewHolder.setTime(model.getDate());
                    viewHolder.setCategory(model.getCategory());
                    viewHolder.mView.findViewById(R.id.uparrow).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String voters = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() + "/voters";
                            final Firebase voterdatabase = new Firebase(voters);
                            voterdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(user).exists()){
                                        System.out.println(dataSnapshot.child(user) + "has voted");
                                        String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() + 1);

                                                }
                                                return Transaction.success(mutableData);


                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                voterdatabase.child(user).removeValue();

                                                notifyDataSetChanged();


                                            }

                                        });

                                    }else{
                                        System.out.println("no user"); //gfdgdfghfghgtrshogtrhs
                                        final String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        final Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() - 1);

                                                }
                                                return Transaction.success(mutableData);

                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                //final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                // ref = database.getReference("https://school-events-3b62e.firebaseio.com/" + getItem(position).getName().toString() + "/voters");
                                                //Map<String, Object> voterUpdates = new HashMap<String, Object>();
                                                //voterUpdates.put(user, "voted");

                                                //ref.updateChildren(voterUpdates);

                                                Map<String, Object> nickname = new HashMap<String, Object>();
                                                nickname.put(user, "voted");
                                                voterdatabase.updateChildren(nickname);

                                                //Firebase ref = voterdatabase.push();
                                                //ref.setValue("voted");
                                                //notifyDataSetChanged();
                                                //view.setPressed(true);

                                            }

                                        });

                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    });
                    viewHolder.mView.findViewById(R.id.person1_card).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //goes to detailed card
                            String card = model.getName();
                            Intent intent = new Intent(view.getContext(),Showinfo.class);
                            intent.putExtra("card",card); //send info class card name
                            startActivity(intent);

                        }
                    });


                }
            };
            mRecyclerView.setAdapter(mAdapter);

        } else if (id == R.id.networking) {
            getSupportActionBar().setTitle("Networking Events");
            Intent intent = getIntent();
            final String user = intent.getStringExtra("user");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference mstorage = storage.getReferenceFromUrl("gs://school-events-3b62e.appspot.com");
            Query greek = ref.orderByChild("category").equalTo("networking");
            mAdapter = new FirebaseRecyclerAdapter<Event, FirebaseHolder>(Event.class,R.layout.event,FirebaseHolder.class,greek) {
                @Override
                protected void populateViewHolder(final FirebaseHolder viewHolder, final Event model, final int position) {

                    mstorage.child("networking" +"/"+model.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());
                        }
                    });
                    viewHolder.setName(model.getName());
                    viewHolder.setVotes(Math.abs(model.getVotes()));
                    if (model.getDescription().length() > 8){
                        viewHolder.setDescription(model.getDescription().substring(0,8) + "...");
                        viewHolder.mView.findViewById(R.id.description).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder full = new AlertDialog.Builder(view.getContext());
                                full.setMessage(model.getDescription());
                                full.show();
                            }
                        });
                    }
                    viewHolder.setTime(model.getDate());
                    viewHolder.setCategory(model.getCategory());
                    viewHolder.mView.findViewById(R.id.uparrow).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String voters = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() + "/voters";
                            final Firebase voterdatabase = new Firebase(voters);
                            voterdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(user).exists()){
                                        System.out.println(dataSnapshot.child(user) + "has voted");
                                        String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() + 1);

                                                }
                                                return Transaction.success(mutableData);


                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                voterdatabase.child(user).removeValue();

                                                notifyDataSetChanged();


                                            }

                                        });

                                    }else{
                                        System.out.println("no user");
                                        final String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        final Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() - 1);

                                                }
                                                return Transaction.success(mutableData);

                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                //final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                // ref = database.getReference("https://school-events-3b62e.firebaseio.com/" + getItem(position).getName().toString() + "/voters");
                                                //Map<String, Object> voterUpdates = new HashMap<String, Object>();
                                                //voterUpdates.put(user, "voted");

                                                //ref.updateChildren(voterUpdates);

                                                Map<String, Object> nickname = new HashMap<String, Object>();
                                                nickname.put(user, "voted");
                                                voterdatabase.updateChildren(nickname);

                                                //Firebase ref = voterdatabase.push();
                                                //ref.setValue("voted");
                                                //notifyDataSetChanged();
                                                //view.setPressed(true);

                                            }

                                        });

                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    });
                    viewHolder.mView.findViewById(R.id.person1_card).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //goes to detailed card
                            String card = model.getName();
                            Intent intent = new Intent(view.getContext(),Showinfo.class);
                            intent.putExtra("card",card); //send info class card name
                            startActivity(intent);

                        }
                    });


                }
            };
            mRecyclerView.setAdapter(mAdapter);

        } else if (id == R.id.basketball) {
            getSupportActionBar().setTitle("Basketball");
            Intent intent = getIntent();
            final String user = intent.getStringExtra("user");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference mstorage = storage.getReferenceFromUrl("gs://school-events-3b62e.appspot.com");
            Query greek = ref.orderByChild("category").equalTo("basketball");
            mAdapter = new FirebaseRecyclerAdapter<Event, FirebaseHolder>(Event.class,R.layout.event,FirebaseHolder.class,greek) {
                @Override
                protected void populateViewHolder(final FirebaseHolder viewHolder, final Event model, final int position) {

                    mstorage.child("basketball" +"/"+model.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());
                        }
                    });
                    viewHolder.setName(model.getName());
                    viewHolder.setVotes(Math.abs(model.getVotes()));
                    if (model.getDescription().length() > 8){
                        viewHolder.setDescription(model.getDescription().substring(0,8) + "...");
                        viewHolder.mView.findViewById(R.id.description).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder full = new AlertDialog.Builder(view.getContext());
                                full.setMessage(model.getDescription());
                                full.show();
                            }
                        });
                    }
                    viewHolder.setTime(model.getDate());
                    viewHolder.setCategory(model.getCategory());
                    viewHolder.mView.findViewById(R.id.uparrow).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String voters = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() + "/voters"; //Check who has voted
                            final Firebase voterdatabase = new Firebase(voters);
                            voterdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(user).exists()){ // if this user has voted
                                        System.out.println(dataSnapshot.child(user) + "has voted");
                                        String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() + 1);

                                                }
                                                return Transaction.success(mutableData);


                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                voterdatabase.child(user).removeValue();

                                                notifyDataSetChanged();


                                            }

                                        });

                                    }else{
                                        System.out.println("no user");
                                        final String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        final Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() - 1);

                                                }
                                                return Transaction.success(mutableData);

                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                //final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                // ref = database.getReference("https://school-events-3b62e.firebaseio.com/" + getItem(position).getName().toString() + "/voters");
                                                //Map<String, Object> voterUpdates = new HashMap<String, Object>();
                                                //voterUpdates.put(user, "voted");

                                                //ref.updateChildren(voterUpdates);

                                                Map<String, Object> nickname = new HashMap<String, Object>();
                                                nickname.put(user, "voted");
                                                voterdatabase.updateChildren(nickname);

                                                //Firebase ref = voterdatabase.push();
                                                //ref.setValue("voted");
                                                //notifyDataSetChanged();
                                                //view.setPressed(true);

                                            }

                                        });

                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    });
                    viewHolder.mView.findViewById(R.id.person1_card).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //goes to detailed card
                            String card = model.getName();
                            Intent intent = new Intent(view.getContext(),Showinfo.class);
                            intent.putExtra("card",card); //send info class card name
                            startActivity(intent);

                        }
                    });


                }
            };
            mRecyclerView.setAdapter(mAdapter);

        } else if (id == R.id.academic) {
            getSupportActionBar().setTitle("Academic Events");
            Intent intent = getIntent();
            final String user = intent.getStringExtra("user");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference mstorage = storage.getReferenceFromUrl("gs://school-events-3b62e.appspot.com");
            Query greek = ref.orderByChild("category").equalTo("academic");
            mAdapter = new FirebaseRecyclerAdapter<Event, FirebaseHolder>(Event.class,R.layout.event,FirebaseHolder.class,greek) {
                @Override
                protected void populateViewHolder(final FirebaseHolder viewHolder, final Event model, final int position) {
                    mstorage.child("academic" +"/"+model.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            viewHolder.setPicture(uri.toString(),viewHolder.mView.getContext());
                        }
                    });
                    viewHolder.setName(model.getName());
                    viewHolder.setVotes(Math.abs(model.getVotes()));
                    if (model.getDescription().length() > 8){
                        viewHolder.setDescription(model.getDescription().substring(0,8) + "...");
                        viewHolder.mView.findViewById(R.id.description).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder full = new AlertDialog.Builder(view.getContext());
                                full.setMessage(model.getDescription());
                                full.show();
                            }
                        });
                    }
                    viewHolder.setTime(model.getDate());
                    viewHolder.setCategory(model.getCategory());
                    viewHolder.mView.findViewById(R.id.uparrow).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewHolder.mView.findViewById(R.id.uparrow).setClickable(false);
                            String voters = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() + "/voters"; //Check who has voted
                            final Firebase voterdatabase = new Firebase(voters);
                            voterdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(user).exists()){ // if this user has voted
                                        System.out.println(dataSnapshot.child(user) + "has voted");
                                        String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() + 1);
                                                    viewHolder.mView.findViewById(R.id.uparrow).setClickable(true);

                                                }
                                                return Transaction.success(mutableData);


                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                voterdatabase.child(user).removeValue();

                                                notifyDataSetChanged();


                                            }

                                        });

                                    }else{
                                        System.out.println("no user");
                                        final String database = "https://school-events-3b62e.firebaseio.com/" + getItem(position).getName() +"/votes";
                                        final Firebase truedatabase = new Firebase(database);
                                        truedatabase.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData mutableData) {
                                                if (mutableData.getValue() == null) {
                                                    mutableData.setValue(-1);

                                                } else {
                                                    mutableData.setValue((long) mutableData.getValue() - 1);

                                                }
                                                return Transaction.success(mutableData);

                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                //final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                // ref = database.getReference("https://school-events-3b62e.firebaseio.com/" + getItem(position).getName().toString() + "/voters");
                                                //Map<String, Object> voterUpdates = new HashMap<String, Object>();
                                                //voterUpdates.put(user, "voted");

                                                //ref.updateChildren(voterUpdates);

                                                Map<String, Object> nickname = new HashMap<String, Object>();
                                                nickname.put(user, "voted");
                                                voterdatabase.updateChildren(nickname);

                                                //Firebase ref = voterdatabase.push();
                                                //ref.setValue("voted");
                                                //notifyDataSetChanged();
                                                //view.setPressed(true);

                                            }

                                        });

                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    });
                    viewHolder.mView.findViewById(R.id.person1_card).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String card = model.getName();
                            Intent intent = new Intent(view.getContext(),Showinfo.class);
                            intent.putExtra("card",card);
                            startActivity(intent);

                        }
                    });


                }
            };
            mRecyclerView.setAdapter(mAdapter);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
