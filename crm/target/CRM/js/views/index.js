$(".easyui-tree").tree({
    onClick:function(node){
        addTab(node.text,node.id);
    }
});

function addTab(title,url){
    if ($('.easyui-tabs').tabs('exists', title)) {
        $('.easyui-tabs').tabs('select',title)
    } else {
        var content = '<iframe src='+url+' style="height: 100%;width: 100%;" frameborder=0></iframe>';
        $('.easyui-tabs').tabs('add',{
            title:title,
            closable:true,
            content:content
        });
    }
}