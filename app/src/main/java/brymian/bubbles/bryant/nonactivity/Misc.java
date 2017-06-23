package brymian.bubbles.bryant.nonactivity;

import android.util.Log;

public class Misc {

    public String dateTimeConverter(String dateTime){
        String[] dateTimeArray = dateTime.split(" ");
        String date = dateTimeArray[0];
        String time = dateTimeArray[1];
        String[] dateArray = date.split("-");
        String[] timeArray = time.split(":");
        String year = dateArray[0];
        String month = dateArray[1];
        String day = dateArray[2];
        String hour = timeArray[0];
        String min = timeArray[1];

        //Attempting to take the 0 in front of single digit out
        int dayInt = Integer.valueOf(day);

        switch (month){
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }

        //Attempting to remove the 0 that comes before any single digit
        int hourInt = Integer.valueOf(hour);
        String ampm = "AM";
        if (hourInt > 12){
            hourInt = hourInt - 12;
            ampm = "PM";
        }
        else if (hourInt == 0){
            hourInt = 12;
        }

        return month + " " + dayInt + ", " + year + " at " + hourInt + ":" + min + " " + ampm;
    }

}
