package com.gunabatishop;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import in.codeshuffle.typewriterview.TypeWriterView;
import soft.insafservice.apphelper.MyFunc;


public class AboutFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View rootView;
    Button callBTN, emailBTN, mapBTN;
    TypeWriterView typeWriter;
    RelativeLayout aboutHeaderRL;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_about, container, false);
        callBTN = rootView.findViewById(R.id.callBTN);
        emailBTN = rootView.findViewById(R.id.emailBTN);
        mapBTN = rootView.findViewById(R.id.mapBTN);
        typeWriter = rootView.findViewById(R.id.typeWriter);
        aboutHeaderRL = rootView.findViewById(R.id.aboutHeaderRL);

        aboutHeaderRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunc.openLinkWithFbOrBrowser(getContext(),SpKey.APP_URL);
            }
        });

//        typeWriter.setDelay(1);
//
//        //Setting music effect On/Off
//        typeWriter.setWithMusic(true);
//        //Animating Text
//        typeWriter.animateText("www.GunabatiShop.com");

      callBTN.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent callIntent = new Intent(Intent.ACTION_DIAL);
              callIntent.setData(Uri.parse("tel:" + MyFunc.getSP(SpKey.CONTACT_NUMBER_KEY,"")));
              startActivity(callIntent);

          }
      });
      emailBTN.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              sendMail();
          }
      });
      mapBTN.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String uri = MyFunc.getSP(SpKey.MAP_URL,"");
              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
              intent.setPackage("com.google.android.apps.maps");
              try
              {
                  startActivity(intent);
              }
              catch(ActivityNotFoundException ex)
              {
                  try
                  {
                      Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                      startActivity(unrestrictedIntent);
                  }
                  catch(ActivityNotFoundException innerEx)
                  {
                      Toast.makeText(getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                  }
              }

          }
      });

        return rootView;
    }

    public void sendMail() {
        try {
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+MyFunc.getSP(SpKey.CONTACT_EMAIL,""))));
        }catch (Exception e){
            Toast.makeText(getContext(), "No Email App Available", Toast.LENGTH_LONG).show();
        }
    }
}
