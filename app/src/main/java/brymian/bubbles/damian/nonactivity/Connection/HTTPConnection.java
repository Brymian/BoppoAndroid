package brymian.bubbles.damian.nonactivity.Connection;

/**
 * Created by Ziomster on 3/9/2016.
 */
public class HTTPConnection {

    public final String SERVER  = "http://192.168.1.12:8080/";
    //private final String SERVER  = "http://73.194.170.63:8080/";
    private final String UPLOADS = "Bubbles/Uploads/";
    private final String PHP     = "BubblesServer/";

    public HTTPConnection() {}

    public String getWebServerString() {
        return SERVER + PHP;
    }

    public String getUploadServerString() {
        return SERVER + UPLOADS;
    }
}
