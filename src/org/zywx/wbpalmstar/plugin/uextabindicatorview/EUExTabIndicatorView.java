package org.zywx.wbpalmstar.plugin.uextabindicatorview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.zywx.wbpalmstar.base.BDebug;
import org.zywx.wbpalmstar.engine.DataHelper;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.EBrowserWindow;
import org.zywx.wbpalmstar.engine.container.ContainerAdapter;
import org.zywx.wbpalmstar.engine.container.ContainerViewPager;
import org.zywx.wbpalmstar.engine.multipop.MultiPopAdapter;
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

    public boolean open(String[] params) {
        if (params == null || params.length < 1) {
            return false;
        }
        String json = params[0];
        OpenVO openVO= DataHelper.gson.fromJson(json,OpenVO.class);
//        SlidingTabLayout tabLayout=new SlidingTabLayout(mContext);
        tabLayout=new SmartTabLayout(mContext);
        tabLayout.setBackgroundColor(Color.parseColor(openVO.bgColor));
        tabLayout.setDefaultTabTextColor(Color.parseColor(openVO.textColor));
        tabLayout.setDividerColors(Color.parseColor(openVO.dividerColor));
        tabLayout.setSelectedIndicatorColors(Color.parseColor(openVO.indicatorColor));
        if (openVO.bindMode==1){//绑定到multipop
            ViewPager pager=mBrwView.getBrowserWindow().getMultiPopPagerMap().get(openVO.multiPopName);
            if (pager==null){
                BDebug.e("multiPop is null...");
                return false;
            }
            MultiPopAdapter adapter= (MultiPopAdapter) pager.getAdapter();
            adapter.setMultiPopTitles(openVO.titles);
            tabLayout.setViewPager(pager);
        }else{
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
            if (openVO.titles!=null) {
                ((ContainerAdapter) containerViewPager.getAdapter()).setContainerTitles(openVO.titles);
            }
            tabLayout.setViewPager(containerViewPager);
        }
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(openVO.w,openVO.h);
        lp.topMargin=openVO.y;
        lp.leftMargin=openVO.x;
        mBrwView.addViewToCurrentWindow(tabLayout,lp);
        return true;
   }


    public void test(String[] params){
        for (int i=0;i<=5;i++){
            TextView textView=new TextView(mContext);
            textView.setText("第"+i+"页");
            textView.setTextSize(30);
            textView.setBackgroundColor(Color.BLUE);
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

    public void setVisible(String[] params){
        if (tabLayout!=null){
            tabLayout.setVisibility(params[0].equals("0")?View.GONE:View.VISIBLE);
        }
    }

}
