local cjson = require("cjson")
local resty_md5 = require "resty.md5"
local md5 = resty_md5:new()
--升级版(能处理content-type=multipart/form-data的表单)：
local function explode ( _str,seperator )
        local pos, arr = 0, {}
                for st, sp in function() return string.find( _str, seperator, pos, true ) end do
                        table.insert( arr, string.sub( _str, pos, st-1 ) )
                        pos = sp + 1
                end
        table.insert( arr, string.sub( _str, pos ) )
        return arr
end
local args = {}
local file_args = {}
local is_have_file_param = false
local function init_form_args()
  local receive_headers = ngx.req.get_headers()
  local request_method = ngx.var.request_method
  if "GET" == request_method then
		args = ngx.req.get_uri_args()
  elseif "POST" == request_method then
  	args = ngx.req.get_uri_args()
    ngx.req.read_body()
      --判断是否是multipart/form-data类型的表单
    if string.sub(receive_headers["content-type"],1,20) == "multipart/form-data;" then   
      is_have_file_param = true
      content_type = receive_headers["content-type"]
      --body_data可是符合http协议的请求体，不是普通的字符串
      body_data = ngx.req.get_body_data()

      --请求体的size大于nginx配置里的client_body_buffer_size，则会导致请求体被缓冲到磁盘临时文件里，client_body_buffer_size默认是8k或者16k
      if not body_data then
          local datafile = ngx.req.get_body_file()
          if not datafile then
            error_code = 1
            error_msg = "no request body found"
          else
            local fh, err = io.open(datafile, "r")
            if not fh then
                error_code = 2
                error_msg = "failed to open " .. tostring(datafile) .. "for reading: " .. tostring(err)
            else
                fh:seek("set")
                body_data = fh:read("*a")
                fh:close()
                if body_data == "" then
                        error_code = 3
                        error_msg = "request body is empty"
                end
            end
          end
      end
      local new_body_data = {}
      --确保取到请求体的数据
      if not error_code then
	        local boundary = "--" .. string.sub(receive_headers["content-type"],31)
	        local body_data_table = explode(tostring(body_data),boundary)
	        local first_string = table.remove(body_data_table,1)
	        local last_string = table.remove(body_data_table)
	        for i,v in ipairs(body_data_table) do        
              local start_pos,end_pos,capture,capture2 = string.find(v,'Content%-Disposition: form%-data; name="(.+)"; filename="(.*)"')																				                                       
              --普通参数
              if not start_pos then
                  local t = explode(v,"\r\n\r\n")
                  local temp_param_name = string.sub(t[1],41,-2)
                  local temp_param_value = string.sub(t[2],1,-3)
                  args[temp_param_name] = temp_param_value                                                
              else
               --文件类型的参数，capture是参数名称，capture2是文件名                            
                      file_args[capture] = capture2
                      table.insert(new_body_data,v)
              end
	        end
	        table.insert(new_body_data,1,first_string)
	        table.insert(new_body_data,last_string)
	        --去掉app_key,app_secret等几个参数，把业务级别的参数传给内部的API
	        body_data = table.concat(new_body_data,boundary)--body_data可是符合http协议的请求体，不是普通的字符串
      end
    else
    	local post_args = ngx.req.get_post_args();
    	if args and post_args  then
				for k,v in pairs(post_args) do
				    args[k] = v
				end    		
    	end
    end
    if args then
    		local keys = {}
    		local sign_str ="";
				for k,v in pairs(args) do
				    if v==true then
				    	sign_str =sign_str .. k;
				    else
				    	table.insert(keys,k)
				    end
				end      	
    	  --ngx.say(cjson.encode(args))
    	  table.sort(keys)
				for key,value in pairs(keys) do  
				    sign_str =sign_str .. value;
				    --ngx.say("key:", key,",value:", value) 
				end 
				--ngx.say("sign_str:", sign_str)
				local sign_str_base64 =ngx.encode_base64(sign_str)
				--ngx.say("sign_str_base64:", sign_str_base64)
				--ngx.say("sign_str:", ngx.decode_base64(sign_str_base64))       
				local ok = md5:update(sign_str)
				local result = md5:final()	
				local str = require "resty.string"			
				ngx.say(str.to_hex(result))      
    end
  end
end
--ngx.header.content_type = "application/json"
init_form_args()