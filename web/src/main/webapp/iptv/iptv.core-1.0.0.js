/**
 * Created by cherish on 2017/12/14.
 */

(function (window, undefined) {


    var
        // 将 undefined 转换为字符串 "undefined"
        core_strundefined = typeof undefined,

        location = window.location,
        document = window.document,
        docElem = document.documentElement,

        //设置别名
        _iptv = window.iptv,
        _$ = window.$,

        // 储存了常见类型的 typeof 的哈希表
        class2type = {},
        // 定义当前版本
        core_version = '1.0.0',
        // 其次，这里定义了一个空的数组对象 ，如果下文行文需要调用数组对象的 concat 、push 、slice 、indexOf 方法
        // 将会调用 core_concat 、core_push 、core_slice 、和 core_indexOf ，这四个变量事先存储好了这四个方法的入口
        // 同时使用 call 或 apply 调用这些方法也可以使类数组也能用到数组的方法
        core_deletedIds = [],
        core_concat = core_deletedIds.concat,
        core_push = core_deletedIds.push,
        core_slice = core_deletedIds.slice,
        core_indexOf = core_deletedIds.indexOf,

        core_toString = class2type.toString,
        //hasOwnProperty:返回boolean值，参数是字符串，用于检查某对象是否存在该字符串的属性，该方法不会检查对象的原型链中是否存在该属性
        //var a = {name:"n"}; a.hasOwnProperty("name");return true
        core_hasOwn = class2type.hasOwnProperty,
        core_trim = core_version.trim,
        quickExpr = /(^[#&.])([\w]+)$/;

    //定义iptv构造函数
    iptv = function (selector, context) {
        return new iptv.fn.init(selector, context);
    };
    // 给 iptv.prototype 设置别名 iptv.fn
    // iptv.prototype 即是 iptv的原型，挂载在 iptv.prototype 上的方法，即可让所有 iptv 对象使用
    iptv.fn = iptv.prototype = {
        // 当前版本
        iptv: core_version,
        constructor: iptv,
        /**
         * 初始化方法
         */
        init: function (selector) {
            // 如果传入的参数为空，则直接返回this
            if (!selector) {
                return this;
            }
            var match;
            if (typeof selector == "string") {
                match = quickExpr.exec(selector)
                //处理id DOM
                if (match && match[1] === "#") {
                    var ele = document.getElementById(match[2]);
                    var ret = iptv(ele);
                    ret.context = ele;
                    ret.selector = match[2];
                    return ret;
                } else if (match && match[1] === "&") {
                    //焦点   
                }
            }

            if ( selector.selector && selector.context ) {
                this.selector = selector.selector;
                this.context = selector.context;
            }
            return this;
        },
        //当前操作的上下文对象
        context: null,
        //当前的选择器
        selector: "",

    };
    //重置原型对象为iptv
    iptv.fn.init.prototype = iptv.fn;


    /**
     * 定义继承方法
     * @type {iptv.extend}
     */
    iptv.extend = iptv.fn.extend = function () {
        var src, copyIsArray, copy, name, options, clone,
            target = arguments[0] || {},
            i = 1,
            length = arguments.length,
            deep = false;

        // target 是传入的第一个参数
        // 如果第一个参数是布尔类型，则表示是否要深递归，
        if (typeof target === "boolean") {
            deep = target;
            target = arguments[1] || {};
            // 如果传了类型为 boolean 的第一个参数，i 则从 2 开始
            i = 2;
        }
        // 如果传入的第一个参数是 字符串或者其他
        if (typeof target !== "object" && !iptv.isFunction(target)) {
            target = {};
        }
        // 如果参数的长度为 1 ，表示是 iptv 静态方法
        if (length === i) {
            target = this;
            --i;
        }
        // 可以传入多个复制源
        // i 是从 1或2 开始的
        for (; i < length; i++) {
            // 将每个源的属性全部复制到 target 上
            if ((options = arguments[i]) != null) {
                // Extend the base object
                for (name in options) {
                    // src 是源（即本身）的值
                    // copy 是即将要复制过去的值
                    src = target[name];
                    copy = options[name];
                    // 防止有环，例如 extend(true, target, {'target':target});
                    if (target === copy) {
                        continue;
                    }
                    // 如果是深复制
                    if (deep && copy && (iptv.isPlainObject(copy) || (copyIsArray = iptv.isArray(copy)))) {
                        // 数组
                        if (copyIsArray) {
                            copyIsArray = false;
                            clone = src && iptv.isArray(src) ? src : [];
                            // 对象
                        } else {
                            clone = src && iptv.isPlainObject(src) ? src : {};
                        }
                        // 递归
                        target[name] = iptv.extend(deep, clone, copy);
                    } else if (copy !== undefined) {
                        target[name] = copy;
                    }
                }
            }
        }

        // 也就是如果不传需要覆盖的源，调用 $.extend 其实是增加 iptv 的静态方法
        return target;
    };
    //添加iptv静态方法
    iptv.extend({
        /**
         * 随机数
         */
        expando: "iptv" + (core_version + Math.random()).replace(/\D/g, ""),
        /**
         * 判断传入对象是否为 function
         * @returns {boolean}
         */
        isFunction: function (obj) {
            return iptv.type(obj) === "function";
        },
        /**
         * 判断传入对象是否为数组
         * @returns {boolean}
         */
        isArray: Array.isArray || function (obj) {
            return iptv.type(obj) === "array";
        },
        /**
         *  判断传入对象是否为 window 对象
         * @param obj
         * @returns {boolean}
         */
        isWindow: function (obj) {
            return obj != null && obj == obj.window;
        },
        // 确定它的参数是否是一个数字
        isNumeric: function (obj) {
            //isFinite:参数是一个数字，用于判断这个数字是否是无穷大数字，如果是无穷大，返回false，如果数字正常返回true
            return !isNaN(parseFloat(obj)) && isFinite(obj);
        },

        /**
         * 确定JavaScript 对象的类型
         * @param obj
         * @returns {*}
         */
        type: function (obj) {
            // 如果传入的为 null --> "null"
            if (obj == null) {
                return String(obj);
            }
            // 利用事先存好的 hash 表 class2type 作精准判断
            return typeof obj === "object" || typeof obj === "function" ?
                class2type[core_toString.call(obj)] || "object" :
                typeof obj;
        },
        /**
         * 测试对象是否是纯粹的对象
         * 通过 "{}" 或者 "new Object" 创建的
         * @param obj
         * @returns {Boolean ,Number ,String ,Function ,Array ,Date ,RegExp ,Object ,Error}
         */
        isPlainObject: function (obj) {
            var key;
            if (!obj || iptv.type(obj) !== "object" || obj.nodeType || iptv.isWindow(obj)) {
                return false;
            }

            try {
                if (obj.constructor &&
                    !core_hasOwn.call(obj, "constructor") &&
                    !core_hasOwn.call(obj.constructor.prototype, "isPrototypeOf")) {
                    return false;
                }
            } catch (e) {
                return false;
            }
            if (iptv.support.ownLast) {
                for (key in obj) {
                    return core_hasOwn.call(obj, key);
                }
            }
            for (key in obj) {
            }

            return key === undefined || core_hasOwn.call(obj, key);
        },
        /**
         * 检查对象是否为空（不包含任何属性）
         * @param obj
         * @returns {boolean}
         */
        isEmptyObject: function (obj) {
            var name;
            for (name in obj) {
                return false;
            }
            return true;
        },
        /**
         * 为 JavaScript 的 "error" 事件绑定一个处理函数
         * @param msg 错误描述
         */
        error: function (msg) {
            throw new Error(msg);
        },
        trim: function (text) {
            return text == null ? "" : (text + "").replace(rtrim, "");
        },
        /**
         * eval的变异，使用效果一样，只不过是在全局作用域中执行 参数data
         * @param data
         */
        globalEval: function (data) {
            // 如果 data 不为空
            if (data && iptv.trim(data)) {
                (window.execScript || function (data) {
                    // 在chrome一些旧版本里eval.call( window, data )无效
                    window["eval"].call(window, data);
                })(data);
            }
        },
        /**
         * 判断某个DOM是否是指定的name名称
         * iptv.nodeName(document.getElementById("h"),"h2")--->return true/false
         * @param elem  DOM节点对象
         * @param name  需要判断的节点名称
         * @returns {boolean}
         */
        nodeName: function (elem, name) {
            return elem.nodeName && elem.nodeName.toLowerCase() === name.toLowerCase();
        },
        /**
         * 循环数组或对象
         * @param obj
         * @param callback
         * @param args
         * @returns {*}
         */
        each: function (obj, callback, args) {
            var value,
                i = 0,
                length = obj.length,
                isArray = iptv.isArraylike(obj); // 判断是不是数组

            // 传了第三个参数
            if (args) {
                if (isArray) {
                    for (; i < length; i++) {
                        // 相当于:
                        // args = [arg1, arg2, arg3];
                        // callback(args1, args2, args3)。然后callback里边的this指向了obj[i]
                        value = callback.apply(obj[i], args);

                        if (value === false) {
                            // 注意到，当callback函数返回值会false的时候，注意是全等！循环结束
                            break;
                        }
                    }
                    // 非数组
                } else {
                    for (i in obj) {
                        value = callback.apply(obj[i], args);

                        if (value === false) {
                            break;
                        }
                    }
                }
            } else {
                // 数组
                if (isArray) {
                    for (; i < length; i++) {
                        // 相当于callback(i, obj[i])。然后callback里边的this指向了obj[i]
                        value = callback.call(obj[i], i, obj[i]);

                        if (value === false) {
                            break;
                        }
                    }
                    // 非数组
                } else {
                    for (i in obj) {
                        value = callback.call(obj[i], i, obj[i]);

                        if (value === false) {
                            break;
                        }
                    }
                }
            }

            return obj;
        },

        /**
         *  merge的两个参数必须为数组，作用就是修改第一个数组，使得它末尾加上第二个数组
         * @param first
         * @param second
         * @returns {*}
         */
        merge: function (first, second) {
            var l = second.length,
                i = first.length,
                j = 0;

            if (typeof l === "number") {
                for (; j < l; j++) {
                    first[i++] = second[j];
                }
            } else {
                while (second[j] !== undefined) {
                    first[i++] = second[j++];
                }
            }
            first.length = i;
            return first;
        },
        /**
         * 获取当前时间的时间戳
         * @returns {number}
         */
        now: function () {
            return (new Date()).getTime();
        },
        /**
         * 返回对象是否是数组还是类数组对象
         * @param obj
         * @returns {boolean} true:是数组，false:不是数组
         */
        isArraylike: function (obj) {
            var length = obj.length,
                type = iptv.type(obj);

            if (iptv.isWindow(obj)) {
                return false;
            }

            if (obj.nodeType === 1 && length) {
                return true;
            }

            return type === "array" || type !== "function" && (length === 0 || typeof length === "number" && length > 0 && (length - 1) in obj);
        },
        /**
         * DOM ready 是否已经完成
         */
        isReady: false,
        ready: function (callback_) {

            // 确定 body 存在
            if (!document.body) {
                // 在 setTimeout 中触发的函数, 一定会在 DOM 准备完毕后触发
                return setTimeout(iptv.ready, 0, callback_);
            }
            // 记录 DOM ready 已经完成
            iptv.isReady = true;
            /* if (iptv.fn.trigger) {
             iptv(document).trigger("ready").off("ready");
             }*/
            console.log("DOM加载完成");
            callback_.call(this, iptv.isReady);
        },
    });

    iptv.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (i, name) {
        class2type["[object " + name + "]"] = name.toLowerCase();
    });

    //定义对象方法
    iptv.fn.extend({
       

    });


    // window.iptv = window.$ = iptv;
    window.iptv  = iptv;
})(window);

console.log(iptv.ready(function (i) {
    console.log(iptv("#h"));
    console.log($("#h"));
}))