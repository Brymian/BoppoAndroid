package brymian.bubbles.damian.fragment.Authenticate;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.bryant.Tabs.MainActivity;
import brymian.bubbles.damian.activity.AuthenticateActivityFacebook;
import brymian.bubbles.damian.activity.TESTActivity;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;

/**
 * Created by Ziomster on 7/8/2015.
 */
public class LaunchFragment extends Fragment implements View.OnClickListener {

    ImageButton ibLoginApp, ibLoginFacebook;
    TextView tvDebug;
    TextView tvNewLayout;

    public LaunchFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_launch, container, false);

        ibLoginApp = (ImageButton) rootView.findViewById(R.id.ibLoginApp);
        ibLoginApp.setOnClickListener(this);
        ibLoginFacebook = (ImageButton) rootView.findViewById(R.id.ibLoginFacebook);
        ibLoginFacebook.setOnClickListener(this);
        tvDebug = (TextView) rootView.findViewById(R.id.tvDebug);
        tvNewLayout = (TextView) rootView.findViewById(R.id.tvNewLayout);
        tvDebug.setOnClickListener(this);
        tvNewLayout.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //@Override
    public void onClick(View v) {

        FragmentManager fm = getActivity().getFragmentManager();

        if (v.getId() == R.id.ibLoginApp)
        {
            startFragment(fm, R.id.fragment_authenticate, new LoginFragment());
        }
        else if (v.getId() == R.id.ibLoginFacebook)
        {
            startActivity(new Intent(getActivity(), AuthenticateActivityFacebook.class));
        }
        else if (v.getId() == R.id.tvDebug)
        {
            startActivity(new Intent(getActivity(), TESTActivity.class));
        }
        else if (v.getId() == R.id.tvNewLayout)
        {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }
}
