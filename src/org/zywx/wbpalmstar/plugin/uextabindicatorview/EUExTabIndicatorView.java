package org.zywx.wbpalmstar.plugin.uextabindicatorview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.zywx.wbpalmstar.engine.DataHelper;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.EBrowserWindow;
import org.zywx.wbpalmstar.engine.container.ContainerViewPager;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.plugin.uextabindicatorview.vo.OpenVO;

public class EUExTabIndicatorView extends EUExBase {

    private static final String BUNDLE_DATA = "data";
    SmartTabLayout tabLayout ;

    public EUExTabIndicatorView(Context context, EBrowserView eBrowserView) {
        super(context, eBrowserView);
    }

    @Override
    protected boolean clean() {
        return false;
    }
    

    @Override
    public void onHandleMessage(Message message) {
        if(message == null){
            return;
        }
        Bundle bundle=message.getData();
        switch (message.what) {

        default:
                super.onHandleMessage(message);
        }
    }

    public void open(String[] params) {
        if (params == null || params.length < 1) {
            errorCallback(0, 0, "error params!");
            return;
        }
        String json = params[0];
        OpenVO openVO= DataHelper.gson.fromJson(json,OpenVO.class);
//        SlidingTabLayout tabLayout=new SlidingTabLayout(mContext);
         tabLayout=new SmartTabLayout(mContext);
        ContainerViewPager containerViewPager=null;
        EBrowserWindow mWindow = mBrwView.getBrowserWindow();
        int count = mWindow.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = mWindow.getChildAt(i);
            if (view instanceof ContainerViewPager) {
                ContainerViewPager pager = (ContainerViewPager) view;
                if (openVO.containerId.equals(pager.getContainerVO().getId())) {
                    containerViewPager=pager;
                }
            }
        }
        tabLayout.setBackgroundColor(Color.parseColor(openVO.bgColor));
        tabLayout.setDefaultTabTextColor(Color.parseColor(openVO.textColor));
        tabLayout.setDividerColors(Color.parseColor(openVO.dividerColor));
        tabLayout.setSelectedIndicatorColors(Color.parseColor(openVO.indicatorColor));
        tabLayout.setViewPager(containerViewPager);
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(openVO.w,openVO.h);
        lp.topMargin=openVO.y;
        lp.leftMargin=openVO.x;
        mBrwView.addViewToCurrentWindow(tabLayout,lp);
    }


    public void test(String[] params){
        for (int i=0;i<=5;i++){
            TextView textView=new TextView(mContext);
            textView.setText("第"+i+"页");
            textView.setTextSize(30);
            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT);
            addSubviewToContainer(textView,i,params[0],layoutParams);
        }
    }

    public void close(String[] params) {
        if (tabLayout!=null){
            tabLayout.setViewPager(null);
            removeViewFromCurrentWindow(tabLayout);
        }
    }

    private void callBackPluginJs(String methodName, String jsonData){
        String js = SCRIPT_HEADER + "if(" + methodName + "){"
                + methodName + "('" + jsonData + "');}";
        onCallback(js);
    }

}
