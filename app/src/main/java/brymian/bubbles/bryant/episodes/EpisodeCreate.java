package brymian.bubbles.bryant.episodes;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import brymian.bubbles.R;
import brymian.bubbles.bryant.addLocation.AddLocation;
import brymian.bubbles.bryant.cropImage.CropImageActivity;
import brymian.bubbles.bryant.episodes.addfriends.EpisodeAddFriends;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;


public class EpisodeCreate extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout tilTitle;
    Toolbar mToolbar;
    EditText etEpisodeTitle;
    String episodeTitle ,privacy, category, type;
    String startYear, startMonth, startDayOfMonth, startHourOfDay, startMinute, startSecond;
    String endYear, endMonth, endDayOfMonth, endHourOfDay, endMinute, endSecond;
    public static TextView tvAddLocation;
    TextView tvUploadImage, tvChooseLogo, tvStartDate, tvStartTime, tvEndDate, tvEndTime;
    ImageView ivEpisodeImage;
    FloatingActionButton fabDone;
    double latitude;
    double longitude;
    int uiid, eid;

    long calendarStartInMillis, calendarEndInMillis;
    boolean isStartDateSelected = false, isEndDateSelected = false, isEndTimeChanged = false;

    AlertDialog uploadDialog = null;
    AlertDialog mainDialog = null;
    AlertDialog travelDialog = null;
    AlertDialog socialDialog = null;
    AlertDialog sportDialog = null;
    AlertDialog musicDialog = null;
    AlertDialog miscDialog = null;

    int DONE_CODE = 3;
    int CAMERA_CODE = 2;
    int GALLERY_CODE = 1;

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

        tvUploadImage = (TextView) findViewById(R.id.tvUploadImage);
        tvUploadImage.setOnClickListener(this);

        tvChooseLogo = (TextView) findViewById(R.id.tvChooseLogo);
        tvChooseLogo.setOnClickListener(this);

        ivEpisodeImage = (ImageView) findViewById(R.id.ivEpisodeImage);

        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);
        try{
            Log.e("location", getLocation());
        }
        catch (NullPointerException npe){
            npe.printStackTrace();
        }
        setPrivacy("Public");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAddLocation:
                AddLocation addLocation = new AddLocation();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.episode_create, addLocation);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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

            case R.id.tvUploadImage:
                uploadImageDialog();
                break;

            case R.id.tvChooseLogo:
                chooseLogoDialog();
                break;

            /* upload image dialog */
            case R.id.tvCamera:
                //startActivityForResult(new Intent(this, CameraActivity.class), CAMERA_CODE);
                break;

            case R.id.tvGallery:
                startActivityForResult(new Intent(EpisodeCreate.this, CropImageActivity.class).putExtra("from", "gallery"), GALLERY_CODE);
                uploadDialog.dismiss();
                break;

            /* main logo dialog */
            case R.id.tvTravelCategory:
                chooseLogoTravelTypeDialog();
                break;

            case R.id.tvSocialCategory:
                chooseLogoSocialTypeDialog();
                break;

            case R.id.tvSportCategory:
                chooseLogoSportTypeDialog();
                break;

            case R.id.tvMusicCategory:
                chooseLogoMusicTypeDialog();
                break;

            case R.id.tvMiscCategory:
                chooseLogoMiscTypeDialog();
                break;

            /* travel logo dialog */
            case R.id.tvTravel:
                setCategory("Travel");
                setType("Travel");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvVacation:
                setCategory("Travel");
                setType("Vacation");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvHike:
                setCategory("Travel");
                setType("Hike");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvCruise:
                setCategory("Travel");
                setType("Cruise");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvAdventure:
                setCategory("Travel");
                setType("Adventure");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* social logo dialog */
            case R.id.tvSocial:
                setCategory("Social");
                setType("Social");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvParty:
                setCategory("Social");
                setType("Party");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvGetTogether:
                setCategory("Social");
                setType("Get-together");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvWedding:
                setCategory("Social");
                setType("Wedding");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvMovie:
                setCategory("Social");
                setType("Movie");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* sport logo dialog */
            case R.id.tvSport:
                setCategory("Sport");
                setType("Sport");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvSoccer:
                setCategory("Sport");
                setType("Soccer");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvBasketball:
                setCategory("Sport");
                setType("Basketball");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvFootball:
                setCategory("Sport");
                setType("Football");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvBaseball:
                setCategory("Sport");
                setType("Baseball");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvTennis:
                setCategory("Sport");
                setType("Tennis");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* music logo dialog */
            case R.id.tvMusic:
                setCategory("Music");
                setType("Music");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvConcert:
                setCategory("Music");
                setType("Concert");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvFestival:
                setCategory("Music");
                setType("Festival");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvMusical:
                setCategory("Music");
                setType("Musical");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvOpera:
                setCategory("Music");
                setType("Opera");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* misc logo dialog */
            case R.id.tvMisc:
                setCategory("Miscellaneous");
                setType("Miscellaneous");
                miscDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvCarnival:
                setCategory("Miscellaneous");
                setType("Carnival");
                miscDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvReview:
                setCategory("Miscellaneous");
                setType("Review");
                miscDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.fabDone:
                createEpisode();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        location = null;
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
                    byte[] byteArray = data.getByteArrayExtra("image");
                    Bitmap imageDecoded = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    ivEpisodeImage.setImageBitmap(imageDecoded);
                }
            }
        }
        else if (requestCode == CAMERA_CODE){
            if (resultCode == RESULT_OK){
                Log.e("camera", "works");
            }
        }
    }

    private void createEpisode(){
        Log.e("createEpisode",  "title: " +  getEpisodeTitle() +
                                "\ncategory: " + getCategory() +
                                "\ntype: " + getType() +
                                "\nprivacy: " + getPrivacy() +
                                "\ninvite type: " + "host" +
                                "\nimageAllowed: " + "true" +
                                "\nstartDateTime: " + getStartDate() + " " + getStartTime() +
                                "\nendDateTime: " + getEndDate() + " " + getEndTime() +
                                "\nlocation: " + getLocation() +
                                "\nlat: " + getLatitude() +
                                "\nlng: " + getLongitude());
        new EventRequest(this).createEvent(
                SaveSharedPreference.getUserUID(EpisodeCreate.this),
                getEpisodeTitle(),
                getCategory(),
                getType(),
                getPrivacy(),
                "Host",
                true,
                getStartDate() + " " + getStartTime(),
                null,
                getLatitude(),
                getLongitude(),
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        String[] result = string.split(" ");
                        if (result[0].equals("Success.")){
                            tilTitle.setErrorEnabled(false);
                            setEid(Integer.valueOf(result[2]));
                            uploadImage();
                        }
                        if (string.contains("Duplicate entry")){
                            tilTitle.setError("Episode already exits");
                        }
                                /*
                                for(String something: string.split(" ")){
                                    if(something.equals("Success.")){
                                        uploadImage();
                                        //startActivityForResult(new Intent(EpisodeCreate.this, EpisodeAddFriends.class).putExtra("episodeTitle", getEpisodeTitle()), DONE_CODE);
                                    }
                                } **/
                    }
                }

        );
    }

    private void uploadImage(){
        new UserImageRequest(this).uploadImage(
                SaveSharedPreference.getUserUID(this),
                null,
                imageName(),
                "Private",
                null,
                null,
                BitMapToString(((BitmapDrawable) ivEpisodeImage.getDrawable()).getBitmap()),
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        setUiid(Integer.parseInt(string));
                        addImageToEvent();
                    }
                }
        );
    }

    private void addImageToEvent(){
        Integer[] uiids = {getUiid()};
        new EventUserImageRequest(this).addImagesToEvent(getEid(), uiids, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    JSONArray jArray = new JSONArray(string);
                    for (int i = 0; i < jArray.length(); i++){
                        if (jArray.get(i).equals("Success")){
                            setEpisodeProfileImage();
                        }
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setEpisodeProfileImage(){
        new EventUserImageRequest(this).setEuiEventProfileSequence(getEid(), getUiid(), (short)0, new StringCallback() {
            @Override
            public void done(String string) {
                if (string.equals("\"Event user image event profile sequence has been successfully updated.\"")){
                    startActivityForResult(new Intent(EpisodeCreate.this, EpisodeAddFriends.class).putExtra("eid", getEid()), DONE_CODE);
                }
            }
        });
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

    private void uploadImageDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_upload_image_alertdialog, null);

        TextView tvCamera = (TextView) alertLayout.findViewById(R.id.tvCamera);
        TextView tvGallery = (TextView) alertLayout.findViewById(R.id.tvGallery);

        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        uploadDialog = alert.create();
        uploadDialog.setCanceledOnTouchOutside(true);
        uploadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        uploadDialog.show();
    }

    private void chooseLogoDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_alertdialog, null);

        TextView tvTravel = (TextView) alertLayout.findViewById(R.id.tvTravelCategory);
        TextView tvSocial = (TextView) alertLayout.findViewById(R.id.tvSocialCategory);
        TextView tvSport = (TextView) alertLayout.findViewById(R.id.tvSportCategory);
        TextView tvMusic = (TextView) alertLayout.findViewById(R.id.tvMusicCategory);
        TextView tvMisc = (TextView) alertLayout.findViewById(R.id.tvMiscCategory);

        tvTravel.setOnClickListener(this);
        tvSocial.setOnClickListener(this);
        tvSport.setOnClickListener(this);
        tvMusic.setOnClickListener(this);
        tvMisc.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        mainDialog = alert.create();
        mainDialog.setCanceledOnTouchOutside(true);
        mainDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mainDialog.show();
    }

    private void chooseLogoTravelTypeDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_travel_alertdialog, null);

        TextView tvTravel = (TextView) alertLayout.findViewById(R.id.tvTravel);
        TextView tvVacation = (TextView) alertLayout.findViewById(R.id.tvVacation);
        TextView tvHike = (TextView) alertLayout.findViewById(R.id.tvHike);
        TextView tvCruise = (TextView) alertLayout.findViewById(R.id.tvCruise);
        TextView tvAdventure = (TextView) alertLayout.findViewById(R.id.tvAdventure);

        tvTravel.setOnClickListener(this);
        tvVacation.setOnClickListener(this);
        tvHike.setOnClickListener(this);
        tvCruise.setOnClickListener(this);
        tvAdventure.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        travelDialog = alert.create();
        travelDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        travelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        travelDialog.show();
    }

    private void chooseLogoSocialTypeDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_social_alertdialog, null);

        TextView tvSocial = (TextView) alertLayout.findViewById(R.id.tvSocial);
        TextView tvParty = (TextView) alertLayout.findViewById(R.id.tvParty);
        TextView tvGetTogether = (TextView) alertLayout.findViewById(R.id.tvGetTogether);
        TextView tvWedding = (TextView) alertLayout.findViewById(R.id.tvWedding);
        TextView tvMovie = (TextView) alertLayout.findViewById(R.id.tvMovie);

        tvSocial.setOnClickListener(this);
        tvParty.setOnClickListener(this);
        tvGetTogether.setOnClickListener(this);
        tvWedding.setOnClickListener(this);
        tvMovie.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        socialDialog = alert.create();
        socialDialog.setCanceledOnTouchOutside(true);
        socialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        socialDialog.show();
    }

    private void chooseLogoSportTypeDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_sport_alertdialog, null);

        TextView tvSport = (TextView) alertLayout.findViewById(R.id.tvSport);
        TextView tvSoccer = (TextView) alertLayout.findViewById(R.id.tvSoccer);
        TextView tvBasketball = (TextView) alertLayout.findViewById(R.id.tvBasketball);
        TextView tvFootball = (TextView) alertLayout.findViewById(R.id.tvFootball);
        TextView tvBaseball = (TextView) alertLayout.findViewById(R.id.tvBaseball);
        TextView tvTennis = (TextView) alertLayout.findViewById(R.id.tvTennis);

        tvSport.setOnClickListener(this);
        tvSoccer.setOnClickListener(this);
        tvBasketball.setOnClickListener(this);
        tvFootball.setOnClickListener(this);
        tvBaseball.setOnClickListener(this);
        tvTennis.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        sportDialog = alert.create();
        sportDialog.setCanceledOnTouchOutside(true);
        sportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sportDialog.show();
    }

    private void chooseLogoMusicTypeDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_music_alertdialog, null);

        TextView tvMusic = (TextView) alertLayout.findViewById(R.id.tvMusic);
        TextView tvConcert = (TextView) alertLayout.findViewById(R.id.tvConcert);
        TextView tvFestival = (TextView) alertLayout.findViewById(R.id.tvFestival);
        TextView tvMusical = (TextView) alertLayout.findViewById(R.id.tvMusical);
        TextView tvOpera = (TextView) alertLayout.findViewById(R.id.tvOpera);

        tvMusic.setOnClickListener(this);
        tvConcert.setOnClickListener(this);
        tvFestival.setOnClickListener(this);
        tvMusical.setOnClickListener(this);
        tvOpera.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        musicDialog = alert.create();
        musicDialog.setCanceledOnTouchOutside(true);
        musicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        musicDialog.show();
    }

    private void chooseLogoMiscTypeDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_misc_alertdialog, null);

        TextView tvMisc = (TextView) alertLayout.findViewById(R.id.tvMisc);
        TextView tvCarnival = (TextView) alertLayout.findViewById(R.id.tvCarnival);
        TextView tvReview = (TextView) alertLayout.findViewById(R.id.tvReview);

        tvMisc.setOnClickListener(this);
        tvCarnival.setOnClickListener(this);
        tvReview.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        miscDialog = alert.create();
        miscDialog.setCanceledOnTouchOutside(true);
        miscDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        miscDialog.show();
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        return SaveSharedPreference.getUserUID(this) + "_" + charSequenceName;
    }

    private String getEpisodeTitle(){
        this.episodeTitle = etEpisodeTitle.getText().toString();
        return episodeTitle;
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

    private void setUiid(int uiid){
        this.uiid = uiid;
    }

    private int getUiid(){
        return this.uiid;
    }

    private void setCategory(String category){
        this.category = category;
    }

    private String getCategory(){
        return this.category;
    }

    private void setType(String type){
        this.type = type;
    }

    private String getType(){
        return this.type;
    }

    private void setEid(int eid){
        this.eid = eid;
    }

    private int getEid(){
        return this.eid;
    }

    public static String location;

    public static void setLocation(String location){
        EpisodeCreate.location = location;
    }

    private String getLocation(){
        return EpisodeCreate.location;
    }
}