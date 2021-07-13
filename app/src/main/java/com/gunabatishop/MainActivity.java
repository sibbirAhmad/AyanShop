package com.gunabatishop;

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
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import static com.gunabatishop.BrowserFragment.browserFragmentInstance;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    MenuItem previousCheckedMenu;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyFunc.initSP(MainActivity.this);
        //checkNightMode();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.topAppBar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
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
                switch (item.getItemId()){
                    case  R.id.menu_browser :
                        opeBrowserFragment();
                        return true;
                    case R.id.menu_about:
                        opeAboutFragment();
                        return true;
                        case R.id.menu_youtube:
                       MyFunc.openLinkWithFbOrBrowser(MainActivity.this,SpKey.YOUTUBE_CHANNEL);
                        return true;
                    case R.id.menu_fb_page:
                        MyFunc.openLinkWithFbOrBrowser(MainActivity.this,SpKey.FACEBOOK_PAGE);
                        return true;
                    case R.id.menu_fb_group:
                        MyFunc.openLinkWithFbOrBrowser(MainActivity.this,SpKey.FACEBOOK_GROUP);
                        return true;
                    case R.id.menu_whatsapp:
                        MyFunc.openLinkWithFbOrBrowser(MainActivity.this,SpKey.WHATS_APP);
                        return true;
                    case R.id.menu_feedback:
                        try {
                            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+SpKey.CONTACT_EMAIL)));
                        }catch (Exception e){
                            Log.e(TAG, "onNavigationItemSelected: ", e);
                            Toast.makeText(MainActivity.this, "No Email App Available", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    case R.id.menu_share_app:
                        MyFunc.shareText(MainActivity.this,"Share with..","GunabatiShop app",SpKey.APP_APP_LINK);
                        return true;
                    case R.id.menu_policy:
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
                        builder.setTitle("Privacy & Policy");
                        builder.setMessage(R.string.privacy);

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                        return true;
                    case R.id.menu_exit:
                        MainActivity.this.finishAffinity();

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

     BrowserFragment browserFragment;
    private void opeBrowserFragment(){
        Fragment fragment;
        fragment = new BrowserFragment();
        loadFragment(fragment);
        browserFragment = BrowserFragment.getInstance();
    }
    private void opeAboutFragment(){
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
                if(isChecked){
                 //MyFunc.modeManage(MainActivity.this,"1");
                    setDarkMode();

                }else{
                    //MyFunc.modeManage(MainActivity.this,"0");
                    setLiteMode();

                }
                //Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
            }
        });
        if(MyFunc.getSP(SpKey.isNigh,"0").contains("1")){
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
            case R.id.menu_back :
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

    private void checkNightMode(){
        if(!initialModeChecked){
            initialModeChecked = true;
            if(MyFunc.getSP(SpKey.isNigh,"0").contains("1")){
                setDarkMode();
                //Toast.makeText(this, "DarkMode", Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(this, "Lite", Toast.LENGTH_SHORT).show();
                setLiteMode();
            }
        }else {
           // Toast.makeText(this, "Mode Aready Change", Toast.LENGTH_SHORT).show();
        }

    }
    private void setDarkMode(){
        MyFunc.putSP(SpKey.isNigh,"1");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //recreate();
    }
    private void setLiteMode(){
        MyFunc.putSP(SpKey.isNigh,"0");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //recreate();
    }


    @Override
    public void onBackPressed() {
        if(browserFragmentInstance!=null){
            browserFragmentInstance.backWebView();
        }
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
