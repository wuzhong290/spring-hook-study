location /redis {
    content_by_lua_file lua/redis.lua;
}