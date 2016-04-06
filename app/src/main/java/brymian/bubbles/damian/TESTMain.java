package brymian.bubbles.damian;

import java.util.List;

import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.ImageRequest;
import brymian.bubbles.objects.Image;

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
        String imagePrivacyLabel = "";
        String imagePurposeLabel = "";
        new ImageRequest(this).getImagesByPrivacyAndPurpose(imagePrivacyLabel, imagePurposeLabel, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());
                for (Image image : images) {
                    System.out.println("Image #" + images.indexOf(image));
                    System.out.println("Image EID: " + image.userImageEid);
                    System.out.println("Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("Image GPS Longitude: " + image.userImageGpsLongitude);
                }
            }
        });
    }
}
