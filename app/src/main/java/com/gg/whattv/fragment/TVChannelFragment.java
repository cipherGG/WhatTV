package com.gg.whattv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gg.whattv.R;
import com.gg.whattv.activity.TVProgramActivity;
import com.gg.whattv.bean.TVCategory;
import com.gg.whattv.engine.TV;

import java.util.ArrayList;
import java.util.List;

public class TVChannelFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private RecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;

    private int mTab;
    private List<TVCategory.Channel> channels;

    public TVChannelFragment() {
    }

    public static TVChannelFragment newInstance(int pId) {
        TVChannelFragment fragment = new TVChannelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, pId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTab = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvchannel, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_tv_channel);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_fragment_tv_channel);

        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
        //above method made the interface no SwipeRefresh style
        //must call the .post(),the post work in main thread
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
                channels = TV.getTVChannels(mTab);

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
            channels = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            size = channels.size();

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
                View view = inflater.inflate(R.layout.item_fragment_tv_channel, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                TVCategory.Channel channel = channels.get(position);

                ((ItemViewHolder) holder).channel = channel;
                ((ItemViewHolder) holder).name.setText(channel.channelName);
                //((ItemViewHolder) holder).link.setText(channel.url);
            }
        }


        private class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TVCategory.Channel channel;
            TextView name;
            //TextView link;

            public ItemViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.tv_item_fragment_tv_channel_name);
                //link = (TextView) itemView.findViewById(R.id.tv_item_fragment_tv_channel_link);

                itemView.findViewById(R.id.cd_item_fragment_tv_channel).setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TVProgramActivity.class);
                intent.putExtra("code", channel.rel);
                intent.putExtra("channelName", channel.channelName);
                getContext().startActivity(intent);
            }
        }
    }


}
