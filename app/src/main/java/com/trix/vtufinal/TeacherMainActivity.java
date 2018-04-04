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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by jeevan on 22/08/17.
 */
public class TeacherMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.teachermain);
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

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        TextView name = (TextView)findViewById(R.id.textView4);
        if(b!=null)
        {
//            Log.e("tidmain",tid);
            tid =(String) b.get("tid");
//            name.setText(tid);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        Intent intent = new Intent(TeacherMainActivity.this, attendenceteacher.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void calender(View view){
        Intent intent = new Intent(TeacherMainActivity.this, calender.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void exam(View view){
        Intent intent = new Intent(TeacherMainActivity.this, exam.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }
    public void internals(View view){
        Intent intent = new Intent(TeacherMainActivity.this,teacherinternalsselect.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void noticeboard(View view){
        Intent intent = new Intent(TeacherMainActivity.this,teachernoticeboard.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void notes(View view){
        Intent intent = new Intent(TeacherMainActivity.this,teachernotes.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void placements(View view){
        Intent intent = new Intent(TeacherMainActivity.this,placement.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }
}
