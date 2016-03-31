package brymian.bubbles.objects;

public class EventUser {

    public Integer eid;
    public Integer uid;

    public String eventUserTypeLabel;
    public String eventUserInviteStatusLabel;
    public String eventUserInviteStatusActionTimestamp;

    public EventUser(Integer eid, Integer uid, String eventUserTypeLabel,
        String eventUserInviteStatusLabel, String eventUserInviteStatusActionTimestamp)
    {
        this.eid = eid;
        this.uid = uid;
        this.eventUserTypeLabel = eventUserTypeLabel;
        this.eventUserInviteStatusLabel = eventUserInviteStatusLabel;
        this.eventUserInviteStatusActionTimestamp = eventUserInviteStatusActionTimestamp;
    }

}
