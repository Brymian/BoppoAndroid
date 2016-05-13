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

public class UserImageRequest {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public UserImageRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void getImagesByUidAndPurpose(Integer uid, String imagePurposeLabel, ImageListCallback imageListCallback) {
        pd.show();
        new GetImagesByUidAndPurpose(uid, imagePurposeLabel, imageListCallback).execute();
    }

    public void getImagesByPrivacyAndPurpose(String imagePrivacyLabel, String imagePurposeLabel,
        ImageListCallback imageListCallback)
    {
        //pd.show();
        new GetImagesByPrivacyAndPurpose(imagePrivacyLabel, imagePurposeLabel, imageListCallback).execute();
    }

    public void getImageProfileMaxAmount(IntegerCallback integerCallback)
    {
        pd.show();
        new GetImageProfileMaxAmount(integerCallback).execute();
    }



    private class GetImagesByUidAndPurpose extends AsyncTask<Void, Void, List<Image>> {

        Integer uid;
        String imagePurposeLabel;
        ImageListCallback imageListCallback;

        private GetImagesByUidAndPurpose(Integer uid, String imagePurposeLabel, ImageListCallback imageListCallback) {
            this.uid = uid;
            this.imagePurposeLabel = imagePurposeLabel;
            this.imageListCallback = imageListCallback;
        }

        @Override
        protected List<Image> doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() +
                    "AndroidIO/UserImageRequest.php?function=getImagesByUidAndPurpose";

            Post request = new Post();

            try {

                JSONObject jsonImageObject = new JSONObject();
                jsonImageObject.put("uid", getNullOrValue(uid));
                jsonImageObject.put("imagePurposeLabel", imagePurposeLabel);
                String jsonImageString = jsonImageObject.toString();

                String response = request.post(url, jsonImageString);
                JSONArray jImageArray = new JSONArray(response);

                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < jImageArray.length(); i++)
                {
                    JSONObject jImage = jImageArray.getJSONObject(i);
                    Image image = new Image (
                        getLongObjectFromObject(jImage.get("uiid")),
                        getIntegerObjectFromObject(jImage.get("uid")),
                        getIntegerObjectFromObject(jImage.get("userImageSequence")),
                        httpConnection.getUploadServerString() +
                                jImage.getString("userImagePath").replaceAll(" ", "%20"),
                        jImage.getString("userImageName"),
                        jImage.getString("userImagePrivacyLabel"),
                        jImage.getString("userImagePurposeLabel"),
                        getIntegerObjectFromObject(jImage.get("userImageEid")),
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



    private class GetImagesByPrivacyAndPurpose extends AsyncTask<Void, Void, List<Image>> {

        String imagePrivacyLabel;
        String imagePurposeLabel;
        ImageListCallback imageListCallback;

        private GetImagesByPrivacyAndPurpose(String imagePrivacyLabel, String imagePurposeLabel,
             ImageListCallback imageListCallback)
        {
            this.imagePrivacyLabel = imagePrivacyLabel;
            this.imagePurposeLabel = imagePurposeLabel;

            this.imageListCallback = imageListCallback;
        }

        @Override
        protected List<Image> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/UserImageRequest.php?function=getImagesByPrivacyAndPurpose";

            Post request = new Post();
            try
            {
                JSONObject jsonImageObject = new JSONObject();

                jsonImageObject.put("imagePrivacyLabel", getNullOrValue(imagePrivacyLabel));
                jsonImageObject.put("imagePurposeLabel", getNullOrValue(imagePurposeLabel));

                String jsonImageString = jsonImageObject.toString();

                String response = request.post(url, jsonImageString);
                JSONArray jImageArray = new JSONArray(response);

                List<Image> imageList = new ArrayList<>();
                for (int i = 0; i < jImageArray.length(); i++)
                {
                    JSONObject jImage = jImageArray.getJSONObject(i);
                    Image image = new Image (
                        getLongObjectFromObject(jImage.get("uiid")),
                        getIntegerObjectFromObject(jImage.get("uid")),
                        getIntegerObjectFromObject(jImage.get("userImageSequence")),
                        httpConnection.getUploadServerString() +
                            jImage.getString("userImagePath").replaceAll(" ", "%20"),
                        jImage.getString("userImageName"),
                        jImage.getString("userImagePrivacyLabel"),
                        jImage.getString("userImagePurposeLabel"),
                        getIntegerObjectFromObject(jImage.get("userImageEid")),
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

}
