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
import android.widget.ImageView;
import android.widget.TextView;

import com.gg.whattv.R;
import com.gg.whattv.bean.MovieVideo;
import com.gg.whattv.engine.Movie;
import com.gg.whattv.utils.PicassoUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_movie_video)
public class MovieVideoActivity extends BaseActivity {
    @ViewInject(R.id.tb_activity_movie_video)
    private Toolbar toolbar;
    @ViewInject(R.id.iv_activity_movie_video_top)
    private ImageView top;
    @ViewInject(R.id.tv_activity_movie_video_title)
    private TextView title;
    @ViewInject(R.id.tv_activity_movie_video_tag)
    private TextView tag;
    @ViewInject(R.id.tv_activity_movie_video_act)
    private TextView act;
    @ViewInject(R.id.tv_activity_movie_video_rating)
    private TextView rating;
    @ViewInject(R.id.tv_activity_movie_video_area)
    private TextView area;
    @ViewInject(R.id.tv_activity_movie_video_dir)
    private TextView dir;
    @ViewInject(R.id.tv_activity_movie_video_desc)
    private TextView desc;
    @ViewInject(R.id.rv_activity_movie_video_bottom)
    private RecyclerView recyclerView;

    private String q;
    private RecyclerAdapter recyclerAdapter;
    private MovieVideo.Result detail;
    List<MovieVideo.Result.B> video_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        loadData();
    }

    private void initView() {
        Intent intent = getIntent();
        q = intent.getStringExtra("q");

        toolbar.setTitle(q);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setAdapter(recyclerAdapter);
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                detail = Movie.getVideoDetail(q);

                if (detail == null) {
                    mHandler.sendEmptyMessage(1);

                } else {
                    if (detail.video_rec != null) {
                        video_rec = detail.video_rec;
                    }

                    mHandler.sendEmptyMessage(2);
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    title.setText("没有找到相关信息");
                    tag.setVisibility(View.GONE);
                    act.setVisibility(View.GONE);
                    rating.setVisibility(View.GONE);
                    area.setVisibility(View.GONE);
                    dir.setVisibility(View.GONE);
                    desc.setVisibility(View.GONE);

                    break;
                case 2:
                    PicassoUtils.loadImage(MovieVideoActivity.this, detail.cover, top);
                    title.setText(detail.title);
                    tag.setText(detail.tag);
                    act.setText(detail.act);
                    rating.setText(detail.rating);
                    area.setText(detail.area);
                    dir.setText(detail.dir);
                    desc.setText(detail.desc);

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
            video_rec = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            size = video_rec.size();

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
                View view = inflater.inflate(R.layout.item_activity_movie_video, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                MovieVideo.Result.B b = video_rec.get(position);

                PicassoUtils.loadImage(getContext(), b.cover, ((ItemViewHolder) holder).cover);
                ((ItemViewHolder) holder).title.setText(b.title);
            }
        }

        private class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView cover;
            TextView title;

            public ItemViewHolder(View itemView) {
                super(itemView);
                cover = (ImageView) itemView.findViewById(R.id.iv_item_activity_movie_video);
                title = (TextView) itemView.findViewById(R.id.tv_item_activity_movie_video);

                itemView.findViewById(R.id.cd_item_activity_movie_video).setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                q = title.getText().toString();

                Intent intent = new Intent(MovieVideoActivity.this, MovieVideoActivity.class);
                intent.putExtra("q", q);

                MovieVideoActivity.this.startActivity(intent);
            }
        }
    }
}
