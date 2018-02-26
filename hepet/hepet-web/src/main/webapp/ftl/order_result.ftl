<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>下单详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/order_detail.css">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src='${host.js}/common.js'></script>
</head>
<body style="padding-bottom: 20px;">
<!-- 订单详情	 -->
<main>
	<#--  <div class="order-status">
		<#if orderInfo.status=='NOPAY'>
			<p class="status">订单待支付 </p>
		<#elseif orderInfo.status=='NOSEND'>
			<p class="status">订单支付成功！</p>
			<p class="tips">正在为你备货中，稍后请关注订单状态&快递信息</p>
		<#elseif orderInfo.status=='NORECEIVE'>
			<p class="status">订单已发货</p>
		<#elseif orderInfo.status=='CLOSED'>
			<p class="status">订单已关闭</p>
		<#elseif orderInfo.status=='CANCEL'>
			<p class="status">订单已取消</p>
		<#elseif orderInfo.status=='REFUND'>
			<p class="status">订单已退货</p>
		<#elseif orderInfo.status=='SUCCESS'>
			<p class="status">订单完成</p>
		</#if>
	</div>  -->

	<div class="address">
			<div class="left">
					<p>
							<b>${orderInfo.contact}</b>
							<span>${orderInfo.phone}</span>
					</p>
					<p>${orderInfo.address}</p>
			</div>
	</div>
	<div class="gray-line"></div>
 	<div class="prod-info">
        <div class="top">
            <p>产品信息</p>
						<#if orderInfo.status=='NOPAY'>
							<p class="status no-pay">待付款 </p>
						<#elseif orderInfo.status=='NOSEND'>
							<p class="status">支付成功</p>
						<#elseif orderInfo.status=='NORECEIVE'>
							<p class="status send">已发货</p>
						<#elseif orderInfo.status=='CLOSED'>
							<p class="status">已关闭</p>
						<#elseif orderInfo.status=='CANCEL'>
							<p class="status">已取消</p>
						<#elseif orderInfo.status=='REFUND'>
							<p class="status">已退货</p>
						<#elseif orderInfo.status=='SUCCESS'>
							<p class="status">订单完成</p>
						</#if>
        </div>
        <div class="bottom">
            <div class="img">
                <img src="${orderInfo.listImgUrl}">
            </div>
            <div class="right">
                <p class="title">${orderInfo.goodsName}</p>
                <p class="tags">${orderInfo.subDesc}</p>
                <p class="price">¥ ${orderInfo.price}</p>
            </div>
						<#if orderInfo.status=='NORECEIVE'>
							<div class="logic light" onclick="window.location.href='${host.base}/hepet/order/kd_query?orderId=${orderInfo.id}'">查看物流</div>
						<#elseif orderInfo.status=='SUCCESS'>
							<div class="logic" onclick="window.location.href='${host.base}/hepet/order/kd_query?orderId=${orderInfo.id}'">查看物流</div>
						</#if>
           </div>
    </div>
		<div class="gray-line"></div>
    <div class="order-info">
        <p><span>支付方式</span><b>花样金信用额度预付</b></p>
        <p><span>产品金额</span><b>${orderInfo.price}元</b></p>
        <p><span>分期方式</span><b>${orderInfo.period}期</b></p>
        <p><span>还款方式</span><b>等额本息，每月还款</b></p>
        <p><span>还款计划</span><b>每期还${funUtils.formatAmt(orderInfo.pricePerPeriod)}元</b></p>
        <p><span>还款日</span><b>每月15日</b></p>
        <div style="width: 100%; border-bottom: 1px solid #ddd; margin-top: 10px; margin-bottom: 10px;"></div>
        <p><span>订单编号</span><b>${orderInfo.orderNum}</b></p>
        <p><span>下单时间</span><b>${orderInfo.createTime?string("yyyy-MM-dd HH:mm:ss")}</b></p>
				<#if orderInfo.status=!'NOPAY'>
				<p><span>支付时间</span><b>${orderInfo.payTime?string("yyyy-MM-dd HH:mm:ss")}</b></p>
				</#if>
    </div>		
	</div>
</main>
<!-- 查看订单按钮 -->
	<#if orderInfo.status!='NOPAY'>
<#--  <div class="btn-box">
	<div class="normal-btn" onclick="window.location.href='${host.base}/hepet/myOrders'">查看我的订单</div>
</div>  -->
<#elseif orderInfo.status=='NOPAY'>
<div class="fix-button">
        <div class="left" id='cancel'>取消订单</div>
        <div class="right" id="confirm">立即支付</div>
    </div>
</#if>

<div class="mask" style="display: none;">
    <div class="mask-wrapper">
      <div class="mask-header">
        <p>输入短信验证码</p>
        <p class="tips">验证码已发送至您的手机${tel}</p>
      </div>
      <div class="mask-body">
				<input type="text" id="codeInput" placeholder="6位数字">
				<div class="sendcode" id="sendCode">发送验证码</div>	
        
      </div>
      <div class="mask-footer">
        <di class="cancle button gray" id="maskCancle">取消</di>
        <di class="ok button" id="maskOk">确定</di>
      </div>
    </div>
  </div>
<script>
	$(function(){
		$("#codeInput").on("input propertychange", function(){
			$(this).val($(this).val().replace(/[^\d]/g,'').substr(0,6));
		})
	})
	$("#confirm").on("click", confirm);
	$("#cancel").on("click", cancel);

	function cancel() {
		$.ajax({
			url:'${host.base}//hepet/order/cancel',
  		type: 'post',
  		dataType: 'json',
  		data: {'orderId' : ${orderInfo.id}},
			success: function(data) {
				if(data.head.code == '0000') {
						$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
						window.location.reload();
	  			} else {
	  				$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
	  			}
			}
		})
	}


	var countDown = 120,
    time = null,
    hasClickSend = false;
	// 浮层禁止滑动
	$(".alert-mask, .mask").on("touchmove", function(e) {
		e.preventDefault();
	})
	$("#maskCancle").on("click", function() {
		$(".mask").hide();
	})
	// 成功跳转
	$("#maskOk").on("click", function() {
		if(!hasClickSend){
			$.mask({type:'alert', alertTips: "请先发送验证码", alertTime: 2000});
			return;
		}
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
						$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
						window.location.reload();
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
      hasClickSend = true;
    });
  }
  //发送验证码的ajax事件
  function sendMesAjax(onSuss) {
  	$.ajax({
  		url:'${host.base}/hepet/order/getPaySmsCode',
  		type: 'post',
  		dataType: 'json',
  		data: {'goodsId' : ${orderInfo.goodsId}},
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