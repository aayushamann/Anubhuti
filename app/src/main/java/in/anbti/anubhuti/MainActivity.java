package in.anbti.anubhuti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import static in.anbti.anubhuti.R.menu.main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        Class fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_main, fragment, "home");
//        transaction.addToBackStack(null);
        transaction.commit();
        setTitle(R.string.app_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager manager = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String TAG = "";
//        if (id == R.id.nav_event_registration) {
//            startActivity(new Intent(this, EventRegistrationActivity.class));
//        }
//        else {
        Fragment fragment = null;
        Class fragmentClass = HomeFragment.class;

        if (id == R.id.nav_home) {
            //fragmentClass = HomeFragment.class;
        } else if (id == R.id.nav_event) {
            fragmentClass = MainEventsFragment.class;
            TAG = "events";
        } else if (id == R.id.nav_schedule) {
            fragmentClass = ScheduleFragment.class;
            TAG = "schedule";
        } else if (id == R.id.nav_about) {
            fragmentClass = AboutFragment.class;
            TAG = "about";
        } else if (id == R.id.nav_register) {
            startActivity(new Intent(this, LoginActivity.class));
            TAG = "register";
        } else if (id == R.id.nav_team) {
            fragmentClass = TeamFragment.class;
            TAG = "team";
        } else if (id == R.id.nav_map) {
            fragmentClass = MapFragment.class;
            TAG = "map";
        }


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_main, fragment, TAG);
        if (id != R.id.nav_home) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

        if (id == R.id.nav_home) {
            setTitle(R.string.app_name);
        } else {
            setTitle(item.getTitle());
        }
//        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //MainEventsFragment Button Click Listeners
    public void OnDanceButton(View view) {
        Button button = (Button) findViewById(R.id.dance_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Dancing");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);

    }

    public void OnSingButton(View view) {
        Button button = (Button) findViewById(R.id.sing_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Singing");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

    public void OnDramaButton(View view) {
        Button button = (Button) findViewById(R.id.drama_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Drama");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

    public void OnArtsButton(View view) {
        Button button = (Button) findViewById(R.id.arts_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Arts");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

    public void OnPhotographyButton(View view) {
        Button button = (Button) findViewById(R.id.photo_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Photography");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

    public void OnQuizButton(View view) {
        Button button = (Button) findViewById(R.id.quiz_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Quiz");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

    public void OnLiteraryButton(View view) {
        Button button = (Button) findViewById(R.id.literary_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Literary");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

    public void OnLanButton(View view) {
        Button button = (Button) findViewById(R.id.lan_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "LAN");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

    public void OnInformalButton(View view) {
        Button button = (Button) findViewById(R.id.informal_button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        final Intent intent = new Intent(this, SubEventsActivity.class)
                .putExtra(Intent.EXTRA_TEXT, "Informals");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 230);
    }

}
