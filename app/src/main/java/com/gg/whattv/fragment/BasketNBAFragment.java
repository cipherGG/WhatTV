package com.gg.whattv.fragment;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.gg.whattv.R;
import com.gg.whattv.bean.BasketNBA;
import com.gg.whattv.bean.BasketTeam;
import com.gg.whattv.engine.Basket;
import com.gg.whattv.utils.PicassoUtils;

import java.util.ArrayList;
import java.util.List;

public class BasketNBAFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private String mParam2;

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerAdapter recyclerAdapter;
    private List<BasketNBA.Result.A.AA> combat;
    private List<BasketTeam.Result.A> team;

    public BasketNBAFragment() {
    }

    public static BasketNBAFragment newInstance(int param1, String param2) {
        BasketNBAFragment fragment = new BasketNBAFragment();
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
        View view = inflater.inflate(R.layout.fragment_basket_nba, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_basket_nba);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_fragment_basket_nba);

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
                if (mParam1 <= 1) {
                    combat = Basket.getCombat(mParam1);
                } else {
                    team = Basket.getTeam(mParam2);
                }

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
            combat = new ArrayList<>();
            team = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            size = (mParam1 <= 1 ? combat.size() : team.size());

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
                View view = inflater.inflate(R.layout.item_fragment_basket_nba, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                if (mParam1 <= 1) {
                    BasketNBA.Result.A.AA aa = combat.get(position);

                    ((ItemViewHolder) holder).player1.setText(aa.player1);
                    ((ItemViewHolder) holder).score.setText(aa.score);
                    ((ItemViewHolder) holder).player2.setText(aa.player2);
                    ((ItemViewHolder) holder).time.setText(aa.time);
                    PicassoUtils.loadImage(getContext(), aa.player1logobig, ((ItemViewHolder) holder).player1log);
                    PicassoUtils.loadImage(getContext(), aa.player2logobig, ((ItemViewHolder) holder).player2log);

                } else {
                    BasketTeam.Result.A a = team.get(position);

                    ((ItemViewHolder) holder).player1.setText(a.player1);
                    ((ItemViewHolder) holder).score.setText(a.score);
                    ((ItemViewHolder) holder).player2.setText(a.player2);
                    ((ItemViewHolder) holder).time.setText(a.m_time);
                    PicassoUtils.loadImage(getContext(), a.player1logo, ((ItemViewHolder) holder).player1log);
                    PicassoUtils.loadImage(getContext(), a.player2logo, ((ItemViewHolder) holder).player2log);
                }
            }
        }

        private class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            BasketNBA.Result.A.AA aa;
            BasketTeam.Result.A a;
            TextView player1;
            TextView player2;
            TextView score;
            TextView time;
            ImageView player1log;
            ImageView player2log;

            public ItemViewHolder(View itemView) {
                super(itemView);
                player1 = (TextView) itemView.findViewById(R.id.tv_item_fragment_basket_nba_player1);
                score = (TextView) itemView.findViewById(R.id.tv_item_fragment_basket_nba_score);
                player2 = (TextView) itemView.findViewById(R.id.tv_item_fragment_basket_nba_player2);
                time = (TextView) itemView.findViewById(R.id.tv_item_fragment_basket_nba_time);
                player1log = (ImageView) itemView.findViewById(R.id.iv_item_fragment_basket_nba_player1);
                player2log = (ImageView) itemView.findViewById(R.id.iv_item_fragment_basket_nba_player2);
            }
        }
    }

}
