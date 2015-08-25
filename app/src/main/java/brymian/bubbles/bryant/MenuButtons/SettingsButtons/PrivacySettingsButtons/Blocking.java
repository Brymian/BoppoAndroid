package brymian.bubbles.bryant.MenuButtons.SettingsButtons.PrivacySettingsButtons;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


import brymian.bubbles.R;

/**
 * Created by Almanza on 7/6/2015.
 */
public class Blocking extends FragmentActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_settings_blocking);


    }

    public void onClick(View view){
        switch (view.getId()){

        }
    }
}
