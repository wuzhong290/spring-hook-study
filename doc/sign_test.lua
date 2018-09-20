local cjson = require("cjson")
local resty_md5 = require "resty.md5"
local md5 = resty_md5:new()
--������(�ܴ���content-type=multipart/form-data�ı�)��
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
      --�ж��Ƿ���multipart/form-data���͵ı�
    if string.sub(receive_headers["content-type"],1,20) == "multipart/form-data;" then   
      is_have_file_param = true
      content_type = receive_headers["content-type"]
      --body_data���Ƿ���httpЭ��������壬������ͨ���ַ���
      body_data = ngx.req.get_body_data()

      --�������size����nginx�������client_body_buffer_size����ᵼ�������屻���嵽������ʱ�ļ��client_body_buffer_sizeĬ����8k����16k
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
      --ȷ��ȡ�������������
      if not error_code then
	        local boundary = "--" .. string.sub(receive_headers["content-type"],31)
	        local body_data_table = explode(tostring(body_data),boundary)
	        local first_string = table.remove(body_data_table,1)
	        local last_string = table.remove(body_data_table)
	        for i,v in ipairs(body_data_table) do        
              local start_pos,end_pos,capture,capture2 = string.find(v,'Content%-Disposition: form%-data; name="(.+)"; filename="(.*)"')																				                                       
              --��ͨ����
              if not start_pos then
                  local t = explode(v,"\r\n\r\n")
                  local temp_param_name = string.sub(t[1],41,-2)
                  local temp_param_value = string.sub(t[2],1,-3)
                  args[temp_param_name] = temp_param_value                                                
              else
               --�ļ����͵Ĳ�����capture�ǲ������ƣ�capture2���ļ���                            
                      file_args[capture] = capture2
                      table.insert(new_body_data,v)
              end
	        end
	        table.insert(new_body_data,1,first_string)
	        table.insert(new_body_data,last_string)
	        --ȥ��app_key,app_secret�ȼ�����������ҵ�񼶱�Ĳ��������ڲ���API
	        body_data = table.concat(new_body_data,boundary)--body_data���Ƿ���httpЭ��������壬������ͨ���ַ���
      end
    else
    	local post_args = ngx.req.get_post_args();
    	if args and post_args  then
				for k,v in pairs(post_args) do
				    args[k] = v
				end    		
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
			--ngx.say(str.to_hex(result)) 
			args["sign"] = str.to_hex(result)
			ngx.req.set_uri_args(args)  
			ngx.req.set_uri("/test", true)   
  end  
end
init_form_args()