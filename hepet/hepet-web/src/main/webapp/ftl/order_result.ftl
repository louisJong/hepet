<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>下单详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/order.css">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src='${host.js}/common.js'></script>
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
<div class="mask" style="display: none;">
    <div class="mask-wrapper">
      <div class="mask-header">
        <p>请输入分期支付验证码</p>
        <div class="bar"></div>
      </div>
      <div class="mask-body">
		<input type="text" id="codeInput">
		<div class="sendcode" id="sendCode">发送验证码</div>
        
      </div>
      <div class="mask-footer">
        <di class="cancle button" id="maskCancle">取消</di>
        <di class="ok button" id="maskOk">确定</di>
      </div>
    </div>
</div>
<#if orderInfo.status=='NOPAY'>
	<div class="btn-box">
		<div class="normal-btn" id="confirm">支付</div>
	</div> 
<#elseif orderInfo.status=='NORECEIVE' || orderInfo.status=='SUCCESS'>
	<div class="btn-box">
		<div class="normal-btn" id="look" onclick="window.location.href='${host.base}/hepet/order/kd_query?orderId=${orderInfo.id}'">查看物流</div>
	</div> 
</#if>
<script>
	$("#confirm").on("click", confirm);
	$("#look").on("click", function(){
		// do sometiong
	})
	var countDown = 120,
    time = null;
	// 浮层禁止滑动
	$(".alert-mask, .mask").on("touchmove", function(e) {
		e.preventDefault();
	})
	$("#maskCancle").on("click", function() {
		$(".mask").hide();
	})
	// 成功跳转
	$("#maskOk").on("click", function() {
		if($("#codeInput").val().length == 0){
			$.mask({type:'alert', alertTips: "验证码不能为空", alertTime: 2000});
			return;
		}
		var params = {
	      orderId:${orderInfo.id},
	      dynamicPwd:$("#codeInput").val()
	    }; 
	  	$.ajax({
	  		url:'${host.base}/hepet/order/payAgain',
	  		type: 'post',
	  		dataType: 'json',
	  		data: params,
	  		success: function(data) {
	  			if(data.head.code == '0000') {
					window.reload();
	  			} else {
	  				$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
	  			}
	  		}
	  	})
	})
	// 发送验证码
	$("#sendCode").on('click', onSendCode);
	//发送验证码事件
	function onSendCode() {
    if ($(this).hasClass('disabled')) {
      return;
    }
    sendMesAjax(function(data) {
	  $.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
      sendMessage();
    });
  }
  //发送验证码的ajax事件
  function sendMesAjax(onSuss) {
  	$.ajax({
  		url:'${host.base}/hepet/order/getPaySmsCode',
  		type: 'post',
  		dataType: 'json',
  		data: {'goodsId' : 21},
  		success: function(data) {
  			if(data.head.code == '0000') {
				onSuss(data)
			} else {
  				$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
  			}
  		}
  	})
  }
  //发送验证码倒计时事件
  function sendMessage() {
    $("#sendCode").addClass('disabled');
    $("#sendCode").html(countDown + "秒");
    if (time) window.clearInterval(time);
    time = window.setInterval(setRemainTime, 1000);

    function setRemainTime() {
      if (countDown == 0) {
        window.clearInterval(time); //停止计时器
        $("#sendCode").removeClass('disabled'); //启用按钮
        $("#sendCode").html("发送验证码");
      } else {
        countDown--;
        $("#sendCode").html(countDown + "秒");
      }
    }
  }
  function confirm() {
	  $(".mask").show();
  }
</script>
</body>
</html>