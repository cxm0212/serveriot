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
    <title>index</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/themes/demo.css">


    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>

    <style>
        /*重点代码*/
        /*默认*/
        .tree-icon, .tree-folder,.tree-folder-open{
            background:none !important;
            display: none;
        }
    </style>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:90%;">
    <div region="west" split="true" title="菜单" style="width:150px;">
        <ul class="easyui-tree" >
            <li>
                <span>设备管理</span>
                <ul>
                    <li>
                        <span><a href="product.jsp" target="iframe">产品</a></span>
                        <%--<ul>--%>
                            <%--<li><span>File 11</span></li>--%>
                            <%--<li><span>File 12</span></li>--%>
                            <%--<li><span>File 13</span></li>--%>
                        <%--</ul>--%>
                    </li>
                    <li><span><a href="device.jsp" target="iframe">设备</a></span></li>
                </ul>
            </li>
        </ul>
    </div>
    <div id="content" region="center"  style="padding:5px;width: 70%;">
        <iframe name="iframe" id="iframe" src="product.jsp" scrolling="false" width="100%" height="100%" frameborder="0"></iframe>
    </div>
</div>



</body>

</html>
