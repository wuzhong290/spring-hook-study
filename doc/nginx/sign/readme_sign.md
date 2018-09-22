http://localhost:28080/getSign?AccessKey=access&home=world&name=hello&work=java&timestamp=now&nonce=random

{
    "sign_str": "AccessKey=access&f=f&home=world&name=hello&nonce=random&timestamp=now&work=java&SecretKey=access",
    "sign": "b716a0c9aa142b98c669a3a5d55b793b"
}

{
    "sign_str": "AccessKey=access&home=world&name=hello&nonce=random&timestamp=now&work=java&SecretKey=accessdddddddddddddddddddddddddddddd",
    "sign": "a4aa1bfec9d3fb6cbb22abbc8af3dd80"
}

代码说明：
location /getSign {
    content_by_lua_file lua/getSign.lua;
}
代码功能：
获取签名
通过AccessKey找到SecretKey，并进行签名



可以重设参数，但是这种方式容易丢参数
ngx.req.set_uri_args(ngx.var.args .. "&sign=" .. sign)