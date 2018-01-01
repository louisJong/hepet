<#import "/common/funUtils.ftl" as funUtils>
<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/swiper.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/prodinfo.css">
	<!-- 引入js -->
  <script src="${host.js}/jquery-1.11.3.min.js"></script>
  <script src="${host.js}/swiper.jquery.min.js"></script>
</head>
<body style="background: #f5f5f5; padding-bottom: 60px;">
<!-- banner滚动 -->
<div class="banner swiper-container">
	<div class="swiper-wrapper">
	</div>
	<div class="swiper-pagination"></div>
</div>
<!-- 商品价格 -->
<div class="pd-price">
	<span class="small-font">￥</span><span>${funUtils.formatNumber(goodsInfo.price,'#,###')} </span> <span class="market">￥${funUtils.formatNumber(goodsInfo.marketPrice,'#,###')}</span>
	<span class="icon">${goodsInfo.tags}</span>
</div>
<!-- 商品名称及副标题 -->
<div class="pd-name">
	<p class="title">${goodsInfo.goodsName}</p>
	<p class="subtit">${goodsInfo.subDesc}</p>
</div>
<!-- 说明 -->
<div class="pd-info">
	<p>说明：<span>商品包税</span><span>假一赔十</span><span>7天无忧退货</span></p>
	<p style="text-indent: 3em"><span>不可使用优惠券</span><span>${goodsInfo.sendType}</span></p>
</div>
<!-- 图文详情	 -->
<div class="pd-detail">
	<div class="title">以下为图文详情</div>
	<div id="pd-detail">
		<p>${goodsInfo.proDetail}</p>
	</div>
</div>
<!-- 购买按钮 -->
<div class="buy-button">
	<div class="amount">￥<font size=5>${goodsInfo.pricePerPeriod}</font>	 x ${goodsInfo.period}期</div>
	<div class="buy">立即购买</div>
</div>
<script type="text/javascript">
var mySwiper = new Swiper('.swiper-container', {
    direction: 'horizontal',
    loop: true,
    autoHeight: true,
    
    // 如果需要分页器
    pagination: '.swiper-pagination'
}) 
var imgs = '${goodsInfo.detailImgUrls}';
var banners = imgs.split(",");

function initBanners() {
	var str = ''
	banners.map(function(item, index) {
    str += '<div class="swiper-slide"><img src="'+ item +'"></div>'
	})
	$(".swiper-wrapper").append($(str));
}
$(function() {
	initBanners();
	var mySwiper = new Swiper ('.swiper-container', {
    direction: 'horizontal',
    loop: true,
    autoHeight: true,
    // 如果需要分页器
    pagination: '.swiper-pagination'
	}) 
	$(".buy").click(function(){
		window.location.href = '${host.base}/hepet/orderConfirm?goodsId=${goodsId}&num=1';
	});
})
</script>
</body>
</html>