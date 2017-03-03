package brymian.bubbles.damian.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.MiscellaneousRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.NewsFeedRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserCommentRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.Image;
import brymian.bubbles.objects.User;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getDoubleObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getIntegerObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getLongObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getBooleanObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.printLongString;

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
        new ServerRequestMethods(this).getUsers(1, "Damian", new UserListCallback() {
            @Override
            public void done(List<User> userList) {
                for (User user : userList)
                {
                    System.out.println("Uid: " + user.getUid());
                    System.out.println("First Name: " + user.getFirstName());
                    System.out.println("Last Name: " + user.getLastName());
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Friendship Status: " + user.getFriendshipStatus());
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
        new UserImageRequest(this).uploadImage(1, "UserImageName", "Regular", "Public", 12.345, 67.890, "lel image", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESPONSE: " + string);
            }
        });
        */
        /*
        new UserImageRequest(this).getImagesByUidAndPurpose(2, "Profile", null, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                for (Image image : images) {
                    System.out.println();
                    System.out.println("USER IMAGE #: " + images.indexOf(image));
                    System.out.println("--------------------");
                    System.out.println("User Image Identifier: " + image.uiid);
                    System.out.println("User Identifier: " + image.uid);
                    System.out.println("User Image Profile Sequence" + image.userImageProfileSequence);
                    System.out.println("User Image Sequence: " + image.userImageSequence);
                    System.out.println("User Image Path: " + image.userImagePath);
                    System.out.println("User Image Name: " + image.userImageName);
                    System.out.println("User Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("User Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("User Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("User Image GPS Longitude: " + image.userImageGpsLongitude);
                }
            }
        });
        new UserImageRequest(this).setImage(7, 9, null, null, null, null, null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        new UserImageRequest(this).getImagesByUidAndPurpose(2, "Profile", null, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                for (Image image : images) {
                    System.out.println();
                    System.out.println("USER IMAGE #: " + images.indexOf(image));
                    System.out.println("--------------------");
                    System.out.println("User Image Identifier: " + image.uiid);
                    System.out.println("User Identifier: " + image.uid);
                    System.out.println("User Image Sequence: " + image.userImageSequence);
                    System.out.println("User Image Profile Sequence" + image.userImageProfileSequence);
                    System.out.println("User Image Path: " + image.userImagePath);
                    System.out.println("User Image Name: " + image.userImageName);
                    System.out.println("User Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("User Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("User Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("User Image GPS Longitude: " + image.userImageGpsLongitude);
                }
            }
        });
        */
        /*
        new UserImageRequest(this).getImagesByUidAndPurpose(1, "Regular", false, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                for (Image image : images) {
                    System.out.println();
                    System.out.println("USER IMAGE #: " + images.indexOf(image));
                    System.out.println("--------------------");
                    System.out.println("User Image Identifier: " + image.uiid);
                    System.out.println("User Identifier: " + image.uid);
                    System.out.println("User Image Sequence: " + image.userImageSequence);
                    System.out.println("User Image Profile Sequence: " + image.userImageProfileSequence);
                    System.out.println("User Image Path: " + image.userImagePath);
                    System.out.println("User Image Name: " + image.userImageName);
                    System.out.println("User Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("User Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("User Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("User Image GPS Longitude: " + image.userImageGpsLongitude);
                }
            }
        });
        new UserImageRequest(this).getImagesByUidAndPurpose(1, "Regular", null, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                for (Image image : images) {
                    System.out.println();
                    System.out.println("USER IMAGE #: " + images.indexOf(image));
                    System.out.println("--------------------");
                    System.out.println("User Image Identifier: " + image.uiid);
                    System.out.println("User Identifier: " + image.uid);
                    System.out.println("User Image Sequence: " + image.userImageSequence);
                    System.out.println("User Image Profile Sequence: " + image.userImageProfileSequence);
                    System.out.println("User Image Path: " + image.userImagePath);
                    System.out.println("User Image Name: " + image.userImageName);
                    System.out.println("User Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("User Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("User Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("User Image GPS Longitude: " + image.userImageGpsLongitude);
                }
            }
        });
        */
        /*
        new UserImageRequest(this).getImagesByPrivacyAndPurpose("Public", "Regular", true, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                for (Image image : images) {
                    System.out.println();
                    System.out.println("USER IMAGE #: " + images.indexOf(image));
                    System.out.println("--------------------");
                    System.out.println("User Image Identifier: " + image.uiid);
                    System.out.println("User Identifier: " + image.uid);
                    System.out.println("User Image Sequence: " + image.userImageSequence);
                    System.out.println("User Image Profile Sequence: " + image.userImageProfileSequence);
                    System.out.println("User Image Path: " + image.userImagePath);
                    System.out.println("User Image Name: " + image.userImageName);
                    System.out.println("User Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("User Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("User Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("User Image GPS Longitude: " + image.userImageGpsLongitude);
                }
            }
        });
        new UserImageRequest(this).getImagesByPrivacyAndPurpose("Public", "Regular", false, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                for (Image image : images) {
                    System.out.println();
                    System.out.println("USER IMAGE #: " + images.indexOf(image));
                    System.out.println("--------------------");
                    System.out.println("User Image Identifier: " + image.uiid);
                    System.out.println("User Identifier: " + image.uid);
                    System.out.println("User Image Sequence: " + image.userImageSequence);
                    System.out.println("User Image Profile Sequence: " + image.userImageProfileSequence);
                    System.out.println("User Image Path: " + image.userImagePath);
                    System.out.println("User Image Name: " + image.userImageName);
                    System.out.println("User Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("User Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("User Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("User Image GPS Longitude: " + image.userImageGpsLongitude);
                }
            }
        });
        new UserImageRequest(this).getImagesByPrivacyAndPurpose("Public", "Regular", null, new ImageListCallback() {
            @Override
            public void done(List<Image> images) {
                System.out.println("TOTAL NUMBER OF IMAGES: " + images.size());

                Boolean lel = true;
                System.out.println(lel);

                for (Image image : images) {
                    System.out.println();
                    System.out.println("USER IMAGE #: " + images.indexOf(image));
                    System.out.println("--------------------");
                    System.out.println("User Image Identifier: " + image.uiid);
                    System.out.println("User Identifier: " + image.uid);
                    System.out.println("User Image Sequence: " + image.userImageSequence);
                    System.out.println("User Image Profile Sequence: " + image.userImageProfileSequence);
                    System.out.println("User Image Path: " + image.userImagePath);
                    System.out.println("User Image Name: " + image.userImageName);
                    System.out.println("User Image Privacy Label: " + image.userImagePrivacyLabel);
                    System.out.println("User Image Purpose Label: " + image.userImagePurposeLabel);
                    System.out.println("User Image GPS Latitude: " + image.userImageGpsLatitude);
                    System.out.println("User Image GPS Longitude: " + image.userImageGpsLongitude);
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
        int userImageSequence = 32;
        String purposeLabel = "Regular";
        new ServerRequestMethods(this).setUserImagePurpose(uid, userImageSequence, purposeLabel, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */
        /*
        int uid = 1;
        int userImageSequence = 30;
        new ServerRequestMethods(this).deleteImage(uid, userImageSequence, new StringCallback() {
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
        new UserRequest(this).setUser(3, null, null, null, "987-654-3210", "Public", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });

        new ServerRequestMethods(this).getUserData(3, new UserCallback() {
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
                System.out.println("Phone: " + user.getPhone());
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
        new EventRequest(this).createEvent(3, "Lel An Event 4", "Public", "Everyone", null, null, null, null, null, new StringCallback() {
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
                System.out.println("EVENT:");
                System.out.println();
                System.out.println("EID = " + event.eid);
                System.out.println("Event Host User Identifier = " + event.eventHostUid);
                System.out.println("Event Host Username = " + event.eventHostUsername);
                System.out.println("Event Host First Name = " + event.eventHostFirstName);
                System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        new FriendshipStatusRequest(this).blockUser(3, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING BLOCK USER RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new FriendshipStatusRequest(this).unblockUser(3, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING UNBLOCK USER RESPONSE HAS BEEN RETURNED: ");
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
        new EventUserRequest(this).addUserToEvent(53, 3, 4, new StringCallback() {
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
        /*
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
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        /*
        new EventRequest(this).getLiveEventDataByRadius(-74.2, 40.7, 50.0, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("<!!!> JSON STRING: <!!!>");
                System.out.println(string);

                try
                {
                    JSONArray jArray = new JSONArray(string);
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        JSONObject jArray_jObject = jArray.getJSONObject(i);
                        JSONObject jEvent = jArray_jObject.getJSONObject("event");
                        System.out.println("<!> EVENT #: " + i + " <!>");

                        System.out.println("Event ID: " + jEvent.getInt("eid"));
                        System.out.println("Event Host UID: " + jEvent.getInt("eventHostUid"));
                        System.out.println("Event Name: " + jEvent.getString("eventName"));
                        System.out.println("Event Host Username: " + jEvent.getString("eventHostUsername"));
                        System.out.println("Event Host First Name: " + jEvent.getString("eventHostFirstName"));
                        System.out.println("Event Host Last Name: " + jEvent.getString("eventHostLastName"));
                        System.out.println("Event Invite Type Label: " +
                            jEvent.getString("eventInviteTypeLabel"));
                        System.out.println("Event Privacy Label: " + jEvent.getString("eventPrivacyLabel"));
                        System.out.println("Event Image Upload Allowed Indicator: " +
                            getBooleanObjectFromObject(jEvent.get("eventImageUploadAllowedIndicator")));
                        System.out.println("Event Start Datetime: " + jEvent.getString("eventStartDatetime"));
                        System.out.println("Event End Datetime: " + jEvent.getString("eventEndDatetime"));
                        System.out.println("Event GPS Latitude: " +
                            getDoubleObjectFromObject(jEvent.get("eventGpsLatitude")));
                        System.out.println("Event GPS Longitude: " +
                            getDoubleObjectFromObject(jEvent.get("eventGpsLongitude")));
                        System.out.println("Event Like Count: " + jEvent.getInt("eventLikeCount"));
                        System.out.println("Event Dislike Count: " + jEvent.getInt("eventDislikeCount"));
                        System.out.println("Event View Count: " + jEvent.getLong("eventViewCount"));

                        Double distanceFromLocation = jArray_jObject.getDouble("distanceFromLocation");
                        System.out.println("Distance From Location: " + distanceFromLocation);
                    }
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        */
        /*
        new FriendshipStatusRequest(this).getFriendshipStatusRequestSentUsers(3, "Blocked", new UserListCallback() {
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
        */
        /*
        new FriendshipStatusRequest(this).getFriendshipStatusRequestReceivedUsers(1, "Friendship Pending", new UserListCallback() {
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
        /*
        new FriendshipStatusRequest(this).rejectFriend(18, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING REJECT FRIEND RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new FriendshipStatusRequest(this).cancelFriend(1, 18, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING CANCEL FRIEND RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new FriendshipStatusRequest(this).unFriend(22, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING CANCEL FRIEND RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new MiscellaneousRequest(this).incrementObjectViewCount(38, "Event", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING INCREMENT OBJECT VIEW COUNT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new EventRequest(this).getEventDataByTopNViews(3, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 VIEW EVENTS: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        /*
        new EventRequest(this).getEventDataByTopNLikes(3, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 LIKED EVENTS: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        new EventRequest(this).getEventDataByTopNDislikes(3, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 DISLIKED EVENTS: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        /*
        new UserLikeRequest(this).setObjectLikeOrDislike(3, "Event", 53, false, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT LIKE OR DISLIKE RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */
        /*
        new UserCommentRequest(this).setObjectComment(3, "Event", 53, null,
            "This is the second test comment.", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(1, "User Image", 10, null,
            "gadgafd", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(3, "User Image", 22, null,
            "sdhghdsfgfddfs", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(4, "User Image", 22, null,
            "MORE GIBBERISH.", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(4, "User Image", 22, null,
            "AAAAAAAAAAAAAAAH", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(5, "User Image", 24, null,
            "The rofflecopter goes schwooh schwooh schwooh", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(1, "User", 3, null,
            "Sup dawg?", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(3, "User", 3, null,
            "Updog.", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(3, "User", 3, null,
            "fghhdsfgsdfsdfhgdfgfsadsfdg", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        try {Thread.sleep(1000);} catch (InterruptedException ie) { ie.printStackTrace();}
        new UserCommentRequest(this).setObjectComment(3, "User", 1, null,
            "Amigo!", null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING SET OBJECT COMMENT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */

        /*
        new UserCommentRequest(this).getObjectComments("Event", 56, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING GET OBJECT COMMENTS RESPONSE HAS BEEN RETURNED: ");
                printLongString(string);

                //try {
                //    JSONObject jObject = new JSONObject(string);
                //    System.out.println(jObject.getJSONArray("comments").getJSONObject(0).getJSONObject("image").getString("userImagePath"));
                //}
                //catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        */
        /*
        new EventRequest(this).updateEvent(38, 3, "Funniest Shiz Evah", "Private", "Friends", null, null, null, null, null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("SERVER RESPONSE REGARDING EVENT CREATION: ");
                System.out.println(string);
                System.out.println("Result string size: " + string.length());
            }
        });
        */
        /*
        new UserImageRequest(this).getImageProfileMaxAmount(new IntegerCallback() {
            @Override
            public void done(Integer integer) {
                System.out.println("SERVER RESPONSE REGARDING IMAGE PROFILE MAX AMOUNT: " + integer);
            }
        });
        */
        /*
        new EventRequest(this).getEventDataByName("test", new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING EVENT PARTIAL NAME: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        /*
        new EventRequest(this).getLiveEventDataByName("test", new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING EVENT PARTIAL NAME: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        new EventRequest(this).getLiveEventDataByMember(1, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        new EventRequest(this).getLiveEventDataByTopNViews(3, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 VIEW EVENTS: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        new EventRequest(this).getLiveEventDataByTopNLikes(3, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 LIKED EVENTS: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        new EventRequest(this).getLiveEventDataByTopNDislikes(3, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 DISLIKED EVENTS: ");

                for (Event event : eventList)
                {
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println();
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Host Username = " + event.eventHostUsername);
                    System.out.println("Event Host First Name = " + event.eventHostFirstName);
                    System.out.println("Event Host Last Name = " + event.eventHostUsername);
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
        /*
        new EventRequest(this).getEventDataByTopNRatings(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("<!!!> JSON STRING: <!!!>");
                System.out.println(string);
                System.out.println("<!!!> END OF JSON STRING: <!!!>");

                try
                {
                    JSONArray jArray = new JSONArray(string);
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        JSONObject jArray_jObject = jArray.getJSONObject(i);
                        JSONObject jEvent = jArray_jObject.getJSONObject("event");
                        System.out.println("<!> EVENT #: " + i + " <!>");

                        System.out.println("Event ID: " + jEvent.getInt("eid"));
                        System.out.println("Event Host UID: " + jEvent.getInt("eventHostUid"));
                        System.out.println("Event Name: " + jEvent.getString("eventName"));
                        System.out.println("Event Host Username: " + jEvent.getString("eventHostUsername"));
                        System.out.println("Event Host First Name: " + jEvent.getString("eventHostFirstName"));
                        System.out.println("Event Host Last Name: " + jEvent.getString("eventHostLastName"));
                        System.out.println("Event Invite Type Label: " +
                                jEvent.getString("eventInviteTypeLabel"));
                        System.out.println("Event Privacy Label: " + jEvent.getString("eventPrivacyLabel"));
                        System.out.println("Event Image Upload Allowed Indicator: " +
                                getBooleanObjectFromObject(jEvent.get("eventImageUploadAllowedIndicator")));
                        System.out.println("Event Start Datetime: " + jEvent.getString("eventStartDatetime"));
                        System.out.println("Event End Datetime: " + jEvent.getString("eventEndDatetime"));
                        System.out.println("Event GPS Latitude: " +
                                getDoubleObjectFromObject(jEvent.get("eventGpsLatitude")));
                        System.out.println("Event GPS Longitude: " +
                                getDoubleObjectFromObject(jEvent.get("eventGpsLongitude")));
                        System.out.println("Event Like Count: " + jEvent.getInt("eventLikeCount"));
                        System.out.println("Event Dislike Count: " + jEvent.getInt("eventDislikeCount"));
                        System.out.println("Event View Count: " + jEvent.getLong("eventViewCount"));
                        System.out.println("Event Rating Ratio: " + jEvent.getDouble("eventRatingRatio"));
                    }
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        new EventRequest(this).getLiveEventDataByTopNRatings(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("<!!!> JSON STRING: <!!!>");
                System.out.println(string);
                System.out.println("<!!!> END OF JSON STRING: <!!!>");

                try
                {
                    JSONArray jArray = new JSONArray(string);
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        JSONObject jArray_jObject = jArray.getJSONObject(i);
                        JSONObject jEvent = jArray_jObject.getJSONObject("event");
                        System.out.println("<!> EVENT #: " + i + " <!>");

                        System.out.println("Event ID: " + jEvent.getInt("eid"));
                        System.out.println("Event Host UID: " + jEvent.getInt("eventHostUid"));
                        System.out.println("Event Name: " + jEvent.getString("eventName"));
                        System.out.println("Event Host Username: " + jEvent.getString("eventHostUsername"));
                        System.out.println("Event Host First Name: " + jEvent.getString("eventHostFirstName"));
                        System.out.println("Event Host Last Name: " + jEvent.getString("eventHostLastName"));
                        System.out.println("Event Invite Type Label: " +
                                jEvent.getString("eventInviteTypeLabel"));
                        System.out.println("Event Privacy Label: " + jEvent.getString("eventPrivacyLabel"));
                        System.out.println("Event Image Upload Allowed Indicator: " +
                                getBooleanObjectFromObject(jEvent.get("eventImageUploadAllowedIndicator")));
                        System.out.println("Event Start Datetime: " + jEvent.getString("eventStartDatetime"));
                        System.out.println("Event End Datetime: " + jEvent.getString("eventEndDatetime"));
                        System.out.println("Event GPS Latitude: " +
                                getDoubleObjectFromObject(jEvent.get("eventGpsLatitude")));
                        System.out.println("Event GPS Longitude: " +
                                getDoubleObjectFromObject(jEvent.get("eventGpsLongitude")));
                        System.out.println("Event Like Count: " + jEvent.getInt("eventLikeCount"));
                        System.out.println("Event Dislike Count: " + jEvent.getInt("eventDislikeCount"));
                        System.out.println("Event View Count: " + jEvent.getLong("eventViewCount"));
                        System.out.println("Event Rating Ratio: " + jEvent.getDouble("eventRatingRatio"));
                    }
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        */
        /*
        new UserImageRequest(this).uploadImage(1, "UserImageName2", "Regular", "Public", 12.345, 67.890, "lel image", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESPONSE: " + string);
            }
        });
        */
        /*
        new UserImageRequest(this).addImagesToEvent(59, Arrays.asList(1, 4, 7), new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("<!!!> JSON STRING: <!!!>");
                System.out.println(string);
                System.out.println("<!!!> END OF JSON STRING: <!!!>");

                try
                {
                    JSONArray jArray = new JSONArray(string);
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        System.out.println("Status of Adding Image #" + i + " to UIID: " + jArray.get(i));
                    }
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        */
        /*
        new UserImageRequest(this).getImagesByEid(59, new StringCallback() {
            @Override
            public void done(String string) {

                System.out.println("<!!!> JSON STRING: <!!!>");
                System.out.println(string);
                System.out.println("<!!!> END OF JSON STRING: <!!!>");

                try
                {
                    HTTPConnection httpConnection = new HTTPConnection();
                    JSONArray jArray = new JSONArray(string);
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        JSONObject jArray_jObject = jArray.getJSONObject(i);
                        JSONObject jImage = jArray_jObject.getJSONObject("image");
                        System.out.println("<!> IMAGE #: " + i + " <!>");

                        System.out.println("User Image ID: " + jImage.getLong("uiid"));
                        System.out.println("User ID: " + getIntegerObjectFromObject(jImage.get("uid")));
                        System.out.println("User Image Sequence: " + getIntegerObjectFromObject(jImage.get("userImageSequence")));
                        System.out.println("User Image Profile Sequence: " + getIntegerObjectFromObject(jImage.get("userImageProfileSequence")));
                        System.out.println("User Image Path: " +
                            httpConnection.getUploadServerString() + jImage.getString("userImagePath").replaceAll(" ", "%20"));
                        System.out.println("User Image Name: " + jImage.getString("userImageName"));
                        System.out.println("User Image Privacy Label: " + jImage.getString("userImagePrivacyLabel"));
                        System.out.println("User Image Purpose Label: " + jImage.getString("userImagePurposeLabel"));
                        System.out.println("User Image GPS Latitude: " + getDoubleObjectFromObject(jImage.get("userImageGpsLatitude")));
                        System.out.println("User Image GPS Longitude: " + getDoubleObjectFromObject(jImage.get("userImageGpsLongitude")));
                        System.out.println("User Image Upload Timestamp: " + jImage.getString("userImageUploadTimestamp"));
                    }
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }

            }
        });
        new UserImageRequest(this).getImagesByEid(59, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */
        /*
        new EventUserRequest(this).getEventUsersData("Joined", 56, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */
        new NewsFeedRequest(this).getNewsEvents(2, 40, new StringCallback() {
            @Override
            public void done(String string) {
                printLongString(string);
            }
        });
    }

}