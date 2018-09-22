--
-- Created by IntelliJ IDEA.
-- User: wuzhong
-- Date: 2018/9/22
-- Time: 20:25
-- To change this template use File | Settings | File Templates.
--
local cjson = require("cjson")
local resty_md5 = require "resty.md5"
local md5 = resty_md5:new()
local args = {}
local function init_form_args()
    local request_method = ngx.var.request_method
    if "GET" == request_method then
        args = ngx.req.get_uri_args()
    elseif "POST" == request_method then
        args = ngx.req.get_uri_args()
        ngx.req.read_body()
        local post_args = ngx.req.get_post_args();
        if args and post_args  then
            for k,v in pairs(post_args) do
                args[k] = v
            end
        end
    end
    if args then
        local keys = {}
        local req_body ="";
        local SecretKey = "";
        local sign_str = "";
        local sign = {};
        for k,v in pairs(args) do
            if v==true then
                req_body = req_body .. k;
            elseif k=='AccessKey' then
                table.insert(keys,k)
                SecretKey = v;
            else
            		table.insert(keys,k)    
            end
        end
        table.sort(keys)
        for key,value in pairs(keys) do
        		if string.len(sign_str) == 0 then
            	sign_str =value .. "=" .. args[value];
            else
            	sign_str =sign_str .. "&" .. value .. "=" .. args[value];
            end         		
        end
        sign_str = sign_str .. "&SecretKey=" .. SecretKey .. req_body
        md5:update(sign_str)
        local result = md5:final()
        local str = require "resty.string"
        sign["sign"] = str.to_hex(result)
        sign["sign_str"] = sign_str
        ngx.header.content_type = "application/json"
        ngx.say(cjson.encode(sign))
    end
end
init_form_args()
