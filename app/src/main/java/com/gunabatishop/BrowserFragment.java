package com.gunabatishop;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class BrowserFragment extends Fragment {

    public static BrowserFragment browserFragmentInstance;
    public static BrowserFragment getInstance(){
        return browserFragmentInstance;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    View rootView;
    WebView webView;
    RelativeLayout webLoader,errorView;
    ProgressBar progressBar;
    Button tryAgainBtn,openBrowser;
    String runningUrl;
    String webUrl = "http://gunabatishop.com";
    //String webUrl = "http://bike.c1.biz";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyFunc.initSP(getContext());
        rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        webLoader = rootView.findViewById(R.id.webLoader);
        errorView = rootView.findViewById(R.id.errorView);
        webView = rootView.findViewById(R.id.webView);
        progressBar = rootView.findViewById(R.id.progressBar);
        tryAgainBtn = rootView.findViewById(R.id.tryAgainBtn);
        openBrowser = rootView.findViewById(R.id.openBrowser);
        browserFragmentInstance = this;
        initWebView();
        loadUrl(webUrl);

        openBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunc.openLinkWithFbOrBrowser(getContext(),runningUrl);
            }
        });
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageRefresh();
            }
        });

        return rootView;
    }

    String TAG = "BrowserFragment";

    private void initWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        //----- todo : Check if Dark mode activated
        if(MyFunc.getSP(SpKey.isNigh,"0").contains("1")){
            try {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            }catch (Exception e){

            }
        }



        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        CookieSyncManager.getInstance().startSync();
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);



        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                runningUrl = url;
                hideError();
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "Finished loading URL: " + url);
                runningUrl = url;

            }


            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                //Toast.makeText(getContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                runningUrl = failingUrl;
                showError();

            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                Log.d(TAG, "onProgressChanged: "+progress);
                if(progress<100 && webLoader.getVisibility()==View.GONE){
                    webLoader.setVisibility(View.VISIBLE);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(progress,true);
                }else {
                    progressBar.setProgress(progress);
                }
                if(progress>=100){
                    webLoader.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadUrl(String url){
        webView.loadUrl(url);
    }
    private void hideLoader(){

    }
    private void showLoader(){
    }
    private void hideError(){
        errorView.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
    }
    private void showError(){
        webView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);

    }
    public void pageRefresh(){
        loadUrl(runningUrl);
    }
    public void backWebView(){
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            //Toast.makeText(getContext(), "No Back Page", Toast.LENGTH_SHORT).show();
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setTitle("Do you want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finishAffinity();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }

    }
    public void goHome(){
        loadUrl(webUrl);
    }
    public void shareUrl(){
        MyFunc.shareText(getContext(),"Share With ","",runningUrl);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //super.onCreateOptionsMenu(menu);
//        getContext().getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }


}
