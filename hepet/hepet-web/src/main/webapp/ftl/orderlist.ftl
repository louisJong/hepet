<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>我的订单</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/mescroll.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}">
	<link rel="stylesheet" type="text/css" href="${host.css}/orderlist.css?v=${host.version}">
	<link rel="stylesheet" type="text/css" href="${host.css}/mall.css?v=${host.version}">
	<!-- 引入js -->
  <script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
  <script type="text/javascript" src="${host.js}/common.js?v=${host.version}"></script>
  <script src="${host.js}/mescroll.min.js"></script>
</head>
<body style="padding-top: 44px; background: #f5f5f5">
<header style="background: #fff"></header>
<script type="text/javascript">
var _mescroll = null;
var statusMap = {'NOPAY':'待付款', 'NOSEND': '待发货', 'NORECEIVE': '待收货', 'CLOSED':'已关闭', 'REFUND':'已退货', 'SUCCESS':'完成' , 'CANCEL':'已取消'}
var categorys = [{"code": "ALL", name: "全部"}, {"code": "NOSEND", name: "待发货"},{"code": "NORECEIVE", name: "待收货"}]
var curNavIndex = categorys[0]['code']; //默认初始选中的nav
var mescrollArr = new Array(categorys.length);
var categorysMap = {}
categorys.forEach(function(item, index) {
	categorysMap[item['code']] = index;
})
$(function() {
	initNav();
	//初始化
	mescrollArr[categorysMap[curNavIndex]] = initMescroll("mescroll"+curNavIndex, "dataList"+curNavIndex);
	$("#mescroll"+curNavIndex).show();
	/*初始化菜单*/
	$(".nav-item").click(function(){
		var i= $(this).attr("i");
		if(curNavIndex != i) {
			//更改列表条件
			$(".nav-item.actived").removeClass("actived");
			$(this).addClass("actived");
			//隐藏当前列表
			$("#mescroll" + curNavIndex).hide();
			//显示对应的列表
			curNavIndex = i;
			$("#mescroll" + curNavIndex).show();
			//取出菜单所对应的mescroll对象,如果未初始化则初始化
			if(mescrollArr[categorysMap[curNavIndex]] == null) mescrollArr[categorysMap[curNavIndex]] = initMescroll("mescroll"+curNavIndex, "dataList"+curNavIndex);
		}
	})
})
/*创建MeScroll对象*/
function initMescroll(mescrollId,clearEmptyId){
	//创建MeScroll对象,内部已默认开启下拉刷新,自动执行up.callback,刷新列表数据;
	var mescroll = new MeScroll(mescrollId, {
		//上拉加载的配置项
		up: {
			callback: getListData,
			noMoreSize: 2, 
			empty: {
				icon: "${host.img}/mescroll-empty.png", 
				tip: "暂无相关数据~", 
				btntext: "", 
			},
			clearEmptyId: clearEmptyId, //相当于同时设置了clearId和empty.warpId; 简化写法;默认null					
		}
	});
	return mescroll;
}

function initNav() {
	var str = '';
	var listContent = '';
	categorys.map(function(item, index) {
	str += '<div i="'+ item['code'] +'" class="nav-item '+ (item['code'] === curNavIndex ? 'actived ': '') +'" i="0">'+ item['name'] +'</div>';	
	listContent += '<div id="mescroll'+ item['code'] +'" class="list mescroll hide"><ul id="dataList'+ item['code'] +'" class="data-list"></ul></div>';
	})
	$("header").append($(str));
	$("body").append($(listContent));
}

/*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
function getListData(page){
	//联网加载数据
	var dataIndex = curNavIndex; //记录当前联网的nav下标,防止快速切换时,联网回来curNavIndex已经改变的情况;
	getListDataFromNet(dataIndex, page.num, page.size, function(pageData){
		//后台接口有返回列表的总数据量 totalSize
		mescrollArr[categorysMap[dataIndex]].endBySize(pageData.listData.length, pageData.count); 
		//设置列表数据
		setListData(pageData,dataIndex);
	}, function(){
		//联网失败的回调,隐藏下拉刷新和上拉加载的状态;
		mescrollArr[categorysMap[dataIndex]].endErr();
	});
}

function setListData(pageData, dataIndex) {
	var listDom=$("#dataList"+dataIndex);
	var dataList = pageData['listData'];
	for(var i = 0; i < dataList.length; i++) {
		var od = dataList[i];	
		var str = '<a class="list-item" href="${host.base}/hepet/order/result?orderId='+ od['id'] +'">';
		str += '<div class="top">';
		str += '<div class="left">订单号:'+ (od['orderNum'] ? od['orderNum']: '') +'</div>';
		str += '<div class="right font-base">'+ od['status'] ? statusMap[od['status']] : '完成' +'</div>';
		str += '</div>';
		str += '<div class="center">';
		str += '<div><img class="left" src="'+od.listImgUrl+'"/></div>';
		str += '<div class="right">';
		str += '<p>'+ (od['brandName'] || '') + od['goodsName']  +'</p>';
		od['subDesc'] ? str += '<p class="small-tips">'+ od['subDesc'] +'</p>' : '';
		str += '</div></div>';
		str += '<div class="bottom">总价：￥ '+ od['price'] + '</div>';
		str += '</a>';
		listDom.append($(str));
	}
}
function getListDataFromNet(curNavIndex, pageNum, pageSize, successCallback, errorCallback) {
	$.ajax({
    type: 'GET',
    url: '${host.base}/hepet/userOrderList',
    data: {
    	pageIndex: pageNum-1,
    	limit: 10,
		status: curNavIndex === 'ALL' ? '' : curNavIndex
    },
    dataType: 'json',
    success: function(data){
		if(data.head.code == '0000') {
			var listData=[];
			var _data = {
			listData: listData,
			count: data.body.count
			}
			categorys.map(function(item, index) {
			if(curNavIndex == categorys[index]['code']){
							for (var i = 0; i < data.body.orderList.length; i++) {
					_data.listData.push(data.body.orderList[i]);
				}							
				}
			})	  
			console.log('ajax:', _data)       
			//回调
			successCallback(_data);
		}else {
			errorCallback
		}
      
    },
    // error: errorCallback
    error: function(err) {
    	console.log('aa', err)
    }
  });
}
</script>
</body>
</html>