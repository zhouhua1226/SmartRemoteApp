package com.game.smartremoteapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.activity.ctrl.view.CtrlActivity;
import com.game.smartremoteapp.adapter.ZWWAdapter;
import com.game.smartremoteapp.base.BaseFragment;
import com.game.smartremoteapp.bean.ZwwRoomBean;
import com.game.smartremoteapp.utils.SPUtils;
import com.game.smartremoteapp.utils.UserUtils;
import com.game.smartremoteapp.utils.Utils;
import com.game.smartremoteapp.view.EmptyLayout;
import com.game.smartremoteapp.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by hongxiu on 2017/9/25.
 */
public class ZWWJFragment extends BaseFragment {
    private static final String TAG = "ZWWJFragment";
    @BindView(R.id.zww_recyclerview)
    RecyclerView zwwRecyclerview;
    @BindView(R.id.zww_emptylayout)
    EmptyLayout zwwEmptylayout;

    private List<ZwwRoomBean> roomBeens = new ArrayList<>();
    private ZWWAdapter zwwAdapter;
    private String sessionId;
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zww;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        onClick();
    }

    private void initData() {
        Utils.showLogE(TAG, "afterCreate:::::>>>>" + roomBeens.size());
        zwwAdapter = new ZWWAdapter(getActivity(), roomBeens);
        zwwRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        zwwRecyclerview.addItemDecoration(new SpaceItemDecoration(15));
        zwwRecyclerview.setAdapter(zwwAdapter);
    }

    private void onClick() {
        zwwAdapter.setmOnItemClickListener(onItemClickListener);
    }

    public void notifyAdapter(List<ZwwRoomBean> rooms) {
        roomBeens = rooms;
        zwwAdapter.notify(roomBeens);
    }

    public void setOnClickEmptyListener(EmptyLayout.OnClickReTryListener onClickEmptyListener) {
        zwwEmptylayout.setOnClickReTryListener(onClickEmptyListener);
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
        phone = (String) SPUtils.get(getContext(), UserUtils.SP_TAG_PHONE, "");
        UserUtils.setNettyInfo(sessionId, phone, "");
        UserUtils.doNettyConnect();
    }

    public ZWWAdapter.OnItemClickListener onItemClickListener =
            new ZWWAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if ((roomBeens.size() > 0) && (!Utils.isEmpty(sessionId))) {
                        String room_id = roomBeens.get(position).getDOLL_ID();
                        boolean room_status = false;
                        UserUtils.setNettyInfo(sessionId, phone, room_id);
                        if (roomBeens.get(position).getDOLL_STATE().equals("0")) {
                            room_status = true;
                        } else if (roomBeens.get(position).getDOLL_STATE().equals("1")) {
                            room_status = false;
                        }
                        enterNext(roomBeens.get(position).getDOLL_NAME(),
                                roomBeens.get(position).getCAMERA_NAME_01(),
                                room_status);
                    }
                }
            };

    private void enterNext(String name, String camera, boolean status) {
        Intent intent = new Intent(getActivity(), CtrlActivity.class);
        intent.putExtra(Utils.TAG_ROOM_NAME, name);
        intent.putExtra(Utils.TAG_CAMERA_NAME, camera);
        intent.putExtra(Utils.TAG_ROOM_STATUS, status);
        startActivity(intent);
    }
}
