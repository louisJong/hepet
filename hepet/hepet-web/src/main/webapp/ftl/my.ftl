<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>我的账户</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}">
	<link rel="stylesheet" type="text/css" href="${host.css}/my.css?v=${host.version}">
	<!-- 引入js -->
  	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1273825318'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s13.cnzz.com/z_stat.php%3Fid%3D1273825318' type='text/javascript'%3E%3C/script%3E"));var _czc = _czc || [];_czc.push(["_setAccount", "3D1273825318"]);</script>
  	<script type="text/javascript" src="${host.js}/common.js?v=${host.version}"></script>
	<script>
		var curUrl = encodeURIComponent(window.location.href);
	</script>
</head>
<body style="background: #f5f5f5;">
<div class="list">
	<!-- 如果没有地址跳转add_address.html 有地址跳转address.html -->
	<a class="list-item" id="goToAddress">
		<div class="left">管理地址</div>
		<div class="right icon-right"></div>
	</a>
	<a class="list-item" href='${host.base}/hepet/myOrders'>
		<div class="left">我的订单</div>
		<div class="right icon-right"></div>
	</a>
</div> 
<div class="list">
	<a class="list-item" href='${host.base}/html/help.html'>
		<div class="left">帮助中心</div>
		<div class="right icon-right"></div>
	</a>
</div> 
 <footer></footer>
</body>



<script type="text/javascript">

$(function() {
	$("#goToAddress").on("click", function() {
		window.location.href="${host.base}/hepet/addresses?from=my&curUrl="+curUrl;
	})
    new Footer([{text:'精选推荐', code:'jxtj', link:'${host.base}/hepet/index'},{text: '商品分类', code:'spfl', link:'${host.base}/hepet/mall'}, {text: '我的', code:'wode', link:'${host.base}/hepet/my'}], 2).init();
});
</script>

</html>