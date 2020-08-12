<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>客户关系管理系统-首页</title>
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-v1.9.0/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-v1.9.0/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-v1.9.0/demo/demo.css" />
    <script type="text/javascript" src="js/jquery-easyui-v1.9.0/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-v1.9.0/jquery.easyui.min.js"></script>
    <%
        pageContext.setAttribute("ctp",request.getContextPath());
    %>
</head>

<body>
    <div class="easyui-layout" fit="true" style="width:100%;height:100%;">
        <div data-options="region:'north'" style="height:100px;background: url('${ctp}/img/banner-pic.gif') no-repeat;background-size: cover">
            <h1>客户关系管理系统</h1>
        </div>

        <div data-options="region:'west'" class="easyui-accordion" style="width:300px;">
            <div title="菜单">
                <ul class="easyui-tree">
                    <li id="/index">
                        <span>员工管理</span>
                    </li>
                    <li>
                        <span>员工管理1</span>
                    </li>
                </ul>
            </div>
            <div title="帮助">
                <ul class="easyui-tree">
                    <li>
                        <span>员工管理2</span>
                    </li>
                    <li>
                        <span>员工管理3</span>
                    </li>
                </ul>
            </div>
        </div>
        <div data-options="region:'center'">
            <div class="easyui-tabs" style="height:100%">
                <div title="欢迎页" style="padding:10px" data-options="closable:true">
                    欢迎
                </div>
            </div>
        </div>
        <div data-options="region:'south',split:true" style="height:50px;background: url('${ctp}/img/banner-pic.gif') no-repeat;background-size: cover;text-align: center">
            版权所有
        </div>
    </div>
</body>
<script>

    $(".easyui-tree").tree({
        onClick:function(node){
            addTab(node.text,node.id);
        }
    });

    function addTab(title,url){
        if ($('.easyui-tabs').tabs('exists', title)) {
            $('.easyui-tabs').tabs('select',title)
        } else {
            var content = '<iframe src="http://localhost/${ctp}'+url+'" style="height: 100%;width: 100%"></iframe>';
            $('.easyui-tabs').tabs('add',{
                title:title,
                closable:true,
                content:content
            });
        }
    }
</script>

</html>