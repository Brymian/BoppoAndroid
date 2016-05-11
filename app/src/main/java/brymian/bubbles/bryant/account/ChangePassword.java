package brymian.bubbles.bryant.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;

public class ChangePassword extends Fragment implements View.OnClickListener{
    Toolbar mToolbar;
    EditText etOldPassword, etNewPassword, etNewPasswordAgain;
    TextView bChangePassword;
    ImageView ivCurrentPassword, ivConfirmNewPassword;
    boolean isEquals;

    TextWatcher oldPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new ServerRequestMethods(getActivity()).getUserData(SaveSharedPreference.getUserUID(getActivity()), new UserCallback() {
                @Override
                public void done(User user) {
                    String password = user.getPassword();
                    System.out.println("PASSWORD IS: " + password + ", USERNAME IS: " + user.getUsername());
                    if(etOldPassword.getText().toString().equals(password)){
                        ivCurrentPassword.setImageResource(R.mipmap.ic_done_black_24dp);
                    }
                    else{
                        ivCurrentPassword.setImageResource(R.mipmap.ic_close_black_24dp);
                    }
                }
            });
        }
    };

    TextWatcher newPasswordAgainWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (etNewPasswordAgain.getText().toString().equals(etNewPassword.getText().toString())) {
                ivConfirmNewPassword.setImageResource(R.mipmap.ic_done_black_24dp);
                bChangePassword.setVisibility(View.VISIBLE);
                isEquals = true;
            }
            else{
                ivConfirmNewPassword.setImageResource(R.mipmap.ic_close_black_24dp);
                isEquals = false;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.change_password, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.tool_bar);
        mToolbar.setTitle(R.string.Password);
        mToolbar.setTitleTextColor(Color.BLACK);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etOldPassword = (EditText) rootView.findViewById(R.id.etOldPassword);
        etOldPassword.setHint(R.string.Current_Password);
        etNewPassword = (EditText) rootView.findViewById(R.id.etNewPassword);
        etNewPasswordAgain = (EditText) rootView.findViewById(R.id.etNewPasswordAgain);
        bChangePassword = (TextView) rootView.findViewById(R.id.bChangePassword);
        bChangePassword.setText(R.string.Done);
        ivCurrentPassword = (ImageView) rootView.findViewById(R.id.ivCurrentPassword);
        ivConfirmNewPassword = (ImageView) rootView.findViewById(R.id.ivConfirmNewPassword);

        bChangePassword.setOnClickListener(this);
        bChangePassword.setVisibility(View.GONE);

        etOldPassword.addTextChangedListener(oldPasswordWatcher);
        etNewPasswordAgain.addTextChangedListener(newPasswordAgainWatcher);

        return rootView;
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.bChangePassword:
                if(isEquals){
                    new ServerRequestMethods(getActivity()).changePassword(SaveSharedPreference.getUserUID(getActivity()), etNewPasswordAgain.getText().toString(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            if(string.equals("Password changed successfully.")){
                                getFragmentManager().popBackStack();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "New Password needs to confirm", Toast.LENGTH_SHORT).show();
                }

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
