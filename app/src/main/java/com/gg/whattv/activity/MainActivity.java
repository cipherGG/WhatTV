package com.gg.whattv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gg.whattv.R;
import com.gg.whattv.fragment.BasketFragment;
import com.gg.whattv.fragment.FootFragment;
import com.gg.whattv.fragment.MovieFragment;
import com.gg.whattv.fragment.TVFragment;
import com.gg.whattv.utils.DialogUtils;
import com.gg.whattv.utils.PreferenceUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;
    @ViewInject(R.id.nav_view)
    private NavigationView navigationView;

    private long lastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initListener();
    }

    private void initView() {
        toolbar.setTitle("电视节目");
        //为什么底下的不加这个方法就能设置成功？
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.nav_header_main, null));

        TVFragment tvFragment = TVFragment.newInstance("", "");
        fragmentTrans(tvFragment);
    }

    private void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout打开时，返回键不会退出
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (System.currentTimeMillis() - lastTime > 1000) {
            showToast("再按一次退出程序");
            lastTime = System.currentTimeMillis();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more) {
            openActivity(MoreActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_tv:
                toolbar.setTitle("电视节目");

                TVFragment tvFragment = TVFragment.newInstance("", "");
                fragmentTrans(tvFragment);

                break;
            case R.id.nav_basket:
                toolbar.setTitle("NBA赛事");

                BasketFragment basketFragment = new BasketFragment();
                fragmentTrans(basketFragment);

                break;
            case R.id.nav_foot:
                toolbar.setTitle("足球联赛");

                FootFragment footFragment = FootFragment.newInstance("", "");
                fragmentTrans(footFragment);

                break;
            case R.id.nav_movie:
                toolbar.setTitle("近期影视");

                MovieFragment movieFragment = MovieFragment.newInstance("", "");
                fragmentTrans(movieFragment);

                break;
            case R.id.nav_theme:
                DialogUtils.showThemeDialog(this);

                break;
            case R.id.nav_settings:
                showCityDialog();

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fragmentTrans(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_main, fragment);

        transaction.commit();
    }

    private void showCityDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_city, null);

        final EditText et = (EditText) view.findViewById(R.id.et_dialog_city);
        Button btn = (Button) view.findViewById(R.id.btn_dialog_city);

        final AlertDialog dialog = DialogUtils.showAlertDialog(this, "设置所在城市", null, view, null, "返回", null);

        et.setText(PreferenceUtils.getString(MainActivity.this, "city", "北京"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = et.getText().toString().trim();

                if (TextUtils.isEmpty(city)) {
                    et.setError("所在城市不能为空");

                } else {
                    if (PreferenceUtils.putString(MainActivity.this, "city", city)) {

                        MainActivity.this.showSnaker(drawer, "保存成功", null, null);

                        dialog.dismiss();
                    }
                }
            }
        });
    }

}
