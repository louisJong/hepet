<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>物流信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/my.css">
	<!-- 引入js -->
  <script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
  <script type="text/javascript" src="${host.js}/common.js"></script>
</head>
<body style="background: #f5f5f5;">
<div class="list">
</div> 
 <footer></footer>
</body>
<script type="text/javascript">
$(function() {
	alert(JSON.stringify('${list}'))
    $("footer").append(new Footer([{text:'精选推荐', code:'jxtj', link:'${host.base}/hepet/index'},{text: '商品分类', code:'spfl', link:'${host.base}/hepet/mall'}, {text: '我的', code:'wode', link:'${host.base}/hepet/my'}], 2).init());
});
</script>
</html>