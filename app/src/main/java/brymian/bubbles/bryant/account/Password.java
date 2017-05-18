package brymian.bubbles.bryant.account;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

public class Password extends Fragment{

    TextInputLayout tilCurrentPassword, tilNewPassword, tilConfirmNewPassword;
    TextInputEditText tietCurrentPassword, tietNewPassword, tietConfirmNewPassword;
    TextView tvSuccess;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password, container, false);
        final Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Password);

        tilCurrentPassword = (TextInputLayout) view.findViewById(R.id.tilCurrentPassword);
        tilNewPassword = (TextInputLayout) view.findViewById(R.id.tilNewPassword);
        tilConfirmNewPassword = (TextInputLayout) view.findViewById(R.id.tilConfirmPassword);

        tietCurrentPassword = (TextInputEditText) view.findViewById(R.id.tietCurrentPassword);
        tietNewPassword = (TextInputEditText) view.findViewById(R.id.tietNewPassword);
        tietConfirmNewPassword = (TextInputEditText) view.findViewById(R.id.tietConfirmPassword);

        tvSuccess = (TextView) view.findViewById(R.id.tvSuccess);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (validateCurrentPassword() && validateNewPassword() && validateConfirmNewPassword()){
                    new ServerRequestMethods(getActivity()).changePassword(SaveSharedPreference.getUserUID(getActivity()), tietConfirmNewPassword.getText().toString(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            if (string.contains("successfully")){
                                tvSuccess.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(false);
    }

    private boolean validateCurrentPassword(){
        boolean sitch;
        if (tietCurrentPassword.getText().toString().equals(SaveSharedPreference.getUserPassword(getActivity()))){
            tilCurrentPassword.setErrorEnabled(false);
            sitch = true;
        }
        else {
            tilCurrentPassword.setError("Incorrect password");
            sitch = false;
        }
        return sitch;
    }

    private boolean validateNewPassword(){
        boolean sitch;
        if (tietNewPassword.length() < 8){
            tilNewPassword.setError("Must be at least 8 characters");
            sitch = false;
        }
        else{
            tilNewPassword.setErrorEnabled(false);
            sitch = true;
        }
        return sitch;
    }

    private boolean validateConfirmNewPassword(){
        boolean sitch;
        if (validateNewPassword()){
            if (tietConfirmNewPassword.getText().toString().equals(tietNewPassword.getText().toString())){
                tilConfirmNewPassword.setErrorEnabled(false);
                sitch = true;
            }
            else{
                tilConfirmNewPassword.setError("Password does not match");
                sitch = false;
            }
        }
        else {
            sitch = false;
        }
        return sitch;
    }
}
