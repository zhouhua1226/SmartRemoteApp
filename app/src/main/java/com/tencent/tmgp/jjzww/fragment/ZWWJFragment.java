package com.tencent.tmgp.jjzww.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.ctrl.view.CtrlActivity;
import com.tencent.tmgp.jjzww.activity.home.MainActivity;
import com.tencent.tmgp.jjzww.adapter.ZWWAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.PlayBackBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.bean.ZwwRoomBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.SPUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.EmptyLayout;
import com.tencent.tmgp.jjzww.view.SpaceItemDecoration;
import com.tencent.tmgp.jjzww.view.TextViewAd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by hongxiu on 2017/9/25.
 */
public class ZWWJFragment extends BaseFragment {
    private static final String TAG = "ZWWJFragment";
    @BindView(R.id.zww_recyclerview)
    RecyclerView zwwRecyclerview;
    @BindView(R.id.zww_emptylayout)
    EmptyLayout zwwEmptylayout;
    Unbinder unbinder;
    @BindView(R.id.rolling_tv)
    TextViewAd rollingTv;
    Unbinder unbinder1;

    private List<ZwwRoomBean> roomBeens = new ArrayList<>();
    private ZWWAdapter zwwAdapter;
    private String sessionId;
//    private String phone;
    private EmptyLayout.OnClickReTryListener onClickReTryListener;
    private List<String> list1=new ArrayList<>();
    private List<VideoBackBean> playBackBeanList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zww;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        onClick();
        getUserList();

    }

    private void initText(List<String> listRun) {
        if(listRun.size()>0)
            rollingTv.setFocusable(true);
        rollingTv.setmTexts(listRun);
        rollingTv.setFrontColor(getResources().getColor(R.color.zww_broadcast_text));
        rollingTv.setBackColor(getResources().getColor(R.color.run_text_bg));
        rollingTv.setmDuration(1000);
        rollingTv.setmInterval(1000);
    }

    private void getUserList(){
        HttpManager.getInstance().getUserList(new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> listRankBeanResult) {
                playBackBeanList=listRankBeanResult.getData().getPlayback();
                Log.e("跑马灯","list0="+playBackBeanList.size()+playBackBeanList.get(0).getDOLLNAME());
                for(int i=0;i<playBackBeanList.size();i++){
                    String s="恭喜"+playBackBeanList.get(i).getUSERNAME()+"用户抓中一个"+playBackBeanList.get(i).getDOLLNAME();
                    Log.e("跑马灯","item="+s);
                    list1.add(s);
                }
                Log.e("跑马灯","list1="+list1.size());
                initText(list1);
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


    private void initData() {
        Utils.showLogE(TAG, "afterCreate:::::>>>>" + roomBeens.size());
        dismissEmptyLayout();
        zwwAdapter = new ZWWAdapter(getActivity(), roomBeens);
        zwwRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        zwwRecyclerview.addItemDecoration(new SpaceItemDecoration(15));
        zwwRecyclerview.setAdapter(zwwAdapter);
        if (onClickReTryListener != null) {
            zwwEmptylayout.setOnClickReTryListener(onClickReTryListener);
        }
    }

    private void onClick() {
        zwwAdapter.setmOnItemClickListener(onItemClickListener);
    }

    public void notifyAdapter(List<ZwwRoomBean> rooms) {
        roomBeens = rooms;
        zwwAdapter.notify(roomBeens);
    }

    public void setOnClickEmptyListener(EmptyLayout.OnClickReTryListener o) {
        this.onClickReTryListener = o;
    }

    public void showError() {
        zwwEmptylayout.showEmpty();
    }

    public void showLoading() {
        zwwEmptylayout.showLoading();
    }

    public void dismissEmptyLayout() {
        zwwEmptylayout.dismiss();
    }

    public void setSessionId(String id) {
        this.sessionId = id;
//        phone = (String) SPUtils.get(getContext(), UserUtils.SP_TAG_PHONE, "");
        UserUtils.setNettyInfo(sessionId, UserUtils.UserName, "");
        UserUtils.doNettyConnect();
    }

    public ZWWAdapter.OnItemClickListener onItemClickListener =
            new ZWWAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if ((roomBeens.size() > 0) && (!Utils.isEmpty(sessionId))) {
                        String room_id = roomBeens.get(position).getDOLL_ID();
                        boolean room_status = false;
//                        UserUtils.setNettyInfo(sessionId, phone, room_id);
                        UserUtils.setNettyInfo(sessionId, UserUtils.NickName, room_id);
                        if (roomBeens.get(position).getDOLL_STATE().equals("0")) {
                            room_status = true;
                        } else if (roomBeens.get(position).getDOLL_STATE().equals("1")) {
                            room_status = false;
                        }
                        enterNext(roomBeens.get(position).getDOLL_NAME(),
                                roomBeens.get(position).getCAMERA_NAME_01(),
                                room_status, String.valueOf(roomBeens.get(position).getDOLL_GOLD()),
                                roomBeens.get(position).getDOLL_ID());
                    }
                }
            };

    private void enterNext(String name, String camera, boolean status, String gold,String id) {
        Intent intent = new Intent(getActivity(), CtrlActivity.class);
        intent.putExtra(Utils.TAG_ROOM_NAME, name);
        intent.putExtra(Utils.TAG_CAMERA_NAME, camera);
        intent.putExtra(Utils.TAG_ROOM_STATUS, status);
        intent.putExtra(Utils.TAG_DOLL_GOLD, gold);
        intent.putExtra(Utils.TAG_DOLL_Id,id);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        getUserList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
