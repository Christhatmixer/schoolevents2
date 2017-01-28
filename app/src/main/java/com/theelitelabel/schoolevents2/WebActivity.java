package com.theelitelabel.schoolevents2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.firebase.client.Firebase;

public class WebActivity extends AppCompatActivity {
    WebView website;
    String database;
    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        Intent intent = getIntent();
        String webLink = intent.getStringExtra("site");
        String organization = intent.getStringExtra("name");
        website = (WebView) findViewById(R.id.website);
        website.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        website.setWebViewClient(new WebViewClient());
        WebSettings webSettings = website.getSettings();
        webSettings.setJavaScriptEnabled(true);
        website.loadUrl(webLink);
        toolbar.setTitle(organization);
        Window w = getWindow();
        w.setTitle(organization);



    }

}
