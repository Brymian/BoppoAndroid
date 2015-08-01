package brymian.bubbles.damian.nonactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import brymian.bubbles.R;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getJsonNullableInt;

/**
 * Created by Ziomster on 7/12/2015.
 */
public class ServerRequest {

    private final String SERVER = "http://73.194.170.63:8080/ProjectWolf/";

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

    public void getUsers(String searched_user, UserSetCallback userSetCallback) {
        pd.show();
        new GetUsers(searched_user, userSetCallback).execute();
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
            String url = SERVER + "Database/createUserNormal.php";

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
            String url = SERVER + "Database/createUserFacebook.php";

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
            String url = SERVER + "Database/authUserNormal.php";

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
            String url = SERVER + "Database/authUserFacebook.php";

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

    private class GetUsers extends AsyncTask<Void, Void, Set<User>> {

        String searched_user;
        UserSetCallback userSetCallback;

        private GetUsers(String searched_user, UserSetCallback userSetCallback) {
            this.searched_user = searched_user;
            this.userSetCallback = userSetCallback;
        }

        @Override
        protected Set<User> doInBackground(Void... params) {
            //final String SERVER = "http://73.194.170.63:8080/ProjectWolf/";
            final String SERVER = "http://192.168.1.12:8080/ProjectWolf/";
            String url = SERVER + "Database/getUsers.php";

            //String searched_user = "";
            String jsonSearchedUser = "{\"searched_username\":\"" + searched_user + "\"}";
            Post request = new Post();
            try {
                String response = request.post(url, jsonSearchedUser);
                if (response.equals("INCORRECT STRING.")) {
                    System.out.println(response);
                    return null;
                } else {
                    JSONArray j2dArray = new JSONArray(response);
                    Set<User> userSet = new HashSet<User>();
                    System.out.println(j2dArray.isNull(0));
                    for (int i = 0; i < j2dArray.length(); i++) {
                        JSONObject jUser = (JSONObject) j2dArray.get(i);
                        int uid = jUser.getInt("uid");
                        String username = jUser.getString("username");
                        String namefirst = jUser.getString("namefirst");
                        String namelast = jUser.getString("namelast");
                        User user = new User();
                        user.setUser(uid, null, -1, username, null, namefirst, namelast, null);
                        userSet.add(user);
                    }
                    // The set will be null if nothing matched in the database
                    return userSet;
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
        protected void onPostExecute(Set<User> users) {
            pd.dismiss();
            userSetCallback.done(users);

            super.onPostExecute(users);
        }

    }
}
