package brymian.bubbles.bryant.navigationDrawer;

/**
 * Created by Almanza on 3/11/2016.
 */
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
            drawerHolder.spinner = (Spinner) view.findViewById(R.id.drawerSpinner);
            drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);
            drawerHolder.headerLayout = (LinearLayout) view.findViewById(R.id.headerLayout);
            drawerHolder.itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            drawerHolder.spinnerLayout = (LinearLayout) view.findViewById(R.id.spinnerLayout);

            view.setTag(drawerHolder);

        }
        else {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        if (dItem.isSpinner()) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.spinnerLayout.setVisibility(LinearLayout.VISIBLE);

            List<SpinnerItem> userList = new ArrayList<SpinnerItem>();

            /* Add user information here */
            userList.add(new SpinnerItem(android.R.drawable.ic_menu_myplaces,
                            getFirstNameUserDataLocal()
                    + " " + getLastNameUserDataLocal(),
                            getUsernameUserDataLocal()));

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context,
                    R.layout.custom_spinner_item, userList);

            drawerHolder.spinner.setAdapter(adapter);

            drawerHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    //Toast.makeText(context, "User Changed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }
        else if (dItem.getTitle() != null) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.title.setText(dItem.getTitle());
        }
        else {
            drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));
            drawerHolder.ItemName.setText(dItem.getItemName());

        }
        return view;
    }

    private String getFirstNameUserDataLocal(){
        UserDataLocal udl = new UserDataLocal(getContext());
        User userPhone = udl.getUserData();
        return userPhone.getFirstName();
    }

    private String getLastNameUserDataLocal(){
        UserDataLocal udl = new UserDataLocal(getContext());
        User userPhone = udl.getUserData();
        return userPhone.getLastName();
    }

    private String getUsernameUserDataLocal(){
        UserDataLocal udl = new UserDataLocal(getContext());
        User userPhone = udl.getUserData();
        return userPhone.getUsername();
    }

    private static class DrawerItemHolder {
        TextView ItemName, title;
        ImageView icon;
        LinearLayout headerLayout, itemLayout, spinnerLayout;
        Spinner spinner;
    }
}