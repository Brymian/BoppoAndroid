package brymian.bubbles.bryant.MenuButtons.SettingsButtons;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import brymian.bubbles.R;

public class About extends FragmentActivity implements View.OnClickListener{
    ImageButton ibGoBack;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_settings_about);

        ibGoBack = (ImageButton) findViewById(R.id.ibGoBack);

        ibGoBack.setOnClickListener(this);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ibGoBack:

                break;
        }
    }
}
