package brymian.bubbles.damian;

import java.util.List;

import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserLikeRequest;
import brymian.bubbles.objects.User;

/**
 * Created by Ziomster on 3/17/2016.
 */
public class TESTMain
{
    public static void main (String[] args)
    {
        /*
        Integer eid = ##;

        new EventRequest(this).incrementEventViewCount(eid, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println("THE FOLLOWING INCREMENT EVENT VIEW COUNT RESPONSE HAS BEEN RETURNED: ");
                System.out.println(string);
            }
        });
        */

        Integer uid = 3;
        String objectTypeLabel = "Event";
        Integer oid = 53;
        Boolean userLikeIndicator = false;

        new UserLikeRequest(this).setObjectLikeOrDislike(uid, objectTypeLabel, oid, userLikeIndicator, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
    }
}
