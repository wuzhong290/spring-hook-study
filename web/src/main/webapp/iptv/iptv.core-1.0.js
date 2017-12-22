/**
 * Created by thinkpad on 2017/12/12.
 */
(function( window, undefined ) {
var core_version = "1.0",
    class2type = {},
    core_toString = class2type.toString,
    document = window.document,
    location = window.location,
    IPTV=function() {
        // The IPTV object is actually just the init constructor 'enhanced'
        return new IPTV.fn.init();
    }
IPTV.fn = IPTV.prototype = {
    iptv: core_version,
    constructor: IPTV,
    init: function() {
        return this;
    },
    getElementById:function(id){
        if(typeof(id) =='undefined' || id=='' || id==undefined ||id ==null) return null;
        return document.getElementById(id);
    },
    debug:true,
    getBrowser:function(){
        var b3 = "";
        var b4 = navigator.appName;
        if (b4.indexOf("iPanel") != -1) {
            b3 = "iPanel";
        } else if (b4.indexOf("Microsoft") != -1) {
            b3 = "Miscrosoft";
        } else if (b4.indexOf("Google") != -1) {
            b3 = "Google";
        } else if (b4.indexOf("Netscape") != -1) {
            b3 = "Netscape";
        } else if (b4.indexOf("Opera") != -1) {
            b3 = "Opera";
        }
        return b3;
    },
    getBrowVersion:function(str)
    {
        var bl = false;
        if(this.isNull(str)) return bl ;
        var usa = navigator.userAgent;
        if(usa.indexOf(str) != -1)
        {
            bl = true;
        }
        return bl;
    },
    setCookie:function(name, value, timestr){
        var exp2 = new Date;
        var id = this.isnull(timestr)==false ? timestr :"D1";
        var t = this.getsec(id);
        exp2.setTime(exp2.getTime() +t );
        var path_ = this.getContextPath();
        document.cookie = name + ("=" + escape(value) + ";expires=" + exp2.toGMTString() + ";path=/" + path_ + ";");
    },
    getCookie:function(name){
        var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
        var s = "";
        if (arr != null) {
            s = unescape(arr[2]);
            if(s!=null && s.length >0)
            {
                if(s.indexOf('"', 0) == 0 && s.substring(s.length -1, s.length) == "\"")
                {
                    s = s.substring(1,s.length);
                    s = s.substring(0,s.length-1);
                }
            }

            return s;
        }
        return null;
    },
    delCookie:function(name){
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval=this.getCookie(name);
        var path_ = this.getContextPath();
        if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString()+";path=/" + path_ + ";";
    },
    getsec:function(sec){
        var str1=sec.substring(1,sec.length)*1;
        var str2=sec.substring(0,1);
        if (str2=="S")
        {
            return str1*1000;
        }
        else if(str2 =="M")
        {
            return str1 * 60 * 1000;
        }
        else if (str2=="H")
        {
            return str1*60*60*1000;
        }
        else if (str2=="D")
        {
            return str1*24*60*60*1000;
        }else{
            return 1*24*60*60*1000;
        }
    },
    trim:function(str){
        str = str.replace(/^(\s|\u00A0)+/,'');
        for(var i=str.length-1; i>=0; i--){
            if(/\S/.test(str.charAt(i))){
                str = str.substring(0, i+1);
                break;
            }
        }
        return str;
    },
    requestValue:function(d){
        var b = window.location.href;
        var f = b.indexOf("?");
        var e = b.substr(f + 1);
        var c = e.split("&");
        for ( var a = 0; a < c.length; a++) {
            var g = c[a].split("=");
            if (g[0].toUpperCase() == d.toUpperCase()) {
                return g[1];
            }
        }
        return ""
    },
    //使用keyCode方法，推荐使用onkeydown事件时调用
    keyCode:function(evt){
        evt = evt != null &&  evt != undefined  ? evt : window.event;
        var keyCode = evt.which != null &&  evt.which != undefined &&  evt.which != 0 ? evt.which : evt.keyCode;
        return keyCode;
    },
    formatStr:function(str){
        for ( var i = 0; i < arguments.length - 1; i++) {
            str = str.replace("{" + i + "}", arguments[i + 1]);
        }
        return str;
    },
    changeImg:function(id,imgurl){
        this.getElementById(id).src=imgurl;
    },
    version:function(){
        var version='';
        try{
            version= Authentication.CTCGetConfig("STBType");
        }catch(e){
            //console.log("获取版本失败");
        }
        return version;
    },
    isNull:function(obj){
        //0也判断为有效值
        var l_ = '' + obj;
        var ll_ = '' +0;
        if(l_ == ll_){return false;}
        if(typeof(obj) == 'object' && obj == ''){return false;}
        if(typeof(obj) =='undefined' || obj == undefined || obj == null || obj == ''){return true;}
        return false;
    },
    isNullErrorMsg:function(obj,msg_){
        if(this.isnull(obj))
        {
            try{
                if(this.debug == true)
                {
                    this.html("text", msg_);
                    console.log(msg_);
                }
            }catch(e){}
            return true;
        }
        return false;
    },
    isNotNull:function(obj){
        //0也判断为有效值
        var l_ = '' + obj;
        var ll_ = '' +0;
        if(l_ == ll_){return true;}
        if(typeof(obj) == 'object' && obj == ''){return true;}
        if(typeof(obj) =='undefined' || obj == undefined || obj == null || obj == ''){return false;}
        return true;
    },
    stext:function(id){
        var text="";
        var h = this.getElementById(id);
        if(!this.isNull(h.textContent)){
            text = h.textContent;
        }else{
            text = h.innerText;
        }
        return text;
    },
    focus:function(id){
        this.getElementById(id).focus();
    },
    itext:function(id,text){
        var h = this.getElementById(id);
        if(!this.isNull(text))
        {
            if(!this.isNull(h.innerText)){
                h.innerText =text;
            }else{
                h.textContent=text;
            }
        }
    },
    call:function(fn,args){
        if(typeof(fn) == 'string')
        {
            return eval("("+fn+")");
        }else if(typeof(fn) == 'function')
        {
            if(!this.isArray(args))
            {
                var arr = [];
                for(var i  = 1 ; i < arguments.length ; i++)
                {
                    arr.push(arguments[i]);
                }
                args = arr;
            }
            return fn.apply(window,args);
        }
    },
    hide:function(id)
    {
        //this.$(id).style.display = "none";
        this.getElementById(id).style.visibility = "hidden";
    },
    show:function(id)
    {
        //this.$(id).style.display = "block";
        this.getElementById(id).style.visibility = "visible";
    },
    getContextPath:function()
    {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath=window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName=window.document.location.pathname;
        var pos=curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht=curWwwPath.substring(0,pos);
        //获取带"/"的项目名，如：uimcardprj/
        var projectName=pathName.substring(1,pathName.substr(1).indexOf('/')+2);
        //var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1)---->/uimcardprj
        return projectName;
    },
    //http://localhost:8083
    getHostPath:function()
    {
        //http://localhost:8083/uimcardprj/share/meun.jsp
        var href = location.href;
        //uimcardprj/share/meun.jsp
        var pathname = location.pathname;
        return href.substr(0, href.lastIndexOf(pathname));
   },
    //http://127.0.0.1:8081
    getRootPath:function()
    {
        var result = this.hostPath;
        var pathname = location.pathname;
        if (pathname.indexOf("/") > -1) {
            var paths = pathname.split("/");
            var temps = new Array();
            for (var i = 0; i < paths.length; i++) { if (paths[i].length > 0) { temps.push(paths[i]); } }
            for (var i = 0; i < 0 && i < temps.length; i++) { result += "/" + temps[i]; }
        }
        return result;
   },
    //http://127.0.0.1:8081/gpf/
    getPath:function()
    {
        //http://www.qiguo.com/720p/html/main/main.html
        //    /720p/html/main/main.html
        var pathname = location.pathname;
        var t1 = pathname.indexOf("/",0);
        var sname="";
        //判断域名后面还有没有路径了，如果有就获取域名+工程名
        if(pathname.indexOf("/", t1+1) >-1)//5
        {
            sname = pathname.substring(t1+1, pathname.indexOf("/", t1+1));
            sname =this.hostPath +"/" + sname +"/";
        }
        return sname;
   },
    createToolTip:function(){
        var main = this.getElementById("main");
    },
    html:function(id,html){
        this.getElementById(id).innerHTML = html;
    },
    params:function(data){
        if(this.isNull(data)){return "";}
        var arr = [];
        for(var i in data){
            arr.push(encodeURIComponent(i) + "=" + encodeURIComponent(data[i]));
        }
        return arr.join("&");
   },
    addEvent:function(obj,type,func){
        if(obj.addEventListener){
            obj.addEventListener(type,func,false);
        }else if(obj.attachEvent){
            obj.attachEvent('on'+type,func);
        }
   },
    hasClass:function(id,className){
        if(!this.getElementById(id)){return false;}
        return this.getElementById(id).className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'));
   },
    addClass:function(id,className){
        if(!this.getElementById(id)){return;}
        if(!this.hasClass(id,className)){
            this.getElementById(id).className += ' ' +className;
        }
   },
    removeClass:function(id,className){
        if(!this.getElementById(id)){return;}
        if(this.hasClass(id,className)){
            this.getElementById(id).className = this.getElementById(id).className.replace(new RegExp('(\\s|^)' + className + '(\\s|$)'), ' ');
        }
   },
    toggleClass:function(id,className){
        if(!this.getElementById(id)){return;}
        if(this.hasClass(id,className)){
            this.removeClass(id,className);
        }else{
            this.addClass(id,className);
        }
   },
    /*
     * addEventListener:监听Dom元素的事件
     *
     * target：监听对象
     * type：监听函数类型，如click,mouseover
     * func：监听函数
     */
    addEventHandler:function(target,type,func){
        if(target.addEventListener){
            //监听IE9，谷歌和火狐
            target.addEventListener(type, func, false);
        }else if(target.attachEvent){
            target.attachEvent("on" + type, func);
        }else{
            target["on" + type] = func;
        }
   },
    /*
     * removeEventHandler:移除Dom元素的事件
     *
     * target：监听对象
     * type：监听函数类型，如click,mouseover
     * func：监听函数
     */
    removeEventHandler:function(target, type, func) {
        if (target.removeEventListener){
            //监听IE9，谷歌和火狐
            target.removeEventListener(type, func, false);
        } else if (target.detachEvent){
            target.detachEvent("on" + type, func);
        }else {
            delete target["on" + type];
        }
   },
    /*
     *求两个数之间的随机数
     */
    rangeNum:function(Min,Max){
        var num = Min + Math.floor(Math.random() * (Max - Min));
        return num;
   },

    //+---------------------------------------------------
    //| 求两个时间的天数差 日期格式为 YYYY-MM-dd
    //+---------------------------------------------------
    daysBetween:function(DateOne,DateTwo)
    {
        var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));
        var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);
        var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));

        var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));
        var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);
        var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));

        var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);
        return Math.abs(cha);
   },
    setCss3:function(obj,objAttr){
        //循环属性对象
        for(var i in objAttr){
            var newi=i;
            //判断是否存在transform-origin这样格式的属性
            if(newi.indexOf("-")>0){
                //将-o字符变成大写-O
                var num=newi.indexOf("-");
                newi=newi.replace(newi.substr(num,2),newi.substr(num+1,1).toUpperCase());
            }
            //考虑到css3的兼容性问题,所以这些属性都必须加前缀才行
            obj.style[newi]=objAttr[i];
            //设置首字母大写
            newi=newi.replace(newi.charAt(0),newi.charAt(0).toUpperCase());
            obj.style["webkit"+newi]=objAttr[i];
            obj.style["moz"+newi]=objAttr[i];
            obj.style["o"+newi]=objAttr[i];
            obj.style["ms"+newi]=objAttr[i];
            obj.style["khtml"+newi]=objAttr[i];
        }
   },
    //同步系统时间计时器
    runTimeInterval:setInterval(function()
    {
        if(window.serverTimestamp !=undefined && window.serverTimestamp!= null && window.serverTimestamp !='')
        {
            serverTimestamp =parseInt(serverTimestamp)+1000;
        }else{
            window.serverTimestamp = (new Date()).getTime();
        }
    },1000),
    //获取服务器时间对象
    getServerDate:function()
    {
        var date = new Date();
        if(window.serverTimestamp !=undefined && window.serverTimestamp!= null && window.serverTimestamp !='')
        {
            date = new Date(parseInt(serverTimestamp));
        }
        return date;
   },
    //拼接URL
    getURL:function(url_,params)
    {
        var url = "";
        if(url_.indexOf("?")  > -1)
        {
            url= url_+"&"+params;
        }else{
            url= url_+"?"+params;
        }
        return url;
    }
}
IPTV.fn.init.prototype = IPTV.fn;
IPTV.extend = IPTV.fn.extend = function() {
    var src, copyIsArray, copy, name, options, clone,
        target = arguments[0] || {},
        i = 1,
        length = arguments.length,
        deep = false;

    // Handle a deep copy situation
    if ( typeof target === "boolean" ) {
        deep = target;
        target = arguments[1] || {};
        // skip the boolean and the target
        i = 2;
    }

    // Handle case when target is a string or something (possible in deep copy)
    if ( typeof target !== "object" && !IPTV.isFunction(target) ) {
        target = {};
    }

    // extend jQuery itself if only one argument is passed
    if ( length === i ) {
        target = this;
        --i;
    }

    for ( ; i < length; i++ ) {
        // Only deal with non-null/undefined values
        if ( (options = arguments[ i ]) != null ) {
            // Extend the base object
            for ( name in options ) {
                src = target[ name ];
                copy = options[ name ];

                // Prevent never-ending loop
                if ( target === copy ) {
                    continue;
                }

                // Recurse if we're merging plain objects or arrays
                if ( deep && copy && ( IPTV.isPlainObject(copy) || (copyIsArray = IPTV.isArray(copy)) ) ) {
                    if ( copyIsArray ) {
                        copyIsArray = false;
                        clone = src && IPTV.isArray(src) ? src : [];

                    } else {
                        clone = src && IPTV.isPlainObject(src) ? src : {};
                    }

                    // Never move original objects, clone them
                    target[ name ] = IPTV.extend( deep, clone, copy );

                    // Don't bring in undefined values
                } else if ( copy !== undefined ) {
                    target[ name ] = copy;
                }
            }
        }
    }
    // Return the modified object
    return target;
};
IPTV.extend({
    type: function( obj ) {
        if ( obj == null ) {
            return String( obj );
        }
        return typeof obj === "object" || typeof obj === "function" ?
            class2type[ core_toString.call(obj) ] || "object" :
            typeof obj;
    },
    isFunction: function( obj ) {
        return this.type(obj) === "function";
    },
    isWindow: function( obj ) {
        return obj != null && obj == obj.window;
    },
    isArray: Array.isArray || function( obj ) {
        return this.type(obj) === "array";
    },
    isPlainObject: function( obj ) {
        // Must be an Object.
        // Because of IE, we also have to check the presence of the constructor property.
        // Make sure that DOM nodes and window objects don't pass through, as well
        if ( !obj || this.type(obj) !== "object" || obj.nodeType || this.isWindow( obj ) ) {
            return false;
        }

        try {
            // Not own constructor property must be Object
            if ( obj.constructor &&
                !core_hasOwn.call(obj, "constructor") &&
                !core_hasOwn.call(obj.constructor.prototype, "isPrototypeOf") ) {
                return false;
            }
        } catch ( e ) {
            // IE8,9 Will throw exceptions on certain host objects #9897
            return false;
        }

        // Own properties are enumerated firstly, so to speed up,
        // if last one is own, then all properties are own.

        var key;
        for ( key in obj ) {}

        return key === undefined || core_hasOwn.call( obj, key );
    },
    each: function( obj, callback, args ) {
        var value,
            i = 0,
            length = obj.length,
            isArray = isArraylike( obj );

        if ( args ) {
            if ( isArray ) {
                for ( ; i < length; i++ ) {
                    value = callback.apply( obj[ i ], args );

                    if ( value === false ) {
                        break;
                    }
                }
            } else {
                for ( i in obj ) {
                    value = callback.apply( obj[ i ], args );

                    if ( value === false ) {
                        break;
                    }
                }
            }

            // A special, fast, case for the most common use of each
        } else {
            if ( isArray ) {
                for ( ; i < length; i++ ) {
                    value = callback.call( obj[ i ], i, obj[ i ] );

                    if ( value === false ) {
                        break;
                    }
                }
            } else {
                for ( i in obj ) {
                    value = callback.call( obj[ i ], i, obj[ i ] );

                    if ( value === false ) {
                        break;
                    }
                }
            }
        }

        return obj;
    },
});
function isArraylike( obj ) {
    var length = obj.length,
        type = IPTV.type( obj );

    if (IPTV.isWindow( obj ) ) {
        return false;
    }

    if ( obj.nodeType === 1 && length ) {
        return true;
    }

    return type === "array" || type !== "function" &&
        ( length === 0 ||
            typeof length === "number" && length > 0 && ( length - 1 ) in obj );
}
IPTV.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (i, name) {
    class2type["[object " + name + "]"] = name.toLowerCase();
});
window.IPTV = window.$ = IPTV;
})( window );
