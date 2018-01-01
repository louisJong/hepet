<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>选择收货地址</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/address.css">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src='${host.js}/common.js'></script>
</head>
<body style="padding-bottom: 20px">
<!-- 地址列表	 -->
<div class="address-list"></div>
<!-- 新增地址按钮 -->
<div class="btn-box">
	<div class="green-btn" id="btn">新增收货地址</div>
</div>

<script type="text/javascript">
	var defaultId = commonUtils.getUrlParam('addid') || '';
	var from = commonUtils.getUrlParam('from');
  var curUrl = decodeURIComponent(commonUtils.getUrlParam('curUrl')); //来源路径
	$(function() {
		getAddList();
		if(from == 'order') {
			$("#btn").html("确认");
			$("#btn").on("click", function() {
				var str = commonUtils.changeURLArg(curUrl, 'from', 'addresslist');
				str = commonUtils.changeURLArg(str, 'addid', defaultId);
				window.location.href = str
			})
		} else {
			$("#btn").on("click", function() {
				window.location.href = '${host.base}/hepet/addAddr';
			})
		}

		$("body").on("click", '.address-item', function() {
			$(this).addClass("actived").siblings().removeClass("actived");
			defaultId = $(this).data("id");
		})
	})
	function initAddressList(data) {
		var str = ''
		data.map(function(item, index) {
			var actived = '';
			if(defaultId) {
        actived = item['id'] == defaultId ? 'actived' : ''
			} else {
				actived = index == 0 ? 'actived' : '';
			}
      	str += '<div onClick="window.location.href=\'${host.base}/hepet/addAddr?id='+ item['id']+ '\'" class="address-item '+ actived +'" data-id="'+ item['id'] +'">';
	    str += '<div class="left">';
	    str += '<p class="add-name">'+ item['contact'] +' '+ item['phone'] +'</p>';
	    str += '<div class="add-add">';
	    str += '<p>'+ item['area'] +'</p>';
	    str += '<p>'+ item['address'] +'</p>';
	    str += '</div></div>';
	    str += '<div class="right"></div>';
	    str += '</div>'
		})

		$(".address-list").append($(str));
    
	}
	function getAddList() {
		$.ajax({
			url: '${host.base}/hepet/addressList',
			dataType: 'json',
			type: 'GET',
			success: function(data) {
        if(data.head.code == '0000') {
          initAddressList(data.body.addresses||[]);
        } else {
        	$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
        }
			}
		})
	}
</script>
</body>
</html>