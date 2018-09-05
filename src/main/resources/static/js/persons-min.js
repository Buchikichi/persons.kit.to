var $jscomp=$jscomp||{};$jscomp.scope={};$jscomp.ASSUME_ES5=!1;$jscomp.ASSUME_NO_NATIVE_MAP=!1;$jscomp.ASSUME_NO_NATIVE_SET=!1;$jscomp.objectCreate=$jscomp.ASSUME_ES5||"function"==typeof Object.create?Object.create:function(a){var b=function(){};b.prototype=a;return new b};$jscomp.underscoreProtoCanBeSet=function(){var a={a:!0},b={};try{return b.__proto__=a,b.a}catch(c){}return!1};
$jscomp.setPrototypeOf="function"==typeof Object.setPrototypeOf?Object.setPrototypeOf:$jscomp.underscoreProtoCanBeSet()?function(a,b){a.__proto__=b;if(a.__proto__!==b)throw new TypeError(a+" is not extensible");return a}:null;
$jscomp.inherits=function(a,b){a.prototype=$jscomp.objectCreate(b.prototype);a.prototype.constructor=a;if($jscomp.setPrototypeOf){var c=$jscomp.setPrototypeOf;c(a,b)}else for(c in b)if("prototype"!=c)if(Object.defineProperties){var e=Object.getOwnPropertyDescriptor(b,c);e&&Object.defineProperty(a,c,e)}else a[c]=b[c];a.superClass_=b.prototype};
$jscomp.defineProperty=$jscomp.ASSUME_ES5||"function"==typeof Object.defineProperties?Object.defineProperty:function(a,b,c){a!=Array.prototype&&a!=Object.prototype&&(a[b]=c.value)};$jscomp.getGlobal=function(a){return"undefined"!=typeof window&&window===a?a:"undefined"!=typeof global&&null!=global?global:a};$jscomp.global=$jscomp.getGlobal(this);$jscomp.SYMBOL_PREFIX="jscomp_symbol_";$jscomp.initSymbol=function(){$jscomp.initSymbol=function(){};$jscomp.global.Symbol||($jscomp.global.Symbol=$jscomp.Symbol)};
$jscomp.Symbol=function(){var a=0;return function(b){return $jscomp.SYMBOL_PREFIX+(b||"")+a++}}();$jscomp.initSymbolIterator=function(){$jscomp.initSymbol();var a=$jscomp.global.Symbol.iterator;a||(a=$jscomp.global.Symbol.iterator=$jscomp.global.Symbol("iterator"));"function"!=typeof Array.prototype[a]&&$jscomp.defineProperty(Array.prototype,a,{configurable:!0,writable:!0,value:function(){return $jscomp.arrayIterator(this)}});$jscomp.initSymbolIterator=function(){}};
$jscomp.arrayIterator=function(a){var b=0;return $jscomp.iteratorPrototype(function(){return b<a.length?{done:!1,value:a[b++]}:{done:!0}})};$jscomp.iteratorPrototype=function(a){$jscomp.initSymbolIterator();a={next:a};a[$jscomp.global.Symbol.iterator]=function(){return this};return a};$jscomp.makeIterator=function(a){$jscomp.initSymbolIterator();var b=a[Symbol.iterator];return b?b.call(a):$jscomp.arrayIterator(a)};
$jscomp.polyfill=function(a,b,c,e){if(b){c=$jscomp.global;a=a.split(".");for(e=0;e<a.length-1;e++){var d=a[e];d in c||(c[d]={});c=c[d]}a=a[a.length-1];e=c[a];b=b(e);b!=e&&null!=b&&$jscomp.defineProperty(c,a,{configurable:!0,writable:!0,value:b})}};$jscomp.FORCE_POLYFILL_PROMISE=!1;
$jscomp.polyfill("Promise",function(a){function b(){this.batch_=null}function c(a){return a instanceof d?a:new d(function(b,c){b(a)})}if(a&&!$jscomp.FORCE_POLYFILL_PROMISE)return a;b.prototype.asyncExecute=function(a){null==this.batch_&&(this.batch_=[],this.asyncExecuteBatch_());this.batch_.push(a);return this};b.prototype.asyncExecuteBatch_=function(){var a=this;this.asyncExecuteFunction(function(){a.executeBatch_()})};var e=$jscomp.global.setTimeout;b.prototype.asyncExecuteFunction=function(a){e(a,
0)};b.prototype.executeBatch_=function(){for(;this.batch_&&this.batch_.length;){var a=this.batch_;this.batch_=[];for(var b=0;b<a.length;++b){var c=a[b];a[b]=null;try{c()}catch(k){this.asyncThrow_(k)}}}this.batch_=null};b.prototype.asyncThrow_=function(a){this.asyncExecuteFunction(function(){throw a;})};var d=function(a){this.state_=0;this.result_=void 0;this.onSettledCallbacks_=[];var b=this.createResolveAndReject_();try{a(b.resolve,b.reject)}catch(h){b.reject(h)}};d.prototype.createResolveAndReject_=
function(){function a(a){return function(g){c||(c=!0,a.call(b,g))}}var b=this,c=!1;return{resolve:a(this.resolveTo_),reject:a(this.reject_)}};d.prototype.resolveTo_=function(a){if(a===this)this.reject_(new TypeError("A Promise cannot resolve to itself"));else if(a instanceof d)this.settleSameAsPromise_(a);else{a:switch(typeof a){case "object":var b=null!=a;break a;case "function":b=!0;break a;default:b=!1}b?this.resolveToNonPromiseObj_(a):this.fulfill_(a)}};d.prototype.resolveToNonPromiseObj_=function(a){var b=
void 0;try{b=a.then}catch(h){this.reject_(h);return}"function"==typeof b?this.settleSameAsThenable_(b,a):this.fulfill_(a)};d.prototype.reject_=function(a){this.settle_(2,a)};d.prototype.fulfill_=function(a){this.settle_(1,a)};d.prototype.settle_=function(a,b){if(0!=this.state_)throw Error("Cannot settle("+a+", "+b+"): Promise already settled in state"+this.state_);this.state_=a;this.result_=b;this.executeOnSettledCallbacks_()};d.prototype.executeOnSettledCallbacks_=function(){if(null!=this.onSettledCallbacks_){for(var a=
0;a<this.onSettledCallbacks_.length;++a)f.asyncExecute(this.onSettledCallbacks_[a]);this.onSettledCallbacks_=null}};var f=new b;d.prototype.settleSameAsPromise_=function(a){var b=this.createResolveAndReject_();a.callWhenSettled_(b.resolve,b.reject)};d.prototype.settleSameAsThenable_=function(a,b){var c=this.createResolveAndReject_();try{a.call(b,c.resolve,c.reject)}catch(k){c.reject(k)}};d.prototype.then=function(a,b){function c(a,b){return"function"==typeof a?function(b){try{e(a(b))}catch(l){f(l)}}:
b}var e,f,g=new d(function(a,b){e=a;f=b});this.callWhenSettled_(c(a,e),c(b,f));return g};d.prototype.catch=function(a){return this.then(void 0,a)};d.prototype.callWhenSettled_=function(a,b){function c(){switch(d.state_){case 1:a(d.result_);break;case 2:b(d.result_);break;default:throw Error("Unexpected state: "+d.state_);}}var d=this;null==this.onSettledCallbacks_?f.asyncExecute(c):this.onSettledCallbacks_.push(c)};d.resolve=c;d.reject=function(a){return new d(function(b,c){c(a)})};d.race=function(a){return new d(function(b,
d){for(var e=$jscomp.makeIterator(a),f=e.next();!f.done;f=e.next())c(f.value).callWhenSettled_(b,d)})};d.all=function(a){var b=$jscomp.makeIterator(a),e=b.next();return e.done?c([]):new d(function(a,d){function f(b){return function(c){g[b]=c;h--;0==h&&a(g)}}var g=[],h=0;do g.push(void 0),h++,c(e.value).callWhenSettled_(f(g.length-1),d),e=b.next();while(!e.done)})};return d},"es6","es3");
$jscomp.iteratorFromArray=function(a,b){$jscomp.initSymbolIterator();a instanceof String&&(a+="");var c=0,e={next:function(){if(c<a.length){var d=c++;return{value:b(d,a[d]),done:!1}}e.next=function(){return{done:!0,value:void 0}};return e.next()}};e[Symbol.iterator]=function(){return e};return e};$jscomp.polyfill("Array.prototype.keys",function(a){return a?a:function(){return $jscomp.iteratorFromArray(this,function(a){return a})}},"es6","es3");var AjaxUtils=function(){};
AjaxUtils.fetch=function(a,b,c){c=void 0===c?{}:c;return"function"===typeof fetch?fetch(a,{method:"post",headers:c,body:b,credentials:"include"}):new Promise(function(e,d){var f=new XMLHttpRequest;f.open("post",a);f.withCredentials=!0;f.addEventListener("loadend",function(){200<=f.status&&300>f.status?e(JSON.parse(f.response)):d(f)});Object.keys(c).forEach(function(a){f.setRequestHeader(a,c[a])});f.send(b)})};AjaxUtils.post=function(a,b,c){return AjaxUtils.fetch(a,b)};
AjaxUtils.postJSON=function(a,b){var c={"Content-Type":"application/json"},e=document.querySelector('[name="_csrf_header"]');if(e){e=e.getAttribute("content");var d=document.querySelector('[name="_csrf"]').getAttribute("content");c[e]=d}return AjaxUtils.fetch(a,b,c)};var DateTimeUtils=function(){};
DateTimeUtils.toYMDHMS=function(a,b){b=void 0===b?!0:b;if(isNaN(a.getTime()))return"";var c=a.getFullYear();var e=("0"+(a.getMonth()+1)).slice(-2),d=("0"+a.getDate()).slice(-2),f=("0"+a.getHours()).slice(-2),g=("0"+a.getMinutes()).slice(-2);c=c+"/"+e+"/"+d+" "+f+":"+g;b&&(a=("0"+a.getSeconds()).slice(-2),c+=":"+a);return c};DateTimeUtils.toYMDHM=function(a){return DateTimeUtils.toYMDHMS(a,!1)};
DateTimeUtils.makeISO8601Offset=function(a){a=void 0===a?new Date:a;if(isNaN(a.getTime()))return"";var b=a.getFullYear(),c=("0"+(a.getMonth()+1)).slice(-2),e=("0"+a.getDate()).slice(-2),d=("0"+a.getHours()).slice(-2),f=("0"+a.getMinutes()).slice(-2);a=a.getTimezoneOffset()/60;var g=("0"+Math.abs(a)).slice(-2)+":00";return b+"-"+c+"-"+e+"T"+d+":"+f+":00"+(0>a?"+":"-")+g};
DateTimeUtils.makeISO8601=function(a,b,c){b=void 0===b?null:b;if(0==a.length)return null;a=null!=b&&0<b.length?a+(" "+b):a+(" "+(void 0===c?"00:00":c));return moment(a,"YYYY-MM-DD HH:mm:ss").toISOString()};DateTimeUtils.addDays=function(a,b){a=moment(a,"YYYY-MM-DD");a.add(b,"days");return a.toISOString()};
DateTimeUtils.sub=function(a,b){a=moment(a,"YYYY-MM-DD");"ThreeMonths"==b?a.add(-3,"months"):"SixMonths"==b?a.add(-6,"months"):"OneYear"==b?a.add(-1,"year"):"TwoYears"==b?a.add(-2,"years"):"ThreeYears"==b&&a.add(-3,"years");return a.toISOString()};var ControllerBase=function(a){this.base=document.querySelector("base").getAttribute("href")+a};
ControllerBase.prototype.postJSON=function(a,b){return AjaxUtils.postJSON(a,b).then(function(a){if(!a.result)throw a;return a}).catch(function(a){if(403==a.status)return a=document.querySelector("base").getAttribute("href"),location.href=a,null;NoticeDialog.show(a.message);return null})};ControllerBase.prototype.list=function(a){a=void 0===a?{}:a;a=JSON.stringify(a);return this.postJSON(this.base+"/list",a)};
ControllerBase.prototype.select=function(a){a=void 0===a?{}:a;a=JSON.stringify(a);return this.postJSON(this.base+"/select",a)};ControllerBase.prototype.save=function(a){a=void 0===a?{}:a;a=JSON.stringify(a);return this.postJSON(this.base+"/save",a)};ControllerBase.prototype.delete=function(a){a=void 0===a?{}:a;a=JSON.stringify(a);return this.postJSON(this.base+"/delete",a)};
ControllerBase.prototype.create=function(a,b){b=void 0===b?{}:b;b=JSON.stringify(b);return AjaxUtils.fetch(this.base+"/create",b).then(function(a){return a.blob()}).then(function(b){console.log("done:");console.log(b);b=(window.URL||window.webkitURL).createObjectURL(b);var c=document.createElement("a");document.body.appendChild(c);c.href=b;c.download=a;c.click();(window.URL||window.webkitURL).revokeObjectURL(b)})};var PersonsController=function(){ControllerBase.call(this,"persons")};
$jscomp.inherits(PersonsController,ControllerBase);document.addEventListener("DOMContentLoaded",function(){new Persons});var Persons=function(){this.persons=new PersonsController;this.setupEvents();console.log("Persons.")};
Persons.prototype.setupEvents=function(){var a=this,b=document.getElementById("disuseList"),c=document.getElementById("busyList"),e=document.querySelector("button.create");this.setupSortableList();$("#disuseList li, #busyList li").each(function(d,e){d=e.querySelectorAll("a")[1];$(e).dblclick(function(){"busyList"==e.parentNode.id?b.appendChild(e):c.appendChild(e);a.refreshSortableList()});d.addEventListener("click",function(a){"busyList"==e.parentNode.id&&$("#familyKana").panel("open")})});e.addEventListener("click",
function(){var b="persons"+DateTimeUtils.toYMDHMS(new Date)+".gz";console.log("create!!");a.persons.create(b,{})})};Persons.prototype.setupSortableList=function(){var a=this,b=$(".sortableList");b.sortable({placeholder:"ui-state-highlight",update:function(){a.refreshSortableList()}});$("#disuseList").listview("refresh");b.listview("refresh")};Persons.prototype.refreshSortableList=function(){this.setupSortableList()};
