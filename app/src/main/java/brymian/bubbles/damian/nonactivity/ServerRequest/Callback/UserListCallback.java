package brymian.bubbles.damian.nonactivity.ServerRequest.Callback;

import java.util.List;

import brymian.bubbles.damian.nonactivity.User;

/**
 * Created by Ziomster on 7/20/2015.
 */
public interface UserListCallback {

    public abstract void done(List<User> users);
}
