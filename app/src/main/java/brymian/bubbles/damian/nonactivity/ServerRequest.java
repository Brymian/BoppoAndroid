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

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getJsonNullableInt;
/**
 * Created by Ziomster on 7/12/2015.
 */
public class ServerRequest {

    private final String SERVER = "http://73.194.170.63:8080/";
    //private final String SERVER = "http://192.168.1.12:8080/";
    private final String UPLOADS = "Bubbles/Uploads/";
    private final String PHP = "BubblesServer/";

    private ProgressDialog pd;

    private Activity activity;

    public ServerRequest(Activity activity) {
        this.activity = activity;
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
    }

    public void createUserNormal(User user, StringCallback stringCallback) {
        pd.show();
        new CreateUserNormal(user, stringCallback).execute();
    }

    public void createUserFacebook(User user, StringCallback stringCallback) {
        pd.show();
        new CreateUserFacebook(user, stringCallback).execute();
    }

    public void authUserNormal(User user, VoidCallback voidCallback) {
        pd.show();
        new AuthUserNormal(user, voidCallback).execute();
    }

    public void authUserFacebook(User user, VoidCallback voidCallback) {
        pd.show();
        new AuthUserFacebook(user, voidCallback).execute();
    }

    public void getUsers(String searched_user, UserListCallback userListCallback) {
        pd.show();
        new GetUsers(searched_user, userListCallback).execute();
    }

    public void getFriendStatus(int loggedUserUid, int otherUserUid, StringCallback stringCallback) {
        pd.show();
        new GetFriendStatus(loggedUserUid, otherUserUid, stringCallback).execute();
    }

    public void getFriends(int uid, UserListCallback userListCallback) {
        pd.show();
        new GetFriends(uid, userListCallback).execute();
    }

    public void getImagePaths(int uid, String purpose, StringListCallback stringListCallback) {
        pd.show();
        new GetImagePaths(uid, purpose, stringListCallback).execute();
    }

    public void setFriendStatus(int loggedUserUid, int otherUserUid, StringCallback stringCallback) {
        pd.show();
        new SetFriendStatus(loggedUserUid, otherUserUid, stringCallback).execute();
    }

    public void uploadImage(int uid, String purpose, String name, String image, StringCallback stringCallback) {
        pd.show();
        new UploadImage(uid, purpose, name, image, stringCallback).execute();
    }

    /*
    public void uploadVideo(String name, StringCallback stringCallback) {
        pd.show();
        new UploadVideo(name, stringCallback).execute();
    }
    */

    private class CreateUserNormal extends AsyncTask<Void, Void, String> {

        User user;
        StringCallback stringCallback;

        private CreateUserNormal(User user, StringCallback stringCallback) {
            this.user = user;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = SERVER + PHP + "DBIO/createUserNormal.php";

            String jsonUser =
                "{\"username\":\"" + user.username() + "\"," +
                " \"password\":\"" + user.password() + "\"," +
                " \"namefirst\":\"" + user.namefirst() + "\"," +
                " \"namelast\":\"" + user.namelast() + "\"," +
                " \"email\":\"" + user.email() + "\"}";
            Post request = new Post();
            try {
                String response = request.post(url, jsonUser);
                return response; // Successful SQL command returns one empty space (" ")
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

    private class CreateUserFacebook extends AsyncTask<Void, Void, String> {

        User user;
        StringCallback stringCallback;

        private CreateUserFacebook(User user, StringCallback stringCallback) {
            this.user = user;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = SERVER + PHP + "DBIO/createUserFacebook.php";

            String jsonUser =
                    "{\"facebook_uid\":\"" + user.facebookUid() + "\"," +
                            " \"namefirst\":\"" + user.namefirst() + "\"," +
                            " \"namelast\":\"" + user.namelast() + "\"," +
                            " \"email\":\"" + user.email() + "\"}";
            Post request = new Post();
            try {
                String response = request.post(url, jsonUser);
                return response; // Successful SQL command returns one empty space (" ")
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
            String url = SERVER + PHP + "DBIO/authUserNormal.php";

            String jsonUser =
                    "{\"username\":\"" + user.username() + "\"," +
                            " \"password\":\"" + user.password() + "\"}";
            Post request = new Post();
            //System.out.println(jsonUser);
            try {
                String response = request.post(url, jsonUser);
                JSONObject jObject = new JSONObject(response);

                if (jObject.length() == 8) {
                    user.setUser(
                            getJsonNullableInt(jObject, "uid"),
                            jObject.getString("facebook_uid"),
                            getJsonNullableInt(jObject, "googlep_uid"),
                            jObject.getString("username"),
                            jObject.getString("password"),
                            jObject.getString("namefirst"),
                            jObject.getString("namelast"),
                            jObject.getString("email")
                    );
                    udl = new UserDataLocal(activity);
                    udl.setUserData(user);
                }
            } catch (IOException ioe) {
                User user = new User();
                user.initUserNormal(Resources.getSystem().getString(R.string.null_user), "");
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
            String url = SERVER + PHP + "DBIO/authUserFacebook.php";

            String jsonUser = "{\"facebook_uid\":\"" + user.facebookUid() + "\"}";
            Post request = new Post();
            //System.out.println(jsonUser);
            try {
                String response = request.post(url, jsonUser);
                System.out.println(response);
                JSONObject jObject = new JSONObject(response);

                if (jObject.length() == 8) {
                    user.setUser(
                            getJsonNullableInt(jObject, "uid"),
                            jObject.getString("facebook_uid"),
                            getJsonNullableInt(jObject, "googlep_uid"),
                            jObject.getString("username"),
                            jObject.getString("password"),
                            jObject.getString("namefirst"),
                            jObject.getString("namelast"),
                            jObject.getString("email")
                    );
                    udl = new UserDataLocal(activity);
                    udl.setUserData(user);
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

        String searched_user;
        UserListCallback userListCallback;

        private GetUsers(String searched_user, UserListCallback userListCallback) {
            this.searched_user = searched_user;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            String url = SERVER + PHP + "DBIO/getUsers.php";

            System.out.println("SEARCHED USER: " + searched_user);
            //String searched_user = "";
            String jsonSearchedUser = "{\"searched_username\":\"" + searched_user + "\"}";
            Post request = new Post();
            //FriendsActivity friendsActivity = new FriendsActivity();
            try {
                String response = request.post(url, jsonSearchedUser);
                //friendsActivity.tShowFriends.setText(response);
                //System.out.println("RESPONSE: " + response);
                if (response.equals("INCORRECT STRING.")) {
                    System.out.println(response);
                    return null;
                } else {
                    JSONArray j2dArray = new JSONArray(response);
                    List<User> userList = new ArrayList<User>();
                    for (int i = 0; i < j2dArray.length(); i++) {
                        JSONObject jUser = (JSONObject) j2dArray.get(i);
                        int uid = jUser.getInt("uid");
                        String username = jUser.getString("username");
                        String namefirst = jUser.getString("namefirst");
                        String namelast = jUser.getString("namelast");
                        User user = new User();
                        user.setUser(uid, null, -1, username, null, namefirst, namelast, null);
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

    private class GetFriendStatus extends AsyncTask<Void, Void, String> {

        int loggedUserUid;
        int otherUserUid;
        StringCallback stringCallback;

        private GetFriendStatus(int loggedUserUid, int otherUserUid, StringCallback stringCallback) {
            this.loggedUserUid = loggedUserUid;
            this.otherUserUid = otherUserUid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = SERVER + PHP + "DBIO/getFriendStatus.php";

            String jsonFriends =
                    "{\"uid1\":" + loggedUserUid + "," +
                            " \"uid2\":" + otherUserUid + "}";
            Post request = new Post();
            try {
                String response = request.post(url, jsonFriends);
                return response; // Successful SQL command returns one empty space (" ")
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

    private class GetFriends extends AsyncTask<Void, Void, List<User>> {

        int uid;
        UserListCallback userListCallback;

        private GetFriends(int uid, UserListCallback userListCallback) {
            this.uid = uid;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {

            String url = SERVER + PHP + "DBIO/Functions/Friend.php?function=getFriends";
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
                    List<User> userList = new ArrayList<User>();
                    for (int i = 0; i < j2dArray.length(); i++) {
                        JSONObject jUser = (JSONObject) j2dArray.get(i);
                        int uid = jUser.getInt("uid");
                        String namefirst = jUser.getString("namefirst");
                        String namelast = jUser.getString("namelast");
                        User user = new User();
                        user.setUser(uid, null, -1, null, null, namefirst, namelast, null);
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

    private class GetImagePaths extends AsyncTask<Void, Void, List<String>> {

        int uid;
        String purpose;
        StringListCallback stringListCallback;

        private GetImagePaths(int uid, String purpose, StringListCallback stringListCallback) {
            this.uid = uid;
            this.purpose = purpose;
            this.stringListCallback = stringListCallback;
        }

        @Override
        protected List<String> doInBackground(Void... params) {

            String url = SERVER + PHP + "DBIO/Functions/Image.php?function=getImagePaths";
            String jsonGetImagePaths =
                "{\"uid\":" + uid + "," +
                " \"purpose\":\"" + purpose + "\"}";
            Post request = new Post();

            try {

                String response = request.post(url, jsonGetImagePaths);
                if (response.startsWith("PURPOSE") && response.endsWith("DOES NOT EXIST IN THE DATABASE."))
                {
                    System.out.println(response);
                    return null;
                }
                else if (response.equals("UID IS NOT A NUMBER."))
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
                    JSONArray jPaths = new JSONArray(response);
                    List<String> pathList = new ArrayList<String>();
                    for (int i = 0; i < jPaths.length(); i++) {
                        String path = SERVER + UPLOADS + jPaths.getString(i);
                        pathList.add(path);
                    }
                    // The set will be null if nothing matched in the database
                    return pathList;
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
        protected void onPostExecute(List<String> strings) {
            pd.dismiss();
            stringListCallback.done(strings);

            super.onPostExecute(strings);
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

            String url = SERVER + PHP + "DBIO/setFriendStatus.php";

            String jsonFriends =
                "{\"uid1\":" + loggedUserUid + "," +
                " \"uid2\":" + otherUserUid + "}";
            Post request = new Post();
            try {
                String response = request.post(url, jsonFriends);
                return response; // Successful SQL command returns one empty space (" ")
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

    private class UploadImage extends AsyncTask<Void, Void, String> {

        int uid;
        String purpose;
        String name;
        String image;
        StringCallback stringCallback;

        private UploadImage(int uid, String purpose, String name, String image, StringCallback stringCallback) {
            this.uid = uid;
            this.purpose = purpose;
            this.name = name;
            this.image = image;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = SERVER + PHP + "DBIO/Functions/Image.php?function=uploadImage";

            try {
                JSONObject jsonImageObject = new JSONObject();
                jsonImageObject.put("uid", uid);
                jsonImageObject.put("purpose", purpose);
                jsonImageObject.put("name", name);
                jsonImageObject.put("image", image);
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

            super.onPostExecute(string);
        }

    }
}
