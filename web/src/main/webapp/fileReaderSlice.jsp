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
<html>
<head>
</head>

<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">分段读取文件：</div>
        <div class="panel-body">
            <input type="file" id="file" />
            <br />
            <input type="button" value="中止" onclick="stop();" />&emsp;
            <input type="button" value="继续" onclick="containue();" />
            <br />
            <progress id="progressOne" max="100" value="0" style="width:400px;"></progress>
            <blockquote id="Status" style="word-break:break-all;"></blockquote>
        </div>
    </div>
</div>
</body>

<script>
    /*
     * 分段读取文件为blob ，并使用ajax上传到服务器
     * 使用Ajax方式提交上传数据文件大小应该有限值，最好500MB以内
     * 原因短时间过多的ajax请求，Asp.Net后台会崩溃获取上传的分块数据为空
     * 取代方式，长连接或WebSocket
     */
    var fileBox = document.getElementById('file');
    var reader = null;  //读取操作对象
    var step = 20000;//1024 * 1024 * 3.5;  //每次读取文件大小
    var cuLoaded = 0; //当前已经读取总数
    var file = null; //当前读取的文件对象
    var enableRead = true;//标识是否可以读取文件
    fileBox.onchange = function () {
        //获取文件对象
        file = this.files[0];
        var total = file.size;
        console.info("文件大小：" + file.size);
        var startTime = new Date();
        reader = new FileReader();
        //读取一段成功
        reader.onload = function (e) {
            //处理读取的结果
            var result = reader.result;
            var loaded = e.loaded;
            if (enableRead == false)
                return false;
            //将分段数据上传到服务器
            uploadFile(result, cuLoaded, function () {
                console.info('loaded:' + cuLoaded + '----current:' + loaded);
                //如果没有读完，继续
                cuLoaded += loaded;
                if (cuLoaded < total) {
                    readBlob(cuLoaded);
                } else {
                    console.log('总共用时：' + (new Date().getTime() - startTime.getTime()) / 1000);
                    cuLoaded = total;
                }
                //显示结果进度
                var percent = (cuLoaded / total) * 100;
                document.getElementById('Status').innerText = percent;
                document.getElementById('progressOne').value = percent;
            });
        }
        //开始读取
        readBlob(0);
        //关键代码上传到服务器
        function uploadFile(result, startIndex, onSuccess) {
            var blob = new Blob([result]);
            //提交到服务器
            var fd = new FormData();
            fd.append('file', blob);
            fd.append('filename', encodeURI(file.name));
            fd.append('loaded', startIndex);
            console.info('filename:' + encodeURI(file.name) + '----loaded:' + startIndex);
            var xhr = new XMLHttpRequest();
            xhr.open('post', '<%=basePath%>upload/pluploadtest', true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    if (onSuccess)
                        onSuccess();
                } else if (xhr.status == 500) {
                    //console.info('请求出错，' + xhr.responseText);
                    setTimeout(function () {
                        containue();
                    }, 1000);
                }
            }
            //开始发送
            xhr.send(fd);
        }
    }
    //指定开始位置，分块读取文件
    function readBlob(start) {
        //指定开始位置和结束位置读取文件
        var blob = file.slice(start, start + step);
        reader.readAsArrayBuffer(blob);
    }
    //中止
    function stop() {
        //中止读取操作
        console.info('中止，cuLoaded：' + cuLoaded);
        enableRead = false;
        reader.abort();
    }
    //继续
    function containue() {
        console.info('继续，cuLoaded：' + cuLoaded);
        enableRead = true;
        readBlob(cuLoaded);
    }
</script>
</html>
