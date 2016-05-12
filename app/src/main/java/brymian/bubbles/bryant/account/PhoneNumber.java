package brymian.bubbles.bryant.account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brymian.bubbles.R;

/**
 * Created by almanza1112 on 5/11/16.
 */
public class PhoneNumber extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_phone_number, container, false);


        return rootView;
    }
}
