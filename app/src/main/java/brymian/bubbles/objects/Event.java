package brymian.bubbles.objects;

public class Event {

    public Integer eid;

    public Integer eventHostUid;
    public String  eventName;

    public String eventHostUsername;
    public String eventHostFirstName;
    public String eventHostLastName;
    public String  eventInviteTypeLabel;
    public String  eventPrivacyLabel;
    public Boolean eventImageUploadAllowedIndicator;
    public String  eventStartDatetime;
    public String  eventEndDatetime;
    public Double  eventGpsLatitude;
    public Double  eventGpsLongitude;
    public Integer eventLikeCount;
    public Integer eventDislikeCount;
    public Long    eventViewCount;

    public Event(Integer eid, Integer eventHostUid, String eventName,
                 String eventHostUsername, String eventHostFirstName, String eventHostLastName,
                 String eventInviteTypeLabel, String eventPrivacyLabel, Boolean eventImageUploadAllowedIndicator,
                 String eventStartDatetime, String eventEndDatetime, Double eventGpsLatitude, Double eventGpsLongitude,
                 Integer eventLikeCount, Integer eventDislikeCount, Long eventViewCount)
    {
        this.eid = eid;

        this.eventHostUid = eventHostUid;
        this.eventName = eventName;

        this.eventHostUsername = eventHostUsername;
        this.eventHostFirstName = eventHostFirstName;
        this.eventHostLastName = eventHostLastName;
        this.eventInviteTypeLabel = eventInviteTypeLabel;
        this.eventPrivacyLabel = eventPrivacyLabel;
        this.eventImageUploadAllowedIndicator = eventImageUploadAllowedIndicator;
        this.eventStartDatetime = eventStartDatetime;
        this.eventEndDatetime = eventEndDatetime;
        this.eventGpsLatitude = eventGpsLatitude;
        this.eventGpsLongitude = eventGpsLongitude;
        this.eventLikeCount = eventLikeCount;
        this.eventDislikeCount = eventDislikeCount;
        this.eventViewCount = eventViewCount;
    }
}