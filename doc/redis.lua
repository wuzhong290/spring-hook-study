local redis_util=require("redisutil")
local redis1=redis_util:new()
local value=redis1:cmd("get", "h1234");
ngx.say("h1234:", value)
redis1:cmd("INCR", "qg_INCR")


