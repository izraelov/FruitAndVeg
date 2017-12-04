package com.idoon.fruitandveg.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.idoon.fruitandveg.Model.User;

/**
 * Created by itayi on 02/12/2017.
 */

public class Common {

    public static User currentUser;

    public static boolean isConnectedToInterner(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null )
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for(int i=0 ; i<info.length ; i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}
