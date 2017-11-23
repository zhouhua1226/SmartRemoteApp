package com.game.smartremoteapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.adapter.ListRankAdapter;
import com.game.smartremoteapp.base.BaseFragment;
import com.game.smartremoteapp.view.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hongxiu on 2017/9/27.
 */
public class ListRankFragment extends BaseFragment {
    @BindView(R.id.listrank_recycrview)
    RecyclerView listrankRecycrview;
    Unbinder unbinder;
    private ListRankAdapter listRankAdapter;
    private List<String>list;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listrank;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initlist();
        initData();
        OnClick();

    }

    private void initData() {
//        listRankAdapter=new ListRankAdapter(getContext(),list);
//        listrankRecycrview.setAdapter(listRankAdapter);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//        listrankRecycrview.setLayoutManager(linearLayoutManager);
//        listrankRecycrview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
}

    private void initlist(){
        list= new ArrayList<String>();
        for (int i=0;i<10;i++){

            list.add(""+i);
        }
    }

    public void OnClick(){
        listRankAdapter.setOnItemClickListener(new ListRankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position) {
//                MyToast.getToast(getContext(),"我要查看"+position).show();
            }

        });
    }

}
