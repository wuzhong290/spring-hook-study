(function($, window, undefined) {
    $.Delegate = function() {
        if ($.isFunction(this)) {
            throw Error('Kill if you didn\'t use the \'new\'.');
        }
        this.constructor_.apply(this, arguments);
    };
    $.extend($.Delegate.prototype, {
        delegateClass : Function,

        constructor_ : function() {
            this._DELEGATE_OBJ_ = this.newDelegateInstance.apply(this, arguments || []);
            var propertyNames = this.getPropertyNames(this.delegateClass.prototype);
            for (var i = 0, p = propertyNames[i]; (p = propertyNames[i]); i++) {
                (function(that) {
                    var propertyName = p;
                    $.Delegate.delegateProperty(that, that._DELEGATE_OBJ_, propertyName);
                })(this);
            }
        },
        newDelegateInstance : function() {
            return (!!arguments[0] && arguments[0] instanceof Array) ? new (this.delegateClass.bind.apply(this.delegateClass, [ undefined ].concat([].slice
                .call(arguments[0]))))() : new (this.delegateClass.bind.apply(this.delegateClass, [ undefined ].concat([].slice.call(arguments))))();
        },
        getPropertyNames : function(source) {
            return (!!Object.getOwnPropertyNames) ? Object.getOwnPropertyNames(source) : [];
        }
    });
    $.extend($.Delegate, {
        test:function(){
            alert(1);
        },
        delegateProperty : function(target, source, propertyName) {
            if (!!target[propertyName]) {
                return;
            }
            var name = propertyName;

            if ($.isFunction(source[name])) {
                target[name] = function() {
                    var result = source[name].apply(source, arguments);
                    return /^set/.test(name) ? target : result;
                };
            } else {
                target[name] = source[name];
            }
        },
        delegateClassProparties : function(targetClass, sourceClass) {
            var classPropertyNames =
                (!!targetClass.prototype.getPropertyNames) ? targetClass.prototype.getPropertyNames(sourceClass) : $.Delegate.prototype
                    .getPropertyNames(sourceClass);
            for (var i = 0, cp = classPropertyNames[i]; (cp = classPropertyNames[i]); i++) {
                (function(thisClass) {
                    var classPropertyName = cp;
                    if (/^(?:arguments|caller|prototype)$/.test(classPropertyName)) {
                        return;
                    }
                    if (!!thisClass[classPropertyName]) {
                        return;
                    }
                    $.Delegate.delegateProperty(thisClass, sourceClass, classPropertyName);
                })(targetClass);
            }
        },
        inheritInstance : function(originalClass, targetClass) {
            var T = targetClass || Function;
            var O = originalClass;
            var F = function() {
                if ($.isFunction(this)) {
                    return O.apply(O, arguments);
                }
                $.Delegate.apply(this, arguments);
                T.apply(this, arguments);
            };

            F.prototype = Object.create($.Delegate.prototype);
            $.extend(F, $.Delegate);
            $.extend(F.prototype, {
                delegateClass : O
            });
            F.delegateClassProparties(F, O);
            F.classDelegateMethod = function(delegateMethod, args) {
                return O[delegateMethod].apply(O, args || []);
            };
            return F;
        }
    });
})(IPTV, window);