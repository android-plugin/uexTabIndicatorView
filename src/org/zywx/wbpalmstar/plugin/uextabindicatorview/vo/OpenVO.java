package org.zywx.wbpalmstar.plugin.uextabindicatorview.vo;

import java.util.List;

/**
 * Created by ylt on 2016/10/9.
 */

public class OpenVO {

    public int x;
    public int y;
    public int w;
    public int h;
    public String containerId;
    public String multiPopName;
    public int bindMode=0;//0:与容器绑定，1：与multiPop绑定，默认为0
    public List<String> titles;
    public String textColor="#2196F3";
    public String bgColor="white";
    public String dividerColor="#00000000";
    public String indicatorColor="#1976D2";
    public int textSize;


}
