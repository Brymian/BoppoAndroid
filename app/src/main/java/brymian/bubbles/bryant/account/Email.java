package brymian.bubbles.bryant.account;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.RegularExpressions;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

public class Email extends Fragment{
    private Pattern pattern = Pattern.compile(RegularExpressions.EMAIL_PATTERN);
    TextInputLayout tilEmail;
    TextInputEditText tietEmail;
    TextView tvSuccess;
    FloatingActionButton fabDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.email, container, false);
        final Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Email);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilEmail = (TextInputLayout) view.findViewById(R.id.tilEmail);
        tietEmail = (TextInputEditText) view.findViewById(R.id.tietEmail);
        tietEmail.setText(SaveSharedPreference.getEmail(getActivity()));

        tvSuccess = (TextView) view.findViewById(R.id.tvSuccess);
        fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSuccess.setVisibility(View.GONE);
                if (validateEmail()){
                    new ServerRequestMethods(getActivity()).changeEmail(SaveSharedPreference.getUserUID(getActivity()), tietEmail.getText().toString(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            if(string.contains("successfully")){
                                tvSuccess.setVisibility(View.VISIBLE);
                                SaveSharedPreference.clearEmail(getActivity());
                                SaveSharedPreference.setEmail(getActivity(), tietEmail.getText().toString());
                            }
                            else if (string.contains("already in use")){
                                tilEmail.setError("Email already in use");
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

    private boolean validateEmail() {
        boolean sitch;
        Matcher matcher = pattern.matcher(tietEmail.getText().toString());
        if (matcher.matches()){
            tilEmail.setErrorEnabled(false);
            sitch = true;
        }
        else{
            tilEmail.setError("Invalid email");
            sitch = false;
        }
        return sitch;
    }

}
