package com.gg.whattv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gg.whattv.R;
import com.gg.whattv.bean.FootTeam;
import com.gg.whattv.engine.Foot;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_foot_team)
public class FootTeamActivity extends BaseActivity {
    @ViewInject(R.id.tb_activity_foot_team)
    private Toolbar toolbar;
    @ViewInject(R.id.rv_activity_foot_team)
    private RecyclerView recyclerView;

    private RecyclerAdapter recyclerAdapter;
    private String team;
    private List<FootTeam.Result.A> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        updateItems();
    }

    private void initView() {
        Intent intent = getIntent();
        team = intent.getStringExtra("team");

        //必须放到setSupportActionBar之前
        toolbar.setTitle(team);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setAdapter(recyclerAdapter);
    }

    private void updateItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teamList = Foot.getFootTeam(FootTeamActivity.this.team);

                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        int size;
        final int EMPTY_TYPE = 1;
        final int ITEM_TYPE = 2;

        public RecyclerAdapter() {
            teamList = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            size = teamList.size();

            return size == 0 ? 1 : size;
        }

        @Override
        public int getItemViewType(int position) {
            return size == 0 ? EMPTY_TYPE : ITEM_TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            if (viewType == EMPTY_TYPE) {
                View view = inflater.inflate(R.layout.item_empty, parent, false);

                return new EmptyViewHolder(view);

            } else {
                View view = inflater.inflate(R.layout.item_activity_foot_team, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                FootTeam.Result.A a = teamList.get(position);

                ((ItemViewHolder) holder).c1.setText(a.c1);
                ((ItemViewHolder) holder).c2.setText(a.c2);
                ((ItemViewHolder) holder).c3.setText(a.c3);
                ((ItemViewHolder) holder).c4T1.setText(a.c4T1);
                ((ItemViewHolder) holder).c4R.setText(a.c4R);
                ((ItemViewHolder) holder).c4T2.setText(a.c4T2);
            }
        }

        private class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView c1;
            TextView c2;
            TextView c3;
            TextView c4T1;
            TextView c4R;
            TextView c4T2;

            public ItemViewHolder(View itemView) {
                super(itemView);
                c1 = (TextView) itemView.findViewById(R.id.tv_item_activity_foot_team_c1);
                c2 = (TextView) itemView.findViewById(R.id.tv_item_activity_foot_team_c2);
                c3 = (TextView) itemView.findViewById(R.id.tv_item_activity_foot_team_c3);
                c4T1 = (TextView) itemView.findViewById(R.id.tv_item_activity_foot_team_c4T1);
                c4R = (TextView) itemView.findViewById(R.id.tv_item_activity_foot_team_c4R);
                c4T2 = (TextView) itemView.findViewById(R.id.tv_item_activity_foot_team_c4T2);

            }
        }
    }

}
