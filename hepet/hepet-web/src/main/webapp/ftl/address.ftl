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
<div class="button" id="btn">
	<p><i class="add-icon"></i>添加新地址</p>
</div>

<script type="text/javascript">
	var defaultId = commonUtils.getUrlParam('addid') || '';
	var from = commonUtils.getUrlParam('from');
	$(function() {
		getAddList();
		$("body").on("click",".no-address, #btn", function() {
			if(from === 'order'){
				var curUrl = commonUtils.getUrlParam('curUrl');
				window.location.href = '${host.base}/hepet/addAddr?curUrl='+curUrl+'&from=order';
			}else {
				var curUrl = window.location.href.split("?")[0];
				window.location.href = '${host.base}/hepet/addAddr?curUrl='+encodeURIComponent(curUrl);
			}
		})
		$(".address-list").on("click", ".edite", function(e) {
			e.preventDefault()
			if(from === 'order'){
				var curUrl = commonUtils.getUrlParam('curUrl');
				window.location.href = '${host.base}/hepet/addAddr?id='+$(this).data("id")+'&curUrl='+curUrl+'&from=order';
			}else {
				var curUrl = window.location.href.split("?")[0];
				window.location.href = '${host.base}/hepet/addAddr?id='+$(this).data("id")+'&curUrl='+encodeURIComponent(curUrl);
			}
			
		})

		$(".address-list").on("click", ".delete", function(e) {
			e.preventDefault()
			$.ajax({
				url: '${host.base}/hepet/address/delete',
				type:'post',
				dataType: 'json',
				data: {"id": $(this).data("id")},
				success: function(data) {
					console.log(data)
					if(data.head.code == '0000') {
						$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000});
						window.location.reload();
					} else {
						$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
					}
				}

			})
		})
		$(".address-list").on("click", '.touchBtn', function(e) {	
			e.preventDefault()		
			if(from=="order"){
				var curUrl = decodeURIComponent(commonUtils.getUrlParam('curUrl'));
				$(this).parents(".address-card").addClass("touch");
				defaultId = $(this).parents(".address-card").data("id");
				var str = commonUtils.changeURLArg(curUrl, 'from', 'addresslist');
				str = commonUtils.changeURLArg(str, 'addid', defaultId);
				window.location.href = str
			}
		})
	})
	function initAddressList(data) {
		var str = ''
		if(data.length <=0) {
			$("#btn").hide();
			str += '<div class="no-address">';
			str += '<p class="add-icon"></p>';
			str += '<p>点击添加地址</p></div>';  
			str += '<div class="gray-line"></div>'; 
			$("body").prepend($(str));
		} else {
			data.map(function(item, index) {
				str += '<div class="address-card" data-id="'+ item['id'] +'">';
				str += '<div class="card-top touchBtn">';
				str += '<p><b>'+ item['contact'] +'</b><span>'+ item['phone'] +'</span></p>';
				str += '<p class="addressdesc">'+ item['area'] + item['address'] +'</p>';
				str += '</div>';
				str += '<div class="card-bottom">';
				str += '<p data-id="'+ item['id'] +'" class="edite"><i class="edite-icon icon"></i><span>编辑</span></p>';
				str += '<p data-id="'+ item['id'] +'" class="delete"><i class="delete-icon icon"></i><span>删除</span></p>';            
				str += '</div></div>'
			})

			$(".address-list").append($(str));
		}
		
    
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