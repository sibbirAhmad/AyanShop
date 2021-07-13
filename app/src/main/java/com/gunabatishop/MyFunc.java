package com.gunabatishop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

public class MyFunc {

    public static SharedPreferences sp;
    public static void initSP(Context context){
        sp =context.getSharedPreferences("TempSave", Context.MODE_PRIVATE);
    }

//    public static MySqlDatabaseHelper sqlHelper;
//    public static void initSql(Context context){
//        sqlHelper = new MySqlDatabaseHelper(context);
//    }


    public static void putSP( String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSP(String key, String defaultValue) {
        String value = sp.getString(key, defaultValue);
        return value;
    }
    public static void modeManage(Context context,String mode){
        if(mode.contains("1")){
            MyFunc.putSP(SpKey.isNigh,"1");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            MyFunc.putSP(SpKey.isNigh,"0");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static void openLinkWithFbOrBrowser(Context context,String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
    public static void shareText(Context context, String shareTitle,String subject ,String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        context.startActivity(Intent.createChooser(intent,shareTitle));
    }

    public static void sendMail(Context context,String email) {
        try {
            context.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(email)));
        }catch (Exception e){
            Toast.makeText(context, "No Email App Available", Toast.LENGTH_LONG).show();
        }
    }
}
