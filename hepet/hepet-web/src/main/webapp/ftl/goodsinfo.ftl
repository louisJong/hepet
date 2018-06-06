<#import "/common/funUtils.ftl" as funUtils>
<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/swiper.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}">
	<link rel="stylesheet" type="text/css" href="${host.css}/prodinfo.css">
	<!-- 引入js -->
  <script src="${host.js}/jquery-1.11.3.min.js"></script>
  <script src="${host.js}/swiper.jquery.min.js"></script>
  <script src="${host.js}/common.js"></script>
  <style>
  .pd-detail {width: 100%;}
  .media-wrap {width: 100%;}
  .media-wrap img{width: 100%;}
  #pd-detail img{width: 100%;}
  </style>
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
	<span class="small-font" >￥</span><span>${funUtils.formatAmt(goodsInfo.price)} </span> <span class="market">￥${funUtils.formatAmt(goodsInfo.marketPrice)}</span>
	<#--  <span class="icon">${goodsInfo.tags}</span>  -->
</div>
<!-- 商品名称及副标题 -->
<div class="pd-name">
	<p class="title">${goodsInfo.goodsName}</p>
	<p class="subtit">${goodsInfo.subDesc}</p>
</div>
<!-- 说明 -->
<div class="pd-info">
	<#--  <p>说明：<span>${goodsInfo.descript?default('无')}</span></p>  -->
	<p>产地：<span>${goodsInfo.region?default('无')}</span></p>
	<p>发货：<span>${goodsInfo.sendType?default('无')}</span></p>
</div>
<!-- 图文详情	 -->
<div class="pd-detail">
	<div class="title">以下为图文详情</div>
	<div id="pd-detail" style="margin-top: 10px">
		<p>${goodsInfo.proDetail}</p>
	</div>
</div>
<!-- 购买按钮 -->
<div class="buy-button">
	<div class="amount">￥<font size=5>${goodsInfo.pricePerPeriod}</font>	 x ${goodsInfo.period}期</div>
	<div class="buy">去分期</div>
</div>
<script type="text/javascript">
var imgs = '${goodsInfo.detailImgUrls}';
var banners = imgs.split(",");

function initBanners() {
	var str = ''
	banners.map(function(item, index) {
    str += '<div class="swiper-slide"><img src="'+ item +'"/></div>'
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
    pagination: '.swiper-pagination',
	paginationType: 'fraction',
	}) 
	$(".buy").click(function(){
		$.ajax({
			url: '${host.base}/hepet/user/acctStatus',
			type:'post',
			dataType: 'json',
			data:{
			'goodsId':${goodsId}
			},
			success: function(data) {
				console.log(data)
				if(data.head.code == '0000') {
					if("SUCC" == data.body.applyStatus){
						$.mask({
							type:'loading',
							imageSrc: '${host.img}/loading.gif',
							loadingStatus: 'show',
						})
						window.location.href = '${host.base}/hepet/orderConfirm?goodsId=${goodsId}&num=1';
					}else if("ING" == data.body.applyStatus){
						$.mask({type:'alert', alertTips: '用户贷款审批中，请耐心等待！', alertTime: 2000});
					}else{
						if(confirm("去申请？")){
							var script = $(data.body.funcSc);   //创建script标签
							$('body').append(script);   //将标签插入body尾部
							//window.location.href = '${host.base}'+data.body.redirectUrl;
						}
					}
					
				} else if(data.head.code == '0001'){
					if(confirm("去登录？")){
						var script = $(data.body.funcSc);   //创建script标签
						$('body').append(script);   //将标签插入body尾部
					}
				} else {
					$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000});
				}
			}

		})
	
		<#--
		$.mask({
			type:'loading',
			imageSrc: '${host.img}/loading.gif',
			loadingStatus: 'show',
		})
		window.location.href = '${host.base}/hepet/orderConfirm?goodsId=${goodsId}&num=1';
		-->
	});
})
</script>
</body>
</html>