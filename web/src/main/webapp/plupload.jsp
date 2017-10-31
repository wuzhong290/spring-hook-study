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
<!-- 配置界面上的css -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plupload/js/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>

<!-- 国际化中文支持 -->
<script type="text/javascript" src="<%=basePath%>plupload/js/i18n/zh_CN.js"></script>

<script type="text/javascript">
    // Initialize the widget when the DOM is ready
    $(function() {
        // Setup html5 version
        function plupload(){
            $("#uploader").pluploadQueue({
                // General settings
                runtimes : 'flash,html5,gears,browserplus,silverlight,html4',
                url : "<%=basePath%>upload/plupload",
                //unique_names: true,
                chunk_size : '1mb',
                //rename : true,
                dragdrop: true,
                filters : {
                    // Maximum file size
                    max_file_size : '10mb',
                    // Specify what files to browse for
                    mime_types: [
                        {title : "Image files", extensions : "jpg,gif,png"},
                        {title : "Zip files", extensions : "zip"}
                    ]
                },
                // Resize images on clientside if we can
                resize: {
                    width : 200,
                    height : 200,
                    quality : 90,
                    crop: false
                    // crop to exact dimensions
                },             // Flash settings
                flash_swf_url : '<%=basePath%>plupload/js/Moxie.swf',
                // Silverlight settings
                silverlight_xap_url : '<%=basePath%>plupload/js/Moxie.xap' ,
                // 参数
                multipart_params: {'user': 'Rocky', 'time': '2012-06-12'}

            });
        }

        plupload();

        $('#Reload').click(function(){
            plupload();
        });
    });

</script>

<div style="width:750px; margin:0px auto;">
    <div id="uploader">
        <p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
    </div>
    <input value="继续上传" id="Reload" type="button">
</div>
</body>
</html>
