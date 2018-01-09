<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>我的订单</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/mescroll.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/orderlist.css">
	<!-- 引入js -->
  <script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
  <script type="text/javascript" src="${host.js}/common.js"></script>
  <script src="${host.js}/mescroll.min.js"></script>
</head>
<body style="background: #f5f5f5;">
<div class="list mescroll" id='mescroll'>
	<div id="dataList"></div>
</div>	
<script type="text/javascript">
var _mescroll = null;
var statusMap = {'NOPAY':'待付款', 'NOSEND': '待发货', 'NORECEIVE': '待收货', 'CLOSED':'已关闭', 'REFUND':'已退货', 'SUCCESS':'完成'}
$(function() {
	_mescroll = new MeScroll("mescroll", {
		//上拉加载的配置项
		up: {
			callback: getListData,
			noMoreSize: 4, 
			empty: {
				icon: "${host.img}/mescroll-empty.png", 
				tip: "暂无相关数据~", 
				btntext: "去逛逛 >", 
				btnClick: function(){
					window.location.href='${host.base}/hepet/mall';
				} 
			},
			clearEmptyId: "dataList", //相当于同时设置了clearId和empty.warpId; 简化写法;默认null
			toTop:{ //配置回到顶部按钮
				src : "${host.img}/mescroll-totop.png", 
				offset : 1000
			}
		}
	});
})
function getListData(page) {
	getListDataFromNet(page.num, page.size, function(pageData) {
		console.log(pageData.listData.length, pageData.count)
		_mescroll.endBySize(pageData.listData.length, pageData.count); 
		setListData(pageData);
	}, function() {
		_mescroll.endErr();
	})
}

function setListData(pageData) {
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
		$("#dataList").append($(str));
	}
}
function getListDataFromNet(pageNum, pageSize, successCallback, errorCallback) {
	$.ajax({
    type: 'GET',
    url: '${host.base}/hepet/userOrderList',
    data: {
    	pageIndex: pageNum-1,
    	limit: 10
    },
    dataType: 'json',
    success: function(data){
    	if(data.head.code == '0000') {
				var listData=[];
        var _data = {
        	listData: listData,
        	count: data.body.count
        }
		for (var i = 0; i < data.body.orderList.length; i++) {
			_data.listData.push(data.body.orderList[i]);
		}			      
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