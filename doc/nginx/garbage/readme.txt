openresty-1.13.6.2-win64\lua\post.lua

location /printpost {
   content_by_lua_file lua/post.lua;
}
用于计算签名，参考例子：https://blog.csdn.net/xiejunna/article/details/70738853
