package fr.univ_reims.julien.healthassistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User user;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = getIntent().getParcelableExtra("user");

        TextView mUserName = (TextView) findViewById(R.id.user_name);
        mUserName.setText(user.getLastName() + " " + user.getFirstName().toUpperCase());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        TextView mUserBirthday = (TextView) findViewById(R.id.user_birthday);
        mUserBirthday.setText(sdf.format(user.getBirthday().getTime()) + " (" + calculateAge(user.getBirthday()) + " ans)");
    }

    @Override
    public void onBackPressed() {
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        TextView mUserName = (TextView) findViewById(R.id.nav_user_name);
        mUserName.setText(user.getLastName() + " " + user.getFirstName().toUpperCase());
        TextView mUserEmail = (TextView) findViewById(R.id.nav_user_email);
        mUserEmail.setText(user.getEmail());

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
        } else if (id== R.id.action_logout) {
            SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(getString(R.string.shared_preferences_healthapp), Context.MODE_PRIVATE);

            HealthAppDbHelper mDbHelper = new HealthAppDbHelper(getContext());
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = HealthAppContract.HealthAppEntry._ID + " LIKE ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { String.valueOf(sharedPref.getInt(getString(R.string.last_login_id), -1)) };
            // Issue SQL statement.
            db.delete(HealthAppContract.HealthAppEntry.TABLE_NAME, selection, selectionArgs);

            // Delete last login id
            sharedPref.edit().clear().apply();

            mDbHelper.close();
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Context getContext() {
        return this.context;
    }

    private int calculateAge(Calendar birthday) {
        Calendar now = Calendar.getInstance();
        int res = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if ((birthday.get(Calendar.MONTH) > now.get(Calendar.MONTH)) || (birthday.get(Calendar.MONTH) == now.get(Calendar.MONTH) && birthday.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)))
            res--;
        return res;
    }

}
