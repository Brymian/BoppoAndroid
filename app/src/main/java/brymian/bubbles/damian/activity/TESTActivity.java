package brymian.bubbles.damian.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Image;
import brymian.bubbles.damian.nonactivity.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.StringListCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserListCallback;

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
        new ServerRequest(this).getUsers("Damian", new UserListCallback() {
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
        new ServerRequest(this).getFriendStatus(1, 5, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Not friends."
        });
        new ServerRequest(this).getFriendStatus(5, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Not friends."
        });
        */
        /*
        new ServerRequest(this).setFriendStatus(1, 5, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Friend request sent successfully."
        });
        new ServerRequest(this).setFriendStatus(1, 5, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Already sent friend request to user."
        });
        new ServerRequest(this).getFriendStatus(5, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "User is awaiting confirmation for friend request."
        });
        new ServerRequest(this).setFriendStatus(5, 1, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            } // Should say "Friend request accepted."
        });
        // ANY setFriendStatus AND getFriendStatus FOR THE TWO USERS
        // ... SHOULD NOW SAY "Already friends with user."
        */

        /*
        new ServerRequest(this).getFriends(1, new UserListCallback() {
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
        new ServerRequest(this).uploadImage(1, "UserImageName", "Regular", "Public", 12.345, 67.890, "lel image", new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESPONSE: " + string);
            }
        });
        */

        /*
        new ServerRequest(this).getImages(1, "All", new ImageListCallback() {
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
        new ServerRequest(this).syncUserFacebook(uidA, facebookUid, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });

        int uidB = 1;
        String userAccountPrivacyLabel = "Public";
        new ServerRequest(this).setUserAccountPrivacyLabel(uidB, userAccountPrivacyLabel, new StringCallback() {
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
        new ServerRequest(this).setUserImagePurpose(uid, uiid, purposeLabel, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */
        /*
        int uid = 1;
        int uiid = 30;
        new ServerRequest(this).deleteImage(uid, uiid, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */

        /*
        int uid = 1;
        String newPassword = "lolol444";
        new ServerRequest(this).changePassword(uid, newPassword, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */

        /*
        int uid = 4;
        String newEmail = "lol444@lol.com";
        new ServerRequest(this).changeEmail(uid, newEmail, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("RESULT: " + string);
            }
        });
        */
    }

}