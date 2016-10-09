package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.os.Handler;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.tbinterface.GuideInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦涵 on 2016/5/21.
 */
public class GuidePresenter extends BaseActivityPresenter<GuideInterface>
        implements GuideInterface {
    private GuideInterface guideInterface;
    private Activity activity;
    public Handler hd = null;
    private BNRoutePlanNode mBNRoutePlanNode;

    public GuidePresenter(GuideInterface guideInterface, Activity activity, BNRoutePlanNode mBNRoutePlanNode){
        this.guideInterface = guideInterface;
        this.activity = activity;
        this.mBNRoutePlanNode = mBNRoutePlanNode;
    }

    private void addCustomizedLayerItems() {
        List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<BNRouteGuideManager.CustomizedLayerItem>();
        BNRouteGuideManager.CustomizedLayerItem item1 = null;
        if (mBNRoutePlanNode != null) {
            item1 = new BNRouteGuideManager.CustomizedLayerItem(mBNRoutePlanNode.getLongitude(), mBNRoutePlanNode.getLatitude(),
                    mBNRoutePlanNode.getCoordinateType(), activity.getResources().getDrawable(R.drawable.ic_launcher),
                    BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
            items.add(item1);

            BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        }
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }

    public void createHandler(final double longitude, final double latitude, final String end) {
        if (hd == null) {
            hd = new Handler(activity.getMainLooper()) {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == AppConstants.MSG_SHOW) {
                        addCustomizedLayerItems();
                    } else if (msg.what == AppConstants.MSG_HIDE) {
                        BNRouteGuideManager.getInstance().showCustomizedLayer(false);
                    } else if (msg.what == AppConstants.MSG_RESET_NODE) {
                        //目的地
                        BNRouteGuideManager.getInstance().resetEndNodeInNavi(
                                new BNRoutePlanNode(longitude, latitude, end, null, BNRoutePlanNode.CoordinateType.BD09LL));
                    }
                }
            };
        }
    }
}
