<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1" session="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Cookie cookie = new Cookie("JSESSIONID", "111111111111111");
cookie.setMaxAge(24 * 60 * 60); //24??
response.addCookie(cookie);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="iptv/iptv.core-1.0.js"></script>
    <script type="text/javascript" src="iptv/iptv.Delegate-1.0.js"></script>
    <script type="text/javascript" src="iptv/iptv.Delegate.date-1.0.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
      <form action="<%=path%>/upload/upload" method="post" enctype="multipart/form-data">
          <input type="file" name="file" />
          <input type="submit" value="Submit" />
      </form>
      <script type="text/javascript">
          console.info(IPTV().debug);
      </script>
  </body>
</html>
