package brymian.bubbles.damian.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventUserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.ImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.EventUser;
import brymian.bubbles.objects.Image;
import brymian.bubbles.objects.User;

/**
 * Created by Ziomster on 7/29/2015.
 */
public class TESTActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
    }

    private void test() {

        /*
        new ServerRequestMethods(this).getUsers("Damian", new UserListCallback() {
            @Override
            public void done(List<User> userList) {
                for (User user : userList)
                {
                    System.out.println("Uid: " + user.getUid());
                    System.out.println("First Name: " + user.getFirstName());
                    System.out.println("Last Name: " + user.getLastName());
                    System.out.println("Username: " + user.getUsername());
                }
            }
        });
        */
        /*
        new ServerRequestMethods(this).getFriendStatus(1, 5, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Not friends."
        });
        new ServerRequestMethods(this).getFriendStatus(5, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Not friends."
        });
        */
        /*
        new ServerRequestMethods(this).setFriendStatus(1, 5, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Friend request sent successfully."
        });
        new ServerRequestMethods(this).setFriendStatus(1, 5, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Already sent friend request to user."
        });
        new ServerRequestMethods(this).getFriendStatus(5, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "User is awaiting confirmation for friend request."
        });
        new ServerRequestMethods(this).setFriendStatus(5, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Friend request accepted."
        });
        // ANY setFriendStatus AND getFriendStatus FOR THE TWO USERS
        // ... SHOULD NOW SAY "Already friends with user."
        */

        /*
        new ServerRequestMethods(this).getFriends(1, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                if (users != null)
                    System.out.println("FRIEND COUNT: " + users.size());
                else
                    System.out.println("UID OF THE USER DOES NOT EXIST OR IS NOT A NUMBER.");
            }
        });
        */

        /*
        new ServerRequestMethods(this).uploadImage(1, "UserImageName", "Regular", "Public", 12.345, 67.890, "lel image", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESPONSE: " + string);
            }
        });
        */

        /*
        new ServerRequestMethods(this).getImagesByUid(1, "All", new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                for (Image image : images) {
                    System.out.println("Image #" + images.indexOf(image));
                    System.out.println("Path: " + image.getPath());
                    System.out.println("Path: " + image.getUserImagePrivacyLabel());
                    System.out.println("Path: " + image.getUserImagePurposeLabel());
                    System.out.println("Path: " + image.getUserImageGpsLatitude());
                    System.out.println("Path: " + image.getUserImageGpsLongitude());
                }
            }
        });
        */

        /*
        int uidA = 1;
        String facebookUid = "1098660393497582";
        new ServerRequestMethods(this).syncUserFacebook(uidA, facebookUid, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });

        int uidB = 1;
        String userAccountPrivacyLabel = "Public";
        new ServerRequestMethods(this).setUserAccountPrivacyLabel(uidB, userAccountPrivacyLabel, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */

        /*
        int uid = 1;
        int uiid = 32;
        String purposeLabel = "Regular";
        new ServerRequestMethods(this).setUserImagePurpose(uid, uiid, purposeLabel, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */
        /*
        int uid = 1;
        int uiid = 30;
        new ServerRequestMethods(this).deleteImage(uid, uiid, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */

        /*
        int uid = 1;
        String newPassword = "lolol444";
        new ServerRequestMethods(this).changePassword(uid, newPassword, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */

        /*
        int uid = 4;
        String newEmail = "lol444@lol.com";
        new ServerRequestMethods(this).changeEmail(uid, newEmail, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */

        /*
        int uid = 1;
        new ServerRequestMethods(this).getUserData(uid, new UserCallback() {
            @Override
            public void done(User user) {
                System.out.println("Uid: " + user.getUid());
                System.out.println("Facebook Uid: " + user.getFacebookUid());
                System.out.println("Google+ Uid: " + user.getGooglepUid());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Password: " + user.getPassword());
                System.out.println("First Name: " + user.getFirstName());
                System.out.println("Last Name: " + user.getLastName());
                System.out.println("E-mail: " + user.getEmail());
                System.out.println("Creation date: " + user.getUserAccountCreationTimestamp());
                System.out.println("Privacy: " + user.getUserAccountPrivacy());
            }
        });
        */

        /*
        new ServerRequestMethods(this).getUserFriendRequestUsers(1, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                System.out.println("TOTAL NUMBER OF USERS WHO SENT A FRIEND REQUEST TO UID 1: " + users.size());

                for (User user : users) {
                    System.out.println("User #" + users.indexOf(user));
                    System.out.println("UID: " + user.getUid());
                }
            }
        });


        new ServerRequestMethods(this).getUserSentFriendRequestUsers(1, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                System.out.println("TOTAL NUMBER OF USERS TO WHOM UID 1 SENT A FRIEND REQUEST: " + users.size());

                for (User user : users) {
                    System.out.println("User #" + users.indexOf(user));
                    System.out.println("UID: " + user.getUid());
                }
            }
        });
        */

        /*
        new EventRequest(this).createEvent(3, "Something Event2", "Public", "Everyone", null, null, null, null, null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("SERVER RESPONSE REGARDING EVENT CREATION: ");
                System.out.println(string);
                System.out.println("Result string size: " + string.length());
            }
        });
        */

        /*
        new EventRequest(this).getEid(3, "Fake Event", new IntegerCallback() {
            @Override
            public void done(Integer integer) {
                System.out.println("SERVER RESPONSE REGARDING EVENT IDENTIFIER: ");
                System.out.println(integer);
            }
        });
        */

        /*
        new EventRequest(this).getEventData(30, new EventCallback() {
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
        new EventRequest(this).deleteEvent(19, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DELETE REQUEST RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new FriendshipStatusRequest(this).blockUser(4, 3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING BLOCK USER RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new ServerRequestMethods(this).getFriendStatus(1, 4, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */
        /*
        new EventUserRequest(this).addUserToEvent(10, 2, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("MESSAGE REGARDING ADDING THE USER TO THE EVENT: ");
                System.out.println(string);
            }
        });
        */
        /*
        new EventUserRequest(this).getEventUserData(18, 4, new EventUserCallback() {
            @Override
            public void done(EventUser eventUser) {
                System.out.println("EID: " + eventUser.eid);
                System.out.println("UID: " + eventUser.uid);
                System.out.println("Event User Type Label: " + eventUser.eventUserTypeLabel);
                System.out.println("Event User Invite Status Label: " + eventUser.eventUserInviteStatusLabel);
                System.out.println("eventUserInviteStatusActionTimestamp: " + eventUser.eventUserInviteStatusActionTimestamp);
            }
        });
        */

        new EventRequest(this).getEventDataByMember(1, new EventListCallback() {
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

        /*
        new ImageRequest(this).getImagesByPrivacyAndPurpose("Public", "Regular", new ImageListCallback() {
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
        */
        /*
        new FriendshipStatusRequest(this).getFriendshipStatusRequestSentUsers(1, "Request Sent", new UserListCallback() {
            @Override
            public void done(List<User> users) {
                System.out.println("TOTAL NUMBER OF USERS TO WHOM UID 1 SENT A REQUEST: " + users.size());

                for (User user : users) {
                    System.out.println("User #" + users.indexOf(user));
                    System.out.println("UID: " + user.getUid());
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("First Name: " + user.getFirstName());
                    System.out.println("Last Name: " + user.getLastName());
                }
                System.out.println();
            }
        });

        new FriendshipStatusRequest(this).getFriendshipStatusRequestReceivedUsers(1, "Request Sent", new UserListCallback() {
            @Override
            public void done(List<User> users) {
                System.out.println("TOTAL NUMBER OF USERS FROM WHOM UID 2 RECEIVED A REQUEST: " + users.size());

                for (User user : users) {
                    System.out.println("User #" + users.indexOf(user));
                    System.out.println("UID: " + user.getUid());
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("First Name: " + user.getFirstName());
                    System.out.println("Last Name: " + user.getLastName());
                }
                System.out.println();
            }
        });
        */
    }

}