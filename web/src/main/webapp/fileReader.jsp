<%--
  Created by IntelliJ IDEA.
  User: zhong.wu
  Date: 2017/10/27
  Time: 11:09
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
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    var result=document.getElementById("result");
    var file=document.getElementById("file");

    //判断浏览器是否支持FileReader接口
    if(typeof FileReader == 'undefined'){
        result.InnerHTML="<p>你的浏览器不支持FileReader接口！</p>";
        //使选择控件不可操作
        file.setAttribute("disabled","disabled");
    }

    function readAsDataURL(){
        //检验是否为图像文件
        var file = document.getElementById("file").files[0];
        if(!/image\/\w+/.test(file.type)){
            alert("看清楚，这个需要图片！");
            return false;
        }
        var reader = new FileReader();
        //将文件以Data URL形式读入页面
        reader.readAsDataURL(file);
        reader.onload=function(e){
            var result=document.getElementById("result");
            //显示文件
            result.innerHTML='<img src="' + this.result +'" alt="" />';
        }
    }

    function readAsBinaryString(){
        var file = document.getElementById("file").files[0];
        var reader = new FileReader();
        //将文件以二进制形式读入页面
        reader.readAsBinaryString(file);
        reader.onload=function(f){
            var result=document.getElementById("result");
            //显示文件
            result.innerHTML=this.result;
        }
    }

    function readAsText(){
        var file = document.getElementById("file").files[0];
        var reader = new FileReader();
        //将文件以文本形式读入页面
        reader.readAsText(file);
        reader.onload=function(f){
            var result=document.getElementById("result");
            //显示文件
            result.innerHTML=this.result;
        }
    }
</script>
<p>
    <label>请选择一个文件：</label>
    <input type="file" id="file" multiple="multiple"/>
    <input type="button" value="读取图像" onclick="readAsDataURL()" />
    <input type="button" value="读取二进制数据" onclick="readAsBinaryString()" />
    <input type="button" value="读取文本文件" onclick="readAsText()" />
</p>
<div id="result" name="result"></div>
</body>
</html>
