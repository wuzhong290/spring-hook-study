--ngx.header.content_type = "application/json"
local request_method = ngx.var.request_method
ngx.say('hello ngx_lua!!!!',request_method);
ngx.say('cookie_JSESSIONID',ngx.var.cookie_JSESSIONID);
