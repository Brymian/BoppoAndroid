package brymian.bubbles.bryant.search;

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

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

public class SearchTabFragmentUsers extends Fragment{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_tab_fragment_users, container, false);
        SearchActivity.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                new ServerRequestMethods(getActivity()).getUsers(SaveSharedPreference.getUserUID(getActivity()), SearchActivity.etSearch.getText().toString(), new UserListCallback() {
                    @Override
                    public void done(List<User> users) {
                        List<Integer> uid = new ArrayList<>();
                        List<String> friendStatus = new ArrayList<>();
                        List<String> usersFirstLastName = new ArrayList<>();
                        List<String> usersUsername = new ArrayList<>();
                        try {
                            if(users.size() > 0) {
                                for (int i = 0; i < users.size(); i++) {
                                    //if (users.get(i).getUid() != SaveSharedPreference.getUserUID(SearchActivity.this)) {
                                    usersFirstLastName.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                                    usersUsername.add(i, users.get(i).getUsername());
                                    uid.add(i, users.get(i).getUid());
                                    friendStatus.add(i, users.get(i).getFriendshipStatus());
                                    //}
                                }
                            }
                        }
                        catch (NullPointerException npe){
                            npe.printStackTrace();
                        }


                        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_search_users);
                        adapter = new SearchRecyclerAdapterUsers(getActivity(), usersFirstLastName, usersUsername, uid, friendStatus);
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

        return view;
    }
}
