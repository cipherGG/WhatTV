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
import android.widget.ImageView;
import android.widget.TextView;

import com.gg.whattv.R;
import com.gg.whattv.activity.MovieVideoActivity;
import com.gg.whattv.bean.MovieLocal;
import com.gg.whattv.engine.Movie;
import com.gg.whattv.utils.PicassoUtils;
import com.gg.whattv.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private List<MovieLocal.Result.A.AA> movies;

    public MovieFragment() {
    }

    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_movie);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_fragment_movie);

        recyclerAdapter = new RecyclerAdapter();

        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerAdapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                loadData();
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

                loadData();
            }
        });
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String string = PreferenceUtils.getString(getContext(), "city", "北京");

                movies = Movie.getMovieLocal(string);

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
            movies = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            size = movies.size();

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
                View view = inflater.inflate(R.layout.item_fragment_movie, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                MovieLocal.Result.A.AA aa = movies.get(position);

                ((ItemViewHolder) holder).aa = aa;
                ((ItemViewHolder) holder).name.setText(aa.tvTitle);
                ((ItemViewHolder) holder).date.setText(aa.playDate.data);
                PicassoUtils.loadImage(getContext(), aa.iconaddress, ((ItemViewHolder) holder).pic);

            }
        }

        private class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            MovieLocal.Result.A.AA aa;
            ImageView pic;
            TextView name;
            TextView date;

            public ItemViewHolder(View itemView) {
                super(itemView);
                pic = (ImageView) itemView.findViewById(R.id.iv_item_fragment_movie);
                name = (TextView) itemView.findViewById(R.id.tv_item_fragment_movie_name);
                date = (TextView) itemView.findViewById(R.id.tv_item_fragment_movie_date);

                itemView.findViewById(R.id.cd_item_fragment_movie).setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieVideoActivity.class);

                intent.putExtra("q", aa.tvTitle);

                getContext().startActivity(intent);
            }
        }
    }

}
