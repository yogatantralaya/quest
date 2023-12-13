<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Student Details</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
<div id="header">
<h2>Student Details</h2>
</div>
</div>
<div id="container">
<div id="content">
<input type="button" value="Add new student"
onclick="window.location.href='add-student-form.jsp' ; return false;"
class="add-student-button">
<table border ="1">
<tr>
<th>First Name</th>
<th>Last Name</th>
<th>Id</th>
<th>Email</th>
<th>Action</th>
</tr>
<c:forEach var="tempStudent" items="${Student_List}">
<c:url var="tempUpdateLink" value="StudentDatabaseServlet">
<c:param name="command" value="LOAD"/>
<c:param name="id" value="${tempStudent.id}"/>
</c:url>
<c:url var="tempDeleteLink" value="StudentDatabaseServlet">
<c:param name="command" value="DELETE"/>
<c:param name="id" value="${tempStudent.id}"/>
</c:url>
<tr>
<td>${tempStudent.firstName}</td>
<td>${tempStudent.lastName}</td>
<td>${tempStudent.id}</td>
<td>${tempStudent.email}</td>
<td><a href="${tempUpdateLink}">Update</a> | <a href="${tempDeleteLink}"
onclick="if(!(confirm('Do you want to delete this user?'))) return false">Delete</a></td>
</tr>
</c:forEach>
</table>
</div>
</div>
</body>
</html>