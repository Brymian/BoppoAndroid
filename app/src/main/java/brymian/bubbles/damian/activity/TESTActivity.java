package brymian.bubbles.damian.activity;

import android.app.Activity;
import android.os.Bundle;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Event;

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
        new ServerRequestMethods(this).getImages(1, "All", new ImageListCallback() {
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
                System.out.println("TOTAL NUMBER OF USERS: " + users.size());

                for (User user : users) {
                    System.out.println("User #" + users.indexOf(user));
                    System.out.println("UID: " + user.getUid());
                }
            }
        });
        */


        new Event(this).createEvent("Fake Event", 3, "Public", "Everyone", false, null, null, -1.0, -1.0, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("SERVER RESPONSE REGARDING EVENT CREATION: ");
                System.out.println(string);
                System.out.println("Result string size: " + string.length());
            }
        });


    }

}