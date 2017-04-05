package in.anbti.anubhuti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.HashMap;

public class StudentRegActivity extends AppCompatActivity {

//    String StudentEmail;
//    String StudentName;
//    String StudentGender;
//    String StudentId;

    private EditText sName;
    private EditText sEmail;
    private EditText sContact;
    private EditText sCollege;
    private TextView sFbId;
    private RadioGroup sGender;
    private RadioGroup sYear;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_student_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.submit_button);
        button.setEnabled(true);
        sName = (EditText) findViewById(R.id.student_name);
        sEmail = (EditText) findViewById(R.id.student_email);
        sContact = (EditText) findViewById(R.id.student_contact);
        sCollege = (EditText) findViewById(R.id.student_college);
        sGender = (RadioGroup) findViewById(R.id.student_gender);
        sYear = (RadioGroup) findViewById(R.id.student_academic_year);
        sFbId = (TextView) findViewById(R.id.student_facebook_id);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        String email = bundle.getString("email");
        String gender = bundle.getString("gender");
        String fbId = bundle.getString("fbId");

        sName.setText(name);
        sEmail.setText(email);
        sFbId.setText(fbId);
        if (gender.equalsIgnoreCase("male")) {
            sGender.check(R.id.student_gender_male);
        } else {
            sGender.check(R.id.student_gender_female);
        }
        sYear.check(R.id.student_academic_year_first);
    }

//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this, "You Need to Submit this form to be able to Continue..", Toast.LENGTH_SHORT).show();
//    }

    public String getStudentFacebookId() {
        return sFbId.getText().toString();
    }

    public String getStudentName() {
        return sName.getText().toString();
    }

    public String getStudentEmail() {
        return sEmail.getText().toString();
    }


    public String getStudentContact() {
        return sContact.getText().toString();
    }

    public String getStudentCollege() {
        return sCollege.getText().toString();
    }


    public void onSubmitRegistration(View view) {
        RadioGroup studentGenderRadio = (RadioGroup) findViewById(R.id.student_gender);
        RadioGroup studentAcademicYearGroup = (RadioGroup) findViewById(R.id.student_academic_year);

        int selectedGenderId = studentGenderRadio.getCheckedRadioButtonId();
        RadioButton genderRb = (RadioButton) findViewById(selectedGenderId);
        int selectedYearId = studentAcademicYearGroup.getCheckedRadioButtonId();
        RadioButton yearRb = (RadioButton) findViewById(selectedYearId);

        String studentFbId = getStudentFacebookId();
        String studentName = getStudentName();
        String studentEmail = getStudentEmail();
        String studentContact = getStudentContact();
        String studentCollege = getStudentCollege();
        String studentGender = genderRb.getText().toString();
        String st_yr = yearRb.getText().toString();
        String studentAcademicYear = yearRb.getText().toString();

        if (st_yr.equals(getString(R.string.academic_year_first))) {
            studentAcademicYear = "1";
        } else if (st_yr.equals(getString(R.string.academic_year_second))) {
            studentAcademicYear = "2";
        } else if (st_yr.equals(getString(R.string.academic_year_third))) {
            studentAcademicYear = "3";
        } else if (st_yr.equals(getString(R.string.academic_year_final))) {
            studentAcademicYear = "4";
        }

        if (studentFbId.isEmpty() || studentName.isEmpty() || studentEmail.isEmpty() || studentGender.isEmpty() ||
                studentContact.isEmpty() || studentCollege.isEmpty() || studentAcademicYear.isEmpty()) {
            Toast.makeText(this, "All fields are required to be filled", Toast.LENGTH_SHORT).show();
        } else {
            addStudent(studentFbId, studentName, studentEmail, studentGender, studentContact, studentCollege,
                    studentAcademicYear);
        }
    }

    //Registering the student
    private void addStudent(String studentId, String name, String email, String gender,
                            String contact, String college, String year) {
        button.setEnabled(false);

        AddStudent as = new AddStudent(studentId, name, email, gender,
                contact, college, year);
        as.execute();
    }

    class AddStudent extends AsyncTask<Void, Void, String> {
        private ProgressDialog loading;
        String studentId;
        String name;
        String email;
        String gender;
        String contact;
        String college;
        String year;

        AddStudent(final String stId, final String stName, final String stEmail, final String stGender,
                   final String stContact, final String stCollege, final String stYear) {
            studentId = stId;
            name = stName;
            email = stEmail;
            gender = stGender;
            contact = stContact;
            college = stCollege;
            year = stYear;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(StudentRegActivity.this, "Registering...", "Wait...",
                    false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            StudentRegActivity.this.finish();
        }

        @Override
        protected String doInBackground(Void... v) {
            HashMap<String, String> params = new HashMap<>();
            params.put(Config.KEY_FB_ID, studentId);
            params.put(Config.KEY_STUD_NAME, name);
            params.put(Config.KEY_STUD_EMAIL, email);
            params.put(Config.KEY_STUD_GENDER, gender);
            params.put(Config.KEY_STUD_CONTACT, contact);
            params.put(Config.KEY_STUD_COLLEGE, college);
            params.put(Config.KEY_STUD_YEAR, year);

            RequestHandler rh = new RequestHandler();
            return rh.sendPostRequest(Config.URL_ADD_STUDENT, params);
        }
    }
}
