package brymian.bubbles.damian.activity;

import android.app.Activity;
import android.os.Bundle;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.CustomException.SetOrNotException;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;


public class TESTActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test();
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
        // Worked as of 2017-04-20
        /*
        new UserImageRequest(this).getImagesByUid(1, true, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
                try
                {
                    System.out.println((new JSONObject(string).getJSONArray("images").getJSONObject(0).getString("userImagePath")));
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        new UserImageRequest(this).getImagesByUid(1, false, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
                try
                {
                    System.out.println((new JSONObject(string).getJSONArray("images").getJSONObject(0).getString("userImagePath")));
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        new UserImageRequest(this).getImagesByUid(1, null, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
                try
                {
                    System.out.println((new JSONObject(string).getJSONArray("images").getJSONObject(0).getString("userImagePath")));
                }
                catch (JSONException jsone) { jsone.printStackTrace(); }
            }
        });
        */
        /* // Worked as of 2017-04-06
        try
        {
            new UserImageRequest(this).setImage(30, null, null, null, null, null,
                new Boolean[]{null, true, false, false, false, false},
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println(string);
                    }
            });
        }
        catch (SetOrNotException sone)
        {
            sone.printStackTrace();
            // Do something here, if need to handle this
        }
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
        int userImageSequence = 15;
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

        /* // Works as of 2017-05-30
        try {
            new UserRequest(this).setUser(
                1, "Damian", "Niedzielski", null, "201-396-4179", "Public",
                new Boolean[]{null, false, false, false, true, false}, new StringCallback()
            {
                @Override
                public void done(String string) {
                    System.out.println(string);
                }
            });
        }
        catch (SetOrNotException sone)
        {
            sone.printStackTrace();
            // Do something here, if need to handle this
        }
        */
        /*
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
        new EventRequest(this).createEvent(3, "Damian's Descriptive Event", "Music", "Festival",
            "Public", "Everyone", null, "This is a sample event description.",
            null, null, null, null, new StringCallback()
        {
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
        /* // Works correctly as of 2017-06-08
        new EventRequest(this).getEventData(18, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
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
        /* // Works correctly as of 2017-05-01
        new EventUserRequest(this).addUserToEvent(16, 2, 3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("MESSAGE REGARDING ADDING THE USER TO THE EVENT: ");
                System.out.println(string);
            }
        });
        */
        /*
        new EventUserRequest(this).getEventUserData(18, 4, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */
        /*
        new EventRequest(this).getEventDataByMember(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED: ");

                System.out.println(string);
            }
        });
        */
        /*
        new EventRequest(this).getEventDataByRadius(-74.2, 40.7, 50.0, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });

        new EventRequest(this).getLiveEventDataByRadius(-74.2, 40.7, 50.0, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */

        /* // Works as of 2017-05-30
        new FriendshipStatusRequest().getFriendshipStatusRequestSentUsers(11, "Friendship Pending", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */
        /* // Works as of 2017-05-30
        new FriendshipStatusRequest().getFriendshipStatusRequestReceivedUsers(1, "Friendship Pending", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
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
        new EventRequest(this).getEventDataByTopNViews(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 VIEW EVENTS: ");
                System.out.println(string);
            }
        });
        new EventRequest(this).getLiveEventDataByTopNViews(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 VIEW EVENTS: ");
                System.out.println(string);
            }
        });
        */
        /*
        new EventRequest(this).getEventDataByTopNLikes(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 LIKED EVENTS: ");
                System.out.println(string);
            }
        });
        new EventRequest(this).getLiveEventDataByTopNLikes(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING TOP 3 LIKED EVENTS: ");
                System.out.println(string);
            }
        });
        */
        /*
        new EventRequest(this).getEventDataByTopNDislikes(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        new EventRequest(this).getLiveEventDataByTopNDislikes(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
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
        try
        {
            new EventRequest(this).updateEvent(4, null, null, "Private", "Friends", null, null, null,
                "This is a sample event description that has been updated.",
                null, null, null, null,
                new Boolean[]{null, false, false, false, false, false, false, false, true, false,
                    false, false, false}, new StringCallback() {
                @Override
                public void done(String string) {
                    System.out.println("SERVER RESPONSE REGARDING EVENT CREATION: ");
                    System.out.println(string);
                    System.out.println("Result string size: " + string.length());
                }
            });
        }
        catch (SetOrNotException sone)
        {
            sone.printStackTrace();
            // Do something here, if need to handle this
        }
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
        new EventRequest(this).getEventDataByName("test", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING EVENT PARTIAL NAME: ");
                System.out.println(string);
            }
        });
        */
        /*
        new EventRequest(this).getLiveEventDataByName("test", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED FOR THE FOLLOWING EVENT PARTIAL NAME: ");
                System.out.println(string);
            }
        });
        */
        /*
        new EventRequest(this).getLiveEventDataByMember(1, new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */

        new EventRequest(this).getEventDataByTopNRatings(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        new EventRequest(this).getLiveEventDataByTopNRatings(3, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });


        // Worked as of 2017-04-03
        /*
        new UserImageRequest(this).uploadImage(1, 11, "UserImageNameEEE", "Public", 12.345, 67.890,
            "ROTFL image", "ROTFL image thumbnail", new StringCallback()
        {
            @Override
            public void done(String string) {
                System.out.println("RESPONSE: " + string);
            }
        });
        */
        /*
        // Worked as of 2017-04-03
        new UserImageRequest(this).uploadImage(1, 11, "imageName()2", "Public", null, null, "kj43kjhhjgbj345jhb5546kjh452j3hjksd", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESPONSE: " + string);
            }
        });
        */
        /*
        new EventUserImageRequest(this).addImagesToEvent(59, new Integer[]{2, 3}, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */
        /* // Works correctly as of 2017-04-13
        new EventUserRequest(this).getEventUsersData("Joined", 56, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        */
        /*
        new NewsFeedRequest(this).getNewsEvents(1, 10, new StringCallback() {
            @Override
            public void done(String string)
            {
                printLongString(string);
            }
        });
        */
        /*
        new EventUserImageRequest(this).setEuiEventProfileSequence(59, 26, (short)2, new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println(string);
            }
        });
        */
        /*
        new UserImageRequest(this).getImagesByEid(59, true, new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println(string);
            }
        });
        new UserImageRequest(this).getImagesByEid(59, false, new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println(string);
            }
        });
        new UserImageRequest(this).getImagesByEid(59, null, new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println(string);
            }
        });
        */
        /* // Works correctly as of 2017-04-05
        new UserRequest(this).getUsersSearchedByName(1, "dam nie", new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println(string);
            }
        });
        */
        /* // Works correctly as of 2017-04-05
        new UserRequest(this).getFriends(1, new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println(string);
            }
        });
        */
        // Worked as of 2017-04-11
        /*
        try
        {
            new EventUserRequest(this).setEventUser(59, 12, "Moderator", "Joined",
                new Boolean[]{null, null, true, true},
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println(string);
                    }
            });
        }
        catch (SetOrNotException sone)
        {
            sone.printStackTrace();
            // Do something here, if need to handle this
        }
        */
        /* // Works correctly as of 2017-04-25
        User user = new User();
        user.initUserNormal("Dummy", "assword");
        user.setUserNormalLogin("Dummy", "assword", "Jan", "Kowalski", "janko@o2.pl");
        user.setPhone("973-774-8175");

        new ServerRequestMethods(this).createUserNormal(user, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("UID: " + string);
            }
        });
        */
        /*
        new AddressRequest().addUnparsedAddress("UPS I.S Headquarters", "340 MacArthur Blvd., Mahwah NJ, 07430", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("ADD-UNPARSED-ADDRESS RESULTS: ");
                printLongString(string);
            }
        });
        */
        /*
        try
        {
            System.out.println(
                new AddressRequest().addUnparsedAddress2("UPS I.S Headquarters", "340 MacArthur Blvd., Mahwah NJ, 07430")
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        */

        /* // Works as of 2017-06-07
        Long eid = 18L;
        String eventAddressName = "UPS I.S Headquarters";
        String eventAddressUnparsedText = "340 MacArthur Blvd., Mahwah NJ, 07430";
        String response = new EventRequest(this)
            .updateEventUnparsedAddress(eid, eventAddressName, eventAddressUnparsedText);
        System.out.println(response);
        */

    }

}