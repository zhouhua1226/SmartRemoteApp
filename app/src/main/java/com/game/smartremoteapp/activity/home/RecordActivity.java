package com.game.smartremoteapp.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.adapter.GameCurrencyAdapter;
import com.game.smartremoteapp.adapter.RecordAdapter;
import com.game.smartremoteapp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/26.
 */
public class RecordActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.recode_recyclerview)
    RecyclerView recodeRecyclerview;

    private RecordAdapter recordAdapter;
    private List<String> list;

    //我的主娃娃记录
    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initList();
        recordAdapter=new RecordAdapter(this,list);
        recodeRecyclerview.setAdapter(recordAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recodeRecyclerview.setLayoutManager(linearLayoutManager);
        recodeRecyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


    }

    private void initList(){
        list=new ArrayList<String>();
        for (int i=0;i<10;i++){
            list.add(""+i);

        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }
}
