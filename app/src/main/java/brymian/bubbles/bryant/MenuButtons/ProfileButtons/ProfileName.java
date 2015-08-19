package brymian.bubbles.bryant.MenuButtons.ProfileButtons;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import brymian.bubbles.R;

/**
 * Created by Almanza on 8/19/2015.
 */
public class ProfileName extends FragmentActivity implements View.OnClickListener{
    TextView tFirstName, tLastName, tUsername;
    Button bEditFirstName, bEditLastname, bEditUsername;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_name);

        tFirstName = (TextView) findViewById(R.id.tFirstName);
        tLastName = (TextView) findViewById(R.id.tLastName);
        tUsername = (TextView) findViewById(R.id.tUsername);

        bEditFirstName = (Button) findViewById(R.id.bEditFirstName);
        bEditLastname = (Button) findViewById(R.id.bEditLastName);
        bEditUsername = (Button) findViewById(R.id.bEditUsername);

        bEditLastname.setOnClickListener(this);
        bEditFirstName.setOnClickListener(this);
        bEditUsername.setOnClickListener(this);

        setNames();

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bEditFirstName:

                break;
            case R.id.bEditLastName:

                break;
            case R.id.bEditUsername:

                break;
        }
    }

    public void setNames(){

        tFirstName.setText("");
        tLastName.setText("");
        tUsername.setText("");

    }
}
