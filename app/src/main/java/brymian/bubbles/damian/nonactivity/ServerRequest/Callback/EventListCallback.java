package brymian.bubbles.damian.nonactivity.ServerRequest.Callback;

import java.util.List;

import brymian.bubbles.objects.Event;

/**
 * Created by Ziomster on 7/20/2015.
 */
public interface EventListCallback {

    public abstract void done(List<Event> eventList);
}
