package brymian.bubbles.bryant.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

public class Privacy extends Fragment implements CompoundButton.OnCheckedChangeListener{
    Switch sPictures, sAccount;
    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_privacy, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Privacy);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sPictures = (Switch) rootView.findViewById(R.id.sPictures);
        sAccount = (Switch) rootView.findViewById(R.id.sAccount);

        if(SaveSharedPreference.getUserAccountPrivacy(getActivity()).length() !=0){
            sAccount.setChecked(true);
        }
        if(SaveSharedPreference.getUserPicturePrivacy(getActivity()).length() != 0){
            sPictures.setChecked(true);
        }
        sAccount.setOnCheckedChangeListener(this);
        sPictures.setOnCheckedChangeListener(this);
        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b){
        switch (compoundButton.getId()){
            case R.id.sAccount:
                if(b){
                    if(SaveSharedPreference.getUserAccountPrivacy(getActivity()).length() == 0){
                        SaveSharedPreference.setUserAccountPrivacy(getActivity());
                        new ServerRequestMethods(getActivity())
                                .setUserAccountPrivacy(
                                        SaveSharedPreference.getUserUID(getActivity()),  /* user UID */
                                        "Private",                              /* Public/Private */
                                        new StringCallback() {
                            @Override
                            public void done(String string) {
                                if(string.equals("User updated successfully.")){
                                    Toast.makeText(getActivity(), "Pictures switched to private", Toast.LENGTH_SHORT).show();
                                }                            }
                        });
                    }
                }
                else{
                    if(SaveSharedPreference.getUserAccountPrivacy(getActivity()).length() != 0){
                        SaveSharedPreference.clearUserAccountPrivacy(getActivity());
                        new ServerRequestMethods(getActivity())
                                .setUserAccountPrivacy(
                                        SaveSharedPreference.getUserUID(getActivity()),
                                        "Public",
                                        new StringCallback() {
                            @Override
                            public void done(String string) {
                                if(string.equals("User updated successfully.")){
                                    Toast.makeText(getActivity(), "Pictures switched to public", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                break;

            case R.id.sPictures:
                if(b){
                    if(SaveSharedPreference.getUserPicturePrivacy(getActivity()).length() == 0){
                        SaveSharedPreference.setUserPicturePrivacy(getActivity());
                    }
                }
                else{
                    if(SaveSharedPreference.getUserPicturePrivacy(getActivity()).length() != 0){
                        SaveSharedPreference.clearUserPicturePrivacy(getActivity());
                    }
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}