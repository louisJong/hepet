webpackJsonp([9],{568:function(e,r,t){"use strict";function a(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(r,"__esModule",{value:!0});var s=t(92),n=a(s),u=t(144),o=a(u),c=t(3),d=a(c);t(145);var l=t(146),f=t(69);r.default={namespace:"login",state:{errorShow:!1,errorTips:""},effects:{login:n.default.mark(function e(r,t){var a,s,u=r.payload,c=(t.select,t.call),p=t.put;return n.default.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,c(l.login,(0,d.default)({},u));case 2:if(a=e.sent,!(s=a.data)){e.next=17;break}if("0000"!=s.head.code){e.next=13;break}return o.default.success("\u767b\u5f55\u6210\u529f"),e.next=9,p({type:"app/setState",payload:{userId:s.body.userId}});case 9:return e.next=11,p(f.routerRedux.push("/categorys"));case 11:e.next=15;break;case 13:return e.next=15,p({type:"setError",payload:{errorShow:!0,errorTips:s.head.msg}});case 15:e.next=18;break;case 17:o.default.error("\u7f51\u7edc\u5f02\u5e38");case 18:case"end":return e.stop()}},e,this)})},reducers:{setError:function(e,r){return(0,d.default)({},e,r.payload)}}},e.exports=r.default}});