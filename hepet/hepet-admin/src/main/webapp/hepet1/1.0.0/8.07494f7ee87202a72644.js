webpackJsonp([8],{574:function(e,a,t){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(a,"__esModule",{value:!0});var n=t(92),s=r(n),d=t(144),o=r(d),u=t(3),c=r(u);t(145);var i=t(146),l=t(142);a.default={namespace:"order",state:{orderList:[],tableLoading:!0,currentPage:1,pageSize:10,total:0,sum:0,searchParams:{},editorVisible:!1,editorData:{}},subscriptions:{setup:function(e){var a=e.dispatch;e.history.listen(function(e){"/order"===e.pathname&&a({type:"queryOrder"})})}},effects:{queryOrder:s.default.mark(function e(a,t){var r,n,d,u,f=a.payload,p=t.select,y=t.call,b=t.put;return s.default.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,p();case 2:return r=e.sent.order,n=f?f.searchParams:r.searchParams,e.next=6,b({type:"setLoading",payload:{tableLoading:!0}});case 6:return e.next=8,y(i.orderList,(0,c.default)({},n,{pageIndex:r.currentPage-1,limit:r.pageSize}));case 8:return d=e.sent,u=d.data,e.next=12,b({type:"setLoading",payload:{tableLoading:!1}});case 12:if(!u){e.next=21;break}if("0000"!=u.head.code){e.next=18;break}return e.next=16,b({type:"querySuccess",payload:{orderList:u.body.orderList,searchParams:n,total:u.body.count,sum:u.body.sum}});case 16:e.next=19;break;case 18:(0,l.ajaxFail)(u);case 19:e.next=22;break;case 21:o.default.error("\u7f51\u7edc\u5f02\u5e38");case 22:case"end":return e.stop()}},e,this)}),addKdInfo:s.default.mark(function e(a,t){var r,n,d=a.payload,u=(t.select,t.call),f=t.put;return s.default.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,u(i.addKdInfo,(0,c.default)({},d.params));case 2:if(r=e.sent,!(n=r.data)){e.next=17;break}if("0000"!=n.head.code){e.next=14;break}return o.default.success(n.head.msg),e.next=9,f({type:"setEditor",payload:{editorData:{},editorVisible:!1}});case 9:return e.next=11,f({type:"queryOrder"});case 11:d.callback(),e.next=15;break;case 14:(0,l.ajaxFail)(n);case 15:e.next=18;break;case 17:o.default.error("\u7f51\u7edc\u5f02\u5e38");case 18:case"end":return e.stop()}},e,this)})},reducers:{querySuccess:function(e,a){return(0,c.default)({},e,a.payload)},setLoading:function(e,a){return(0,c.default)({},e,{tableLoading:a.payload.tableLoading})},setPage:function(e,a){return(0,c.default)({},e,{currentPage:a.payload.currentPage})},setEditor:function(e,a){return(0,c.default)({},e,{editorVisible:a.payload.editorVisible,editorData:a.payload.editorData})}}},e.exports=a.default}});