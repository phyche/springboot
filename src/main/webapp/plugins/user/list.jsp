<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8" />
    <title>Test20</title>
</head>
<style>
    td {
        vertical-align: middle;
        text-align: center;
        font-size: 16px;
    }
    th {
        font-weight:bold;
        vertical-align: middle;
        text-align: center;
        font-size: 16px;
    }
</style>
<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">访问User列表</h3>
    </div>
    <div class="panel-body">
        <table>
            <thead>
                <tr>
                    <th width="200px">姓名</th>
                    <th width="200px">头像</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td><img style="width: 150px;height: 120px" src="${user.pic}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script th:src="@{jquery-1.10.2.js}" type="text/javascript"></script>
<script th:inline="javascript">
</script>
</body>
</html>