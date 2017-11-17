package com.brymian.boppo.damian;

import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserRelationshipRequest;

/**
 * Created by Ziomster on 3/17/2016.
 */
public class TESTMain
{

    public static void main (String[] args)
    {
        /*
        Integer eid = 59;
        Integer uid = 12;
        String eventUserTypeLabel = "Moderator";
        String eventUserInviteStatusTypeLabel = "Joined";

        try
        {
            new EventUserRequest(this).setEventUser(eid, uid, eventUserTypeLabel,
                eventUserInviteStatusTypeLabel,
                new Boolean[]{null, null, true, true},
                new StringCallback()
            {
                @Override
                public void done(String string) {
                    System.out.println(string);
                }
            });
        }
        catch (SetOrNotException sone)
        {
            sone.printStackTrace();
            // Do something here, if need to handle this
        }
        */
        Integer uid_1 = 1;
        Integer uid_2 = 2;
        String userRelationshipAction = "";

        new UserRelationshipRequest().setUserRelationship(uid_1, uid_2, userRelationshipAction, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
    }

}
