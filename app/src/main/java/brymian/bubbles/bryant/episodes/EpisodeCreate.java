package brymian.bubbles.bryant.episodes;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import brymian.bubbles.R;
import brymian.bubbles.bryant.addLocation.AddLocation;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;


public class EpisodeCreate extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout tilTitle;
    Toolbar mToolbar;
    TextInputEditText tietDescription;
    EditText etEpisodeTitle;
    String episodeTitle ,privacy, locationAddress, locationName;
    String startYear, startMonth, startDayOfMonth, startHourOfDay, startMinute, startSecond;
    String endYear, endMonth, endDayOfMonth, endHourOfDay, endMinute, endSecond;
    TextView tvAddLocation, tvLocationAddress;
    TextView tvStartDate, tvStartTime, tvEndDate, tvEndTime;
    ImageView ivClearLocation;
    FloatingActionButton fabDone;
    double latitude;
    double longitude;

    long calendarStartInMillis, calendarEndInMillis;
    boolean isStartDateSelected = false, isEndDateSelected = false, isEndTimeChanged = false;

    private final int LOCATION_CODE = 4;

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

        tilTitle = (TextInputLayout) findViewById(R.id.tilTitle);
        etEpisodeTitle = (EditText) findViewById(R.id.etEpisodeTitle);

        tvAddLocation = (TextView) findViewById(R.id.tvAddLocation);
        tvAddLocation.setOnClickListener(this);
        tvLocationAddress = (TextView) findViewById(R.id.tvLocationAddress);
        ivClearLocation = (ImageView) findViewById(R.id.ivClearLocation);
        ivClearLocation.setOnClickListener(this);

        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvStartDate.setText(getDateOnCreate());
        tvStartDate.setOnClickListener(this);

        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvStartTime.setText(getTimeOnCreate());
        tvStartTime.setOnClickListener(this);

        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvEndDate.setOnClickListener(this);

        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvEndTime.setOnClickListener(this);

        tietDescription = (TextInputEditText) findViewById(R.id.tietDescription);

        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);
        privacy = "Public";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAddLocation:
                startActivityForResult(new Intent(this, AddLocation.class), LOCATION_CODE);
                break;
            case R.id.tvStartDate:
                calendarStartAlertDialog("Start Date");
                break;

            case R.id.tvEndDate:
                calendarStartAlertDialog("End Date");
                break;

            case R.id.tvStartTime:
                timeStartAlertDialog("Start Time");
                break;

            case R.id.tvEndTime:
                timeStartAlertDialog("End Time");
                break;

            case R.id.ivClearLocation:
                tvAddLocation.setText("Add Location");
                tvLocationAddress.setText(null);
                tvLocationAddress.setVisibility(View.GONE);
                locationAddress = null;
                locationName = null;
                ivClearLocation.setVisibility(View.GONE);
                break;

            case R.id.fabDone:
                createEpisode();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0){
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            super.onBackPressed();
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
        if (requestCode == LOCATION_CODE){
            if (resultCode == RESULT_OK){
                if (data != null){
                    locationName = data.getStringExtra("locationName");
                    locationAddress = data.getStringExtra("locationAddress");
                    latitude = data.getDoubleExtra("locationLat", 0);
                    longitude = data.getDoubleExtra("locationLng", 0);
                    tvLocationAddress.setVisibility(View.VISIBLE);
                    tvLocationAddress.setText(locationAddress);
                    tvAddLocation.setText(locationName);
                    ivClearLocation.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void createEpisode(){
        Log.e("createEpisode",  "title: " +  getEpisodeTitle() +
                                "\ncategory: " + "Social" +
                                "\ntype: " + "Social Event" +
                                "\nprivacy: " + privacy +
                                "\ninvite type: " + "host" +
                                "\nimageAllowed: " + "true" +
                                "\nstartDateTime: " + getStartDate() + " " + getStartTime() +
                                "\nendDateTime: " + getEndDate() + " " + getEndTime() +
                                "\nlocation: " +  tvAddLocation.getText().toString() +
                                "\nlat: " + latitude +
                                "\nlng: " + longitude);
        new EventRequest(this).createEvent(
                SaveSharedPreference.getUserUID(EpisodeCreate.this),
                getEpisodeTitle(),
                "Social",
                "Social Event",
                privacy,
                "Host",
                true,
                tietDescription.getText().toString(),
                getStartDate() + " " + getStartTime(),
                null,
                latitude,
                longitude,
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("string", string);
                        String[] result = string.split(" ");
                        if (result[0].equals("Success.")){
                            tilTitle.setErrorEnabled(false);
                            EpisodeCreateUploadImage episodeCreateUploadImage = new EpisodeCreateUploadImage();
                            Bundle bundle = new Bundle();
                            bundle.putInt("eid", Integer.valueOf(result[2]));
                            episodeCreateUploadImage.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.episode_create, episodeCreateUploadImage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }
                        if (string.contains("Duplicate entry")){
                            tilTitle.setError("Episode already exits");
                        }
                    }
                }

        );

    }

    private void calendarStartAlertDialog(final String title){
        final int[] tempYearStart = new int[1];
        final int[] tempMonthStart = new int[1];
        final int[] tempDayStart = new int[1];
        final int[] tempYearEnd = new int[1];
        final int[] tempMonthEnd = new int[1];
        final int[] tempDayEnd = new int[1];

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_calendar_alertdialog, null);

        final CalendarView calendarView = (CalendarView) alertLayout.findViewById(R.id.calenderView);
        TextView tvTitle = (TextView) alertLayout.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        Button bOk = (Button) alertLayout.findViewById(R.id.bOk);
        Button bCancel = (Button) alertLayout.findViewById(R.id.bCancel);
        Button bClear = (Button) alertLayout.findViewById(R.id.bClear);
        if (title.equals("Start Date")){
            bClear.setVisibility(View.GONE);
            calendarView.setMinDate(getMinStartDate());
            calendarView.setDate(getSelectedStartDate());
        }
        else if (title.equals("End Date")){
            Log.e("getSelEndDate", getSelectedStartDate() + "");
            calendarView.setDate(getSelectedEndDate());
            calendarView.setMinDate(getEndMinDate());
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if (title.equals("Start Date")){
                    tempYearStart[0] = year;
                    tempMonthStart[0] = month;
                    tempDayStart[0] = dayOfMonth;
                }
                else if (title.equals("End Date")){
                    tempYearEnd[0] = year;
                    tempMonthEnd[0] = month;
                    tempDayEnd[0] = dayOfMonth;
                }
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEndDate.setText("--- --, ----");
                isEndDateSelected = false;
                dialog.dismiss();
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals("Start Date")){
                    if (tempDayStart[0] != 0){
                        setSelectedStartDate(tempYearStart[0], tempMonthStart[0], tempDayStart[0], true);
                        setStartDate(String.valueOf(tempYearStart[0]), String.valueOf(tempMonthStart[0]), String.valueOf(tempDayStart[0]));
                        tvStartDate.setText(getStartDateToDisplay());
                        dialog.dismiss();
                    }
                    else {
                        dialog.dismiss();
                    }
                }
                else if (title.equals("End Date")){
                    if (tempDayEnd[0] != 0){
                        setSelectedEndDate(tempYearEnd[0], tempMonthEnd[0], tempDayEnd[0], true);
                        setEndDate(String.valueOf(tempYearEnd[0]), String.valueOf(tempMonthEnd[0]), String.valueOf(tempDayEnd[0]));
                        tvEndDate.setText(getEndDateToDisplay());
                        dialog.dismiss();
                    }
                    else {
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private long getMinStartDate(){
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

    private void setSelectedStartDate(int year, int month, int dayOfMonth, boolean isSelected){
        this.isStartDateSelected = isSelected;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.calendarStartInMillis = calendar.getTimeInMillis();
    }

    private long getSelectedStartDate(){
        if (!isStartDateSelected){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(startYear));
            calendar.set(Calendar.MONTH, Integer.valueOf(startMonth) - 2);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(startDayOfMonth));
            return calendar.getTimeInMillis();
        }
        else if (isStartDateSelected){
            return calendarStartInMillis;
        }
        return 123;
    }

    private void setSelectedEndDate(int year, int month, int dayOfMonth, boolean isSelected){
        this.isEndDateSelected = isSelected;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.calendarEndInMillis = calendar.getTimeInMillis();
    }

    private long getSelectedEndDate(){
        if (isStartDateSelected){
            return calendarEndInMillis;
        }
        return 123;
    }

    private long getEndMinDate(){
        if (!isStartDateSelected){
            return getMinStartDate();
        }
        else {
            return getSelectedStartDate();
        }
    }

    private String getDateOnCreate(){
        DateFormat monthFormat = new SimpleDateFormat("MM");//research getDateTimeInstance()
        Date monthDate = new Date();

        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date yearDate = new Date();

        DateFormat dayFormat = new SimpleDateFormat("dd");
        Date dayDate = new Date();
        setStartDate(yearFormat.format(yearDate), String.valueOf(Integer.valueOf(monthFormat.format(monthDate)) - 1), dayFormat.format(dayDate));
        //return getMonthString(monthFormat.format(monthDate)) + " " + dayFormat.format(dayDate) + ", " + yearFormat.format(yearDate);
        return getStartDateToDisplay();
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

    private void setStartDate(String year, String month, String dayOfMonth){
        int monthNum = Integer.valueOf(month) + 1;
        if (monthNum < 10){
            this.startMonth = "0" + String.valueOf(monthNum);
        }
        else {
            this.startMonth =  String.valueOf(monthNum);
        }
        this.startYear = year;
        this.startDayOfMonth = dayOfMonth;
    }

    private void setEndDate(String year, String month, String dayOfMonth){
        int monthNum = Integer.valueOf(month) + 1;
        if (monthNum < 10){
            this.endMonth = "0" + String.valueOf(monthNum);
        }
        else {
            this.endMonth =  String.valueOf(monthNum);
        }
        this.endYear = year;
        this.endDayOfMonth = dayOfMonth;
    }

    private String getStartDateToDisplay(){
        return getMonthString(startMonth) + " " + startDayOfMonth + ", " + startYear;
    }

    private String getEndDateToDisplay(){
        return getMonthString(endMonth) + " " + endDayOfMonth + ", " + endYear;
    }

    private String getStartDate(){
        return startYear + "-" + startMonth + "-" + startDayOfMonth;
    }

    private String getEndDate(){
        return endYear + "-" + endMonth + "-" + endDayOfMonth;
    }

    private void timeStartAlertDialog(final String title){
        final String[] tempStartHour = new String[1];
        final String[] tempStartMinute = new String[1];
        final String[] tempStartSecond = new String[1];
        final String[] tempEndHour = new String[1];
        final String[] tempEndMinute = new String[1];
        final String[] tempEndSecond = new String[1];
        final boolean[] isChanged = new boolean[1];
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_time_alertdialog, null);

        TextView tvTitle = (TextView) alertLayout.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        Button bOk = (Button) alertLayout.findViewById(R.id.bOk);
        Button bCancel = (Button) alertLayout.findViewById(R.id.bCancel);
        Button bClear = (Button) alertLayout.findViewById(R.id.bClear);
        TimePicker timePicker = (TimePicker) alertLayout.findViewById(R.id.timePicker);
        if (title.equals("Start Time")){
            bClear.setVisibility(View.GONE);
            timePicker.setHour(Integer.valueOf(startHourOfDay));
            timePicker.setMinute(Integer.valueOf(startMinute));
        }
        else if (title.equals("End Time")){
            if (!isEndTimeChanged){
                timePicker.setHour(Integer.valueOf(startHourOfDay));
                timePicker.setMinute(Integer.valueOf(startMinute));
            }
            else if (isEndTimeChanged){
                timePicker.setHour(Integer.valueOf(endHourOfDay));
                timePicker.setMinute(Integer.valueOf(endMinute));
            }
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                isChanged[0] = true;
                if (title.equals("Start Time")){
                    if (minute == 0){
                        tempStartHour[0] = String.valueOf(hourOfDay);
                        tempStartMinute[0] = "00";
                        tempStartSecond[0] = "00";
                    }
                    else {
                        if (String.valueOf(minute).length() == 1){
                            tempStartHour[0] = String.valueOf(hourOfDay);
                            tempStartMinute[0] = "0" + String.valueOf(minute);
                            tempStartSecond[0] = "00";
                        }
                        else{
                            tempStartHour[0] = String.valueOf(hourOfDay);
                            tempStartMinute[0] = String.valueOf(minute);
                            tempStartSecond[0] = "00";
                        }
                    }
                }
                else if (title.equals("End Time")){
                    if (minute == 0){
                        tempEndHour[0] = String.valueOf(hourOfDay);
                        tempEndMinute[0] = "00";
                        tempEndSecond[0] = "00";
                    }
                    else {
                        if (String.valueOf(minute).length() == 1){
                            tempEndHour[0] = String.valueOf(hourOfDay);
                            tempEndMinute[0] = "0" + String.valueOf(minute);
                            tempEndSecond[0] = "00";
                        }
                        else{
                            tempEndHour[0] = String.valueOf(hourOfDay);
                            tempEndMinute[0] = String.valueOf(minute);
                            tempEndSecond[0] = "00";
                        }
                    }
                }
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals("Start Time")){
                    if (isChanged[0]){
                        setStartTime(tempStartHour[0], tempStartMinute[0], tempStartSecond[0]);
                        tvStartTime.setText(getStartTimeToDisplay());
                        dialog.dismiss();
                    }
                    else if (!isChanged[0]){
                        dialog.dismiss();
                    }
                }
                else if (title.equals("End Time")){
                    if (isChanged[0]){
                        isEndTimeChanged = true;
                        setEndTime(tempEndHour[0], tempEndMinute[0], tempEndSecond[0]);
                        tvEndTime.setText(getEndTimeToDisplay());
                        dialog.dismiss();
                    }
                    else if (!isChanged[0]){
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private String getTimeOnCreate(){
        DateFormat hourFormat = new SimpleDateFormat("HH");//research getDateTimeInstance()
        Date hourDate = new Date();

        DateFormat minuteFormat = new SimpleDateFormat("mm");
        Date minuteDate = new Date();

        DateFormat secondFormat = new SimpleDateFormat("ss");
        Date secondDate = new Date();

        setStartTime(hourFormat.format(hourDate), minuteFormat.format(minuteDate), secondFormat.format(secondDate));
        return getStartTimeToDisplay();
    }

    private String getHourSimple(String hour){
        int newHour = Integer.valueOf(hour);
        if (newHour > 12){
            return String.valueOf(newHour - 12);
        }
        return hour;
    }

    private void setStartTime(String hourOfDay, String minute, String second){
        this.startHourOfDay = hourOfDay;
        this.startMinute = minute;
        this.startSecond = second;
    }

    private void setEndTime(String hourOfDay, String minute, String second){
        this.endHourOfDay = hourOfDay;
        this.endMinute = minute;
        this.endSecond = second;
    }

    private String getStartTime(){
        return startHourOfDay + ":" + startMinute + ":" + startSecond;
    }

    private String getEndTime(){
        return endHourOfDay + ":" + endMinute + ":" + startSecond;
    }

    private String getStartTimeToDisplay(){
        String amPM = "";
        if (Integer.valueOf(startHourOfDay) < 12){
            amPM = "AM";
            if (startHourOfDay.length() == 2 && String.valueOf(startHourOfDay.charAt(0)).equals("0")){
                startHourOfDay = String.valueOf(startHourOfDay.charAt(1));
            }
            else if (startHourOfDay.length() == 1 && String.valueOf(startHourOfDay).equals("0")){
                return  "12" + ":" + startMinute + " " + amPM;
            }
        }
        else if (Integer.valueOf(startHourOfDay) >= 12){
            amPM = "PM";
        }
        return  getHourSimple(String.valueOf(startHourOfDay)) + ":" + startMinute + " " + amPM;
    }

    private String getEndTimeToDisplay(){
        String amPM = "";
        if (Integer.valueOf(endHourOfDay) < 12){
            amPM = "AM";
            if (endHourOfDay.length() == 2 && String.valueOf(endHourOfDay.charAt(0)).equals("0")){
                endHourOfDay = String.valueOf(endHourOfDay.charAt(1));
            }
            else if (endHourOfDay.length() == 1 && String.valueOf(endHourOfDay).equals("0")){
                return  "12" + ":" + endMinute + " " + amPM;
            }
        }
        else if (Integer.valueOf(endHourOfDay) >= 12){
            amPM = "PM";
        }
        return  getHourSimple(String.valueOf(endHourOfDay)) + ":" + endMinute + " " + amPM;
    }

    private String getEpisodeTitle(){
        this.episodeTitle = etEpisodeTitle.getText().toString();
        return episodeTitle;
    }
}