package brymian.bubbles.damian.nonactivity.Connection;

@SuppressWarnings("FieldCanBeLocal")
public class HTTPConnection {

    private final String PROTOCOL = "http";
    //private final String SERVER   = "192.168.1.12";
    private final String SERVER  = "73.194.170.63";
    private final int    PORT     = 8080;
    private final String UPLOADS  = "Bubbles/Uploads/";
    private final String PHP      = "BubblesServer/";

    public HTTPConnection() {}

    public String getWebServerString() {
        return PROTOCOL + "://" + SERVER + ":" + PORT + "/" + PHP;
    }

    public String getUploadServerString() {
        return PROTOCOL + "://" + SERVER + ":" + PORT + "/" + UPLOADS;
    }
}
