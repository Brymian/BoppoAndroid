package brymian.bubbles.bryant.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.User;

public class SearchActivity extends AppCompatActivity implements TextWatcher{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar mToolbar;
    EditText etSearch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setHint(R.string.Search);
        etSearch.addTextChangedListener(this);

        /**
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_search);
        adapter = new SearchRecyclerAdapter(countryList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
         **/
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        new ServerRequestMethods(SearchActivity.this).getUsers(etSearch.getText().toString(), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                List<String> usersString = new ArrayList<String>();
                try {
                    for(int i = 0; i< users.size(); i++){
                        usersString.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                    }
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView_search);
                adapter = new SearchRecyclerAdapter(usersString);
                layoutManager = new LinearLayoutManager(SearchActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });

    }
}
