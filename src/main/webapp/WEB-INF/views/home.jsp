<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
    <h1>a Spring Boot!</h1>
    <c:forEach items="${memberList}" var="member">
        <input type="text" value="${member.name}">
    </c:forEach>
 436562214
    12
    ${map}
    ${map.age}
    13
    ${testa[0].ha}
    <!-- <input type="text" id="1" name="memName">
    <input type="text" id="2" name="memName"> -->
</body>
</html>

<!-- 
<!DOCTYPE html>
<html lang="kor">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>hi</title>
</head>
<body>
    <h1>hi</h1>
</body>
</html> -->