https://blog.csdn.net/fenglvming/article/details/51996406
http://localhost:28080/api_limit?a=1&b=2
限流的目的是在大促或者流量突增期间，我们的后端服务假设某个接口能够扛住的的QPS为10000，
这时候同时有20000个请求进来，经过限流模块，会先放10000个请求，其余的请求会阻塞一段时间。
不简单粗暴的返回404，让客户端重试，同时又能起到流量销峰的作用。
http://localhost:28080/api_brush?a=1&b=2
防刷的目的是为了防止有些IP来爬去我们的网页，获取我们的价格等信息。
不像普通的搜索引擎，这种爬去行为我们经过统计最高每秒300次访问，
平均每秒266次访问。