<#import "/common/host.ftl" as host>
<#import "/common/funUtils.ftl" as funUtils>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>订单信息确认</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/order.css">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src='${host.js}/common.js'></script>
</head>
<body>
<!-- 收货信息	 -->
<div class="address">
	<div class="left">
		<i class="icon peisong "></i><span>收货人:</span>
	</div>
	<div class="center" id='address'></div>
	<div class="right" style="display: none;"><i class="icon-right"></i></div>
</div>
<!-- 商品信息 -->
<div class="prod">
	<div class="left">
		<img src="${goods.listImgUrl}">
	</div>
	<div class="center">
		<p class="pd-name">${goods.goodsName}</p>
		<div class="pd-info">
			<p>发货：${goods.sendType}</p>
		</div>
		<p class="pd-price">RMB ${funUtils.formatNumber(goods.price,'#,###')} </p>
	</div>
</div>
<!-- 支付信息 -->
<div class="payinfo">
	<div class="item">
		<div class="left">分期付款</div>
		<div class="right font-base">RMB ${funUtils.formatNumber(goods.pricePerPeriod,'#,###')}  X ${goods.period}期</div>
	</div>
	<div class="item">
		<div class="left">支付方式</div>
		<div class="right">油彩信用额度<span class='small-font' id ="avaiAmt">（可用${funUtils.formatNumber(availAmt,'#,###')}额度）</span></div>
	</div>
</div>
<!-- 支付按钮 -->
<div class="btn-box">
	<div class="normal-btn" id="confirmBtn">确认订单</div>
</div>
<div class="mask" style="display: none;">
    <div class="mask-wrapper">
      <div class="mask-header">
        <p>请输入短信验证码</p>
        <div class="bar"></div>
      </div>
      <div class="mask-body">
		<input type="text">
		<div class="sendcode" id="sendCode">发送验证码</div>
        
      </div>
      <div class="mask-footer">
        <di class="cancle button" id="maskCancle">取消</di>
        <di class="ok button" id="maskOk">确定</di>
      </div>
    </div>
  </div>
<script type="text/javascript">
    var address = '';
	<#if address??>
	address = ${address};	
	</#if> 
	var from = commonUtils.getUrlParam('from') || '';
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
	$(".maskOk").on("click", function() {
		 var params = {
      goodsId:#{goodsId},
      smsCode:$("#sendCode").val()
    }; 
    if(address){
    	params.addId = address.id;
    }
  	$.ajax({
  		url:'${host.base}/hepet/order/submit',
  		type: 'post',
  		dataType: 'json',
  		data: params,
  		success: function(data) {
  			if(data.head.code == '0000') {
				alert('下单成功');
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
  		url:'${host.base}/hepet/order/submit',
  		type: 'post',
  		dataType: 'json',
  		data: params,
  		success: function(data) {
  			if(data.head.code == '0000') {
				alert('下单成功');
  			} else {
  				$.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
  			}
  		}
  	})
  }
	//最后成功的事件
  function onSuss() {

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
  $(function() {
  	initAdd();
  	if(!address) {
			$("#confirmBtn").addClass("disable");
  	} else {
 			$("#confirmBtn").removeClass("disable");
  	}
  	$("#confirmBtn").on("click", confirm);
  })
  function confirm() {
	  if($(this).hasClass("disable")){
	  return;
	  }
	  $(".mask").show();
  }
  //初始地址
  function initAdd() {
  	var str = ''
  	var curUrl = encodeURIComponent(window.location.href)
  	if(!address) {
       str+= '<a href="${host.base}/hepet/addAddr?from=order&curUrl='+curUrl+'">添加地址信息</a>'
  	} else {
  		var curAdd = address
  		str+= '<p class="add-name">'+ curAdd['contact'] +' '+ curAdd['phone'] +'</p>';
  		str+= '<div class="add-add">';
  		str+= '<p>'+ curAdd['area'] +'</p>';
  		str+= '<p>'+ curAdd['address'] +'</p>';
  		str+= '</div>';  		
  	}
  	$("#address").append($(str));
    //如果地址列表超过1个 可点击到地址列表页切换
    if(${hasMore}==1) { 
    	$(".address").find(".right").show(); 
    	$("#address").on("click" , function() {
    		window.location.href = '${host.base}/hepet/addresses?from=order&addid=${addid}&curUrl='+curUrl;
    	})
    }
  }

</script>
</body>
</html>