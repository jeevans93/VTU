package com.trix.vtufinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class StudentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String USN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.studentmain);
        CardView cardView = (CardView)findViewById(R.id.attendence);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.swing_up_left);
        cardView.startAnimation(animation);
        CardView cardView2 = (CardView)findViewById(R.id.calender);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.swing_up_left);
        cardView2.startAnimation(animation2);
        CardView cardView3 = (CardView)findViewById(R.id.internals);
        Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.swing_up_left);
        cardView3.startAnimation(animation3);
        CardView cardView4 = (CardView)findViewById(R.id.exam);
        Animation animation4 = AnimationUtils.loadAnimation(this,R.anim.swing_up_left);
        cardView4.startAnimation(animation4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            USN =(String) b.get("usn");
            if(b.get("pid")!=null&&!b.get("pid").toString().isEmpty())
            {
                USN=b.getString("pid").substring(0,10);
                Log.e("usnmain",USN);
            }
        }
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            Intent intent =  new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.reset) {
            Intent intent =  new Intent(getApplicationContext(),resetPassword.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void attendence(View view){
        Intent intent = new Intent(StudentMainActivity.this, attendencestudent.class);
        intent.putExtra("usn",USN);
        startActivity(intent);
    }

    public void calender(View view){
        Intent intent = new Intent(StudentMainActivity.this, calender.class);
        startActivity(intent);
    }

    public void exam(View view){
        Intent intent = new Intent(StudentMainActivity.this, exam.class);
        startActivity(intent);
    }
    public void internals(View view){
        Intent intent = new Intent(StudentMainActivity.this,studentinternals.class);
        intent.putExtra("usn",USN);
        startActivity(intent);
    }

    public void noticeboard(View view){
        Intent intent = new Intent(StudentMainActivity.this,noticeboardstudent.class);
        startActivity(intent);
    }

    public void notes(View view){
        Intent intent = new Intent(StudentMainActivity.this,studentnotes.class);
        startActivity(intent);
    }

    public void placements(View view){
        Intent intent = new Intent(StudentMainActivity.this,placement.class);
        startActivity(intent);
    }


}
