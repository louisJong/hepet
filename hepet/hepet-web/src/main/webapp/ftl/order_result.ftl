<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>下单成功</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="css/base.css">
	<link rel="stylesheet" type="text/css" href="css/order.css">
	<!-- 引入js -->
	<script type="text/javascript" src='js/jquery-1.11.3.min.js'></script>
</head>
<body id="orderResult">
<!-- 订单详情	 -->
<main>
	<div class="order-status">
		<p class="status">订单支付成功！</p>
		<p class="tips">正在为你备货中，稍后请关注订单状态&快递信息</p>
	</div>
	<div class="order-detail">
		<div class="item">
			<div class="left">商品</div>
			<div class="right font-weight">商品标题BOSE QuietComfort35 QC35 蓝牙降噪头戴耳机</div>
		</div>
		<div class="item">
			<div class="left">支付信息</div>
			<div class="right">
				<p class="font-base font-weight">月供 ￥123.45 | 总计12期</p>
				<p class="small-font font-gray">每月 7号还款即可</p>
			</div>
		</div>
		<div class="item">
			<div class="left">收货信息</div>
			<div class="right">
				<p class="font-weight">韦小宝 13800138000</p>
				<p class="small-font font-gray">上海市浦东新区<br/>张杨路500号华润时代广场16楼</p>
			</div>
		</div>
	</div>
</main>
<!-- 查看订单按钮 -->
<div class="btn-box">
	<div class="normal-btn">查看我的订单</div>
</div>
</body>
</html>