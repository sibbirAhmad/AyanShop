package com.gunabatishop;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import soft.insafservice.apphelper.MyDS;

public class AppManage {
    Context context;
    static  String TAG  = "AppManage";

    public static String exCreateData(){
        JSONObject object = new JSONObject();
        try{

            object.put(SpKey.CONTACT_EMAIL,SpKey.CONTACT_EMAIL);
            object.put(SpKey.CONTACT_NUMBER,SpKey.CONTACT_NUMBER);
            object.put(SpKey.FACEBOOK_PAGE,SpKey.FACEBOOK_PAGE);
            object.put(SpKey.FACEBOOK_GROUP,SpKey.FACEBOOK_GROUP);
            object.put(SpKey.WHATS_APP,SpKey.WHATS_APP);
            object.put(SpKey.YOUTUBE_CHANNEL,SpKey.YOUTUBE_CHANNEL);
            object.put(SpKey.APP_APP_LINK,SpKey.APP_APP_LINK);
            object.put(SpKey.APP_URL ,SpKey.APP_URL );
            String ex = MyDS.ex(object.toString(),null);
            Log.d(TAG, "exCreateData: "+ex);
            return ex;


        }catch (Exception e){
            Log.e(TAG, "exCreateData: ", e);
        }
        return null;
    }
    public void svData(String json){
        JSONObject object = null;
        try {
            object = new JSONObject(json);

            MyFunc.putSP(SpKey.CONTACT_EMAIL,object.getString(SpKey.CONTACT_EMAIL));
            MyFunc.putSP(SpKey.CONTACT_NUMBER,object.getString(SpKey.CONTACT_NUMBER));
            MyFunc.putSP(SpKey.FACEBOOK_PAGE,object.getString(SpKey.FACEBOOK_PAGE));
            MyFunc.putSP(SpKey.FACEBOOK_GROUP,object.getString(SpKey.FACEBOOK_GROUP));
            MyFunc.putSP(SpKey.WHATS_APP,object.getString(SpKey.WHATS_APP));
            MyFunc.putSP(SpKey.YOUTUBE_CHANNEL,object.getString(SpKey.YOUTUBE_CHANNEL));
            MyFunc.putSP(SpKey.APP_APP_LINK,object.getString(SpKey.APP_APP_LINK));
            MyFunc.putSP(SpKey.APP_URL ,object.getString(SpKey.APP_URL ));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    static int developedVersion = 1;
    public static void checkAppHealth(){
        int appVersion = BuildConfig.VERSION_CODE;
        if(appVersion != developedVersion){

        }
        if(MyFunc.getSP(SpKey.CONTACT_EMAIL,"")==""){
            //Temp Creating data
            String json = AppManage.exCreateData();
            String dd = MyDS.dx(json,null);
            new AppManage().svData(dd);
        };
    }

}
