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
    <script type="text/javascript" src="<%=basePath%>js/spark-md5.js" ></script>
</head>

<body>
<input type="file" id="file" />
<div id="box"></div>
<button id="cal" type="button" onclick="calculate()">计算md5</button>
</body>

<script>

    function calculate(){
        var fileReader = new FileReader(),
                box=document.getElementById('box');
                blobSlice = File.prototype.mozSlice || File.prototype.webkitSlice || File.prototype.slice,
                file = document.getElementById("file").files[0],
                chunkSize = 20000,
                // read in chunks of 2MB
                console.log("file size", file.size);
                chunks = Math.ceil(file.size / chunkSize),
                currentChunk = 0,
                spark = new SparkMD5();

        fileReader.onload = function(e) {
            console.log("read chunk nr", currentChunk + 1, "of", chunks, "result", e.target.result);
            spark.appendBinary(e.target.result); // append binary string
            currentChunk++;

            if (currentChunk < chunks) {
                loadNext();
            }
            else {
                console.log("finished loading");
                box.innerText='MD5 hash:'+spark.end();
                console.info("computed hash", spark.end()); // compute hash
            }
        };

        function loadNext() {
            var start = currentChunk * chunkSize,
                    end = start + chunkSize >= file.size ? file.size : start + chunkSize;

            fileReader.readAsBinaryString(blobSlice.call(file, start, end));
        };

        loadNext();
    }

</script>
</html>
