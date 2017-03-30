package brymian.bubbles.damian;

import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserImageRequest;

/**
 * Created by Ziomster on 3/17/2016.
 */
public class TESTMain
{

    public static void main (String[] args)
    {

        Integer eid = 59;
        Integer uiid = 26;
        Short euiEventProfileSequence = 2;

        /*
        new EventUserImageRequest(this).setEuiEventProfileSequence(eid, uiid, euiEventProfileSequence, new StringCallback() {
            @Override
            public void done(String string)
            {
                System.out.println(string);
            }
        });
        */
    }

}
