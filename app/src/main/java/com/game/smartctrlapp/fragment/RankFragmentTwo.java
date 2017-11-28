package com.game.smartctrlapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.game.smartctrlapp.R;
import com.game.smartctrlapp.adapter.ListRankAdapter;
import com.game.smartctrlapp.base.BaseFragment;
import com.game.smartctrlapp.bean.ListRankBean;
import com.game.smartctrlapp.bean.Result;
import com.game.smartctrlapp.bean.UserBean;
import com.game.smartctrlapp.model.http.HttpManager;
import com.game.smartctrlapp.model.http.RequestSubscriber;
import com.game.smartctrlapp.utils.Utils;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by hongxiu on 2017/11/23.
 */
public class RankFragmentTwo extends BaseFragment {
    private static final String TAG = "RankFragmentTwo-";
    @BindView(R.id.ranktwo_recyclerbiew)
    RecyclerView ranktwoRecyclerbiew;
    private ListRankAdapter listRankAdapter;
    private List<UserBean> list=new ArrayList<>();
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranktwo;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initlist();
        initData();
        OnClick();

    }

    private void initData() {
        listRankAdapter=new ListRankAdapter(getContext(),list);
        Utils.showLogE(TAG+"看看？？",list.size()+"");
        ranktwoRecyclerbiew.setAdapter(listRankAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        ranktwoRecyclerbiew.setLayoutManager(linearLayoutManager);
        ranktwoRecyclerbiew.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }
    private void initlist(){
        HttpManager.getInstance().getListRank(new RequestSubscriber<Result<ListRankBean>>() {
            @Override
            public void _onSuccess(Result<ListRankBean> listRankBeanResult) {
                list =listRankBeanResult.getData().getAppUser();
                listRankAdapter.notify(list);
            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "RankFragmentTwo::::" + e.getMessage());
            }
        });

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
