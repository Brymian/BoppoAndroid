package brymian.bubbles.bryant.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


public class VerifyEmail extends Fragment implements View.OnClickListener{
    Toolbar mToolbar;
    EditText etVerifyEmail;
    TextView bVerifyEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.verify_email, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.tool_bar);
        mToolbar.setTitle(R.string.Email);
        mToolbar.setTitleTextColor(Color.BLACK);
        etVerifyEmail = (EditText) rootView.findViewById(R.id.etVerifyEmail);
        etVerifyEmail.setHint(R.string.Email);
        bVerifyEmail = (TextView) rootView.findViewById(R.id.bVerifyEmail);
        bVerifyEmail.setText(R.string.Save);
        bVerifyEmail.setOnClickListener(this);

        UserDataLocal udl = new UserDataLocal(getActivity());
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        String userEmail = userPhone.getEmail();
        etVerifyEmail.setText(userEmail);
        return rootView;
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.bVerifyEmail:
                new ServerRequestMethods(getActivity()).changeEmail(SaveSharedPreference.getUserUID(getActivity()), etVerifyEmail.getText().toString(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        if(string.equals("Email changed successfully.")){
                            getFragmentManager().popBackStack();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
