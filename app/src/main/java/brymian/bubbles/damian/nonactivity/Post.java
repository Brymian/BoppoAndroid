package brymian.bubbles.damian.nonactivity;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Ziomster on 5/25/2015.
 */
public class Post {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String jsonRegister(String username, String password) {
        return "{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";
    }

    String jsonGetUserExists(String username, String password) {
        return "{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";
    }

    public static void main(String[] args) throws IOException, JSONException {
        Post test = new Post();
        /*
        String json = test.jsonRegister("testuser", "testpass");
        System.out.println(json);
        String response = test.post("http://192.168.1.12:8080/ProjectWolf/Database/register.php", json);
        System.out.println(response);
        */
        String json = test.jsonGetUserExists("Ziomster", "test");
        System.out.println(json);
        String response = test.post("http://192.168.1.12:8080/ProjectWolf/Database/getUserExists.php", json);
        System.out.println(response);

        /*
        Object jobj = JSONValue.parse(response);
        JSONArray jarray = (JSONArray)jobj;
        System.out.println("Count: " + jarray.get(0));
        */
    }
}
