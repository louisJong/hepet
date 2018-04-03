<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>下单详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/order_detail.css">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src='${host.js}/common.js'></script>
</head>
<body style="padding-bottom: 20px;">
<!-- 订单详情	 -->
<main>
	<#--  <div class="order-status">
		<#if orderInfo.status=='NOPAY'>
			<p class="status">订单待支付 </p>
		<#elseif orderInfo.status=='NOSEND'>
			<p class="status">订单支付成功！</p>
			<p class="tips">正在为你备货中，稍后请关注订单状态&快递信息</p>
		<#elseif orderInfo.status=='NORECEIVE'>
			<p class="status">订单已发货</p>
		<#elseif orderInfo.status=='CLOSED'>
			<p class="status">订单已关闭</p>
		<#elseif orderInfo.status=='CANCEL'>
			<p class="status">订单已取消</p>
		<#elseif orderInfo.status=='REFUND'>
			<p class="status">订单已退货</p>
		<#elseif orderInfo.status=='SUCCESS'>
			<p class="status">订单完成</p>
		</#if>
	</div>  -->

	<div class="address">
			<div class="left">
					<p>
							<b>${orderInfo.contact}</b>
							<span>${orderInfo.phone}</span>
					</p>
					<p>${orderInfo.address}</p>
			</div>
	</div>
	<div class="gray-line"></div>
 	<div class="prod-info">
        <div class="top">
            <p>产品信息</p>
						<#if orderInfo.status=='NOPAY'>
							<p class="status no-pay">待付款 </p>
						<#elseif orderInfo.status=='NOSEND'>
							<p class="status">支付成功</p>
						<#elseif orderInfo.status=='NORECEIVE'>
							<p class="status send">已发货</p>
						<#elseif orderInfo.status=='CLOSED'>
							<p class="status">已关闭</p>
						<#elseif orderInfo.status=='CANCEL'>
							<p class="status">已取消</p>
						<#elseif orderInfo.status=='REFUND'>
							<p class="status">已退货</p>
						<#elseif orderInfo.status=='SUCCESS'>
							<p class="status">订单完成</p>
						</#if>
        </div>
        <div class="bottom">
            <div class="img">
                <img src="${orderInfo.listImgUrl}">
            </div>
            <div class="right">
                <p class="title">${orderInfo.goodsName}</p>
                <p class="tags">${orderInfo.subDesc}</p>
                <p class="price">¥ ${orderInfo.price}</p>
            </div>
						<#if orderInfo.status=='NORECEIVE'>
							<div class="logic light" onclick="window.location.href='${host.base}/hepet/order/kd_query?orderId=${orderInfo.id}'">查看物流</div>
						<#elseif orderInfo.status=='SUCCESS'>
							<div class="logic" onclick="window.location.href='${host.base}/hepet/order/kd_query?orderId=${orderInfo.id}'">查看物流</div>
						</#if>
           </div>
    </div>
		<div class="gray-line"></div>
    <div class="order-info">
        <p><span>支付方式</span><b>中银消费钱包额度预付</b></p>
        <p><span>产品金额</span><b>${orderInfo.price}元</b></p>
        <p><span>分期方式</span><b>${orderInfo.period}期</b></p>
        <p><span>还款方式</span><b>等额本息，每月还款</b></p>
        <p><span>还款计划</span><b>每期还${funUtils.formatAmt(orderInfo.pricePerPeriod)}元</b></p>
        <p><span>还款日</span><b>每月15日</b></p>
        <div style="width: 100%; border-bottom: 1px solid #ddd; margin-top: 10px; margin-bottom: 10px;"></div>
        <p><span>订单编号</span><b>${orderInfo.orderNum}</b></p>
        <p><span>下单时间</span><b>${orderInfo.createTime?string("yyyy-MM-dd HH:mm:ss")}</b></p>
				<#if orderInfo.status=!'NOPAY'>
				<p><span>支付时间</span><b>${orderInfo.payTime?string("yyyy-MM-dd HH:mm:ss")}</b></p>
				</#if>
    </div>		
	</div>
</main>
<!-- 查看订单按钮 -->
	<#if orderInfo.status =='NORECEIVE'>
<div class="btn-box">
	<div class="normal-btn" id="confirmReceipt">确认收货</div>
</div>
<#elseif orderInfo.status=='NOPAY'>
<div class="fix-button">
        <div class="left" id='cancel'>取消订单</div>
        <div class="right" id="confirm">立即支付</div>
    </div>
</#if>
<div style="display: none;" id="payh5">
</div>
<script>
	$("#confirm").on("click", confirm);
	$("#cancel").on("click", cancel);
	
	function confirm() {
	  var params = {
	      orderId:${orderInfo.id}
	    }; 
	  	$.ajax({
	  		url:'${host.base}/hepet/order/payAgain',
	  		type: 'post',
	  		dataType: 'json',
	  		data: params,
	  		success: function(data) {
	  			if(data.head.code == '0000') {
	  					$("#payh5").html(data.body.payHtml);
	  					$("#payh5").show();
	  			} else {
	  				$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
	  			}
	  		}
	  	})
 	}
	
	// 确认收货
	$("#confirmReceipt").on("click", confirmReceipt);

	// 取消订单
	function cancel() {
		$.ajax({
			url:'${host.base}/hepet/order/cancel',
  		type: 'post',
  		dataType: 'json',
  		data: {'orderId' : ${orderInfo.id}},
			success: function(data) {
				if(data.head.code == '0000') {
						$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
						setTimeout(function(){window.location.reload()},2000);
	  			} else {
	  				$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
	  			}
			}
		})
	}

	// 确认收货
	function confirmReceipt() {
		$.mask({
			type:'normal', 
			title: '是否确认收货?', 
			text: '', 
			buttons: [
				{buttonTxt: '取消' }, 
				{	buttonTxt: '确认', 
					buttonFn: function() {
						$.ajax({
							url:'${host.base}/hepet/order/finish',
							type: 'post',
							dataType: 'json',
							data: {'orderId' : ${orderInfo.id}},
							success: function(data) {
								if(data.head.code == '0000') {
										$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
										setTimeout(function(){window.location.reload()},2000);
									} else {
										$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
									}
							}
						})
					}
				}]
			});
		
	}

</script>
</body>
</html>