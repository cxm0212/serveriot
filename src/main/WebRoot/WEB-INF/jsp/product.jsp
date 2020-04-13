<%--
  Created by IntelliJ IDEA.
  User: www
  Date: 2020/3/23
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE>
<html lang="zh-CN">
<head>
    <title>产品</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/demo.css">


    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/common.js"></script>


    <script>
        function formatter1(value,row,index) {
            var a = '<a href="#" onclick="findDevice(this)">查看所有设备</a>';
            return a;
        }
        function findDevice(record){
            var rowIndex = getRowIndex(record);
            var rowdata = $("#td_product").datagrid("getRows")[rowIndex];

            parent.$("#iframe").attr("src","device.jsp?productKey="+rowdata.productKey);
        }

        /**
         * 加载时获取所有产品并存缓存
         */
        $.get("/findproductlist",{},function(data){
            sessionStorage.setItem("productlist",JSON.stringify(data.rows));
            $("#td_product").datagrid({data:data})
        },"json")
    </script>
</head>
<body style="margin: 0;padding: 0;">
<div class="easyui-tabs" style="width:99%;height:99%;">
    <div title="所有产品" style="padding:10px;">
        <div style="overflow: auto;">
            <table id="td_product" class="easyui-datagrid table" title="所有产品"
                   data-options="singleSelect:true,collapsible:true,
                   fitColumns:true,nowrap:false,rownumbers:true"
                   style="width: 98%;">
                <thead>
                <tr>
                    <th data-options="field:'productKey',width:80,align:'center'">productKey</th>
                    <th data-options="field:'productName',width:80,align:'center'">产品名称</th>
                    <th data-options="field:'deviceCount',width:100,align:'center'">设备数量</th>
                    <th data-options="field:'gmtCreate',width:100,align:'center',formatter:dataformatter">创建时间</th>
                    <th data-options="field:'cz',width:100,align:'center',formatter:formatter1">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

</div>
</body>