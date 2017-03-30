package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.IntegerCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.Image;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getBooleanObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getDoubleObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getIntegerObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getJsonNullableInt;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getLongObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.isStringAnInteger;

public class UserImageRequest
{
    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public UserImageRequest(Activity activity)
    {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }



    public void getImagesByEid(Integer eid, Boolean euiProfileIndicator, StringCallback stringCallback) {
        pd.show();
        new GetImagesByEid(eid, euiProfileIndicator, stringCallback).execute();
    }

    public void getImagesByUidAndPurpose(Integer uid, String imagePurposeLabel, Boolean eventIndicator,
        ImageListCallback imageListCallback) {
        //pd.show();
        new GetImagesByUidAndPurpose(uid, imagePurposeLabel,eventIndicator,  imageListCallback).execute();
    }

    public void getImagesByPrivacyAndPurpose(String imagePrivacyLabel, String imagePurposeLabel,
        Boolean eventIndicator, ImageListCallback imageListCallback)
    {
        pd.show();
        new GetImagesByPrivacyAndPurpose(imagePrivacyLabel, imagePurposeLabel, eventIndicator,
            imageListCallback).execute();
    }

    public void getImageProfileMaxAmount(IntegerCallback integerCallback)
    {
        pd.show();
        new GetImageProfileMaxAmount(integerCallback).execute();
    }

    public void setImage(Integer uiid, Integer userImageProfileSequence, String userImageName,
      String userImagePurposeLabel, String userImagePrivacyLabel,
      Double userImageGpsLatitude, Double userImageGpsLongitude,
      StringCallback stringCallback) {
        pd.show();
        new SetImage(uiid, userImageProfileSequence, userImageName, userImagePurposeLabel,
            userImagePrivacyLabel, userImageGpsLatitude, userImageGpsLongitude,
            stringCallback).execute();
    }

    public void uploadImage(Integer uid, Integer userImageProfileSequence, String userImageName,
        String userImagePurposeLabel, String userImagePrivacyLabel,
        Double userImageGpsLatitude, Double userImageGpsLongitude,
        String userImage, StringCallback stringCallback)
    {
        pd.show();
        new UploadImage(uid, userImageProfileSequence, userImageName, userImagePurposeLabel,
            userImagePrivacyLabel,  userImageGpsLatitude, userImageGpsLongitude,
            userImage, stringCallback).execute();
    }





    private class GetImagesByEid extends AsyncTask<Void, Void, String> {

        Integer eid;
        Boolean euiProfileIndicator;
        StringCallback stringCallback;

        private GetImagesByEid(Integer eid, Boolean euiProfileIndicator, StringCallback stringCallback) {
            this.eid = eid;
            this.euiProfileIndicator = euiProfileIndicator;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() + "AndroidIO/UserImageRequest.php?function=getImagesByEid";

            Post request = new Post();

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("eid", eid);
                jsonObject.put("euiProfileIndicator", getNullOrValue(euiProfileIndicator));
                String jsonString = jsonObject.toString();

                String response = request.post(url, jsonString);

                return response;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String string) {
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class GetImagesByUidAndPurpose extends AsyncTask<Void, Void, List<Image>> {

        Integer uid;
        String imagePurposeLabel;
        Boolean eventIndicator;
        ImageListCallback imageListCallback;

        private GetImagesByUidAndPurpose(Integer uid, String imagePurposeLabel, Boolean eventIndicator,
            ImageListCallback imageListCallback) {
            this.uid = uid;
            this.imagePurposeLabel = imagePurposeLabel;
            this.eventIndicator = eventIndicator;
            this.imageListCallback = imageListCallback;
        }

        @Override
        protected List<Image> doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() +
                    "AndroidIO/UserImageRequest.php?function=getImagesByUidAndPurpose";

            Post request = new Post();

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", getNullOrValue(uid));
                jsonObject.put("imagePurposeLabel", imagePurposeLabel);
                jsonObject.put("eventIndicator", getNullOrValue(eventIndicator));
                String jsonImageString = jsonObject.toString();

                String response = request.post(url, jsonImageString);

                System.out.println("RESPONSE: " + response);
                JSONArray jImageArray = new JSONArray(response);

                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < jImageArray.length(); i++)
                {
                    JSONObject jImage = jImageArray.getJSONObject(i);
                    Image image = new Image (
                        jImage.getLong("uiid"),
                        getIntegerObjectFromObject(jImage.get("uid")),
                        getIntegerObjectFromObject(jImage.get("userImageSequence")),
                        getIntegerObjectFromObject(jImage.get("userImageProfileSequence")),
                        httpConnection.getUploadServerString() +
                            jImage.getString("userImagePath").replaceAll(" ", "%20"),
                        jImage.getString("userImageName"),
                        jImage.getString("userImagePrivacyLabel"),
                        jImage.getString("userImagePurposeLabel"),
                        getDoubleObjectFromObject(jImage.get("userImageGpsLatitude")),
                        getDoubleObjectFromObject(jImage.get("userImageGpsLongitude"))
                    );
                    imageList.add(image);
                }

                return imageList;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Image> imageList) {
            //pd.dismiss();
            imageListCallback.done(imageList);

            super.onPostExecute(imageList);
        }

    }



    private class GetImagesByPrivacyAndPurpose extends AsyncTask<Void, Void, List<Image>> {

        String imagePrivacyLabel;
        String imagePurposeLabel;
        Boolean eventIndicator;
        ImageListCallback imageListCallback;

        private GetImagesByPrivacyAndPurpose(String imagePrivacyLabel, String imagePurposeLabel,
             Boolean eventIndicator, ImageListCallback imageListCallback)
        {
            this.imagePrivacyLabel = imagePrivacyLabel;
            this.imagePurposeLabel = imagePurposeLabel;
            this.eventIndicator = eventIndicator;
            this.imageListCallback = imageListCallback;
        }

        @Override
        protected List<Image> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/UserImageRequest.php?function=getImagesByPrivacyAndPurpose";

            Post request = new Post();
            try
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("imagePrivacyLabel", getNullOrValue(imagePrivacyLabel));
                jsonObject.put("imagePurposeLabel", getNullOrValue(imagePurposeLabel));
                jsonObject.put("eventIndicator", getNullOrValue(eventIndicator));

                String jsonImageString = jsonObject.toString();

                String response = request.post(url, jsonImageString);
                System.out.println(response);
                JSONArray jImageArray = new JSONArray(response);

                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < jImageArray.length(); i++)
                {
                    JSONObject jImage = jImageArray.getJSONObject(i);
                    Image image = new Image (
                        jImage.getLong("uiid"),
                        getIntegerObjectFromObject(jImage.get("uid")),
                        getIntegerObjectFromObject(jImage.get("userImageSequence")),
                        getIntegerObjectFromObject(jImage.get("userImageProfileSequence")),
                        httpConnection.getUploadServerString() +
                            jImage.getString("userImagePath").replaceAll(" ", "%20"),
                        jImage.getString("userImageName"),
                        jImage.getString("userImagePrivacyLabel"),
                        jImage.getString("userImagePurposeLabel"),
                        getDoubleObjectFromObject(jImage.get("userImageGpsLatitude")),
                        getDoubleObjectFromObject(jImage.get("userImageGpsLongitude"))
                    );
                    imageList.add(image);
                }

                return imageList;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Image> imageList) {
            pd.dismiss();
            imageListCallback.done(imageList);

            super.onPostExecute(imageList);
        }
    }

    private class GetImageProfileMaxAmount extends AsyncTask<Void, Void, Integer> {

        IntegerCallback integerCallback;

        private GetImageProfileMaxAmount(IntegerCallback integerCallback)
        {
            this.integerCallback = integerCallback;
        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            String url = httpConnection.getWebServerString() + "AndroidIO/UserImageRequest.php?function=getImageProfileMaxAmount";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();

                String jsonEventString = jsonEventObject.toString();

                String response = request.post(url, jsonEventString);

                if (isStringAnInteger(response))
                    return Integer.parseInt(response.trim());
                else {
                    System.out.println(response);
                    return -1;
                }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return -11;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            pd.dismiss();
            integerCallback.done(integer);

            super.onPostExecute(integer);
        }

    }

    private class SetImage extends AsyncTask<Void, Void, String> {

        Integer uiid;
        Integer userImageProfileSequence;
        String userImageName;
        String userImagePurposeLabel;
        String userImagePrivacyLabel;
        Double userImageGpsLatitude;
        Double userImageGpsLongitude;
        StringCallback stringCallback;

        private SetImage(Integer uiid, Integer userImageProfileSequence, String userImageName,
            String userImagePurposeLabel, String userImagePrivacyLabel,
            Double userImageGpsLatitude, Double userImageGpsLongitude,
            StringCallback stringCallback) {

            this.uiid = uiid;
            this.userImageProfileSequence = userImageProfileSequence;
            this.userImageName = userImageName;
            this.userImagePurposeLabel = userImagePurposeLabel;
            this.userImagePrivacyLabel = userImagePrivacyLabel;
            this.userImageGpsLatitude = userImageGpsLatitude;
            this.userImageGpsLongitude = userImageGpsLongitude;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/UserImageRequest.php?function=setImage";

            try {
                JSONObject jsonImageObject = new JSONObject();
                jsonImageObject.put("uiid", getNullOrValue(uiid));
                jsonImageObject.put("userImageProfileSequence", getNullOrValue(userImageProfileSequence));
                jsonImageObject.put("userImageName", getNullOrValue(userImageName));
                jsonImageObject.put("userImagePurposeLabel", getNullOrValue(userImagePurposeLabel));
                jsonImageObject.put("userImagePrivacyLabel", getNullOrValue(userImagePrivacyLabel));
                jsonImageObject.put("userImageGpsLatitude", getNullOrValue(userImageGpsLatitude));
                jsonImageObject.put("userImageGpsLongitude", getNullOrValue(userImageGpsLongitude));
                String jsonImage = jsonImageObject.toString();
                Post request = new Post();
                String response = request.post(url, jsonImage);
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
            //Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            super.onPostExecute(string);
        }
    }

    private class UploadImage extends AsyncTask<Void, Void, String> {

        Integer uid;
        Integer userImageProfileSequence;
        String userImageName;
        String userImagePurposeLabel;
        String userImagePrivacyLabel;
        Double userImageGpsLatitude;
        Double userImageGpsLongitude;
        String userImage;
        StringCallback stringCallback;

        private UploadImage(Integer uid, Integer userImageProfileSequence, String userImageName,
            String userImagePurposeLabel, String userImagePrivacyLabel,
            Double userImageGpsLatitude, Double userImageGpsLongitude,
            String userImage, StringCallback stringCallback) {

            this.uid = uid;
            this.userImageProfileSequence = userImageProfileSequence;
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
            String url = httpConnection.getWebServerString() + "AndroidIO/UserImageRequest.php?function=uploadImage";

            try {
                JSONObject jsonImageObject = new JSONObject();
                jsonImageObject.put("uid", uid);
                jsonImageObject.put("userImageProfileSequence", userImageProfileSequence);
                jsonImageObject.put("userImageName", userImageName);
                jsonImageObject.put("userImagePurposeLabel", userImagePurposeLabel);
                jsonImageObject.put("userImagePrivacyLabel", userImagePrivacyLabel);
                jsonImageObject.put("userImageGpsLatitude", userImageGpsLatitude);
                jsonImageObject.put("userImageGpsLongitude", userImageGpsLongitude);
                jsonImageObject.put("userImage", userImage);
                String jsonImage = jsonImageObject.toString();
                Post request = new Post();
                String response = request.post(url, jsonImage);
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
            //Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            super.onPostExecute(string);
        }
    }

}
