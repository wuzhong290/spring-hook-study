http://localhost:28080/modifyArgs?a=1&b=2
Content-Type:application/x-www-form-urlencoded
q:d
d:d

request_uri:/api_order?a=1&b=2&sign=1111111111111POST
[GET ] key:b v:2
[GET ] key:sign v:1111111111111
[GET ] key:a v:1
[post ]:{"d":"d","q":"d"}


http://localhost:28080/modifyArgs?a=1&b=2
Content-Type:application/json
{"age":111}

request_uri:/api_order?a=1&b=2&sign=1111111111111POST
[GET ] key:b v:2
[GET ] key:sign v:1111111111111
[GET ] key:a v:1
[post ]:{"{\"age\":111}":true}
