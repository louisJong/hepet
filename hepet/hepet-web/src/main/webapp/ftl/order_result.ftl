<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>下单成功</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/order.css">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
</head>
<body id="orderResult">
<!-- 订单详情	 -->
<main>
	<div class="order-status">
		<#if orderInfo.status=='NOPAY'>
			<p class="status">订单待支付 </p>
		<#elseif orderInfo.status=='NOSEND'>
			<p class="status">订单支付成功！</p>
			<p class="tips">正在为你备货中，稍后请关注订单状态&快递信息</p>
		<#elseif orderInfo.status=='NORECEIVE'>
			<p class="status">订单已发货</p>
		<#elseif orderInfo.status=='CLOSED'>
			<p class="status">订单已关闭</p>
		<#elseif orderInfo.status=='REFUND'>
			<p class="status">订单已退货</p>
		<#elseif orderInfo.status=='SUCCESS'>
			<p class="status">订单完成</p>
		</#if>
	</div>
	<div class="order-detail">
		<div class="item">
			<div class="left">商品</div>
			<div class="right font-weight">${orderInfo.goodsName}</div>
		</div>
		<div class="item">
			<div class="left">支付信息</div>
			<div class="right">
				<p class="font-base font-weight">月供 ￥ ${funUtils.formatNumber(orderInfo.pricePerPeriod,'#,###')} | 总计12期</p>
				<p class="small-font font-gray">每月 15号还款即可</p>
			</div>
		</div>
		<div class="item">
			<div class="left">收货信息</div>
			<div class="right">
				<p class="font-weight">${orderInfo.contact} ${orderInfo.phone}</p>
				<p class="small-font font-gray">${orderInfo.address}</p>
			</div>
		</div>
		<#if orderInfo.status=='NORECEIVE' || orderInfo.status=='SUCCESS'>
			<div class="item">
			<div class="left">物流信息</div>
			<div class="right">
				<p class="font-weight">物流公司:${orderInfo.kdName}</p>
				<p class="small-font font-gray">快递单号:${orderInfo.kdNo}</p>
			</div>
		</div>
		</#if>
	</div>
</main>
<!-- 查看订单按钮 -->
<div class="btn-box">
	<div class="normal-btn" onclick="window.location.href='${host.base}/hepet/myOrders'">查看我的订单</div>
</div>
</body>
</html>