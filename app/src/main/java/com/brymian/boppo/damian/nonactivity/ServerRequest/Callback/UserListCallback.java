package com.brymian.boppo.damian.nonactivity.ServerRequest.Callback;

import java.util.List;

import com.brymian.boppo.damian.objects.User;

/**
 * Created by Ziomster on 7/20/2015.
 */
public interface UserListCallback {

    public abstract void done(List<User> users);
}
