package brymian.bubbles.bryant.episodes;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.episodes.addfriends.EpisodeAddFriends;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;


public class EpisodeCreate extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    EditText etEpisodeTitle;
    CheckBox cbPrivate, cbCurrentLocation;
    String episodeTitle ,privacy;
    TextView tvCamera, tvGallery, tvDate, tvTime;
    ImageView ivImagePreview;
    FloatingActionButton fabDone;
    double latitude;
    double longitude;
    String year, month, dayOfMonth, hourOfDay, minute, second;

    long calendarTimeInMillis;
    boolean isSelected = false;

    int DONE_CODE = 1;
    int CAMERA_CODE = 2;
    int GALLERY_CODE = 3;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.episode_create);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Create_Episode);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etEpisodeTitle = (EditText) findViewById(R.id.etEpisodeTitle);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setText(getDateOnCreate());
        tvDate.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvDate.setOnClickListener(this);

        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText(getTimeOnCreate());
        tvTime.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvTime.setOnClickListener(this);

        cbPrivate = (CheckBox) findViewById(R.id.cbPrivate);
        cbPrivate.setOnCheckedChangeListener(this);

        cbCurrentLocation = (CheckBox) findViewById(R.id.cbCurrentLocation);
        cbCurrentLocation.setOnCheckedChangeListener(this);

        tvCamera = (TextView) findViewById(R.id.tvCamera);
        tvCamera.setOnClickListener(this);

        tvGallery = (TextView) findViewById(R.id.tvGallery);
        tvGallery.setOnClickListener(this);

        ivImagePreview = (ImageView) findViewById(R.id.ivImagePreview);
        ivImagePreview.setVisibility(View.GONE);

        setPrivacy("Public");
        setLatitude(SaveSharedPreference.getLatitude(this));
        setLongitude(SaveSharedPreference.getLongitude(this));

        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDate:
                calendarAlertDialog();
                break;

            case R.id.tvTime:
                timeAlertDialog();
                break;

            case R.id.tvCamera:
                startActivityForResult(new Intent(this, CameraActivity.class), CAMERA_CODE);
                break;

            case R.id.tvGallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);
                break;

            case R.id.fabDone:
                Log.e("upload" , getDate() + " " + getTime());
                new EventRequest(EpisodeCreate.this).createEvent(
                        SaveSharedPreference.getUserUID(EpisodeCreate.this),      /* uid */
                        getEpisodeTitle(),                          /* Episode Title */
                        getPrivacy(),                               /* Episode Privacy */
                        "Host",                                     /* Invite Type */
                        true,                                       /* Episode Image Allowed Indicator */
                        getDate() + " " + getTime(),                /* Episode start time */
                        null,                                       /* Episode end time */
                        getLatitude(),                              /* Episode GPS latitude */
                        getLongitude(),                             /* Episode GPS longitude */
                        new StringCallback() {
                            @Override
                            public void done(String string) {
                                Log.e("createEvent", string);
                                for(String something: string.split(" ")){
                                    if(something.equals("Success.")){
                                        startActivityForResult(new Intent(EpisodeCreate.this, EpisodeAddFriends.class).putExtra("episodeTitle", getEpisodeTitle()), DONE_CODE);
                                    }
                                    else if(something.equals("Duplicate")){
                                        duplicateEntry();
                                    }
                                }
                            }
                        });
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.cbPrivate){
            if(isChecked){
                privacy = "Private";
            }
            else{
                privacy = "Public";
            }
        }

        if(buttonView.getId() == R.id.cbCurrentLocation){
            if (!isChecked){
                latitude = 0;
                longitude = 0;
            }
            else{
                latitude = SaveSharedPreference.getLatitude(this);
                longitude = SaveSharedPreference.getLongitude(this);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DONE_CODE) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
        else if (requestCode == GALLERY_CODE){
            if(resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        ivImagePreview.setVisibility(View.VISIBLE);
                        ivImagePreview.setImageBitmap(bitmap);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if (requestCode == CAMERA_CODE){
            if (resultCode == RESULT_OK){
                Log.e("camera", "works");
            }
        }
    }

    private void calendarAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_calendar_alertdialog, null);

        CalendarView calendarView = (CalendarView) alertLayout.findViewById(R.id.calenderView);
        calendarView.setDate(getSelectedDate());
        calendarView.setMinDate(getMinDate());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                setSelectedDate(year, month, dayOfMonth, true);
                setDate(String.valueOf(year), String.valueOf(month), String.valueOf(dayOfMonth));
                tvDate.setText(getDateToDisplay());
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @TargetApi(23)
    private void timeAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_time_alertdialog, null);

        TimePicker timePicker = (TimePicker) alertLayout.findViewById(R.id.timePicker);
        timePicker.setHour(Integer.valueOf(hourOfDay));
        timePicker.setMinute(Integer.valueOf(minute));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (minute == 0){
                    setTime(String.valueOf(hourOfDay), "00", "00");
                }else {
                    if (String.valueOf(minute).length() == 1){
                        setTime(String.valueOf(hourOfDay), "0" + String.valueOf(minute), "00");
                    }else{
                        setTime(String.valueOf(hourOfDay), String.valueOf(minute), "00");
                    }
                }
                tvTime.setText(getTimeToDisplay());
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private long getMinDate(){
        DateFormat minDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date minDate = new Date();
        String parts[] = minDateFormat.format(minDate).split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

         return calendar.getTimeInMillis();
    }


    private void setSelectedDate(int year, int month, int dayOfMonth, boolean isSelected){
        this.isSelected = isSelected;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.calendarTimeInMillis = calendar.getTimeInMillis();
    }

    private long getSelectedDate(){
        Log.e("getSelectedDate", month);
        if (!isSelected){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(year));
            calendar.set(Calendar.MONTH, Integer.valueOf(month) - 2);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfMonth));
            return calendar.getTimeInMillis();
        }
        else if (isSelected){
            return calendarTimeInMillis;
        }
        return 123;
    }

    private void duplicateEntry() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_alertdialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private String getEpisodeTitle(){
        this.episodeTitle = etEpisodeTitle.getText().toString();
        return episodeTitle;
    }


    private String getTimeOnCreate(){
        DateFormat hourFormat = new SimpleDateFormat("HH");//research getDateTimeInstance()
        Date hourDate = new Date();

        DateFormat minuteFormat = new SimpleDateFormat("mm");
        Date minuteDate = new Date();

        DateFormat secondFormat = new SimpleDateFormat("ss");
        Date secondDate = new Date();

        setTime(hourFormat.format(hourDate), minuteFormat.format(minuteDate), secondFormat.format(secondDate));
        return getTimeToDisplay();
    }

    private String getHourSimple(String hour){
        int newHour = Integer.valueOf(hour);
        if (newHour > 12){
            return String.valueOf(newHour - 12);
        }
        return hour;
    }


    private String getDateOnCreate(){
        DateFormat monthFormat = new SimpleDateFormat("MM");//research getDateTimeInstance()
        Date monthDate = new Date();

        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date yearDate = new Date();

        DateFormat dayFormat = new SimpleDateFormat("dd");
        Date dayDate = new Date();
        Log.e("getDateOnCreate", String.valueOf(Integer.valueOf(monthFormat.format(monthDate)) - 1));
        setDate(yearFormat.format(yearDate), String.valueOf(Integer.valueOf(monthFormat.format(monthDate)) - 1), dayFormat.format(dayDate));
        return getMonthString(monthFormat.format(monthDate)) + " " + dayFormat.format(dayDate) + ", " + yearFormat.format(yearDate);
    }

    private String getMonthString(String mm){
        switch (mm){
            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
        }
        return "bananas";
    }

    private void setDate(String year, String month, String dayOfMonth){
        Log.e("setDateBeforeInt", month);
        int monthNum = Integer.valueOf(month) + 1;
        Log.e("setDateAfterInt", String.valueOf(monthNum));
        if (monthNum < 10){
            this.month = "0" + String.valueOf(monthNum);
        }
        else {
            this.month =  String.valueOf(monthNum);
        }
        this.year = year;
        this.dayOfMonth = dayOfMonth;
    }

    private String getDate(){
        return year + "-" + month + "-" + dayOfMonth;
    }

    private void setTime(String hourOfDay, String minute, String second){
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
    }

    private String getTime(){
        return hourOfDay + ":" + minute + ":" + second;
    }

    private String getDateToDisplay(){
        return getMonthString(month) + " " + dayOfMonth + ", " + year;
    }

    private String getTimeToDisplay(){
        String amPM = "";
        if (Integer.valueOf(hourOfDay) < 12){
            amPM = "AM";
            if (hourOfDay.length() == 2 && String.valueOf(hourOfDay.charAt(0)).equals("0") ){
                hourOfDay = String.valueOf(hourOfDay.charAt(1));
            }else if(hourOfDay.length() == 1 && String.valueOf(hourOfDay).equals("0")){
                return  "12" + ":" + minute + " " + amPM;
            }
        }
        else if (Integer.valueOf(hourOfDay) >= 12){
            amPM = "PM";
        }
        return  getHourSimple(String.valueOf(hourOfDay)) + ":" + minute + " " + amPM;
    }

    private void setPrivacy(String privacy){
        this.privacy = privacy;
    }

    private String getPrivacy(){
        return privacy;
    }

    private void setLatitude(double latitude){
        this.latitude = latitude;
    }

    private double getLatitude(){
        return latitude;
    }

    private void setLongitude(double longitude){
        this.longitude = longitude;
    }

    private double getLongitude(){
        return longitude;
    }

}