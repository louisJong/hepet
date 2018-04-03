<#import "/common/host.ftl" as host>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <base href="./../" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>支付结果</title>
    	<!-- 引入css -->
    <link rel="stylesheet" type="text/css" href="${host.css}/base.css">
    	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
    <script type="text/javascript" src='${host.js}/common.js'></script>
    <style>
    .icon {
    display: none;
    width: 77px;
    height: 77px;
    margin-bottom: 20px; }

    .box {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
        -ms-flex-pack: center;
            justify-content: center;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
        -ms-flex-direction: column;
            flex-direction: column;
    -webkit-box-align: center;
        -ms-flex-align: center;
            align-items: center;
    margin-top: 100px; }

    .text {
    font-size: 18px; }
    #confirmBtn {
        display: block;
        text-decoration: none;
    }
    </style>
</head>
<body>
 <div class="box">
     <!-- 成功icon  -->
     <img id="succIcon" class="icon" src="${host.img}/status-succ.png"/>
     <!-- 失败icon -->
     <img id="errIcon" class="icon" src="${host.img}/status-fail.png"/>
     <span class="text" id="text"></span>
     <!-- 按钮随机设置 -->
     <div class="btn-box">
        <a class="normal-btn" id="confirmBtn" >查看订单</a>
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
        data: {outOrderNo: ${outOrderNo}},
        success: function(data) {
            if(data.head.code == '0000') {
                if(data.body.result === "payed") {
                    $("#succIcon").show()
                    $("#text").html("订单支付成功")
                    $("#confirmBtn").attr("href", "${host.base}/hepet/order/result?orderId="+data.body.id)
                } else {
                    $("#errIcon").show()
                    $("#text").html("订单支付失败")
                    // 我的订单地址。。。。
                    $("#confirmBtn").attr("href", "${host.base}/hepet/order/result?orderId="+data.body.id)
                }
                $(".bgmask").hide();

            } else {
                $("#errIcon").show()
                $("#text").html(data.head.msg)
                // 我的订单地址。。。。
                $("#confirmBtn").attr("href", "${host.base}/hepet/order/result?orderId="+data.body.id)
               $(".bgmask").hide();
            }
        }
    })
 })
 </script>
</body>
</html>