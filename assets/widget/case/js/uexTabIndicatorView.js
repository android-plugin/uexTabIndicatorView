var btn = 0;
if (UNIT_TEST) {
    var uexTabIndicatorViewCase = {
         "open":function(){
             var containerId="123";
             uexWindow.createPluginViewContainer({
                 id:containerId,
                 x:0,
                 y:500,
                 w:1080,
                 h:1920,
                 titles:["头条","精选","轻松一刻","娱乐","热点","体育"]
             });
             uexTabIndicatorView.test(containerId);
             var param = {
                x:0,
                y:150,
                w:1080,
                h:150,
                textColor:"#F44336",
                bgColor:"#FFFFFF",
                dividerColor:"#D32F2F",
                indicatorColor:"#D32F2F",
                containerId:containerId
             };
             uexTabIndicatorView.open(param);
             UNIT_TEST.assert(true);
             setTimeout(function(){
                uexTabIndicatorView.close();
             },10000);
        }
    };
    UNIT_TEST.addCase("TabIndicatorView", uexTabIndicatorViewCase);
}