local request_method = ngx.var.request_method
ngx.say('hello ngx_lua!!!!',request_method);