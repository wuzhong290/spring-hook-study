local redis_util=require("redisutil")
local redis=redis_util:new()
redis:cmd("SET", "SET_TEST","111111111111111");
redis:cmd("SETEX", "SETEX_TEST", 600, "111111111111111");
local value=redis:cmd("GET", "SET_TEST");
ngx.say("SET_TEST:", value)
redis:cmd("INCR", "qg_INCR")
redis:cmd("MSET", "MSET_TEST1","MSET_TEST1","MSET_TEST2","MSET_TEST2");
local values= redis:cmd("MGET", "MSET_TEST1", "MSET_TEST33", "MSET_TEST2");
for key,v in pairs(values) do
	ngx.say("MGET:", key,"=",v)
end
 


