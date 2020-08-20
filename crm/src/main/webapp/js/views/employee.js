$(function () {
    //1. 抽取变量:每次都是从上而下的搜索组件，重复的组件会多次搜索，抽取变量一次搜索，减少访问页面的次数
    //2. 所有方法统一管理
    //3. 统一处理按钮监听
    var empDatagrid , empEditAndRemove , empDialog , empForm , empFormId , empKeyWord;
    empDatagrid = $("#emp_datagrid");
    empEditAndRemove = $("[iconCls='icon-edit'],[iconCls='icon-remove']");
    empDialog = $("#emp_dialog");
    empForm = $("#emp_form");
    empFormId = $("#emp_form [name='id']");
    empKeyWord = $("[name='keyWord']");

    //分页
    empDatagrid.datagrid({
        fit:true,
        url:'employee_list',
        fitColumns:true,
        rownumbers:true,
        singleSelect : true,
        pagination:true,
        pageList : [ 1, 5, 10, 20 ],
        toolbar:'#emp_datagrid_tb',
        columns:[[
            /* 宽度随便设置 */
            {field:'username',align:'center',title:"用户名",width:100},
            {field:'realname',align:'center',title:"真实姓名",width:100},
            {field:'tel',align:'center',title:'电话',width:100},
            {field:'email',align:'center',title:'邮箱',width:100},
            {field:'dept',align:'center',title:'部门',width:100,formatter:deptFormatter},
            {field:'inputTime',align:'center',title:'入职时间',width:100},
            {field:'state',align:'center',title:'状态',width:100,formatter:stateFormatter},
            {field:'admin',align:'center',title:'是否超级管理员',width:100,formatter:adminFormatter}
        ]],
        onClickRow:function (rowIndex,rowData) {
            //在职
            if (rowData.state){
                empEditAndRemove.linkbutton("enable");
            }else {
                //离职，不需要对离职员工进行编辑与删除操作，设置按钮不可用
                empEditAndRemove.linkbutton("disable");

            }
        }
    });

    //对话框
    empDialog.dialog({
        width:300,
        height:320,
        buttons:"#emp_dialog_tb",
        closed:true
    });

    //对所有携带了data-cmd的按钮进行监听
    $("a[data-cmd]").on("click",function () {
        //获取data-cmd携带的值
        var cmd = $(this).data("cmd");
        if (cmd)
            cmdObj[cmd]();
    });

    /*---- cmdObj一定要放在加载页面的函数中，否则无法使用抽取出来的变量 ----*/
    var cmdObj = {
        //新增员工
        add:function () {
            //打开对话框
            empDialog.dialog("open");
            //设置对话框标题
            empDialog.dialog("setTitle","新增");
            //情空表单中的内容
            empForm.form("clear");
        },

        //保存员工
        save:function () {
            //使用id字段是否为空的方法进行判断，新增时，隐含域中id为空，更新时，自动注入id，id非空
            var idVal = empFormId.val();
            var url;
            if (idVal){
                url = "employee_update"
            }else {
                url="employee_save";
            }
            var values = $("#cbRole").combobox("getValues");
            //发送异步请求
            empForm.form("submit",{
                url:url,
                onSubmit:function(param){
                    for (var i = 0; i < values.length; i++) {
                        param["roles["+i+"].id"] = values[i];
                    }
                },
                success:function (data) {
                    data = $.parseJSON(data);
                    if (data.success){
                        $.messager.alert("温馨提示",data.msg,"info",function () {
                            //关闭对话框
                            empDialog.dialog("close");
                            //刷新页面
                            empDatagrid.datagrid("load");
                        });
                    }else {
                        $.messager.alert("温馨提示",data.msg,"info");
                    }
                }
            });
            return false;
        },

        cancel:function () {
            empDialog.dialog("close");
        },

        edit:function () {
            //获取选中的数据
            var rowData = empDatagrid.datagrid("getSelected");
            if (rowData){
                //打开对话框
                empDialog.dialog("open");
                //设置对话框标题
                empDialog.dialog("setTitle","编辑");
                //情空表单中的内容
                empForm.form("clear");
                //对话框传入选中的数据
                // console.log(rowData);
                //特殊属性的处理：为rowData添加新字段dept.id
                if (rowData.dept)
                    rowData["dept.id"] = rowData.dept.id;
                //发送同步请求，获取到角色后再继续执行
                var html = $.ajax({
                    url: "role_queryByEid?eid="+rowData.id,
                    async: false
                }).responseText;
                html = $.parseJSON(html);
                $("#cbRole").combobox("setValues",html);
                empDialog.form("load",rowData);   //基于同名匹配规则，下拉框要求传入是dept.id
            }else {
                $.messager.alert("温馨提示","请选择一条需要编辑的数据","info");
            }
        },

        remove:function () {
            //获取选中的数据
            var rowData = empDatagrid.datagrid("getSelected");
            if (rowData){
                $.messager.confirm("温馨提示","您确定要删除这条数据吗？",function (yes) {
                    if (yes){
                        $.get("employee_remove?id="+rowData.id,
                            function (data) {
                                if (data.success){
                                    $.messager.alert("温馨提示",data.msg,"info",function () {
                                        //刷新数据表格
                                        empDatagrid.datagrid("reload");
                                    });
                                }else {
                                    $.messager.alert("温馨提示",data.msg,"info");
                                }
                            },"json")
                    }
                });
            }else {
                $.messager.alert("温馨提示","请选择一条需要删除的数据","info");
            }
        },

        refresh:function () {
            empDatagrid.datagrid("reload");
        },

        searchBtn:function () {
            var value = empKeyWord.val();
            empDatagrid.datagrid("load",{
                keyWord:value
            })
        }
    };

});

/*---- 以下三个格式化方法不可以包含在cmdObj中，否则由于不携带data-cmd数据，无法调用 ----*/
/**
 * 将部门字段显示格式化的方法
 * @param value    原来的值
 * @param record    对应的当前记录
 * @param index    索引
 */
function deptFormatter(value,record,index) {
    return value?value.name:value;
}
function stateFormatter (value,record,index) {
    return value?'<span style="color: green">正常</span>':'<span style="color: red">离职</span>';
}
function adminFormatter (value,record,index) {
    return value?'是':'否';
}

