package brymian.bubbles.objects;

/**
 * Created by Ziomster on 8/29/2015.
 */
public class  Image {

    public Long uiid;

    public Integer uid;
    public Integer userImageSequence;

    public String  userImagePath;
    public String  userImageName;
    public String  userImagePrivacyLabel;
    public String  userImagePurposeLabel;
    public Integer userImageEid;
    public Double  userImageGpsLatitude;
    public Double  userImageGpsLongitude;

    public Image(Long uiid, Integer uid, Integer userImageSequence, String userImagePath,
        String userImageName, String userImagePrivacyLabel, String userImagePurposeLabel,
        Integer userImageEid, Double userImageGpsLatitude, Double userImageGpsLongitude)
    {
        this.uiid = uiid;
        this.uid = uid;
        this.userImageSequence = userImageSequence;
        this.userImagePath = userImagePath;
        this.userImageName = userImageName;
        this.userImagePrivacyLabel = userImagePrivacyLabel;
        this.userImagePurposeLabel = userImagePurposeLabel;
        this.userImageEid = userImageEid;
        this.userImageGpsLatitude = userImageGpsLatitude;
        this.userImageGpsLongitude = userImageGpsLongitude;
    }

}
