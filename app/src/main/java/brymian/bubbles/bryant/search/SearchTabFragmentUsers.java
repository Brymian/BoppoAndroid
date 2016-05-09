package brymian.bubbles.bryant.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

/**
 * Created by almanza1112 on 5/8/16.
 */
public class SearchTabFragmentUsers extends Fragment{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    static View view;
    TextView tvUsersSomething;

    public static List<Integer> staticUID  = new ArrayList<>();
    public static List<String> staticFriendStatus = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_tab_fragment_users, container, false);
        tvUsersSomething = (TextView) view.findViewById(R.id.tvUsersSomething);
        SearchActivity.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                new ServerRequestMethods(getActivity()).getUsers(SearchActivity.etSearch.getText().toString(), new UserListCallback() {
                    @Override
                    public void done(List<User> users) {
                    List<String> usersString = new ArrayList<String>();
                    List<String> usersUsername = new ArrayList<String>();
                    try {
                    if(users.size() > 0) {
                    for (int i = 0; i < users.size(); i++) {
                    //if (users.get(i).getUid() != SaveSharedPreference.getUserUID(SearchActivity.this)) {
                    usersString.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                    usersUsername.add(i, users.get(i).getUsername());
                    staticUID.add(i, users.get(i).getUid());
                    final int j = i;
                    new ServerRequestMethods(getActivity()).getFriendStatus(SaveSharedPreference.getUserUID(getActivity()), users.get(i).getUid(), new StringCallback() {
                    @Override
                    public void done(String string) {
                    staticFriendStatus.add(j, string);
                    }
                    });
                    //}
                    }
                    }
                    }
                    catch (NullPointerException npe){
                    npe.printStackTrace();
                    }


                    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_search_users);
                    adapter = new SearchRecyclerAdapter(usersString, usersUsername);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addOnItemTouchListener(new SearchRecyclerItemClickListener(getActivity(), new SearchRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("uid", staticUID.get(position)).putExtra("profile", staticFriendStatus.get(position)));
                    }
                    })
                    );
                    }
                });
            }
        });

        return view;
    }
}
