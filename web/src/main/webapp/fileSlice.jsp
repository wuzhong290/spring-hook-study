<%--
  Created by IntelliJ IDEA.
  User: zhong.wu
  Date: 2017/10/27
  Time: 11:09
  To change this template use File | Settings | File Templates.
  https://github.com/satazor/js-spark-md5.git
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>upload</title>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>
<body>
<input type="file" name="file" id="file">
<button id="upload" onClick="upload()">upload</button>
<script type="text/javascript">
    var bytesPerPiece = 1024 * 1024; // 每个文件切片大小定为1MB .
    var totalPieces;
    //发送请求
    function upload() {
        var blob = document.getElementById("file").files[0];
        var start = 0;
        var end;
        var index = 0;
        var filesize = blob.size;
        var filename = blob.name;

        //计算文件切片总数
        totalPieces = Math.ceil(filesize / bytesPerPiece);
        while(start < filesize) {
            end = start + bytesPerPiece;
            if(end > filesize) {
                end = filesize;
            }

            var chunk = blob.slice(start,end);//切割文件
            var formData = new FormData();
            formData.append("file", chunk, filename);
            $.ajax({
                url: '<%=basePath%>upload/pluploadtest',
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false,
            }).done(function(res){

            }).fail(function(res) {

            });
            start = end;
            index++;
        }
    }
</script>
</body>
</html>