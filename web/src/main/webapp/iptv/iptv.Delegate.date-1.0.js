(function($, window, undefined) {
    $.extend({
        getLocale : function() {
            var browserLocaleStr = navigator.browserLanguage || navigator.language || navigator.userLanguage || undefined;
            if (!!!browserLocaleStr) {
                throw Error('It was not possible to get the locale information.');
            }
            return browserLocaleStr.substr(0, 2);
        },
        isUndefined : function(obj) {
            return $.type(obj) === 'undefined';
        },
        sleep : function(timeout) {
            timeout = timeout || 0;
            var sourceTime = new Date().getTime();
            var targetTime = sourceTime + timeout;
            while(sourceTime < targetTime) {
                sourceTime = new Date().getTime();
            }
        }
    });
    var delegateClass = Date,

    setLastDayList_ = function() {
        if ($.Date.isLeapYear(this.getFullYear())) {
            this.lastDayList = [ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
        } else {
            this.lastDayList = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
        }
    };

    $.Date = $.Delegate.inheritInstance(delegateClass, function() {
        if (isNaN(this._DELEGATE_OBJ_)) {
            return this._DELEGATE_OBJ_;
        }
        setLastDayList_.apply(this, arguments);
        this.locale = $.getLocale();
        this.name = '$.Date';
    });
    $.extend($.Date, {
        isLeapYear : function(year) {
            if (arguments.length === 0) {
                throw Error("this method needs arguments.");
            }
            return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
        }
    });
    $.extend($.Date.prototype, {
        isLeapYear : function() {
            return $.Date.isLeapYear(this.getFullYear());
        }
    });
}(IPTV, window));