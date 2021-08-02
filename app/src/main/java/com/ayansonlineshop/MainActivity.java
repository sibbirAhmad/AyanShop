package com.ayansonlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import soft.insafservice.apphelper.MyFunc;

import static com.ayansonlineshop.AppManage.isAppOk;
import static com.ayansonlineshop.BrowserFragment.browserFragmentInstance;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivityInstance;

    public static MainActivity getInstance() {
        return mainActivityInstance;
    }

    MaterialToolbar toolbar;
    AppBarLayout appBarLayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    MenuItem previousCheckedMenu;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyFunc.initSP(MainActivity.this);
        if (!isAppOk) {
            MyFunc.securityError(MainActivity.this, null, null);

        }

        //checkNightMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityInstance = this;
        toolbar = findViewById(R.id.topAppBar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        appBarLayout = findViewById(R.id.appBarLayout);
        setSupportActionBar(toolbar);
       // getSupportActionBar().hide();

        manageModeSwitch();
        //Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView, true);

            }
        });


        //navigationView.setItemIconTintList(null);
        previousCheckedMenu = navigationView.getCheckedItem();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                previousCheckedMenu.setChecked(false);
                item.setChecked(true);
                previousCheckedMenu = item;
                drawerLayout.closeDrawers();
                //------------- todo : Manage Menu
                switch (item.getItemId()) {
                    case R.id.menu_browser:
                        opeBrowserFragment();
                        return true;
                    case R.id.menu_contact_info:
                        opeAboutFragment();
                        return true;
                    case R.id.menu_my_cart:
                        BrowserFragment.getInstance().loadUrl(SpKey.CART_URL);
                        return true;
                    case R.id.menu_my_whitelist:
                        BrowserFragment.getInstance().loadUrl(SpKey.WISH_LIST_URL);
                        return true;
                    case R.id.menu_track_order:
                        BrowserFragment.getInstance().loadUrl(SpKey.TRACK_ORDER_URL);
                        return true;
                    case R.id.menu_about_us:
                        //opeBrowserFragment();
                        BrowserFragment.getInstance().loadUrl(SpKey.ABOUT_US_URL);
                        return true;
                    case R.id.menu_fb_page:
                        MyFunc.openLinkWithFbOrBrowser(MainActivity.this, SpKey.FACEBOOK_PAGE);
                        return true;
                    case R.id.menu_fb_group:
                        MyFunc.openLinkWithFbOrBrowser(MainActivity.this, SpKey.FACEBOOK_GROUP);
                        return true;
                    case R.id.menu_whatsapp:
                        MyFunc.openLinkWithFbOrBrowser(MainActivity.this, SpKey.WHATS_APP);
                        return true;
                    case R.id.menu_feedback:
                        try {
                            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + MyFunc.getSP(SpKey.CONTACT_EMAIL, ""))));
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "No Email App Available", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    case R.id.menu_share_app:
                        MyFunc.shareText(MainActivity.this, "Share with..", "Download Ayan Shop app", SpKey.APP_APP_LINK);
                        return true;
                    case R.id.menu_policy:
                        BrowserFragment.getInstance().loadUrl(SpKey.PRIVACY_POLICY_URL);

//                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
//                        builder.setTitle("Privacy & Policy");
//                        builder.setMessage(R.string.privacy);
//
//                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        builder.show();
                        return true;
                    case R.id.menu_exit:
                        MainActivity.this.finishAffinity();
                        return true;
                    case R.id.menu_developer_info:
                        showDevDialog();

                    default:

                }

                return true;
            }
        });

        //------------------- TODO : Firs Open Browser
        opeBrowserFragment();
    }


    /**
     * 88888888888888888888888888888 Mange Nav Menu *****************************
     */
    //TODO : Manage AppBar Hide or Show
    boolean isAppbarShowing = true;

    public void showAppbar() {
        if (!isAppbarShowing) {
//            appBarLayout.setExpanded(true, true);
//            getSupportActionBar().show();
            isAppbarShowing = true;
            Log.d(TAG, "showAppbar: SHOWING....");
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
            if (toolbar.getVisibility() == View.GONE) {
                toolbar.setVisibility(View.VISIBLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                //currentContentView.requestLayout();
            }

        }
    }

    public void hideAppBar() {
        if (isAppbarShowing) {
//            appBarLayout.setExpanded(false, true);
//            getSupportActionBar().hide();
            toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            if (toolbar.getVisibility() == View.VISIBLE) {
                toolbar.setVisibility(View.GONE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

            }
            Log.d(TAG, "showAppbar: Hiding....");


//            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL);  // clear all scroll flags
            isAppbarShowing = false;
        }
    }


    BrowserFragment browserFragment;

    private void opeBrowserFragment() {
        Fragment fragment;
        fragment = new BrowserFragment();
        loadFragment(fragment);
        browserFragment = BrowserFragment.getInstance();
    }

    private void opeAboutFragment() {
        Fragment fragment;
        fragment = new AboutFragment();
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    SwitchMaterial switchMaterial;
    public static boolean initialModeChecked = false;

    private void manageModeSwitch() {

        View drawerSwitch = navigationView.getMenu().findItem(R.id.menu_mode).getActionView();
        switchMaterial = drawerSwitch.findViewById(R.id.drawer_switch);

        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //MyFunc.modeManage(MainActivity.this,"1");
                    setDarkMode();

                } else {
                    //MyFunc.modeManage(MainActivity.this,"0");
                    setLiteMode();

                }
                //Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
            }
        });
        if (MyFunc.getSP(SpKey.isNigh, "0").contains("1")) {
            switchMaterial.setChecked(true);
        }

    }


    //------------------------------------- todo : Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back:
                browserFragmentInstance.backWebView();
                return true;
            case R.id.menu_refresh:
                BrowserFragment.getInstance().pageRefresh();
                return true;
            case R.id.menu_share:
                browserFragmentInstance.shareUrl();
                return true;
            case R.id.menu_home:
                BrowserFragment.getInstance().goHome();
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }


    //--------------------------- todo : Handle Dark Mode

    private void checkNightMode() {
        if (!initialModeChecked) {
            initialModeChecked = true;
            if (MyFunc.getSP(SpKey.isNigh, "0").contains("1")) {
                setDarkMode();
                //Toast.makeText(this, "DarkMode", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "Lite", Toast.LENGTH_SHORT).show();
                setLiteMode();
            }
        } else {
            // Toast.makeText(this, "Mode Aready Change", Toast.LENGTH_SHORT).show();
        }

    }

    private void setDarkMode() {
        MyFunc.putSP(SpKey.isNigh, "1");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //recreate();
    }

    private void setLiteMode() {
        MyFunc.putSP(SpKey.isNigh, "0");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //recreate();
    }


    @Override
    public void onBackPressed() {
        if (browserFragmentInstance != null) {
            browserFragmentInstance.backWebView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    //todo : dev dialog
    private void showDevDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Developer");
        builder.setMessage(getString(R.string.dev_info));

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Find on Facebook", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyFunc.openLinkWithFbOrBrowser(MainActivity.this, getString(R.string.dev_fb));
            }
        });

        builder.show();


    }
}
