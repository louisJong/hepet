<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>海贝</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/swiper.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/home.css">
	<!-- 引入js -->
  <script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
  <script src="${host.js}/swiper.jquery.min.js"></script>
	<script src="${host.js}/common.js"></script>
</head>
<body style="padding-bottom: 60px;">
	<!-- 广告页 -->
	<div class="page-ad"></div>
	<!-- 国际大牌折扣季 -->
	<div class="page-discount"></div>
	<!-- 分类 -->
	<div class="page-class"></div>
	<!-- 简介 -->
	<div class="page-info"></div>
	<!-- 告知 -->
	<div class="page-law">
		<p>
		订单从保税仓发出，自物流公司<br/>		  
		</p>
	</div>
	<footer></footer>
	<script type="text/javascript">
		var banners = ${banners};
	    var categorys = ${categorys};
		$(function() {
				$("footer").append(new Footer([{text:'精选推荐', code:'jxtj', link:'${host.base}/hepet/index'},{text: '商品分类', code:'spfl', link:'${host.base}/hepet/mall'}, {text: '我的', code:'wode', link:'${host.base}/hepet/my'}], 0).init());
			initBanner();
	    //国际大牌折扣季
		  var mySwiper = new Swiper ('.swiper-container', {
			    direction: 'horizontal',
			    loop: true,
			    
			    // 如果需要分页器
			    pagination: '.swiper-pagination'
			}) 
      //初始化分类区域
			initCategorys();
			//initAdPage();
			initInfoPage();
		})
    //banner区域
		function initBanner() {
			banners = banners || [];
			if(banners.length <= 0) {
				return;
			}		
		  var str = '<div class="title">国际大牌折扣季</div>';
			str += '<div class="banner swiper-container">';
      str += '<div class="swiper-wrapper">';
      banners.map(function(item, index){
      	str += '<a class="swiper-slide" href="'+ item['bannerLink'] +'"><img src="'+ item['imgUrl'] +'"></a>'
      })
  
      str += '</div><div class="swiper-pagination"></div></div>';
      $(".page-discount").append($(str));		

		}
		//分类区域绘制
		function initCategorys() {
			var _categorys = categorys || [];
			if(_categorys.length <= 0) {
				return;
			} 
			var str = '';
			_categorys.map(function(item, index) {
				str += '<a class="class-item" href="${host.base}/hepet/mall?categoryCode='+item['code'] +'">';
				str += '<i class="class-icon '+ item['code'] +'"></i>';
				str += '<span>'+ item['name'] +'</span>';
				str += '</a>';
			});
			$(".page-class").append($(str));
		}
    //广告页
		function initAdPage() {
			var str = '<div class="ad-item">';
			    str += '<div class="ad-title">Beats Solo3 Wireless <br/> 头戴式耳机 - 金色</div>';
			    str += '<div class="ad-subtit">潮流利器，无线聆听，感受音乐无拘束</div>';
			    str += '<div class="ad-img"><img src="${host.img}/ad/ad_01.png"/></div>';
			    str += '<div class="ad-price">RMB 1,699</div>';
			    str += '<div class="ad-subprice">分期低至 每月170.59</div>';
			    str += '</div>';
      var $dom = str + str + str;
      $(".page-ad").append($($dom));		 
		}
    //下面信息区域
		function initInfoPage() {
			var itemArr = [
				{title: '100%免费配送', subtit: '在所支持范围内（基本全国）完全免除配送费', icon: 'peisong'},
			  {title: '100%正品保证', subtit: '正品保障 假一赔十', icon: 'zhengping'},
			  {title: '30天无忧退货', subtit: '国内退货 售后无忧', icon: 'tuihuo'}
			]

			var title = '<div class="info-title"><img src="${host.img}/logo.png"></div>';
			var list = '<div class="info-list">';
			itemArr.map(function(item, index) {
				var itemStr = '<div class="list-item">';
				  itemStr += '<i class="icon '+ item['icon'] +'"></i>';
          itemStr += '<div class="right">';
          itemStr += '	<p class="item-title">'+ item['title'] +'</p>';
          itemStr += '<p class="item-subtit">'+ item['subtit'] +'</p>';
          itemStr += '</div>';
          itemStr += '</div>';
        list += itemStr;
			})
			list += '</div>';
      $(".page-info").append($(title)).append($(list));
		}
	</script>
</body>
</html>