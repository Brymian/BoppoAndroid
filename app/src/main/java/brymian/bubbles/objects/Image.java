package brymian.bubbles.objects;

/**
 * Created by Ziomster on 8/29/2015.
 */
public class  Image {

    private String path;
    private String userImagePrivacyLabel;
    private String userImagePurposeLabel;
    private int    eid;
    private double userImageGpsLatitude;
    private double userImageGpsLongitude;

    public Image(String path, String userImagePrivacyLabel, String userImagePurposeLabel,
         double userImageGpsLatitude, double userImageGpsLongitude, int eid)
    {
        this.path = path;
        this.userImagePrivacyLabel = userImagePrivacyLabel;
        this.userImagePurposeLabel = userImagePurposeLabel;
        this.userImageGpsLatitude = userImageGpsLatitude;
        this.userImageGpsLongitude = userImageGpsLongitude;
        this.eid = eid;
    }

    /*
    public void setPath(String path) {
        this.path = path;
    }

    public void setUserImagePrivacyLabel(String userImagePrivacyLabel) {
        this.userImagePrivacyLabel = userImagePrivacyLabel;
    }

    public void setUserImagePurposeLabel(String userImagePurposeLabel) {
        this.userImagePurposeLabel = userImagePurposeLabel;
    }

    public void setUserImageGpsLatitude(double userImageGpsLatitude) {
        this.userImageGpsLatitude = userImageGpsLatitude;
    }

    public void setUserImageGpsLongitude(double userImageGpsLongitude) {
        this.userImageGpsLongitude = userImageGpsLongitude;
    }
    */

    public String getPath() {
        return path;
    }

    public String getUserImagePrivacyLabel() {
        return userImagePrivacyLabel;
    }

    public String getUserImagePurposeLabel() {
        return userImagePurposeLabel;
    }

    public double getUserImageGpsLatitude() {
        return userImageGpsLatitude;
    }

    public double getUserImageGpsLongitude() {
        return userImageGpsLongitude;
    }

    public int getEid() { return eid; }
}
