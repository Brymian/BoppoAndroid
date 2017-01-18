package brymian.bubbles.bryant.episodes;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.MiscellaneousRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserCommentRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserLikeRequest;
import brymian.bubbles.objects.Event;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;



public class EpisodeActivity extends AppCompatActivity implements View.OnClickListener{
    TextView  tvEpisodeHostName, tvEpisodeHostUsername, tvLikeCount, tvDislikeCount, tvViewCount, tvCommentsNumber;
    FloatingActionButton fabPlay;
    ImageView ivLike, ivDislike, ivAddComment;
    EditText etAddComment;
    Toolbar mToolbar;
    RecyclerView rvComments;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static int eid;
    private boolean isHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        /*--------------------------------Checking for putExtras()--------------------------------*/
        int eid;
        String episodeTitle;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eid = 0;
                episodeTitle = null;
            }
            else {
                eid = extras.getInt("eid");
                episodeTitle = extras.getString("episodeTitle");
            }
        }
        else {
            eid = savedInstanceState.getInt("eid");
            episodeTitle = savedInstanceState.getString("episodeTitle");
        }
        /*----------------------------------------------------------------------------------------*/

        tvEpisodeHostName = (TextView) findViewById(R.id.tvEpisodeHostName);
        tvEpisodeHostUsername = (TextView) findViewById(R.id.tvEpisodeHostUsername);
        tvLikeCount = (TextView) findViewById(R.id.tvLikeCount);
        tvDislikeCount = (TextView) findViewById(R.id.tvDislikeCount);
        tvViewCount = (TextView) findViewById(R.id.tvViewCount);
        ivLike = (ImageView) findViewById(R.id.ivLike);
        ivLike.setOnClickListener(this);
        ivDislike = (ImageView) findViewById(R.id.ivDislike);
        ivDislike.setOnClickListener(this);
        fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        tvCommentsNumber = (TextView) findViewById(R.id.tvCommentsNumber);
        etAddComment = (EditText) findViewById(R.id.etAddComment);
        etAddComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivAddComment = (ImageView) findViewById(R.id.ivAddComment);
        ivAddComment.setOnClickListener(this);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        setEid(eid);
        incrementViewCount(eid);
        getEpisodeInfo(eid);
        getEpisodeComments();

        mToolbar.setTitle(episodeTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();

        switch (view.getId()){
            case R.id.ivLike:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", getEid(), true, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Like", string);
                    }
                });

                break;

            case R.id.ivDislike:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", getEid(), false, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Dislike", string);
                    }
                });
                break;
            /**
            case R.id.fabParticipants:
                startFragment(fm, R.id.episode_activity, new EpisodeParticipants());
                break;
**/
            case R.id.fabPlay:
                startFragment(fm, R.id.episode_activity, new EpisodeView());
                Log.e("fabPlay", "touched");
                break;

            case R.id.ivAddComment:
                addComment();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void addComment(){
        new UserCommentRequest(this).setObjectComment(SaveSharedPreference.getUserUID(this), "Event", getEid(), null, etAddComment.getText().toString(), null, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("addComment", string);
            }
        });
    }
/**
    private void setFloatingActionButtons(){
        fabLike = (FloatingActionButton) findViewById(R.id.fabLike);
        fabLike.setOnClickListener(this);
        fabDislike = (FloatingActionButton) findViewById(R.id.fabDislike);
        fabDislike.setOnClickListener(this);
        fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        fabPlay.setOnClickListener(this);

        fabSettings = (FloatingActionButton) findViewById(R.id.fabSettings);

        if(getIsHost()){
            fabSettings.setOnClickListener(this);
        }else{
            fabSettings.hide(true);
            //fabSettings.show(false);
        }


        fabComment = (FloatingActionButton) findViewById(R.id.fabComment);
        fabParticipants = (FloatingActionButton) findViewById(R.id.fabParticipants);
        fabParticipants.setOnClickListener(this);
        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
    }

 **/

    private void getEpisodeComments() {
        new UserCommentRequest(this).getObjectComments("Event", getEid(), new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    if (string.equals("\"The specified object with the specified ID does not exist, or it has no comments.\"")){
                        tvCommentsNumber.setText("0");
                    }
                    else{
                        Log.e("comments", string);
                        List<Integer> uid = new ArrayList<>();
                        List<String> userComment = new ArrayList<>();
                        List<String> userCommentTimestamp = new ArrayList<>();
                        //List<Integer> userCommentParentUcid = new ArrayList<>();

                        JSONObject object = new JSONObject(string);
                        JSONArray jArray  = object.getJSONArray("comments");
                        tvCommentsNumber.setText(String.valueOf(jArray.length()));
                        for (int i = 0; i < jArray.length(); i++){
                            JSONObject jArray_jObject = jArray.getJSONObject(i);
                            uid.add(jArray_jObject.getInt("uid"));
                            userComment.add(jArray_jObject.getString("userComment"));
                            userCommentTimestamp.add(jArray_jObject.getString("userCommentSetTimestamp"));
                            //userCommentParentUcid.add(jArray_jObject.getInt("parentUcid"));
                        }

                        adapter = new EpisodeActivityCommentsRecyclerAdapter(EpisodeActivity.this, uid, userComment, userCommentTimestamp);
                        layoutManager = new LinearLayoutManager(EpisodeActivity.this);
                        rvComments.setLayoutManager(layoutManager);
                        rvComments.setNestedScrollingEnabled(false);
                        rvComments.setAdapter(adapter);
                    }
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getEpisodeInfo(int eid){
        new EventRequest(this).getEventData(eid, new EventCallback() {
            @Override
            public void done(Event event) {
                mToolbar.setTitle(event.eventName);
                tvEpisodeHostName.setText(event.eventHostFirstName + " " + event.eventHostLastName);
                tvEpisodeHostUsername.setText(event.eventHostUsername);
                tvLikeCount.setText(String.valueOf(event.eventLikeCount));
                tvDislikeCount.setText(String.valueOf(event.eventDislikeCount));
                tvViewCount.setText(String.valueOf(event.eventViewCount) + " views");
                if(event.eventHostUid == SaveSharedPreference.getUserUID(EpisodeActivity.this)){
                    setIsHost(true);
                }
                else{
                    setIsHost(false);
                }
            }
        });
    }


    private void incrementViewCount(int eid){
        new MiscellaneousRequest(this).incrementObjectViewCount(eid, "Event", new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("View Count", string);
            }
        });
    }
    private void setEid(int eid){
        EpisodeActivity.eid = eid;
    }

    public static int getEid(){
        return eid;
    }

    private void setIsHost(boolean isHost){
        this.isHost = isHost;
    }

    private boolean getIsHost(){
        return isHost;
    }

}
