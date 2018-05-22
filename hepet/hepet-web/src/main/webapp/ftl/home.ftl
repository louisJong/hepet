<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>海贝</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/swiper.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}">
	<link rel="stylesheet" type="text/css" href="${host.css}/index.css?v=${host.version}">
	<!-- 引入js -->
  <script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script src="https://unpkg.com/better-scroll/dist/bscroll.min.js"></script>
  <script src="${host.js}/swiper.jquery.min.js"></script>
	<script src="${host.js}/common.js?v=${host.version}"></script>
	<script src="${host.js}/index.js?v=${host.version}"></script>
	<style>
        .nav-fixed {
            position: fixed;
            top: 0;
			z-index: 99;
        }

        .classify-box-fixed {
            margin-top: 40px;
        }
    </style>
</head>
<body style="padding-bottom: 60px;">
	<div>

		<div id="slide" class="slide" class="index-slide" alt="star">
				<!-- 轮播图片数量可自行增减 -->
				<div class="img">
						<img src="${host.img}/1.png" />
						<img class="imgblur" src="${host.img}/1.png" />
				</div>
				<div class="img">
						<img src="${host.img}/2.png" />
						<img class="imgblur" src="${host.img}/1.png" />
				</div>
				<div class="img">
						<img src="${host.img}/3.png" />
						<img class="imgblur" src="${host.img}/1.png" />
				</div>
				<div class="img">
						<img src="${host.img}/4.png" />
						<img class="imgblur" src="${host.img}/1.png" />
				</div>
				<div class="img">
						<img src="${host.img}/5.png" />
						<img class="imgblur" src="${host.img}/1.png" />
				</div>
		</div>

		<div class="tags">
				<div class="tags-item">
						<span class="tag-icon"></span>
						<span>全场分期</span>
				</div>
				<div class="tags-item">
						<span class="tag-icon"></span>
						<span>最长12期</span>
				</div>
				<div class="tags-item">
						<span class="tag-icon"></span>
						<span>正品保证</span>
				</div>
		</div>

		<div class="classify-tag nav">
				<div style="width: 450px; display: flex;">
						<span class="actived">推荐</span>
						<span>美妆</span>
						<span>家电</span>
						<span>母婴</span>
						<span>户外</span>
						<span>服饰</span>
						<span>奢侈品</span>
				</div>
		</div>

		<div class="classify-box">
				<div class="classify-item">
						<div class="prod-item">
								<div class="prod-item_img">
									<div class="prod-item_tag">直降100</div>	
									<img src="${host.img}/6.png">
									<div class="prod-item_prif">人手必入系列</div>
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99 <span style="font-size: 12px;">x 12期</span></div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
				</div>
				<div class="classify-item">
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
				</div>
				<div class="classify-item">
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
				</div>
				<div class="classify-item">
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
				</div>
				<div class="classify-item">
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
				</div>
				<div class="classify-item">
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
				</div>

				<div class="classify-item">
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
								<div class="prod-item_img">
										<img src="${host.img}/6.png">
								</div>
								<div class="prod-item_name">四倍蚕丝面膜12片</div>
								<div class="prod-item_price">¥1399
										<span>¥1899</span>
								</div>
								<div class="prod-item_period">¥99</div>
						</div>
						<div class="prod-item">
										<div class="prod-item_img">
												<img src="${host.img}/6.png">
										</div>
										<div class="prod-item_name">四倍蚕丝面膜12片</div>
										<div class="prod-item_price">¥1399
												<span>¥1899</span>
										</div>
										<div class="prod-item_period">¥99</div>
								</div>
								<div class="prod-item">
												<div class="prod-item_img">
														<img src="${host.img}/6.png">
												</div>
												<div class="prod-item_name">四倍蚕丝面膜12片</div>
												<div class="prod-item_price">¥1399
														<span>¥1899</span>
												</div>
												<div class="prod-item_period">¥99</div>
										</div>
				</div>

				<div style="width: 100%; height: 70px;"></div>
				<footer></footer>
		</div>
	</div>

    <script>      
        var arr = [];
        for (var i = 0; i < $(".classify-item").length; i++) {
            arr.push($(".classify-item:nth-child(" + (i + 1) + ")").offset().top);
        }
        $(function () {
			 $("footer").append(new Footer([{text:'精选推荐', code:'jxtj', link:'${host.base}/hepet/index'},{text: '商品分类', code:'spfl', link:'${host.base}/hepet/mall'}, {text: '我的', code:'wode', link:'${host.base}/hepet/my'}], 0).init());
			
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


                for (var j = 0; j < arr.length; j++) {
                    if (distance > arr[j] - 40 && distance <= arr[j + 1] - 30) {
                        scrollmethod.scrollToElement(
                            document.querySelector('.nav span:nth-child(' + (j + 1) + ')'), null,
                            true, true)
                        $(".nav span").removeClass("actived");
                        $(".nav span:nth-child(" + (j + 1) + ")").addClass("actived");
                    }
                    if(!arr[j + 1] && distance > arr[j] - 40) {
                        $(".nav span").removeClass("actived");
                        $(".nav span:nth-child(" + (j + 1) + ")").addClass("actived");
                    }
                }
            })
        })


        var BScroll = window.BScroll;
        scrollmethod = new BScroll('.nav', {
            eventPassthrough: true,
            scrollX: true,
            scrollY: false,
            preventDefault: false
        })

        $(".nav span").on("click", function () {

            $(".nav span").removeClass("actived");
            $(this).addClass("actived");

            var offsetHeight = $(".classify-item:nth-child(" + ($(this).index() + 1) + ')').offset().top;
			
            $('html, body').animate({
                scrollTop: offsetHeight - $(".nav").height() + 20
            }, 'slow');

            scrollmethod.scrollToElement(
                document.querySelector('.nav span:nth-child(' + $(this).index() + ')'), null, true, true)
        })
    </script>
</body>
</html>