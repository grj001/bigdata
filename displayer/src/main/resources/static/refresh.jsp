<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.println(basePath);
%>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>host data</title>
</head>

<body>
<script type="text/javascript">

    function clock() {
        console.log("start")
        $.ajax({
            url: 'data',
            success: function (data) {
                console.log(data)
                document.getElementById("clock").value = data;
            }
        })

    }

    window.setInterval("clock()", 1);
</script>

<button onclick="int=window.clearInterval(int)">停止</button>
</body>
</html>