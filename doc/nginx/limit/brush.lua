--
-- Created by IntelliJ IDEA.
-- User: wuzhong
-- Date: 2018/9/23
-- Time: 1:05
-- To change this template use File | Settings | File Templates.
--
local redis_util = require("redisutil")
local redis = redis_util:new()
local clientIP = ngx.req.get_headers()["X-Real-IP"]
if clientIP == nil then
    clientIP = ngx.req.get_headers()["x_forwarded_for"]
end
if clientIP == nil then
    clientIP = ngx.var.remote_addr
end
ngx_log(ngx_ERR, "wait sleep error: ", err)
local incrKey = "user:"..clientIP..":freq"
local blockKey = "user:"..clientIP..":block"
local is_block,err = redis:cmd("GET", blockKey);-- check if ip is blocked
if tonumber(is_block) == 1 then
    ngx.exit(ngx.HTTP_FORBIDDEN)
end

res, err = redis:cmd("INCR", incrKey)

if res == 1 then
    res, err = redis:cmd("expire", incrKey, 1)
end

if res > 200 then
    res, err = redis:cmd("SETEX", blockKey, 600, 1);
end
