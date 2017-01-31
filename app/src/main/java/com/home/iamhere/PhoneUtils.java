package com.home.iamhere;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

/**
 * Created by victor on 29.01.17.
 */

public class PhoneUtils {
    public static String getMyPhoneNumber(Context context){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    public static String getMy10DigitPhoneNumber(Context context){
        String s = getMyPhoneNumber(context);
        return s.substring(2);
    }
}
