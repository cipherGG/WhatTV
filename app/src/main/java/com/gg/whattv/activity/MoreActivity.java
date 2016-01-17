package com.gg.whattv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gg.whattv.R;
import com.gg.whattv.utils.PreferenceUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_more)
public class MoreActivity extends BaseActivity {
    @ViewInject(R.id.btn_activity_more)
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int count = PreferenceUtils.getInt(this, "count", 0);

        btn.setText(String.valueOf(count));
    }

    @Event(value = R.id.btn_activity_more)
    private void onClick(View v) {
        int current = PreferenceUtils.getInt(this, "count", 0) + 1;

        if (!PreferenceUtils.putInt(this, "count", current)) {
            return;
        }
        btn.setText(String.valueOf(current));

        if (current == 10) {
            showToast("猜猜这是干嘛的");
        } else if (current == 50) {
            showToast("其实并没有什么卵用");
        } else if (current == 100) {
            showToast("点的我好痒啊");
        } else if (current == 150) {
            showToast("再点我就要放大招了");
        } else if (current == 200) {
            showToast("都躲开，我要开始装逼了");
        } else if (current == 250) {
            showToast("第一次看见比我还无聊的人");
        } else if (current == 300) {
            showToast("骚年，你很有耐性啊");
        } else if (current == 350) {
            showToast("不跟我学做菜真是可惜了");
        } else if (current == 400) {
            showToast("我手里有5T硬盘，hiahiahia~");
        } else if (current == 450) {
            showToast("不玩了不玩了");
        } else if (current == 500) {
            showToast("真的不玩了" +
                    "\n感谢您对本软件的支持" +
                    "\n有任何疑问或开心事或伤心事，都可找我分享或发泄" +
                    "\n再见啦皮卡丘！！！！");
        }
    }

}
