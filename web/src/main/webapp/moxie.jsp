<%--
  Created by IntelliJ IDEA.
  User: thinkpad
  Date: 2017/10/25
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript" src="<%=basePath%>plupload/js/moxie.js"></script>
<script type="text/javascript">
    var fileInput = new moxie.file.FileInput({
        browse_button: 'file-picker', // or document.getElementById('file-picker')
        accept: [
            {title: "Images", extensions: "jpg,gif,png"} // accept only images
        ]
    });

    fileInput.onchange = function(e) {
        // do something to files array
        console.info(e.target.files); // or this.files or fileInput.files
    };

    fileInput.init(); // initialize
</script>

<div id="container">
    <a id="file-picker" href="javascript:;">Browse...</a>
</div>
</body>
</html>