package in.anbti.anubhuti;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout1;
    HashMap<String, Integer> Hash_file_maps1;
    SliderLayout sliderLayout2;
    HashMap<String, Integer> Hash_file_maps2;

    private static final String TAG = "CountdownTimer";

    private ProgressWheel mDaysWheel;
    private ProgressWheel mHoursWheel;
    private ProgressWheel mMinutesWheel;
    private ProgressWheel mSecondsWheel;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;

    // Timer setup
    Time conferenceTime = new Time(Time.getCurrentTimezone());
    int hour = 0;
    int minute = 0;
    int second = 0;
    int monthDay = 17;
    // month is zero based...7 == August
    int month = 1;
    int year = 2017;

    // Values displayed by the timer
    private int mDisplayDays;
    private int mDisplayHours;
    private int mDisplayMinutes;
    private int mDisplaySeconds;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        conferenceTime.setToNow();

        mDaysWheel = (ProgressWheel) rootView.findViewById(R.id.activity_countdown_timer_days);
        mHoursWheel = (ProgressWheel) rootView.findViewById(R.id.activity_countdown_timer_hours);
        mMinutesWheel = (ProgressWheel) rootView.findViewById(R.id.activity_countdown_timer_minutes);
        mSecondsWheel = (ProgressWheel) rootView.findViewById(R.id.activity_countdown_timer_seconds);

        t1 = (TextView) rootView.findViewById(R.id.activity_countdown_timer_days_text);
        t2 = (TextView) rootView.findViewById(R.id.activity_countdown_timer_hours_text);
        t3 = (TextView) rootView.findViewById(R.id.activity_countdown_timer_minutes_text);
        t4 = (TextView) rootView.findViewById(R.id.activity_countdown_timer_seconds_text);

        configureConferenceDate();

        Hash_file_maps1 = new HashMap<>();
        sliderLayout1 = (SliderLayout) rootView.findViewById(R.id.slider1);

        Hash_file_maps1.put("Gajendra Verma", R.drawable.slider1_image1);
        Hash_file_maps1.put("Gajendra Verma.", R.drawable.slider1_image2);
        Hash_file_maps1.put("Star Night", R.drawable.slider1_image3);
        Hash_file_maps1.put("Star Night.", R.drawable.slider1_image4);
        Hash_file_maps1.put("Kavi Sammelan", R.drawable.slider1_image5);
        Hash_file_maps1.put("Kavi Sammelan.", R.drawable.slider1_image6);
        Hash_file_maps1.put("Kavi Sammelan..", R.drawable.slider1_image7);
        Hash_file_maps1.put("Kavi Sammelan...", R.drawable.slider1_image8);
        Hash_file_maps1.put("Kavi Sammelan....", R.drawable.slider1_image9);
        Hash_file_maps1.put("Battle of Bands", R.drawable.slider1_image10);
        Hash_file_maps1.put("Enjoy!", R.drawable.slider1_image11);

        for (String name : Hash_file_maps1.keySet()) {

            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(Hash_file_maps1.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout1.addSlider(textSliderView);
        }
        sliderLayout1.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout1.setCustomAnimation(new DescriptionAnimation());
        sliderLayout1.setDuration(3010);
        sliderLayout1.addOnPageChangeListener(this);


        Hash_file_maps2 = new HashMap<>();
        sliderLayout2 = (SliderLayout) rootView.findViewById(R.id.slider2);

        Hash_file_maps2.put("Nukkad Natak", R.drawable.slider2_image1);
        Hash_file_maps2.put("Fine Arts", R.drawable.slider2_image2);
        Hash_file_maps2.put("Street Art", R.drawable.slider2_image3);
        Hash_file_maps2.put("Charcoal Painting", R.drawable.slider2_image4);
        Hash_file_maps2.put("Street Painting", R.drawable.slider2_image5);
        Hash_file_maps2.put("Dance", R.drawable.slider2_image6);
        Hash_file_maps2.put("Nukkad Natak.", R.drawable.slider2_image7);
        Hash_file_maps2.put("Dancing", R.drawable.slider2_image8);
        Hash_file_maps2.put("Acting", R.drawable.slider2_image9);

        for (String name : Hash_file_maps2.keySet()) {

            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(Hash_file_maps2.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout2.addSlider(textSliderView);
        }
        sliderLayout2.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout2.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout2.setCustomAnimation(new DescriptionAnimation());
        sliderLayout2.setDuration(2090);
        sliderLayout2.addOnPageChangeListener(this);

        return rootView;
    }

    private void configureConferenceDate() {

        conferenceTime.set(second, minute, hour, monthDay, month, year);
        conferenceTime.normalize(true);
        long confMillis = conferenceTime.toMillis(true);

        Time nowTime = new Time(Time.getCurrentTimezone());
        nowTime.setToNow();
        nowTime.normalize(true);
        long nowMillis = nowTime.toMillis(true);

        long milliDiff = confMillis - nowMillis;

        new CountDownTimer(milliDiff, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // decompose difference into days, hours, minutes and seconds
                mDisplayDays = (int) ((millisUntilFinished / 1000) / 86400);
                mDisplayHours = (int) (((millisUntilFinished / 1000) - (mDisplayDays * 86400)) / 3600);
                mDisplayMinutes = (int) (((millisUntilFinished / 1000) - ((mDisplayDays * 86400) + (mDisplayHours * 3600))) / 60);
                mDisplaySeconds = (int) ((millisUntilFinished / 1000) % 60);

                mDaysWheel.setText(String.valueOf(mDisplayDays));
                mDaysWheel.setProgress(mDisplayDays);

                mHoursWheel.setText(String.valueOf(mDisplayHours));
                mHoursWheel.setProgress(mDisplayHours * 15);

                mMinutesWheel.setText(String.valueOf(mDisplayMinutes));
                mMinutesWheel.setProgress(mDisplayMinutes * 6);

                Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
                an.setFillAfter(true);

                mSecondsWheel.setText(String.valueOf(mDisplaySeconds));
                mSecondsWheel.setProgress(mDisplaySeconds * 6);
            }

            @Override
            public void onFinish() {
                //TODO: this is where you would launch a subsequent activity if you'd like.  I'm currently just setting the seconds to zero
                mSecondsWheel.setVisibility(View.INVISIBLE);
                mMinutesWheel.setVisibility(View.INVISIBLE);
                mHoursWheel.setVisibility(View.INVISIBLE);
                mDaysWheel.setVisibility(View.INVISIBLE);
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
                t4.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    @Override
    public void onStop() {
        sliderLayout1.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
