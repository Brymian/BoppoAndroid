package brymian.bubbles.bryant.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import brymian.bubbles.R;
import brymian.bubbles.bryant.main.MainActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

public class SendTo extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    TextView tvPrivate, tvCurrentLocation;
    CheckBox cbPrivate, cbCurrentLocation;
    LinearLayout llDone;
    ImageButton ibDone;
    String privacy = "Public", encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_to);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.Send_To);

        String encodedImage;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                encodedImage= null;
            }
            else {
                encodedImage = extras.getString("encodedImage");
            }
        }
        else {
            encodedImage = savedInstanceState.getString("encodedImage");
        }

        this.encodedImage = encodedImage;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvPrivate = (TextView) findViewById(R.id.tvPrivate);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        cbPrivate = (CheckBox) findViewById(R.id.cbPrivate);
        if(SaveSharedPreference.getUserPicturePrivacy(this).length() != 0){
            cbPrivate.setChecked(true);
            privacy = "Private";
        }
        cbPrivate.setOnCheckedChangeListener(this);
        cbCurrentLocation = (CheckBox) findViewById(R.id.cbCurrentLocation);
        cbCurrentLocation.setOnCheckedChangeListener(this);
        ibDone = (ImageButton) findViewById(R.id.ibDone);
        llDone = (LinearLayout) findViewById(R.id.llDone);
        llDone.setOnClickListener(this);
        ibDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.llDone || v.getId() == R.id.ibDone) {
            System.out.println("privacy: " + privacy);
            new ServerRequestMethods(this).uploadImage(
                    SaveSharedPreference.getUserUID(this),      /* uid */
                    imageName(),                                /* image name */
                    "Regular",                                  /* image purpose label */
                    privacy,                                    /* image privacy label */
                    SaveSharedPreference.getLatitude(this),     /* image latitude */
                    SaveSharedPreference.getLongitude(this),    /* image longitude */
                    encodedImage,                               /* image in String */
                    new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(SendTo.this, "String: " + string, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SendTo.this, MainActivity.class));
                        }
                    });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.cbPrivate:
                if(isChecked){
                    privacy = "Private";
                }
                else{
                    privacy = "Public";
                }

                break;

            case R.id.cbCurrentLocation:


                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        return SaveSharedPreference.getUserUID(this) + "_" + charSequenceName;
    }
}
