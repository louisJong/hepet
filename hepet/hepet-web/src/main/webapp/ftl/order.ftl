<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>订单信息确认</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}2">
	<link rel="stylesheet" type="text/css" href="${host.css}/order.css?v=${host.version}2">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src='${host.js}/common.js?v=${host.version}'></script>
</head>
<body style="background: #f1f4f6">
<!-- 收货信息	 -->
<div id="addressbox" class="address" style="background: #fff;">
	<div class="left">
		<i class="icon peisong "></i><span>收货人:</span>
	</div>
	<div class="center" id='address'></div>
	<div class="right" style="display: none;"><i class="icon-right"></i></div>
</div>
<!-- 商品信息 -->
<div class="prod" style="background: #fff; margin-top: 10px;">
	<div class="left">
		<img style="height: 80px;" src="${goods.listImgUrl}">
	</div>
	<div class="center">
		<p class="pd-name">${goods.goodsName}</p>
		<div class="pd-info">
			<p>发货：${goods.sendType}</p>
		</div>
		<p class="pd-price" style="color: #000;">¥ <span style="font-size: 20px;">${funUtils.formatAmt(goods.price)} </span></p>
	</div>
</div>
<!-- 支付信息 -->
<div class="payinfo" style="background: #fff; margin-top: 10px;" >
	<div class="item">
		<div class="left">分期付款</div>
		<div class="right font-base" style="font-size: 20px;">${goods.period}期</div>
	</div>
	<div class="item">
		<div class="left">每期支付</div>
		<div class="right font-base" style="font-size: 20px;">¥ ${funUtils.formatAmt(goods.pricePerPeriod)}</div>
	</div>
</div>
<div class="payinfo" style="background: #fff; margin-top: 10px;" >
	<div class="item">
		<div class="left">支付方式</div>
		<div class="right" style="line-height: 22px; color: #4a4a4a;">
		中银消费钱包<br/><span style="font-size: 14px;">剩余额度¥ ${funUtils.formatAmt(availAmt)}</span>
		</div>
	</div>
</div>
<!-- 支付按钮 -->

<#if availAmt < goods.price> 
<div class="btn-box">
	<div class="normal-btn disable" id="confirmBtn">确认</div>
</div>
<div style="font-size: 16px; color: #f06059; margin-top: 16px; text-align: center;">您的当前额度不足，无法分期该产品</div>
<#else>
<div class="btn-box">
	<div class="normal-btn" id="confirmBtn">确认</div>
</div>
</#if>
<div style="display: none;" id="payh5">
</div>
<script type="text/javascript">
    var address = '';
	<#if address??>
	address = ${address};	
	</#if> 
	var from = commonUtils.getUrlParam('from') || '';
  
  $(function() {
  	initAdd();
  	if(!address || ${availAmt} < ${goods.price} ) {
			$("#confirmBtn").addClass("disable");
  	} else {
 			$("#confirmBtn").removeClass("disable");
  	}
  	$("#confirmBtn").on("click", confirm);
  })
  function confirm() {
	  if($(this).hasClass("disable")){
	  	return;
	  }
	  // 成功跳转
		var params = {
	      goodsId:#{goodsId}
	    }; 
	    if(address){
	    	params.addId = address.id;
	    }
	  	$.ajax({
	  		url:'${host.base}/hepet/order/pay',
	  		type: 'post',
	  		dataType: 'json',
	  		data: params,
	  		success: function(data) {
	  			$(".mask").hide();
	  			if(data.head.code == '0000') {
	  					$("#payh5").html(data.body.payHtml);
	  					$("#payh5").show();
	  			} else {
	  				$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
	  			}
	  		}
	  	})
  }

  //初始地址
  function initAdd() {
  	var str = ''
  	var curUrl = encodeURIComponent(window.location.href)
  	if(!address) {
       str+= '<a style="line-height: 35px; color: #4a4a4a; width: 100%; text-align: center;" href="${host.base}/hepet/addAddr?from=order&curUrl='+curUrl+'">+ 添加收货地址</a>'
	   $("#addressbox").html("");
	   $("#addressbox").append($(str));
  	} else {
  		var curAdd = address
  		str+= '<p class="add-name">'+ curAdd['contact'] +' '+ curAdd['phone'] +'</p>';
  		str+= '<div class="add-add">';
  		str+= '<p>'+ curAdd['area'] +'</p>';
  		str+= '<p>'+ curAdd['address'] +'</p>';
  		str+= '</div>';  		
		$("#address").append($(str));
  	}
  	
    //如果地址列表超过1个 可点击到地址列表页切换
    if(${hasMore}==1) { 
    	$(".address").find(".right").show(); 
    	$("#address").on("click" , function() {
    		window.location.href = '${host.base}/hepet/addresses?from=order&addid=${addid}&curUrl='+curUrl;
    	})
    }
  }

</script>

<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1273825318'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s13.cnzz.com/z_stat.php%3Fid%3D1273825318' type='text/javascript'%3E%3C/script%3E"));</script>

</body>
</html>
