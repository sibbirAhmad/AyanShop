package com.gunabatishop;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import soft.insafservice.apphelper.MyDS;
import soft.insafservice.apphelper.MyFunc;

public class AppManage {
    Context context;
    static  String TAG  = "AppManage";

    public static String exCreateData(){
        JSONObject object = new JSONObject();
        try{
            /**
             * I user is new or updt then new ss have to create... Jus for dv
             * Some Line commented....
             * for any data update ss value have to be recreate
             */

            object.put(SpKey.CONTACT_EMAIL,SpKey.CONTACT_EMAIL);
            object.put(SpKey.CONTACT_NUMBER_KEY,SpKey.CONTACT_NUMBER);
            object.put(SpKey.MAP_URL,SpKey.MAP_URL);
            object.put(SpKey.FACEBOOK_PAGE,SpKey.FACEBOOK_PAGE);
            object.put(SpKey.FACEBOOK_GROUP,SpKey.FACEBOOK_GROUP);
            object.put(SpKey.WHATS_APP,SpKey.WHATS_APP);
            object.put(SpKey.YOUTUBE_CHANNEL,SpKey.YOUTUBE_CHANNEL);
            object.put(SpKey.APP_APP_LINK,SpKey.APP_APP_LINK);
            object.put(SpKey.APP_URL ,SpKey.APP_URL );
            String ex = MyDS.ex(object.toString(),null);
            //Log.d(TAG, "exCreateData: "+ex);
            return ex;


        }catch (Exception e){
            Log.e(TAG, "exCreateData: ", e);
        }
        return null;
    }
    public static void svData(String json){
        JSONObject object = null;
        try {
            object = new JSONObject(json);

            MyFunc.putSP(SpKey.CONTACT_EMAIL,object.getString(SpKey.CONTACT_EMAIL));
            MyFunc.putSP(SpKey.CONTACT_NUMBER_KEY,object.getString(SpKey.CONTACT_NUMBER_KEY));
            MyFunc.putSP(SpKey.MAP_URL,object.getString(SpKey.MAP_URL));
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
    public static boolean isAppOk = false;
    public static void checkAppHealth(Context context){
        int appVersion = BuildConfig.VERSION_CODE;
        //----- todo : Check scam update
        if(appVersion != developedVersion){
            MyFunc.securityError(context,null,null);
            isAppOk = false;
        }else {
            if(!MyFunc.getSP(SpKey.VERSION_CHECK_KEY,"0").contains(appVersion+"")){
                //----- My Be new User or... App Updated... Need to reset data
                String dd = MyDS.dx(context.getString(R.string.app_ss),null);
                svData(dd);
                MyFunc.putSP(SpKey.VERSION_CHECK_KEY,appVersion+"");
                Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();

            }
            isAppOk = true;

        }
        // todo : Chck scam data
        if(MyFunc.getSP(SpKey.APP_URL,"")==""){
            //Temp Creating data
            //-----------Line hidden---------- exCreateData();
            String dd = MyDS.dx(context.getString(R.string.app_ss),null);
            //-----------------Log.d(TAG, "checkAppHealth: "+dd);
             svData(dd);

        };
    }

}
