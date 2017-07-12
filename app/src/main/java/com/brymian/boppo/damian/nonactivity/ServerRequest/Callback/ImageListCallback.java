package com.brymian.boppo.damian.nonactivity.ServerRequest.Callback;

import java.util.List;

import com.brymian.boppo.damian.objects.Image;

/**
 * Created by Ziomster on 7/20/2015.
 */
public interface ImageListCallback {

    public abstract void done(List<Image> imageList);
}
