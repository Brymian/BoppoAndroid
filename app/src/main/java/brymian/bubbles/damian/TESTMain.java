package brymian.bubbles.damian;

import java.util.List;

import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;

/**
 * Created by Ziomster on 3/17/2016.
 */
public class TESTMain
{
    public static void main (String[] args)
    {
        /*
        Integer eid = 30;

        new EventRequest(this).getEventData(eid, new EventCallback() {
            @Override
            public void done(Event event) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED: ");
                System.out.println();
                System.out.println("EID = " + event.eid);
                System.out.println("Event Host User Identifier = " + event.eventHostUid);
                System.out.println("Event Name = " + event.eventName);
                System.out.println("Event Invite Type Label = " + event.eventInviteTypeLabel);
                System.out.println("Event Privacy Label = " + event.eventPrivacyLabel);
                System.out.println("Event Image Upload Allowed Indicator = " + event.eventImageUploadAllowedIndicator);
                System.out.println("Event Start Datetime = " + event.eventStartDatetime);
                System.out.println("Event End Datetime = " + event.eventEndDatetime);
                System.out.println("Event GPS Latitude = " + event.eventGpsLatitude);
                System.out.println("Event GPS Longitude = " + event.eventGpsLongitude);
                System.out.println("Event Like Count = " + event.eventLikeCount);
                System.out.println("Event Dislike Count = " + event.eventDislikeCount);
                System.out.println("Event View Count = " + event.eventViewCount);
            }
        });
        */

        /*
        Integer eid = 55;
        Integer inviter_uid = 55;
        Integer invitee_uid = 55;

        new EventUserRequest(this).addUserToEvent(eid, inviter_uid, invitee_uid, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */

        /*
        Integer uid = 2;

        new EventRequest(this).getEventDataByMember(uid, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Name = " + event.eventName);
                    System.out.println("Event Invite Type Label = " + event.eventInviteTypeLabel);
                    System.out.println("Event Privacy Label = " + event.eventPrivacyLabel);
                    System.out.println("Event Image Upload Allowed Indicator = " + event.eventImageUploadAllowedIndicator);
                    System.out.println("Event Start Datetime = " + event.eventStartDatetime);
                    System.out.println("Event End Datetime = " + event.eventEndDatetime);
                    System.out.println("Event GPS Latitude = " + event.eventGpsLatitude);
                    System.out.println("Event GPS Longitude = " + event.eventGpsLongitude);
                    System.out.println("Event Like Count = " + event.eventLikeCount);
                    System.out.println("Event Dislike Count = " + event.eventDislikeCount);
                    System.out.println("Event View Count = " + event.eventViewCount);
                }
            }
        });
        */
    }
}
