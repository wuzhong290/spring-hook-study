local redis_util=require("redisutil")
local redis1=redis_util:new()
redis1:cmd("SET", "SET_TEST","111111111111111");
redis1:cmd("SETEX", "SETEX_TEST", 600, "111111111111111");
local value=redis1:cmd("GET", "SET_TEST");
ngx.say("SET_TEST:", value)
redis1:cmd("INCR", "qg_INCR")
redis1:cmd("MSET", "MSET_TEST1","MSET_TEST1","MSET_TEST2","MSET_TEST2");
local values= redis1:cmd("MGET", "MSET_TEST1", "MSET_TEST33", "MSET_TEST2"); 
for key,v in pairs(values) do
	ngx.say("MGET:", key,"=",v)
end
 


