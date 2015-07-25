package brymian.bubbles.damian.nonactivity;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ziomster on 5/25/2015.
 */
public class Post {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client;

    public Post() {
        client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(15, TimeUnit.SECONDS);    // socket timeout
    }

    String post(String url, String json) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;

        response = client.newCall(request).execute();
        return response.body().string();
    }

}
