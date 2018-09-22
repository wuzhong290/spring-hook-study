location /redis {
    content_by_lua_file lua/redis.lua;
}


SET_TEST:111111111111111
MGET:1=MSET_TEST1
MGET:2=null
MGET:3=MSET_TEST2