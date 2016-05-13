package brymian.bubbles.damian.nonactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.VoidCallback;
import brymian.bubbles.objects.Image;
import brymian.bubbles.objects.User;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getJsonNullableInt;

@SuppressWarnings("ALL")
public class ServerRequestMethods {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd;
    private Activity activity;

    public ServerRequestMethods(Activity activity)
    {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");

        this.activity = activity;
        httpConnection = new HTTPConnection();
    }

    public void changeEmail(int uid, String newEmail, StringCallback stringCallback)
    {
        pd.show();
        new ChangeEmail(uid, newEmail, stringCallback).execute();
    }

    public void changePassword(int uid, String newPassword, StringCallback stringCallback)
    {
        pd.show();
        new ChangePassword(uid, newPassword, stringCallback).execute();
    }

    public void createUserNormal(User user, StringCallback stringCallback) {
        pd.show();
        new CreateUserNormal(user, stringCallback).execute();
    }

    public void createUserFacebook(User user, StringCallback stringCallback) {
        pd.show();
        new CreateUserFacebook(user, stringCallback).execute();
    }

    public void linkUserFacebook(int uid, String facebookUid, StringCallback stringCallback) {
        pd.show();
        new LinkUserFacebook(uid, facebookUid, stringCallback).execute();
    }

    public void authUserNormal(User user, VoidCallback voidCallback) {
        pd.show();
        new AuthUserNormal(user, voidCallback).execute();
    }

    public void authUserFacebook(User user, VoidCallback voidCallback) {
        pd.show();
        new AuthUserFacebook(user, voidCallback).execute();
    }

    public void getUsers(Integer searchedByUid, String searchedUser, UserListCallback userListCallback) {
        //pd.show();
        new GetUsers(searchedByUid, searchedUser, userListCallback).execute();
    }

    public void getUserData(int uid, UserCallback userCallback) {
       // pd.show();
        new GetUserData(uid, userCallback).execute();
    }

    public void getUserFriendRequestUsers(int uid, UserListCallback userListCallback) {
        //pd.show();
        new GetUserFriendRequestUsers(uid, userListCallback).execute();
    }

    public void getUserSentFriendRequestUsers(int uid, UserListCallback userListCallback) {
        //pd.show();
        new GetUserSentFriendRequestUsers(uid, userListCallback).execute();
    }

    public void getFriendStatus(int loggedUserUid, int otherUserUid, StringCallback stringCallback) {
        //pd.show();
        new GetFriendStatus(loggedUserUid, otherUserUid, stringCallback).execute();
    }

    public void getFriends(int uid, UserListCallback userListCallback) {
        pd.show();
        new GetFriends(uid, userListCallback).execute();
    }

    public void setFriendStatus(int loggedUserUid, int otherUserUid, StringCallback stringCallback) {
        pd.show();
        new SetFriendStatus(loggedUserUid, otherUserUid, stringCallback).execute();
    }

    public void setUserAccountPrivacy(int uid, String privacyLabel, StringCallback stringCallback) {
        pd.show();
        new SetUserAccountPrivacy(uid, privacyLabel, stringCallback).execute();
    }

    public void setUserImagePurpose(int uid, int userImageSequence, String purposeLabel, StringCallback stringCallback) {
        pd.show();
        new SetUserImagePurpose(uid, userImageSequence, purposeLabel, stringCallback).execute();
    }

    public void uploadImage(int uid, String userImageName, String userImagePurposeLabel,
        String userImagePrivacyLabel, double userImageGpsLatitude, double userImageGpsLongitude,
        String userImage, StringCallback stringCallback)
    {
        pd.show();
        new UploadImage(uid, userImageName, userImagePurposeLabel, userImagePrivacyLabel,
            userImageGpsLatitude, userImageGpsLongitude, userImage, stringCallback).execute();
    }

    public void deleteImage(int uid, int userImageSequence, StringCallback stringCallback)
    {
        pd.show();
        new DeleteImage(uid, userImageSequence, stringCallback).execute();
    }

    private class ChangeEmail extends AsyncTask<Void, Void, String> {

        int uid;
        String newEmail;
        StringCallback stringCallback;

        private ChangeEmail(int uid, String newEmail, StringCallback stringCallback) {

            this.uid = uid;
            this.newEmail = newEmail;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/Functions/User.php?function=changeEmail";

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", uid);
                jsonObject.put("newEmail", newEmail);

                String jsonString = jsonObject.toString();
                Post request = new Post();
                return request.post(url, jsonString);
            } catch (IOException ioe) {
                return ioe.toString();
            } catch (JSONException jsone) {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);
            //Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            super.onPostExecute(string);
        }

    }



    private class ChangePassword extends AsyncTask<Void, Void, String> {

        int uid;
        String newPassword;
        StringCallback stringCallback;

        private ChangePassword(int uid, String newPassword, StringCallback stringCallback) {

            this.uid = uid;
            this.newPassword = newPassword;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/Functions/User.php?function=changePassword";

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", uid);
                jsonObject.put("newPassword", newPassword);

                String jsonString = jsonObject.toString();
                Post request = new Post();
                return request.post(url, jsonString);
            } catch (IOException ioe) {
                return ioe.toString();
            } catch (JSONException jsone) {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);
            //Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            super.onPostExecute(string);
        }

    }



    private class CreateUserNormal extends AsyncTask<Void, Void, String> {

        User user;
        StringCallback stringCallback;

        private CreateUserNormal(User user, StringCallback stringCallback) {
            this.user = user;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/createUserNormal.php";

            String jsonUser =
                "{\"username\":\"" + user.getUsername() + "\"," +
                " \"password\":\"" + user.getPassword() + "\"," +
                " \"firstName\":\"" + user.getFirstName() + "\"," +
                " \"lastName\":\"" + user.getLastName() + "\"," +
                " \"email\":\"" + user.getEmail() + "\"}";
            Post request = new Post();
            try
            {   // Successful SQL command returns one empty space (" ")
                return request.post(url, jsonUser);
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class CreateUserFacebook extends AsyncTask<Void, Void, String> {

        User user;
        StringCallback stringCallback;

        private CreateUserFacebook(User user, StringCallback stringCallback) {
            this.user = user;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/createUserFacebook.php";

            String jsonUser =
                "{\"facebookUid\":\"" + user.getFacebookUid() + "\"," +
                " \"firstName\":\"" + user.getFirstName() + "\"," +
                " \"lastName\":\"" + user.getLastName() + "\"," +
                " \"email\":\"" + user.getEmail() + "\"}";
            Post request = new Post();
            try { // The response - uccessful SQL command returns one empty space (" ")
                return request.post(url, jsonUser);
            } catch (IOException ioe) {
                return ioe.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class LinkUserFacebook extends AsyncTask<Void, Void, String> {

        int uid;
        String facebookUid;
        StringCallback stringCallback;

        private LinkUserFacebook(int uid, String facebookUid, StringCallback stringCallback) {
            this.uid = uid;
            this.facebookUid = facebookUid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/Functions/User.php?function=syncUserFacebook";

            try
            {
                JSONObject jsonUserObject = new JSONObject();

                jsonUserObject.put("uid", uid);
                jsonUserObject.put("facebookUid", facebookUid);

                String jsonUser = jsonUserObject.toString();

                Post request = new Post();
                String response = request.post(url, jsonUser);

                if (response.equals("User updated successfully."))
                {
                    UserDataLocal udl = new UserDataLocal(activity);
                    User user = udl.getUserData();
                    user.setUserFacebookLogin(facebookUid, user.getFirstName(), user.getLastName(), user.getEmail());
                    udl.setUserData(user);
                }
                return response;
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
            catch (JSONException jsone)
            {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class AuthUserNormal extends AsyncTask<Void, Void, Void> {

        User user;
        UserDataLocal udl;
        VoidCallback voidCallback;

        private AuthUserNormal(User user, VoidCallback voidCallback) {
            this.user = user;
            this.voidCallback = voidCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/authUserNormal.php";

            String jsonUser =
                "{\"username\":\"" + user.getUsername() + "\"," +
                " \"password\":\"" + user.getPassword() + "\"}";
            Post request = new Post();

            try
            {
                String response = request.post(url, jsonUser);
                System.out.println("RESPONSE: " + response);

                if (response.equals("USERNAME AND PASSWORD COMBINATION IS INCORRECT."))
                {
                    udl = new UserDataLocal(activity);
                    udl.resetUserData();
                    udl.setLoggedStatus(false);
                }
                else
                {
                    JSONObject jObject = new JSONObject(response);
                    System.out.println("jObject length: " + jObject.length());
                    if (jObject.length() == 10)
                    {
                        System.out.println("PASSWORD CHECK: " + jObject.getString("password"));
                        user.setUser(
                            getJsonNullableInt(jObject, "uid"),
                            jObject.getString("facebookUid"),
                            jObject.getString("googlepUid"),
                            jObject.getString("username"),
                            jObject.getString("password"),
                            jObject.getString("firstName"),
                            jObject.getString("lastName"),
                            jObject.getString("email"),
                            jObject.getString("userAccountCreationTimestamp"),
                            jObject.getString("userAccountPrivacy")
                        );
                        udl = new UserDataLocal(activity);
                        udl.setUserData(user);
                        udl.setLoggedStatus(true);
                        try{ Thread.sleep(1000); } catch (InterruptedException ie) {/**/}
                        System.out.println("getLoggedStatus; " + udl.getLoggedStatus());
                    }
                    else {
                        System.out.println("Unknown login error: ");
                    }
                }
            }
            catch (IOException ioe)
            {
                User user = new User();
                user.initUserNormal(activity.getString(R.string.null_user), "");
                udl = new UserDataLocal(activity);
                udl.setUserData(user);
                ioe.printStackTrace();
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            voidCallback.done(aVoid);

            super.onPostExecute(aVoid);
        }
    }

    private class AuthUserFacebook extends AsyncTask<Void, Void, Void> {

        User user;
        UserDataLocal udl;
        VoidCallback voidCallback;

        private AuthUserFacebook(User user, VoidCallback voidCallback) {
            this.user = user;
            this.voidCallback = voidCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/authUserFacebook.php";

            String jsonUser = "{\"facebookUid\":\"" + user.getFacebookUid() + "\"}";
            Post request = new Post();
            //System.out.println(jsonUser);
            try {
                String response = request.post(url, jsonUser);

                System.out.println("RESPONSE: " + response);

                if (response.equals("FACEBOOK USER DOES NOT EXIST IN THE DATABASE."))
                {
                    System.out.println("FACEBOOK USER DOES NOT EXIST IN THE DATABASE.");
                    System.out.println("UID: " + user.getUid());
                }
                else
                {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 9) {
                        System.out.println("jObject length: " + jObject.length());
                        System.out.println("UID : " + user.getUid());
                        user.setUser(
                            getJsonNullableInt(jObject, "uid"),
                            jObject.getString("facebookUid"),
                            jObject.getString("googlepUid"),
                            jObject.getString("username"),
                            jObject.getString("password"),
                            jObject.getString("firstName"),
                            jObject.getString("lastName"),
                            jObject.getString("email"),
                            jObject.getString("userAccountCreationTimestamp"),
                            jObject.getString("userAccountPrivacy")
                        );
                        udl = new UserDataLocal(activity);
                        udl.setUserData(user);
                        udl.setLoggedStatus(true);
                    }
                }
            } catch (IOException ioe) {
                User user = new User();
                user.initUserFacebook(Resources.getSystem().getString(R.string.null_user));
                udl = new UserDataLocal(activity);
                udl.setUserData(user);
                ioe.printStackTrace();
            } catch (JSONException jsone) {
                jsone.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            voidCallback.done(aVoid);

            super.onPostExecute(aVoid);
        }
    }

    private class GetUsers extends AsyncTask<Void, Void, List<User>> {

        Integer searchedByUid;
        String searchedUser;
        UserListCallback userListCallback;

        private GetUsers(Integer searchedByUid, String searchedUser, UserListCallback userListCallback) {
            this.searchedByUid = searchedByUid;
            this.searchedUser = searchedUser;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/getUsers.php";

            Post request = new Post();
            //SearchUsers friendsActivity = new SearchUsers();
            try {
                //System.out.println("SEARCHED USER: " + searchedUser);
                //String searched_user = "";
                JSONObject jsonSearchUser = new JSONObject();
                jsonSearchUser.put("searchedByUid", searchedByUid);
                jsonSearchUser.put("searchedUser", searchedUser);
                String jsonSearchUserString = jsonSearchUser.toString();

                String response = request.post(url, jsonSearchUserString);
                //friendsActivity.tShowFriends.setText(response);
                System.out.println("RESPONSE: " + response);
                if (response.equals("INCORRECT STRING."))
                {
                    System.out.println(response);
                    return null;
                }
                else
                {
                    JSONArray j2dArray = new JSONArray(response);
                    List<User> userList = new ArrayList<>();
                    for (int i = 0; i < j2dArray.length(); i++)
                    {
                        JSONObject jUser = (JSONObject) j2dArray.get(i);
                        int uid = jUser.getInt("uid");
                        String username = jUser.getString("username");
                        String firstName = jUser.getString("firstName");
                        String lastName = jUser.getString("lastName");
                        String friendshipStatus = jUser.getString("friendshipStatus");
                        User user = new User();
                        user.setUser(uid, null, null, username, null, firstName, lastName, null, null, null);
                        user.setFriendshipStatus(friendshipStatus);
                        userList.add(user);
                    }
                    // The set will be null if nothing matched in the database
                    return userList;
                }
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> users)
        {
            //pd.dismiss();
            userListCallback.done(users);

            super.onPostExecute(users);
        }

    }

    private class GetUserData extends AsyncTask<Void, Void, User> {

        int uid;
        UserCallback userCallback;

        private GetUserData(int uid, UserCallback userCallback) {
            this.uid = uid;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params)
        {
            String url = httpConnection.getWebServerString() + "Older/Functions/User.php?function=getUserData";
            Post request;

            try {

                JSONObject jsonObjectUser = new JSONObject();
                jsonObjectUser.put("uid", uid);
                String jsonStringUser = jsonObjectUser.toString();

                request = new Post();
                String response = request.post(url, jsonStringUser);

                if (response.startsWith("BACK-END ERROR: "))
                {
                    System.out.println(response);
                    return null;
                }
                else
                {
                    JSONObject jObject = new JSONObject(response);

                    User user = new User();
                    user.setUser(
                        jObject.getInt("uid"),
                        jObject.getString("facebookUid"),
                        jObject.getString("googlepUid"),
                        jObject.getString("username"),
                        jObject.getString("password"),
                        jObject.getString("firstName"),
                        jObject.getString("lastName"),
                        jObject.getString("email"),
                        jObject.getString("userAccountCreationTimestamp"),
                        jObject.getString("userAccountPrivacy")
                    );
                    return user;
                }
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user)
        {
            pd.dismiss();
            userCallback.done(user);

            super.onPostExecute(user);
        }

    }

    private class GetUserFriendRequestUsers extends AsyncTask<Void, Void, List<User>> {

        int uid;
        UserListCallback userListCallback;

        private GetUserFriendRequestUsers(int uid, UserListCallback userListCallback) {
            this.uid = uid;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/Functions/User.php?function=getUserFriendRequestUsers";

            //System.out.println("USER: " + uid);
            Post request = new Post();
            try {
                JSONObject jsonUserObject = new JSONObject();
                jsonUserObject.put("uid", uid);

                String jsonUserString = jsonUserObject.toString();
                String response = request.post(url, jsonUserString);
                //friendsActivity.tShowFriends.setText(response);
                //System.out.println("RESPONSE: " + response);
                System.out.println(response);
                if (response.equals("FRIENDSHIP STATUS TYPE LABEL IS NOT VALID.") ||
                    response.startsWith("MYSQL ERROR: "))
                {
                    System.out.println(response);
                    return null;
                }
                else
                {
                    JSONArray jArray = new JSONArray(response);
                    List<User> userList = new ArrayList<>();
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        int uid = jArray.getInt(i);
                        User user = new User();
                        user.setUser(uid, null, null, null, null, null, null, null, null, null);
                        userList.add(user);
                    }
                    // The set will be null if nothing matched in the database
                    return userList;
                }
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> users)
        {
            //pd.dismiss();
            userListCallback.done(users);

            super.onPostExecute(users);
        }

    }

    private class GetUserSentFriendRequestUsers extends AsyncTask<Void, Void, List<User>> {

        int uid;
        UserListCallback userListCallback;

        private GetUserSentFriendRequestUsers(int uid, UserListCallback userListCallback) {
            this.uid = uid;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "Older/Functions/User.php?function=getUserSentFriendRequestUsers";

            //System.out.println("USER: " + uid);
            Post request = new Post();
            try {
                JSONObject jsonUserObject = new JSONObject();
                jsonUserObject.put("uid", uid);

                String jsonUserString = jsonUserObject.toString();
                String response = request.post(url, jsonUserString);
                //friendsActivity.tShowFriends.setText(response);
                //System.out.println("RESPONSE: " + response);
                System.out.println(response);
                if (response.equals("FRIENDSHIP STATUS TYPE LABEL IS NOT VALID.") ||
                    response.startsWith("MYSQL ERROR: "))
                {
                    System.out.println(response);
                    return null;
                }
                else
                {
                    JSONArray jArray = new JSONArray(response);
                    List<User> userList = new ArrayList<>();
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        int uid = jArray.getInt(i);
                        User user = new User();
                        user.setUser(uid, null, null, null, null, null, null, null, null, null);
                        userList.add(user);
                    }
                    // The set will be null if nothing matched in the database
                    return userList;
                }
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> users)
        {
            //pd.dismiss();
            userListCallback.done(users);

            super.onPostExecute(users);
        }

    }

    private class GetFriendStatus extends AsyncTask<Void, Void, String> {

        int loggedUid;
        int otherUid;
        StringCallback stringCallback;

        private GetFriendStatus(int loggedUserUid, int otherUserUid, StringCallback stringCallback) {
            this.loggedUid = loggedUserUid;
            this.otherUid = otherUserUid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/FriendshipStatusRequest.php?function=getFriendshipStatus";

            String jsonFriends = "{\"uid1\":" + loggedUid + "," + " \"uid2\":" + otherUid + "}";
            Post request = new Post();
            try { // Response - Successful SQL command returns one empty space (" ")
                return request.post(url, jsonFriends);
            } catch (IOException ioe) {
                return ioe.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class GetFriends extends AsyncTask<Void, Void, List<User>> {

        int uid;
        UserListCallback userListCallback;

        private GetFriends(int uid, UserListCallback userListCallback) {
            this.uid = uid;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() + "Older/Functions/Friend.php?function=getFriends";
            String jsonLoggedUserUid = "{\"uid\":" + uid + "}";
            Post request = new Post();

            try {

                String response = request.post(url, jsonLoggedUserUid);
                if (response.equals("UID IS NOT A NUMBER."))
                {
                    System.out.println(response);
                    return null;
                }
                else if (response.equals("USER WITH PROVIDED UID DOES NOT EXIST."))
                {
                    System.out.println(response);
                    return null;
                }
                else
                {
                    JSONArray j2dArray = new JSONArray(response);
                    List<User> userList = new ArrayList<>();
                    for (int i = 0; i < j2dArray.length(); i++) {
                        JSONObject jUser = (JSONObject) j2dArray.get(i);
                        int uid = jUser.getInt("uid");
                        String username = jUser.getString("username");
                        String namefirst = jUser.getString("firstName");
                        String namelast = jUser.getString("lastName");
                        User user = new User();
                        user.setUser(uid, null, null, username, null, namefirst, namelast, null, null, null);
                        userList.add(user);
                    }
                    // The set will be null if nothing matched in the database
                    return userList;
                }
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
                return null;
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> users) {
            pd.dismiss();
            userListCallback.done(users);

            super.onPostExecute(users);
        }

    }

    private class SetFriendStatus extends AsyncTask<Void, Void, String> {

        int loggedUserUid;
        int otherUserUid;
        StringCallback stringCallback;

        private SetFriendStatus(int loggedUserUid, int otherUserUid, StringCallback stringCallback) {
            this.loggedUserUid = loggedUserUid;
            this.otherUserUid = otherUserUid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() + "Older/setFriendStatus.php";

            String jsonFriends = "{\"uid1\":" + loggedUserUid + "," + " \"uid2\":" + otherUserUid + "}";
            Post request = new Post();
            try { // Response - Successful SQL command returns one empty space (" ")
                return request.post(url, jsonFriends);
            } catch (IOException ioe) {
                return ioe.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class SetUserAccountPrivacy extends AsyncTask<Void, Void, String> {

        int uid;
        String userAccountPrivacyLabel;
        StringCallback stringCallback;

        private SetUserAccountPrivacy(int uid, String userAccountPrivacyLabel, StringCallback stringCallback) {
            this.uid = uid;
            this.userAccountPrivacyLabel = userAccountPrivacyLabel;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() + "Older/Functions/User.php?function=setUserAccountPrivacyLabel";

            try
            {
                JSONObject jsonUserAccountPrivacyLabelObject = new JSONObject();

                jsonUserAccountPrivacyLabelObject.put("uid", uid);
                jsonUserAccountPrivacyLabelObject.put("userAccountPrivacyLabel", userAccountPrivacyLabel);

                String jsonUserAccountPrivacyLabel = jsonUserAccountPrivacyLabelObject.toString();

                Post request = new Post();
                String response = request.post(url, jsonUserAccountPrivacyLabel);

                if (response.equals("User updated successfully."))
                {
                    UserDataLocal udl = new UserDataLocal(activity);
                    User user = udl.getUserData();
                    user.setUserAccountPrivacyLabel(userAccountPrivacyLabel);
                    udl.setUserData(user);
                }
                return response; // Successful SQL command returns one empty space (" ")
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
            catch (JSONException jsone) {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class SetUserImagePurpose extends AsyncTask<Void, Void, String> {

        int uid;
        int userImageSequence;
        String userImagePurposeLabel;
        StringCallback stringCallback;

        private SetUserImagePurpose(int uid, int userImageSequence, String userImagePurposeLabel, StringCallback stringCallback) {
            this.uid = uid;
            this.userImageSequence = userImageSequence;
            this.userImagePurposeLabel = userImagePurposeLabel;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() + "Older/Functions/Image.php?function=setUserImagePurpose";

            try
            {
                JSONObject jsonUserImagePurposeLabelObject = new JSONObject();

                jsonUserImagePurposeLabelObject.put("uid", uid);
                jsonUserImagePurposeLabelObject.put("userImageSequence", userImageSequence);
                jsonUserImagePurposeLabelObject.put("userImagePurposeLabel", userImagePurposeLabel);

                String jsonUserImagePurposeLabel = jsonUserImagePurposeLabelObject.toString();

                Post request = new Post();
                String response = request.post(url, jsonUserImagePurposeLabel);

                return response; // Successful SQL command returns one empty space (" ")
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
            catch (JSONException jsone) {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class UploadImage extends AsyncTask<Void, Void, String> {

        int uid;
        String userImageName;
        String userImagePurposeLabel;
        String userImagePrivacyLabel;
        double userImageGpsLatitude;
        double userImageGpsLongitude;
        String userImage;
        StringCallback stringCallback;

        private UploadImage(int uid, String userImageName, String userImagePurposeLabel,
            String userImagePrivacyLabel, double userImageGpsLatitude, double userImageGpsLongitude,
            String userImage, StringCallback stringCallback) {

            this.uid = uid;
            this.userImageName = userImageName;
            this.userImagePurposeLabel = userImagePurposeLabel;
            this.userImagePrivacyLabel = userImagePrivacyLabel;
            this.userImageGpsLatitude = userImageGpsLatitude;
            this.userImageGpsLongitude = userImageGpsLongitude;
            this.userImage = userImage;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/Functions/Image.php?function=uploadImage";

            try {
                JSONObject jsonImageObject = new JSONObject();
                jsonImageObject.put("uid", uid);
                jsonImageObject.put("userImageName", userImageName);
                jsonImageObject.put("userImagePurposeLabel", userImagePurposeLabel);
                jsonImageObject.put("userImagePrivacyLabel", userImagePrivacyLabel);
                jsonImageObject.put("userImageGpsLatitude", userImageGpsLatitude);
                jsonImageObject.put("userImageGpsLongitude", userImageGpsLongitude);
                jsonImageObject.put("userImage", userImage);
                /*
                String jsonImage =
                    "{\"uid\":" + uid + "," +
                    " \"purpose\":\"" + purpose + "\"," +
                    " \"name\":\"" + name + "\"," +
                    " \"image\":\"" + image + "\"}";
                System.out.println("[DAMIAN] uid: " + uid);
                System.out.println("[DAMIAN] purpose: " + purpose);
                System.out.println("[DAMIAN] name: " + name);
                System.out.println("[DAMIAN] image length: " + image.length());
                */
                String jsonImage = jsonImageObject.toString();
                //System.out.println(jsonImage);
                Post request = new Post();
                String response = request.post(url, jsonImage);
                //System.out.println(response);
                return response; // Successful SQL command returns one empty space (" ")
            } catch (IOException ioe) {
                return ioe.toString();
            } catch (JSONException jsone) {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);
            //Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            super.onPostExecute(string);
        }

    }

    private class DeleteImage extends AsyncTask<Void, Void, String> {

        int uid;
        int userImageSequence;
        StringCallback stringCallback;

        private DeleteImage(int uid, int userImageSequence, StringCallback stringCallback) {

            this.uid = uid;
            this.userImageSequence = userImageSequence;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "Older/Functions/Image.php?function=deleteImage";

            try
            {
                JSONObject jsonImageObject = new JSONObject();
                jsonImageObject.put("uid", uid);
                jsonImageObject.put("userImageSequence", userImageSequence);

                String jsonImage = jsonImageObject.toString();
                Post request = new Post();

                String response = request.post(url, jsonImage);
                return response; // Successful SQL command returns one empty space (" ")
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
            catch (JSONException jsone)
            {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);
            //Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            super.onPostExecute(string);
        }

    }
}
