<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>海贝</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/swiper.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}">
	<link rel="stylesheet" type="text/css" href="${host.css}/index.css?2">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src="${host.js}/jquery.scrollLoading-min.js"></script>
	<script src="${host.js}/bscroll.min.js"></script>
	<script src="${host.js}/swiper.jquery.min.js"></script>
	<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1273825318'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s13.cnzz.com/z_stat.php%3Fid%3D1273825318' type='text/javascript'%3E%3C/script%3E"));var _czc = _czc || [];_czc.push(["_setAccount", "3D1273825318"]);</script>
	<script src="${host.js}/common.js?v=${host.version}"></script>
	<style>
        .nav-fixed {
            position: fixed;
            top: 0;
			z-index: 99;
        }

        .classify-box-fixed {
            margin-top: 40px;
        }
		.nav span {
			cursor: pointer; -webkit-tap-highlight-color: transparent;
		}
    </style>

</head>
<body style="padding-bottom: 60px;">
	<div>
		<div id="slide" class="slide" class="index-slide" alt="star">							
		</div>
		<div class="tags">
			<div class="tags-item">
				<span class="tag-icon"></span>
				<span>12期免息</span>
			</div>
			<div class="tags-item">
				<span class="tag-icon"></span>
				<span>48h发货</span>
			</div>
			<div class="tags-item">
				<span class="tag-icon"></span>
				<span>全场包邮</span>
			</div>
			<div class="tags-item">
				<span class="tag-icon"></span>
				<span>正品保障</span>
			</div>
		</div>

		<div  class="classify-tag nav">
			<div id="nav" style="width: 470px; display: flex;">
			</div>
		</div>

		<div class="classify-box" id="classify">
		</div>
		<div style="width: 100%; height: 70px;"></div>
		<footer></footer>
	</div>

    <script>      
        var gClassifyOffTops = [];
		var gNavs = ${categorys} || [] ;

		var BScroll = window.BScroll;
		scrollmethod = new BScroll('.nav', {
			eventPassthrough: true,
			scrollX: true,
			scrollY: false,
			preventDefault: false
		})
        $(function () {
			new Footer([{text:'精选推荐', code:'jxtj', link:'${host.base}/hepet/index'},{text: '商品分类', code:'spfl', link:'${host.base}/hepet/mall'}, {text: '我的', code:'wode', link:'${host.base}/hepet/my'}], 0).init();			
			initBanner(${banners});
			initNav(gNavs);
			loadProlist(gNavs);
            window.scrollTo(0, 0);
            var navOffTop = $(".nav").offset().top;
            $(window).scroll(function () { //页面加载时，获取滚动条初始高度
                var distance = document.documentElement.scrollTop || document.body.scrollTop; //获取滚动条初始高度的值 ：0
                if (distance >= navOffTop) {
                    $(".nav").addClass("nav-fixed");
                    $(".classify-box").addClass("classify-box-fixed");
                } else if (distance < navOffTop) {
                    $(".nav").removeClass("nav-fixed");
                    $(".classify-box").removeClass("classify-box-fixed");
                }				
                for (var j = 0; j < gClassifyOffTops.length; j++) {
                    if (distance > gClassifyOffTops[j] - 40 && distance <= gClassifyOffTops[j + 1] - 30) {
                        scrollmethod.scrollToElement(
                            document.querySelector('.nav span:nth-child(' + (j + 1) + ')'), null,
                            true, true)
                        $(".nav span").removeClass("actived");
                        $(".nav span:nth-child(" + (j + 1) + ")").addClass("actived");
                    }
                    if(!gClassifyOffTops[j + 1] && distance > gClassifyOffTops[j] - 40) {
                        $(".nav span").removeClass("actived");
                        $(".nav span:nth-child(" + (j + 1) + ")").addClass("actived");
						scrollmethod.scrollToElement(
                            document.querySelector('.nav span:nth-child(' + (j + 1) + ')'), null,
                            true, true)
                    }
                }
				
            })
        })


	    // banner初始化
		function initBanner(banners) {
			var str = "";
			if ( banners && banners.length > 0 ) {
				for ( var i = 0; i < banners.length; i++ ) {
					str += '<div class="href img" data-href="'+banners[i]["bannerLink"] +'">' ;
					str += '<img src="'+ banners[i].imgUrl+'" />' ;
					str += '<img class="imgblur" src="'+ banners[i].imgUrl+'" />'
					str += '</div>' ;
				}			
			}

			$("#slide").append($(str));
			$("#slide .href").on("click", function(){
				_czc.push(["_trackEvent",'商城点击banner', '商城banner点击', '', '', '']);
				window.location.href = $(this).data("href");
				
			})
		}

		// nav初始化
		function initNav(navs) {
			var str = '<span class="actived" data-id="sytj">推荐</span>';
			if ( navs && navs.length > 0 ) {
				for ( var i = 0; i < navs.length; i++ ) {
					str += '<span data-id="'+ navs[i]['code'] +'">'+ navs[i]["shotName"] +'</span>' ;
				}			
			}
			$("#nav").append($(str));
		}
		function loadProlist(gNavs) {
			$.ajax({
				url: '${host.base}/hepet/index/prolist',
				dataType: 'json',
				type: 'GET',
				success: function(data) {
					var str = "";
					if(data) {
						str += '<div class="classify-item sytj-item">';
						data["sytj"].map(function(item, index) {							
							str += initProdItem(item);							
						})
						str += '</div>';
						$("#classify").append($(str));
						gClassifyOffTops.push($(".sytj-item").offset().top)
						gNavs.map(function(ele, index) {
							var _str = '<div class="classify-item '+ele.code+'-item">';
							if(data[ele.code]) {
								data[ele.code].map(function(value,index) {
									_str += initProdItem(value);	
								})									
							}
							_str += '</div>';
							$("#classify").append($(_str));
							gClassifyOffTops.push($("."+ele.code+'-item').offset().top)
						})

						$("img.lazy").scrollLoading({
    						container: $(".classify-box"),							
						});
					}
				}
			})
		}
		function initProdItem(item) {
			var str = '<a class="prod-item" href="${host.base}/hepet/goodsInfo?goodsId='+ item.id +'">';
			str += '<div class="prod-item_img">';
			str += item.diffPrice && item.diffPrice > 0 ? '<div class="prod-item_tag">直降'+item.diffPrice+'</div>'	 : '';
			str += '<img class="lazy" src="${host.img}/loading.gif?1" data-url="'+ item.listImgUrl+'">';
			str += item.chooseReason ? '<div class="prod-item_prif">'+ item.chooseReason +'</div>' : '';
			str += item.isSellOut ? '<div class="prod-item_soldout"></div>' : '';
			str += '</div>';
			str += '<div class="prod-item_name">'+ item.goodsName +'</div>';
			str += '<div class="prod-item_price">¥'+ item.price +'<span>¥'+item.marketPrice+'</span></div>';
			str += '<div class="prod-item_period">¥'+ item.pricePerPeriod +' <span style="font-size: 12px;">/每期</span></div>';
			str += '</a>';
			return str;
		}

		$("body").on( "click", ".nav span", function () {
			$(".nav span").removeClass("actived");
			$(this).addClass("actived");
			var item = $(this).data("id") + "-item";
			var offsetHeight = $("."+item).offset().top;
			_czc.push(["_trackEvent",'商城切换产品类别', '商城切换产品类别', $(this).html(), '', '']);
			$('html, body').animate({
				scrollTop: offsetHeight - $(".nav").height() + 20
			}, 'slow');

			scrollmethod.scrollToElement(
				document.querySelector('.nav span:nth-child(' + $(this).index() + ')'), null, true, true)
		})


    </script>

	<script src="${host.js}/index.js?v=${host.version}"></script>

</body>
</html>