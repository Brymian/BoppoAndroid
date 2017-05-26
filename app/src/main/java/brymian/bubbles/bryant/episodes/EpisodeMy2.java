package brymian.bubbles.bryant.episodes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

public class EpisodeMy2 extends Fragment {

    Toolbar toolbar;
    TextInputLayout tilSearchEpisodes;
    TextInputEditText tietSearchEpisodes;
    RecyclerView rvEpisodes;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fabCreateEpisode;

    private String from;
    private List<Integer> episodeEid = new ArrayList<>();
    private List<String> episodeName = new ArrayList<>();
    private List<String> episodeType = new ArrayList<>();
    private List<String> episodeImagePath = new ArrayList<>();
    private List<String> episodeHostUsername = new ArrayList<>();
    private List<String> episodeViews = new ArrayList<>();
    private List<String> episodeLocation = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episode_my2, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        tilSearchEpisodes = (TextInputLayout) view.findViewById(R.id.tilSearchEpisodes);
        tietSearchEpisodes = (TextInputEditText) view.findViewById(R.id.tietSearchEpisodes);
        tietSearchEpisodes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchEpisodes(tietSearchEpisodes.getText().toString());
            }
        });

        rvEpisodes = (RecyclerView) view.findViewById(R.id.rvEpisodes);

        fabCreateEpisode  = (FloatingActionButton) view.findViewById(R.id.fabCreateEpisode);
        fabCreateEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EpisodeCreate.class));
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int uid = bundle.getInt("uid", 0);
            from = bundle.getString("from");
            getEpisodes(uid);
            getUsername(uid);
            if (from.equals("profile")){
                toolbar.setPadding(0, getStatusBarHeight(), 0 ,0);
            }
            if (uid != SaveSharedPreference.getUserUID(getActivity())){
                fabCreateEpisode.hide();
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (from.equals("maintab")){
            MenuItem item = menu.findItem(R.id.search);
            item.setVisible(false);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void getUsername(int uid){
        if (uid == SaveSharedPreference.getUserUID(getActivity())){
            String username = SaveSharedPreference.getUsername(getActivity());
            toolbar.setTitle(username + "'s Episodes");
            tilSearchEpisodes.setHint("Search " + username + "'s Episodes");

        }
        new ServerRequestMethods(getActivity()).getUserData(uid, new UserCallback() {
            @Override
            public void done(User user) {
                toolbar.setTitle(user.getUsername() + "'s Episodes");
                tilSearchEpisodes.setHint("Search " + user.getUsername() + "'s Episodes");
            }
        });
    }

    private void getEpisodes(int uid){
        new EventRequest(getActivity()).getEventDataByMember(uid, new StringCallback() {
            @Override
            public void done(String string) {
                if (string.equals("\"No such event exists.\"")){
                }
                else {
                    try{
                        JSONObject jsonObject = new JSONObject(string);
                        String episodesString = jsonObject.getString("events");
                        JSONArray episodeArray = new JSONArray(episodesString);
                        if (episodeArray.length() > 0){
                            for (int i = 0; i < episodeArray.length(); i++){
                                JSONObject episodeObj = episodeArray.getJSONObject(i);
                                String eid = episodeObj.getString("eid");
                                String name = episodeObj.getString("eventName");
                                String type = episodeObj.getString("eventTypeLabel");
                                String views = episodeObj.getString("eventViewCount");

                                String episodeHostString = episodeObj.getString("eventHost");
                                JSONObject episodeHostObj = new JSONObject(episodeHostString);
                                String hostUsername = episodeHostObj.getString("username");

                                String episodeProfileImagesString = episodeObj.getString("eventProfileImages");
                                JSONArray episodeProfileImagesArray = new JSONArray(episodeProfileImagesString);
                                String imagePath;
                                if (episodeProfileImagesArray.length() > 0){
                                    HTTPConnection httpConnection = new HTTPConnection();
                                    String path = httpConnection.getUploadServerString();
                                    JSONObject episodeProfileImagesObj = episodeProfileImagesArray.getJSONObject(0);
                                    imagePath = path + episodeProfileImagesObj.getString("euiPath");
                                }
                                else {
                                    imagePath = "empty";
                                }
                                episodeEid.add(Integer.valueOf(eid));
                                episodeName.add(name);
                                episodeType.add(type);
                                episodeImagePath.add(imagePath);
                                episodeHostUsername.add("By " + hostUsername);
                                episodeViews.add(views + " views");
                                episodeLocation.add("North Arlington, NJ");
                            }
                            /*
                            searchEpisodeEid = episodeEid;
                            searchEpisodeTitle = episodeName;
                            searchEpisodeType = episodeType;
                            searchEpisodeImagePath = episodeImagePath;
                            searchEpisodeHostUsername = episodeHostUsername;
                            searchEpisodeViews = episodeViews;
                            searchEpisodeLocation = episodeLocation;
                            */

                            adapter = new EpisodeMyRecyclerAdapter(getActivity(), "vertical", episodeName, episodeImagePath, episodeEid, episodeType, episodeHostUsername, episodeViews, episodeLocation);
                            layoutManager = new LinearLayoutManager(getActivity());
                            rvEpisodes.setLayoutManager(layoutManager);
                            rvEpisodes.setNestedScrollingEnabled(false);
                            rvEpisodes.setAdapter(adapter);
                        }
                        else {
                            //tvEpisodesNum.setText("0");
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void searchEpisodes(String s){
        List<Integer> searchEpisodeEid = new ArrayList<>();
        List<String> searchEpisodeHostUsername = new ArrayList<>();
        List<String> searchEpisodeTitle = new ArrayList<>();
        List<String> searchEpisodeViews = new ArrayList<>();
        List<String> searchEpisodeLocation = new ArrayList<>();
        List<String> searchEpisodeImagePath = new ArrayList<>();
        List<String> searchEpisodeType = new ArrayList<>();
        for (int i = 0; i < episodeName.size(); i++){
            if (episodeName.get(i).toLowerCase().contains(s.toLowerCase())){
                searchEpisodeEid.add(episodeEid.get(i));
                searchEpisodeHostUsername.add(episodeHostUsername.get(i));
                searchEpisodeTitle.add(episodeName.get(i));
                searchEpisodeViews.add(episodeViews.get(i));
                searchEpisodeLocation.add(episodeLocation.get(i));
                searchEpisodeImagePath.add(episodeImagePath.get(i));
                searchEpisodeType.add(episodeType.get(i));
            }
        }
        adapter = new EpisodeMyRecyclerAdapter(getActivity(), "vertical", searchEpisodeTitle, searchEpisodeImagePath, searchEpisodeEid, searchEpisodeType, searchEpisodeHostUsername, searchEpisodeViews, searchEpisodeLocation);
        layoutManager = new LinearLayoutManager(getActivity());
        rvEpisodes.setLayoutManager(layoutManager);
        rvEpisodes.setNestedScrollingEnabled(false);
        rvEpisodes.setAdapter(adapter);
    }
}
