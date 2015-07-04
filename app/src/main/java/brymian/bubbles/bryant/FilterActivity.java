package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.FilterButtons.ApplyFilter;

/**
 * Created by Almanza on 7/4/2015.
 */
public class FilterActivity extends FragmentActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void onClickApplyFilter(View v){
        Intent applyfilterIntent = new Intent(this, ApplyFilter.class);
        startActivity(applyfilterIntent);
    }
    /**
    public void onClickClearFilter(View v){
        Intent clearfilterIntent = new Intent(this, ClearFilter.class);
        startActivity(clearfilterIntent);

    }
     **/
}
