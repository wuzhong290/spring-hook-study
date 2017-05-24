<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Spring MVC Demo</title>
</head>
 
<body>
    <h2>All Students</h2>
 
    <table border="1">
        <tr>
            <th>Student Id</th>
            <th>Student Name</th>
        </tr>
        <c:forEach items="${students}" var="student">
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
            </tr>
        </c:forEach>
    </table>
 
</body>
</html>
