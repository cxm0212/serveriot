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
    <title>设备</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/demo.css">


    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/common.js"></script>

    <script>
        function formatter1(value,row,index) {
            var a = '<a href="#" onclick="findDeviceInfo(this)">详情</a>';
            return a;
        }
        function findDeviceInfo(record){
            var rowIndex = getRowIndex(record);
            var rowdata = $("#td_devicelist").datagrid("getRows")[rowIndex];

            console.log(rowdata);

            addTab("设备详细信息",rowdata);

        }

        function addTab(title, rowData){
            if ($('#tabsdiv').tabs('exists', title)){
                $('#tabsdiv').tabs('select', title);
            } else {
                var content = '<div style="overflow: auto;padding: 10px;">' +
                    '            <table class="easyui-datagrid table" title="设备详细信息"' +
                    '                   data-options="singleSelect:true,collapsible:true,' +
                    'url:\'getdevicedetail?iotId='+rowData.iotId+'&productKey='+rowData.productKey+'&deviceName='+rowData.deviceName+'\',' +
                    'method:\'get\',fitColumns:true,nowrap:false"' +
                    '                   style="width: 98%;">' +
                    '                <thead>' +
                    '                <tr>' +
                    '                    <th data-options="field:\'iotId\',width:100,align:\'center\'">设备ID</th>' +
                    '                    <th data-options="field:\'deviceName\',width:100,align:\'center\'">设备名称</th>' +
                    '                    <th data-options="field:\'deviceSecret\',width:100,align:\'center\'">设备Secret</th>' +
                    '                    <th data-options="field:\'gmtCreate\',width:100,align:\'center\'">创建时间</th>' +
                    '                    <th data-options="field:\'ipAddress\',width:80,align:\'center\'">所在服务器</th>' +
                    '                    <th data-options="field:\'productKey\',width:80,align:\'center\'">productKey</th>' +
                    '                    <th data-options="field:\'productName\',width:80,align:\'center\'">产品名称</th>' +
                    '                </tr>' +
                    '                </thead>' +
                    '            </table>' +
                    '        </div>'
                $('#tabsdiv').tabs('add',{
                    title:title,
                    content:content,
                    closable:true
                });
            }
        }

        function deviceStateFormatter(value){
            return deviceState.getState(value);
        }

    </script>
    <style>
        table tr td div {
            word-break:break-all;
        }
        /*table隔行换色*/
        .table tr:nth-child(even){
            background: #abcbca;
        }
    </style>
</head>

<body style="margin: 0;padding: 0;">
<div class="easyui-tabs" style="width:99%;height:99%;" id="tabsdiv">
    <div title="所有设备"  style="padding:10px;">
        <div>
            <label>productKey : </label>
            <select class="easyui-combobox" id="productkeycombox" style="width:400px;"></select>
        </div>

        <br>
        <br>
        <div style="overflow: auto;">
            <table id="td_devicelist" class="easyui-datagrid table" title="产品下所有设备"
                   data-options="singleSelect:true,collapsible:true,url:'finddevicelistbyproduct?productKey=<%=request.getParameter("productKey")%>',
                   method:'get',fitColumns:true,nowrap:false,rownumbers:true"
                   style="width: 98%;">
                <thead>
                <tr>
                    <th data-options="field:'iotId',width:100,align:'center'">设备ID</th>
                    <th data-options="field:'deviceName',width:100,align:'center'">设备名称</th>
                    <th data-options="field:'deviceSecret',width:100,align:'center'">设备Secret</th>
                    <th data-options="field:'gmtCreate',width:100,align:'center',formatter:dataformatter">创建时间</th>
                    <%--<th data-options="field:'gmtOnline',width:80,align:'center'">最近上线时间</th>--%>
                    <%--<th data-options="field:'ipAddress',width:80,align:'center'">所在服务器</th>--%>
                    <th data-options="field:'productKey',width:80,align:'center'">productKey</th>
                    <%--<th data-options="field:'productName',width:80,align:'center'">产品名称</th>--%>
                    <th data-options="field:'deviceStatus',width:80,align:'center',formatter:deviceStateFormatter">设备状态</th>
                    <th data-options="field:'cz',width:80,align:'center',formatter:formatter1">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <%--<div title="设备基本信息" closable="true" style="padding:10px;">--%>
        <%--<div style="overflow: auto;">--%>
            <%--<table class="easyui-datagrid table" title="设备详细信息"--%>
                   <%--data-options="singleSelect:true,collapsible:true,url:'getdevicedetail',method:'get',fitColumns:true,nowrap:false"--%>
                   <%--style="width: 98%;">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th data-options="field:'iotId',width:100,align:'center'">设备ID</th>--%>
                    <%--<th data-options="field:'deviceName',width:100,align:'center'">设备名称</th>--%>
                    <%--<th data-options="field:'deviceSecret',width:100,align:'center'">设备Secret</th>--%>
                    <%--<th data-options="field:'gmtCreate',width:100,align:'center'">创建时间</th>--%>
                    <%--<th data-options="field:'ipAddress',width:80,align:'center'">所在服务器</th>--%>
                    <%--<th data-options="field:'productKey',width:80,align:'center'">productKey</th>--%>
                    <%--<th data-options="field:'productName',width:80,align:'center'">产品名称</th>--%>
                    <%--<th data-options="field:'status',width:80,align:'center'">设备状态</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
            <%--</table>--%>
        <%--</div>--%>
    <%--</div>--%>

</div>
</body>

<script>
    var productCombox = [];
    var option = {};
    var productlist = JSON.parse(sessionStorage.getItem("productlist"));
    $.each(productlist,function (i) {
        option = {};
        option["id"]=productlist[i].productKey;
        option["text"]=productlist[i].productKey+"  ("+productlist[i].productName+")";
        if(productlist[i].productKey == "<%=request.getParameter("productKey")%>"){
            option["selected"]=true;
        }
        productCombox[productCombox.length] = option;
    })
    $('#productkeycombox').combobox({
        data : productCombox,
        valueField:'id',
        textField:'text'
    });
    $('#productkeycombox').combobox({
        onChange : function () {
            var prokey = $('#productkeycombox').combobox("getValue");
            console.log(prokey)
            $("#td_devicelist").datagrid({
                url:'finddevicelistbyproduct?productKey='+prokey
            })
        }
    })

</script>