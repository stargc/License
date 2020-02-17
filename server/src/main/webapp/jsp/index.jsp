<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2019/11/8
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>证书下载</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/lib/bootstrap/dist/css/bootstrap.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/lib/bootstrap-datepicker/dist/css/bootstrap-datepicker.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/lib/bootstrap-valid/dist/css/bootstrapValidator.css"/>">
    <style>
        body{
            padding: 0;margin: 0;background-color: #c4fcf4
        }
        form {
            width: 500px;margin: auto;position: absolute;top: 50%;left: 50%;
            transform: translate(-50%,-50%);padding: 5px;border: black 1px solid;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<form class="form-horizontal" role="form" method="post">
    <div class="form-group">
        <label class="col-sm-12" style="text-align: center">证书下载</label>
    </div>
    <div class="form-group">
        <label for="md5" class="col-sm-4 control-label">机器码:</label>
        <div class="col-sm-8">
            <input class="form-control" id="md5" placeholder="请输入机器码" name="md5"/>
        </div>
    </div>
    <div class="form-group">
        <label for="start" class="col-sm-4 control-label">生效开始时间:</label>
        <div class="col-sm-8">
            <input class="form-control" id="start" name="start"/>
        </div>
    </div>
    <div class="form-group">
        <label for="end" class="col-sm-4 control-label">生效结束时间:</label>
        <div class="col-sm-8">
            <input class="form-control" id="end" name="end"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-2">
            <input id="submit" type="button" value="提交" class="btn-primary btn">
        </div>
        <div class="col-sm-6">
            <button type="reset" class="btn btn-primary">重置</button>
        </div>
    </div>
</form>

<script type="text/javascript" src="<c:url value="/lib/jquery/jquery.js"/>"></script>
<script type="text/javascript" src="<c:url value="/lib/bootstrap/dist/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/lib/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/lib/bootstrap-datepicker/dist/locales/bootstrap-datepicker.zh-CN.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/lib/bootstrap-valid/dist/js/bootstrapValidator.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/index.js"/>"></script>
</body>
</html>