package com.gg.whattv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gg.whattv.R;
import com.gg.whattv.activity.FootTeamActivity;
import com.gg.whattv.bean.FootLeague;
import com.gg.whattv.engine.Foot;

import java.util.ArrayList;
import java.util.List;

public class FootLeagueFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private String mParam2;

    private RecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;

    private List<FootLeague.Result.Views.A> saiCheng;

    public FootLeagueFragment() {
    }

    public static FootLeagueFragment newInstance(int param1, String param2) {
        FootLeagueFragment fragment = new FootLeagueFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foot_league, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_foot_league);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_fragment_foot_league);

        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                tabChanged();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);

                tabChanged();
            }
        });
    }

    private void tabChanged() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                saiCheng = Foot.getFootSaiCheng(mParam2);

                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerAdapter.notifyDataSetChanged();

                    //the UI will be repeat without call this method
                    swipeRefresh.setRefreshing(false);
                    break;
            }
        }
    };

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        int size;
        final int EMPTY_TYPE = 1;
        final int ITEM_TYPE = 2;

        public RecyclerAdapter() {
            saiCheng = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            size = saiCheng.size();

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
                View view = inflater.inflate(R.layout.item_fragment_foot_league, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                FootLeague.Result.Views.A a = saiCheng.get(position);

                ((ItemViewHolder) holder).a = a;
                ((ItemViewHolder) holder).c1.setText(a.c1);
                ((ItemViewHolder) holder).c2.setText(a.c2);
                ((ItemViewHolder) holder).c3.setText(a.c3);
                ((ItemViewHolder) holder).c4R.setText(a.c4R);
                ((ItemViewHolder) holder).c4T1.setText(a.c4T1);
                ((ItemViewHolder) holder).c4T2.setText(a.c4T2);
            }
        }

        private class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            FootLeague.Result.Views.A a;
            TextView c1;
            TextView c2;
            TextView c3;
            TextView c4R;
            TextView c4T1;
            TextView c4T2;
            Button player1;
            Button player2;

            public ItemViewHolder(View itemView) {
                super(itemView);
                c1 = (TextView) itemView.findViewById(R.id.tv_item_fragment_foot_league_c1);
                c2 = (TextView) itemView.findViewById(R.id.tv_item_fragment_foot_league_c2);
                c3 = (TextView) itemView.findViewById(R.id.tv_item_fragment_foot_league_c3);
                c4R = (TextView) itemView.findViewById(R.id.tv_item_fragment_foot_league_c4R);
                c4T1 = (TextView) itemView.findViewById(R.id.tv_item_fragment_foot_league_c4T1);
                c4T2 = (TextView) itemView.findViewById(R.id.tv_item_fragment_foot_league_c4T2);
                player1 = (Button) itemView.findViewById(R.id.btn_item_fragment_foot_league_player1);
                player2 = (Button) itemView.findViewById(R.id.btn_item_fragment_foot_league_player2);

                player1.setOnClickListener(this);
                player2.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FootTeamActivity.class);

                switch (v.getId()) {
                    case R.id.btn_item_fragment_foot_league_player1:
                        intent.putExtra("team", a.c4T1);

                        break;
                    case R.id.btn_item_fragment_foot_league_player2:
                        intent.putExtra("team", a.c4T2);

                        break;
                }

                getContext().startActivity(intent);
            }
        }
    }

}
