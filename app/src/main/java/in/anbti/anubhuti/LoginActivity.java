package in.anbti.anubhuti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private CircleImageView imageF;
    private TextView nameF;
    private TextView emailF;
    String StudentEmail;
    String StudentName;
    String StudentGender;
    String StudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        callbackManager = CallbackManager.Factory.create();

        nameF = (TextView) findViewById(R.id.fb_profile_name_id);
        emailF = (TextView) findViewById(R.id.fb_profile_email_id);
        imageF = (CircleImageView) findViewById(R.id.fb_profile_image_id);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        // Other app specific specialization
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                updateUI();
                checkRegistration();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginActivity.this, "Login was cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "Error Logging In", Toast.LENGTH_LONG).show();
            }
        });

        new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    final Profile oldProfile,
                    final Profile currentProfile) {
                updateUI();
                checkRegistration();
            }
        };
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
                            StudentEmail = object.getString("email");
                            StudentGender = object.getString("gender");
                            StudentId = object.getString("id");
                            emailF.setText(StudentEmail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,birthday,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI() {
        Profile profile = Profile.getCurrentProfile();
        if (isInternetConnected()) {
            if (profile != null) {
                makeGraphRequest();
                String imageUrl = profile.getProfilePictureUri(200, 200).toString();
                new DownloadImage(imageF).execute(imageUrl);
                nameF.setText(profile.getName());
            } else {
                imageF.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                nameF.setText("");
                emailF.setText("");
            }
        } else {
            Toast.makeText(LoginActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void checkRegistration() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            StudentName = profile.getName();
            getStudent();
        }
    }

    private void getStudent() {
        class GetStudent extends AsyncTask<Void, Void, String> {
            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Connecting...", "Wait...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                String checkStudent = getJSONString(s);
                if (checkStudent.equals("0")) {
                    Intent intent = new Intent(LoginActivity.this, StudentRegActivity.class);
                    intent.putExtra("fbId", StudentId);
                    intent.putExtra("name", StudentName);
                    intent.putExtra("email", StudentEmail);
                    intent.putExtra("gender", StudentGender);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), "Get Yourself registered", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(getBaseContext(), "Welcome Back " + StudentName + "!!",
                            Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(Config.URL_GET_REG_DETAIL, StudentId);
            }
        }
        GetStudent gs = new GetStudent();
        gs.execute();
    }

    private String getJSONString(String json) {
        String fbId = "0";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            fbId = c.getString(Config.TAG_FB_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fbId;
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
