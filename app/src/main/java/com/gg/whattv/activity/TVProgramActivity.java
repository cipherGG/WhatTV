package com.gg.whattv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gg.whattv.R;
import com.gg.whattv.bean.TVChannel;
import com.gg.whattv.engine.TV;
import com.gg.whattv.utils.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_tvprogram)
public class TVProgramActivity extends BaseActivity {
    @ViewInject(R.id.tb_activity_tv_program)
    private Toolbar toolbar;
    @ViewInject(R.id.fab_activity_tv_program)
    private FloatingActionButton fab;
    @ViewInject(R.id.rv_activity_tv_program)
    private RecyclerView recyclerView;
    private String date = "";
    private String code;
    private RecyclerAdapter recyclerAdapter;
    private List<TVChannel.Program> programs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        updateItems();

        initListener();
    }

    //resetting date
    @Override
    protected void onPause() {
        super.onPause();
        date = "";
    }

    private void initView() {
        Intent intent = getIntent();
        String channelName = intent.getStringExtra("channelName");
        code = intent.getStringExtra("code");

        toolbar.setTitle(channelName);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showDaterPicker(TVProgramActivity.this, new DialogUtils.DateSetCallBack() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Snackbar.make(recyclerView, year + "年" + month + "月" + day + "日",
                                Snackbar.LENGTH_SHORT).show();

                        Calendar c = Calendar.getInstance();
                        c.set(year, month - 1, day);
                        Date date = c.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        //set TVProgramActivity.date
                        TVProgramActivity.this.date = sdf.format(date);

                        updateItems();
                    }
                });
            }
        });
    }

    private void updateItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                programs = TV.getTVProgram(code, date);

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
            programs = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            size = programs.size();

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
                View view = inflater.inflate(R.layout.item_activity_tv_program, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                TVChannel.Program program = programs.get(position);

                ((ItemViewHolder) holder).name.setText(program.pName);
                ((ItemViewHolder) holder).date.setText(program.time);
            }
        }

        private class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            TextView date;

            public ItemViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.tv_item_activity_tv_program_name);
                date = (TextView) itemView.findViewById(R.id.tv_item_activity_tv_program_date);
            }
        }
    }

}
