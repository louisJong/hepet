<#import "/common/host.ftl" as host>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <base href="./../" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>支付结果</title>
    	<!-- 引入css -->
    <link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}">
    <link rel="stylesheet" type="text/css" href="${host.css}/status.css?v=${host.version}">    
    	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
    <script type="text/javascript" src='${host.js}/common.js?v=${host.version}'></script>
    
</head>
<body>

<div id="succ" class="succ-box" style="display: none;">
    <div class="left">
        <img class="icon" src="${host.img}/status_succ.png" />
        <div class="line"></div>
        <div class="dot"></div>
    </div>
    <div class="right">
        <div>
            <p style="font-size: 24px; font-weight: bold;">支付成功</p>
            <p>¥<span style='font-size: 20px'>99</span>×<span style='font-size: 20px'>12</span><span style="font-size: 12px;">期</span></p>
        </div>        
        <div style="font-size: 17px;">
            正在加急备货中，您可以前往<span style="color: #F06059;">商城-我的</span>查看订单状态
        </div>
    </div>
</div> 
<div id="fail" class="fail-box"  style="display: none;">
    <img class="icon" src="${host.img}/status_fail.png"/>
    <p style="font-size: 24px; font-weight: bold; margin-bottom: 9px;">订单支付失败</p>
    <p style="font-size: 17px;" id="errotips">失败原因</p>
</div>
<div class="btnbox">
    <a class="leftbtn" id="confirmBtn">
        查看订单
    </a>
    <div class="rightbtn">
        返回商城
    </div>
</div> 
 <script>


 $(function() {
    $.mask({
        type: 'loading',
        loadingStatus: 'show',
        imageSrc: '${host.img}/loading.gif'
    })
    $.ajax({
        url: '${host.base}/mall/pay/result',
        type: 'get',
        dataType: 'json',
        data: {"outOrderNo": "${outOrderNo}"},
        success: function(data) {
            if(data.head.code == '0000') {
                if(data.body.result === "payed") {
                    $("#succ").show()                    
                    $("#confirmBtn").attr("href", "${host.base}/hepet/order/result?orderId="+data.body.id)
                } else {
                    $("#fail").show()
                    $("#errotips").html("订单支付失败")
                    // 我的订单地址。。。。
                    $("#confirmBtn").attr("href", "${host.base}/hepet/order/result?orderId="+data.body.id)
                }
                $(".bgmask").hide();

            } else {
                $("#fail").show()
                $("#errotips").html(data.head.msg)
                // 我的订单地址。。。。
                $("#confirmBtn").attr("href", "${host.base}/hepet/order/result?orderId="+data.body.id)
               $(".bgmask").hide();
            }
        }
    })
 })
 </script>

 <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1273825318'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s13.cnzz.com/z_stat.php%3Fid%3D1273825318' type='text/javascript'%3E%3C/script%3E"));</script>

</body>
</html>