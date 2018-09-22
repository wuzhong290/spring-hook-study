--
-- Created by IntelliJ IDEA.
-- User: wuzhong
-- Date: 2018/9/22
-- Time: 23:41
-- To change this template use File | Settings | File Templates.
--
local function wait()
    --单位秒
    ngx.sleep(1)
end
local redis_util = require("redisutil")
local redis = redis_util:new()
local connect = redis:getConnect()
local uri = ngx.var.uri -- 获取当前请求的uri
local uriKey = "req:uri:"..uri
--redis.call('expire',KEYS[1],KEYS[2]) 单位秒
local res, err = connect:eval("local res, err = redis.call('INCR',KEYS[1]) if res == 1 then local resexpire, err = redis.call('expire',KEYS[1],KEYS[2]) end return (res)",2,uriKey,1)
while (res > 10)
do
    local twait, err = ngx.thread.spawn(wait)
    ok, threadres = ngx.thread.wait(twait)
    if not ok then
        ngx_log(ngx_ERR, "wait sleep error: ", err)
        break;
    end
    res, err = connect:eval("local res, err = redis.call('INCR',KEYS[1]) if res == 1 then local resexpire, err = redis.call('expire',KEYS[1],KEYS[2]) end return (res)",2,uriKey,1)
end
redis:close(connect)