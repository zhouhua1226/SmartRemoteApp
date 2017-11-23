package com.game.smartremoteapp.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.adapter.GameCurrencyAdapter;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/26.
 */
public class GameCurrencyActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.game_tv)
    TextView gameTv;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<String> list;
    private GameCurrencyAdapter gameCurrencyAdapter;

    //我的游戏币
    @Override
    protected int getLayoutId() {
        return R.layout.activity_gamecurrency;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        gameTv.setText(UserUtils.UserBalance);
        initlist();
        gameCurrencyAdapter=new GameCurrencyAdapter(this,list);
        recyclerview.setAdapter(gameCurrencyAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


    }

    private void initlist(){
        list=new ArrayList<String>();
        for (int i=0;i<10;i++){
            list.add(i+"");
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
