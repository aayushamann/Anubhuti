package in.anbti.anubhuti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class SubEventsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int sub_event_count;
    private String[] sub_event_titles;
    private String[] sub_event_types;
    private String[] sub_event_details;
    private int imageId;
    private String eventCategory;
    private String[] contact1;
    private String[] contact2;

    private String fbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_events);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Resources res = getResources();
        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String eventType = intent.getStringExtra(Intent.EXTRA_TEXT);
//           Drawable img = ContextCompat.getDrawable(this, R.drawable.background_dance_event);
            if (eventType.equals("Dancing")) {
                sub_event_count = 6;
                imageId = R.drawable.background_dance_event;
                sub_event_titles = res.getStringArray(R.array.dance_event_names);
                sub_event_types = res.getStringArray(R.array.dance_event_type);
                sub_event_details = res.getStringArray(R.array.dance_event_details);
                eventCategory = getString(R.string.event_dance);
                contact1 = res.getStringArray(R.array.dance_contact1);
                contact2 = res.getStringArray(R.array.dance_contact2);
                setTitle(getString(R.string.event_dance));
            }
            if (eventType.equals("Singing")) {
                sub_event_count = 5;
                imageId = R.drawable.background_singing_event;
                sub_event_titles = res.getStringArray(R.array.singing_event_names);
                sub_event_types = res.getStringArray(R.array.singing_event_type);
                sub_event_details = res.getStringArray(R.array.singing_event_details);
                eventCategory = getString(R.string.event_sing);
                contact1 = res.getStringArray(R.array.singing_contact1);
                contact2 = res.getStringArray(R.array.singing_contact2);
                setTitle(getString(R.string.event_sing));
            }
            if (eventType.equals("Drama")) {
                sub_event_count = 6;
                imageId = R.drawable.background_drama_event;
                sub_event_titles = res.getStringArray(R.array.drama_event_names);
                sub_event_types = res.getStringArray(R.array.drama_event_type);
                sub_event_details = res.getStringArray(R.array.drama_event_details);
                eventCategory = getString(R.string.event_drama);
                contact1 = res.getStringArray(R.array.drama_contact1);
                contact2 = res.getStringArray(R.array.drama_contact2);
                setTitle(getString(R.string.event_drama));
            }
            if (eventType.equals("Arts")) {
                sub_event_count = 7;
                imageId = R.drawable.background_art_event;
                sub_event_titles = res.getStringArray(R.array.fine_arts_event_names);
                sub_event_types = res.getStringArray(R.array.fine_arts_event_type);
                sub_event_details = res.getStringArray(R.array.fine_arts_event_details);
                eventCategory = getString(R.string.event_arts);
                contact1 = res.getStringArray(R.array.fine_arts_contact1);
                contact2 = res.getStringArray(R.array.fine_arts_contact2);
                setTitle(getString(R.string.event_arts));
            }
            if (eventType.equals("Photography")) {
                sub_event_count = 6;
                imageId = R.drawable.background_photography_event;
                sub_event_titles = res.getStringArray(R.array.photography_event_names);
                sub_event_types = res.getStringArray(R.array.photography_event_type);
                sub_event_details = res.getStringArray(R.array.photography_event_details);
                eventCategory = getString(R.string.event_photography);
                contact1 = res.getStringArray(R.array.photography_contact1);
                contact2 = res.getStringArray(R.array.photography_contact2);
                setTitle(getString(R.string.event_photography));
            }
            if (eventType.equals("Quiz")) {
                sub_event_count = 5;
                imageId = R.drawable.background_quiz_event;
                sub_event_titles = res.getStringArray(R.array.quiz_event_names);
                sub_event_types = res.getStringArray(R.array.quiz_event_type);
                sub_event_details = res.getStringArray(R.array.quiz_event_details);
                eventCategory = getString(R.string.event_quiz);
                contact1 = res.getStringArray(R.array.quiz_contact1);
                contact2 = res.getStringArray(R.array.quiz_contact2);
                setTitle(getString(R.string.event_quiz));
            }
            if (eventType.equals("Literary")) {
                sub_event_count = 12;
                imageId = R.drawable.background_literature_event;
                sub_event_titles = res.getStringArray(R.array.literature_event_names);
                sub_event_types = res.getStringArray(R.array.literature_event_type);
                sub_event_details = res.getStringArray(R.array.literature_event_details);
                eventCategory = getString(R.string.event_literary);
                contact1 = res.getStringArray(R.array.literary_contact1);
                contact2 = res.getStringArray(R.array.literary_contact2);
                setTitle(getString(R.string.event_literary));
            }
            if (eventType.equals("LAN")) {
                sub_event_count = 4;
                imageId = R.drawable.background_informals_event;
                sub_event_titles = res.getStringArray(R.array.lan_event_names);
                sub_event_types = res.getStringArray(R.array.lan_event_type);
                sub_event_details = res.getStringArray(R.array.lan_event_details);
                eventCategory = getString(R.string.event_lan);
                contact1 = res.getStringArray(R.array.lan_contact1);
                contact2 = res.getStringArray(R.array.lan_contact2);
                setTitle(getString(R.string.event_lan));
            }
            if (eventType.equals("Informals")) {
                sub_event_count = 10;
                imageId = R.drawable.background_informals_event;
                sub_event_titles = res.getStringArray(R.array.informal_event_names);
                sub_event_types = res.getStringArray(R.array.informal_event_type);
                sub_event_details = res.getStringArray(R.array.informal_event_details);
                eventCategory = getString(R.string.event_informal);
                contact1 = res.getStringArray(R.array.informal_contact1);
                contact2 = res.getStringArray(R.array.informal_contact2);
                setTitle(getString(R.string.event_informal));
            }
        }

        Log.d("Anubhuti Contact1 name", contact1[0]);
        Log.d("Anubhuti Contact1 no.", contact1[1]);
        Log.d("Anubhuti Contact2 name", contact2[0]);
        Log.d("Anubhuti Contact2 no.", contact2[1]);

        Profile profile = Profile.getCurrentProfile();
        if (isInternetConnected() && profile != null) {
            makeGraphRequest();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(contact1[0], contact1[1], contact2[0], contact2[1]);
            }
        });
    }

    public void dialog(final String contact1name, final String contact1no,
                       final String contact2name, final String contact2no) {


        final DialogItem2[] items = {
                new DialogItem2(contact1name, R.drawable.icon_call),
                new DialogItem2(contact2name, R.drawable.icon_call),
        };

        ListAdapter dialogAdapter = new ArrayAdapter<DialogItem2>(
                this,
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                items) {
            public View getView(int position, View convertView, ViewGroup parent) {
                //Use super class to create the View
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView) v.findViewById(android.R.id.text1);
                //Put the image on the TextView
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                //Add margin between image and text (support various screen densities)
                int dp5 = (int) (10 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);
                return v;
            }
        };

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog
                .setAdapter(dialogAdapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String s = "";
                        Intent phoneIntent;
                        switch (which) {
                            case 0:
                                s = "Calling " + contact1name;
                                phoneIntent = new Intent(Intent.ACTION_DIAL);
                                phoneIntent.setData(Uri.parse("tel:+91" + contact1no));
                                startActivity(phoneIntent);
                                break;
                            case 1:
                                s = "Calling " + contact2name;
                                phoneIntent = new Intent(Intent.ACTION_DIAL);
                                phoneIntent.setData(Uri.parse("tel:+91" + contact2no));
                                startActivity(phoneIntent);
                                break;
                        }
                        Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });


        AlertDialog alertDialogB = alertDialog.create();
        alertDialogB.show();
    }

    public static class DialogItem2 {
        public final String text;
        public final int icon;

        DialogItem2(String text, Integer icon) {
            this.text = text;
            this.icon = icon;
        }

        @Override
        public String toString() {
            return text;
        }
    }


    public void makeGraphRequest() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            fbId = object.getString("id");
                            Log.d("Facebook ID OF Student", fbId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_events, menu);
        return true;
    }

    public void OnEventRegister(View view) {
        if (isInternetConnected()) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                String eventCat = eventCategory;
                String ARG_SUB_EVENT_TITLE = "sub_event_title";
                Fragment curFrag = getVisibleFragment();
                String eventName = curFrag.getArguments().getString(ARG_SUB_EVENT_TITLE);

                registerEvent(fbId, eventCat, eventName);
            } else {
                Toast.makeText(SubEventsActivity.this, "You need to be logged in first!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SubEventsActivity.this, LoginActivity.class));
            }
        } else {
            Toast.makeText(SubEventsActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }
//        String eventCat = eventCategory;
//        String ARG_SUB_EVENT_TITLE = "sub_event_title";
//        Fragment curFrag = getVisibleFragment();
//        String eventName = curFrag.getArguments().getString(ARG_SUB_EVENT_TITLE);
//
//        Log.d("Anubhuti EventsCAT", eventCat);
//        Log.d("Anubhuti EventsName", eventName);
//        Log.d("Anubhuti Id", fbId);

    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (int i = 0; i < sub_event_count; i++) {
                Fragment fragment = fragments.get(i);
                if (fragment != null && fragment.isVisible()) {
                    return fragments.get(i + 1);
                }
            }
//            for(Fragment fragment : fragments){
//                if(fragment != null && fragment.isVisible())
//                    return fragment;
//            }
        }
        return null;
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void registerEvent(String id, String cat, String eve) {
        regEvent as = new regEvent(id, cat, eve);
        as.execute();
    }

    class regEvent extends AsyncTask<Void, Void, String> {
        private ProgressDialog loading;
        String studentId;
        String category;
        String event;

        regEvent(final String id, final String cat, final String eve) {
            studentId = id;
            category = cat;
            event = eve;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(SubEventsActivity.this, "Registering...", "Wait...",
                    false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... v) {
            HashMap<String, String> params = new HashMap<>();
            params.put(Config.KEY_FB_ID, studentId);
            params.put(Config.KEY_EVE_CATEGORY, category);
            params.put(Config.KEY_EVE_NAME, event);

            RequestHandler rh = new RequestHandler();
            return rh.sendPostRequest(Config.URL_REGISTER_EVENT, params);
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SUB_EVENT_TITLE = "sub_event_title";
        private static final String ARG_SUB_EVENT_TYPE = "sub_event_type";
        private static final String ARG_SUB_EVENT_DETAIL = "sub_event_detail";
        private static final String ARG_BACKGROUND_IMAGE = "sub_event_background";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String subEventTitle,
                                                      String subEventType, String subEventDetail,
                                                      int image) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SUB_EVENT_TITLE, subEventTitle);
            args.putString(ARG_SUB_EVENT_TYPE, subEventType);
            args.putString(ARG_SUB_EVENT_DETAIL, subEventDetail);
            args.putInt(ARG_BACKGROUND_IMAGE, image);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sub_events, container, false);

            Drawable image = ContextCompat.getDrawable(getContext(),
                    getArguments().getInt(ARG_BACKGROUND_IMAGE));
            LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.sub_event_background);
            linearLayout.setBackground(image);

            TextView eventTitle = (TextView) rootView.findViewById(R.id.sub_event_title);
            TextView eventType = (TextView) rootView.findViewById(R.id.sub_event_type);
            TextView eventDetails = (TextView) rootView.findViewById(R.id.sub_event_details);
            Button regB = (Button) rootView.findViewById(R.id.sub_event_reg_btn);

            String title = getArguments().getString(ARG_SUB_EVENT_TITLE);
            eventTitle.setText(title);
            eventType.setText(getArguments().getString(ARG_SUB_EVENT_TYPE));
            eventDetails.setText(getArguments().getString(ARG_SUB_EVENT_DETAIL));
            if(title.equalsIgnoreCase("About")) {
                regB.setVisibility(View.INVISIBLE);
            }
            else{
                regB.setVisibility(View.VISIBLE);
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            String subEventTitle = sub_event_titles[position];
            String subEventType = sub_event_types[position];
            String subEventDetail = sub_event_details[position];
            int img = imageId;
            return PlaceholderFragment.newInstance(position, subEventTitle, subEventType,
                    subEventDetail, img);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return sub_event_count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return sub_event_titles[0];
                case 1:
                    if (sub_event_count >= 1)
                        return sub_event_titles[1];
                case 2:
                    if (sub_event_count >= 2)
                        return sub_event_titles[2];
                case 3:
                    if (sub_event_count >= 3)
                        return sub_event_titles[3];
                case 4:
                    if (sub_event_count >= 4)
                        return sub_event_titles[4];
                case 5:
                    if (sub_event_count >= 5)
                        return sub_event_titles[5];
                case 6:
                    if (sub_event_count >= 6)
                        return sub_event_titles[6];
                case 7:
                    if (sub_event_count >= 7)
                        return sub_event_titles[7];
                case 8:
                    if (sub_event_count >= 8)
                        return sub_event_titles[8];
                case 9:
                    if (sub_event_count >= 9)
                        return sub_event_titles[9];
                case 10:
                    if (sub_event_count >= 10)
                        return sub_event_titles[10];
                case 11:
                    if (sub_event_count >= 11)
                        return sub_event_titles[11];
                case 13:
                    if (sub_event_count >= 13)
                        return sub_event_titles[12];
            }
            return null;
        }
    }

}
