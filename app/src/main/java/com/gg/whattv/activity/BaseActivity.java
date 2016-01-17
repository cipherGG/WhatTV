package com.gg.whattv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gg.whattv.R;
import com.gg.whattv.utils.PreferenceUtils;

import org.xutils.x;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //换肤操作
        onPreCreate();

        super.onCreate(savedInstanceState);

        //xUtils不能注解public static 的 View
        x.view().inject(this);
        x.Ext.setDebug(true);
    }

    public void onPreCreate() {
        final String currentTheme = PreferenceUtils.getString(this, "settings_theme", "Blue");

        switch (currentTheme) {
            case "Blue":
                this.setTheme(R.style.BlueTheme);
                break;
            case "Green":
                this.setTheme(R.style.GreenTheme);
                break;
            case "Red":
                this.setTheme(R.style.RedTheme);
                break;
            case "Indigo":
                this.setTheme(R.style.IndigoTheme);
                break;
            case "BlueGrey":
                this.setTheme(R.style.BlueGreyTheme);
                break;
            case "Black":
                this.setTheme(R.style.BlackTheme);
                break;
            case "Orange":
                this.setTheme(R.style.OrangeTheme);
                break;
            case "Purple":
                this.setTheme(R.style.PurpleTheme);
                break;
            case "Pink":
                this.setTheme(R.style.PinkTheme);
                break;
        }
    }

    public Context getContext() {
        return this;
    }

    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(String show) {
        Toast.makeText(BaseActivity.this, show, Toast.LENGTH_SHORT).show();
    }

    public void showSnaker(View view, String show, String action, View.OnClickListener listener) {
        Snackbar.make(view, show, Snackbar.LENGTH_SHORT).setAction(action, listener).show();
    }

    public void openActivity(Class mClass) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        startActivity(intent);
        openAnim();
    }

    public void openActivityResult(Class mClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        startActivityForResult(intent, requestCode);
        openAnim();
    }

    public void openService(Class mClass) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        startService(intent);
    }

    public void stopService(Class mClass) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        stopService(intent);
    }

    private void openAnim() {
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
    }

}
