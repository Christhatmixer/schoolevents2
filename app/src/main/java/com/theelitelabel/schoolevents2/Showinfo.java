package com.theelitelabel.schoolevents2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

public class Showinfo extends AppCompatActivity {
    String database;
    String card;
    TextView description;
    TextView name;
    TextView organization;
    TextView clock;
   CalendarView calendar;

    private int lastPosition = -1;
    private final static int FADE_DURATION = 1000;

    String foodIntent;
    String musicIntent;
    String merchandiseIntent;
    String latIntent;
    String longitudeIntent;
    String nameIntent;
    String categoryIntent;
    String shareMessageIntent;
    String organizationIntent;
    String descriptionIntent;
    String pictureIntent;
    String endTimeIntent;
    String startTimeIntent;
    ImageView food;
    ImageView merchandise;
    ImageView music;
    ImageView image;

    private boolean FAB_Status = false;
    //FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;

    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.ksu_black));
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        organization = (TextView) findViewById(R.id.organization);
        description = (TextView)findViewById(R.id.description);
        name = (TextView) findViewById(R.id.name);
        food = (ImageView) findViewById(R.id.food);
        music = (ImageView) findViewById(R.id.music);
        merchandise = (ImageView) findViewById(R.id.merchandise);
        image = (ImageView) findViewById(R.id.image);
        clock = (TextView) findViewById(R.id.timeRange);


        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);

        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);



        // Get firebase location from MainActivity
        Intent intent = getIntent();
        card = intent.getStringExtra("card");
        foodIntent = intent.getStringExtra("food");
        musicIntent = intent.getStringExtra("music");
        merchandiseIntent = intent.getStringExtra("merchandise");
        latIntent = intent.getStringExtra("lat");
        longitudeIntent = intent.getStringExtra("longitude");
        nameIntent = intent.getStringExtra("name");
        descriptionIntent = intent.getStringExtra("description");
        categoryIntent = intent.getStringExtra("category");
        organizationIntent = intent.getStringExtra("organization");
        shareMessageIntent = intent.getStringExtra("shareMessage");
        pictureIntent = intent.getStringExtra("picture");
        endTimeIntent = intent.getStringExtra("endTime");
        startTimeIntent = intent.getStringExtra("startTime");

        toolbar.setTitle(card);

        organization.setText(organizationIntent);
        description.setText(descriptionIntent);
        clock.setText(startTimeIntent + "-" + endTimeIntent);
        name.setText(card);
        if (merchandiseIntent.contains("No")){
            merchandise.setImageAlpha(20);

        }
        if (foodIntent.contains("No")){
            food.setImageAlpha(20);
        }
        if (musicIntent.contains("No")){
            music.setImageAlpha(20);


        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Twitter setup
        final TwitterAuthConfig authconfig = new TwitterAuthConfig("WmQL3ZnaKQuXqoPXuQQwRHcNr",
                "JJojHAtS8e9s1yLOmEROswFsvpeHV3VTfRPFNjIvDQioK6XSM4");


        //Facebook
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Coming Soon", Toast.LENGTH_SHORT).show();
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .setContentDescription(descriptionIntent)
                        .setContentTitle(card)
                        .build();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fabric.with(v.getContext(),new TwitterCore(authconfig),new TweetComposer());
                final TweetComposer.Builder builder = new TweetComposer.Builder(v.getContext())
                        .text(shareMessageIntent);
                builder.show();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        organization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                database = "https://school-events-3b62e.firebaseio.com/Organizations";
                Firebase organizationChoice = new Firebase(database);
                organizationChoice.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String site = (String) dataSnapshot.child(organizationIntent).getValue();
                        Intent info = new Intent(v.getContext(),WebActivity.class);
                        info.putExtra("site",site);
                        info.putExtra("name",organizationIntent);
                        v.getContext().startActivity(info);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
        System.out.println(musicIntent);
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference mstorage = storage.getReferenceFromUrl("gs://school-events-3b62e.appspot.com");
        mstorage.child(categoryIntent + "/" + pictureIntent).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                setColorPicture(uri.toString(),image.getContext());

            }
        });


        database = "https://school-events-3b62e.firebaseio.com/" + card;

        final Firebase cardchoice = new Firebase(database);
        cardchoice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                System.out.println(dataSnapshot.child("food").getValue());



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {



            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }

                /*Fabric.with(view.getContext(),new TwitterCore(authconfig),new TweetComposer());
                final TweetComposer.Builder builder = new TweetComposer.Builder(view.getContext())
                        .text(shareMessageIntent);
                builder.show();*/

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.remind){
            Intent Rintent = getIntent();
            String description = Rintent.getStringExtra("description");
            String name = Rintent.getStringExtra("card");
            String address = Rintent.getStringExtra("address");
            String food = Rintent.getStringExtra("food");
            String music = Rintent.getStringExtra("music");
            String merchandise = Rintent.getStringExtra("merchandise");
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.Events.TITLE, name);
            intent.putExtra(CalendarContract.Events.DESCRIPTION,  description);
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, address);


            //intent.putExtra(CalendarContract.Events.)
            startActivity(intent);
        }
        else if(id == R.id.map){
            Intent addressintent = new Intent(getApplicationContext(),map.class);
            addressintent.putExtra("lat",Float.valueOf(latIntent));
            addressintent.putExtra("longitude",Float.valueOf(longitudeIntent));
            addressintent.putExtra("name",nameIntent);
            startActivity(addressintent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    private void setScaleAnimation(View view, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
            lastPosition = position;
        }}
    void setColorPicture(final String imageURL, final Context context) {
        final ImageView postImage = (ImageView) findViewById(R.id.image);
        if (BuildConfig.DEBUG) {
            Picasso.with(context).setIndicatorsEnabled(false);
        }
        Picasso.with(context)
                .load(imageURL)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.ic_menu_camera)
                .fit().centerCrop().into(postImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(context)
                        .load(imageURL)
                        .placeholder(R.drawable.ksuyellowwhite)
                        .fit().centerCrop().into(postImage);
            }
        });
    }
    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }
    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }

}
