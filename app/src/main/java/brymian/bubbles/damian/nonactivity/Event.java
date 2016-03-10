package brymian.bubbles.damian.nonactivity;

import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

/**
 * Created by Ziomster on 3/9/2016.
 */
public class Event {

    public String eventName;
    public String eventPrivacyLabel;
    public String eventInviteTypeLabel;
    public String eventStartDatetime;
    public String eventEndDatetime;

    public long eventViewCount;

    public int  eventLikeCount;
    public int  eventDislikeCount;
    public int  eid;
    public int  eventHostUid;

    public double eventGpsLatitude;
    public double eventGpsLongitude;

    public boolean eventImageUploadAllowedIndicator;

    public Event(String eventName, String eventPrivacyLabel, String eventInviteTypeLabel,
         String eventStartDatetime, String eventEndDatetime, long eventViewCount,
         int eventLikeCount, int eventDislikeCount, int eid, int eventHostUid,
         double eventGpsLatitude, double eventGpsLongitude)
    {
        this.eventName = eventName;
        this.eventPrivacyLabel = eventPrivacyLabel;
        this.eventInviteTypeLabel = eventInviteTypeLabel;
        this.eventStartDatetime = eventStartDatetime;
        this.eventEndDatetime = eventEndDatetime;
        this.eventViewCount = eventViewCount;
        this.eventLikeCount = eventLikeCount;
        this.eventDislikeCount = eventDislikeCount;
        this.eid = eid;
        this.eventHostUid = eventHostUid;
        this.eventGpsLatitude = eventGpsLatitude;
        this.eventGpsLongitude = eventGpsLongitude;
    }
}
