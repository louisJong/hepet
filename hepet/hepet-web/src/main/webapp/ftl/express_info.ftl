<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>物流信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/express_info.css?1">
	<!-- 引入js -->
  <script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
  <script type="text/javascript" src="${host.js}/common.js"></script>
</head>
<body>
	<div class="header">
        <div class="left"></div>
        <div class="right">
            <p>物流公司: 圆通快递</p>
            <p>物流单号: 7827497949434935</p>
        </div>
    </div>
    <div class="gray-line"></div>
    <div class="mh-list">
        	<#if list>
        	 <ul>
        		<#list list as item > 
        			 <#if item_index==0>
        				<li class="first">
        			 <#else>
        				<li>
        			 </#if>
        			 	<div class="left">
        			 			<#list item.time?split(" ") as num>
        			 				<p>
        			 					${num}
        			 				</p>
        			 			</#list>
        			 	</div>
        			 	<div class="right">
        			 		<p>${(item.status)}</p>
        			 	</div>
        			 	</li>
        		</#list>
        	 </ul>
        	<#else>
				<p class='no'>暂无物流信息</p>
        	</#if>
    </div>
</body>
</html>