/**
 * Created by thinkpad on 2017/12/12.
 */
(function( window, undefined ) {
var core_version = "1.0",
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
    getHostPath:function()
    {
        //http://localhost:8083/uimcardprj/share/meun.jsp
        var href = location.href;
        //uimcardprj/share/meun.jsp
        var pathname = location.pathname;
        return href.substr(0, href.lastIndexOf(pathname));
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
    },
    type: function( obj ) {
        if ( obj == null ) {
            return String( obj );
        }
        return typeof obj === "object" || typeof obj === "function" ?
        class2type[ core_toString.call(obj) ] || "object" :
            typeof obj;
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
    if ( typeof target !== "object" && !jQuery.isFunction(target) ) {
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
                if ( deep && copy && ( this.isPlainObject(copy) || (copyIsArray = this.isArray(copy)) ) ) {
                    if ( copyIsArray ) {
                        copyIsArray = false;
                        clone = src && this.isArray(src) ? src : [];

                    } else {
                        clone = src && this.isPlainObject(src) ? src : {};
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
window.IPTV = window.$ = IPTV;
})( window );
