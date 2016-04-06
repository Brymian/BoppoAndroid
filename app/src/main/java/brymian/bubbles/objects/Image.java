package brymian.bubbles.objects;

/**
 * Created by Ziomster on 8/29/2015.
 */
public class  Image {

    public String  path;
    public String  userImagePrivacyLabel;
    public String  userImagePurposeLabel;
    public Integer userImageEid;
    public Double  userImageGpsLatitude;
    public Double  userImageGpsLongitude;

    public Image(String path, String userImagePrivacyLabel, String userImagePurposeLabel,
         Integer userImageEid, Double userImageGpsLatitude, Double userImageGpsLongitude)
    {
        this.path = path;
        this.userImagePrivacyLabel = userImagePrivacyLabel;
        this.userImagePurposeLabel = userImagePurposeLabel;
        this.userImageEid = userImageEid;
        this.userImageGpsLatitude = userImageGpsLatitude;
        this.userImageGpsLongitude = userImageGpsLongitude;
    }

}
