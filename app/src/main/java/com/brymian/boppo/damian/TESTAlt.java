package com.brymian.boppo.damian;

import com.brymian.boppo.damian.nonactivity.Miscellaneous;

/**
 * Created by Ziomster on 6/12/2017.
 */

public class TESTAlt
{
    public static void main(String[] args)
    {
        int likes = 5;
        int dislikes = 0;

        Double ratio = ((double)likes)/((double)likes + (double)dislikes);

        System.out.println("INPUT VALUE: ");
        System.out.println(ratio);
        System.out.println("OUTPUT VALUE: ");
        System.out.println(Miscellaneous.convertRatioToNSigfigPercent(ratio, 4).toString());
    }
}
