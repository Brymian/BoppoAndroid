package com.brymian.boppo.damian.objects;

public class EventUser {

    public Integer eid;
    public Integer uid;

    public String eventUserTypeLabel;
    public String eventUserInviteStatusLabel;
    public String eventUserInviteStatusUpsertTimestamp;

    public EventUser(Integer eid, Integer uid, String eventUserTypeLabel,
        String eventUserInviteStatusLabel, String eventUserInviteStatusUpsertTimestamp)
    {
        this.eid = eid;
        this.uid = uid;
        this.eventUserTypeLabel = eventUserTypeLabel;
        this.eventUserInviteStatusLabel = eventUserInviteStatusLabel;
        this.eventUserInviteStatusUpsertTimestamp = eventUserInviteStatusUpsertTimestamp;
    }

}
