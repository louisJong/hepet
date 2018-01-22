<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	 <#if address??>
	 	<title>修改</title>
	 <#else>
	 	<title>新增</title>
	 </#if>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/mobiscroll.custom-3.0.0-beta2.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/address.css">
	<!-- 引入js -->
	<script type="text/javascript" src='${host.js}/jquery-1.11.3.min.js'></script>
	<script type="text/javascript" src='${host.js}/mobiscroll.custom-3.0.0-beta2.min.js'></script>
  <script type="text/javascript" src='${host.js}/common.js'></script>
</head>
<body>
<form id="addressForm">
	<div class="input-label">
		<span class="input-tips">收件人</span><input type="text" name="contact" placeholder="请输入收件人姓名" id='contact'>
	</div>
	<div class="input-label">
		<span class="input-tips">手机号</span><input type="text" name="phone" placeholder="请输入收件人手机号" id="phone">
	</div>
	<div class="input-label">
		<span class="input-tips">所在地区</span><input type="text" name="area" placeholder="请选择省市区" id="area"><i class="icon-right"></i>
	</div>
	<div class="input-label">
		<span class="input-tips address">详细地址</span><textarea placeholder="无需填写省市区，小于100字" name='address' id="address"></textarea>
	</div> 	
</form>		
	
<div class="btn-box">
	<div class="normal-btn disable" id='submit' onclick="submit()"></div>
</div>
<script type="text/javascript" src="${host.js}/city.js"></script>	
<script type="text/javascript">
//初始化form输入框的判断条件
var fieldsCheck = {contact: {checkv: false}, phone: {checkv: false}, address: {checkv: false}, area: {checkv: false}};


var from = commonUtils.getUrlParam('from') || '';
var fromUrl =  decodeURIComponent(commonUtils.getUrlParam('curUrl')) || ''
//初始化省市区的id值 默认二级三级无值时为n
var ProvinceId = '';
var CityId =  '';
var AreaId = '';
<#if address??>
var reginInfo = ${address.reginInfo};
ProvinceId = reginInfo.provinceId;
CityId = reginInfo.cityId;
AreaId =reginInfo.areaId;
</#if>

$(function() {
	//引入city数据
	var city = cityCode;
	//初始化省市区选择器
	initCity(city);
	
	$("#submit").html('确定');

  //进入此页面有值的情况下
  <#if address??>
		fieldsCheck = {contact: {checkv: true}, phone: {checkv: true}, address: {checkv: true}, area: {checkv: true}};
		$("#contact").val('${address.contact}');
		$("#phone").val('${address.phone}');
		$("#address").val('${address.address}');
		$("#area").val('${address.area}');
		checkBtn();
  </#if>
  //绑定事件
  $("#contact").on("input propertychange", handelName);
  $("#phone").on("input propertychange", handelTel);
  $("#area").on("click", function() {$("input[id^=AddrMobiscroll]").click();  })
  $("#address").on("input propertychange", handelAddressDetail);
})
function submit() {
  if($(this).hasClass("disable")) {
  	return;
  } else {
  	// ProvinceId, CityId, AreaId 省市区id
    var params = {
      provinceId : ProvinceId,
      cityId: CityId,
      areaId: AreaId
    }; 
    var arr = $("#addressForm").serializeArray();
    arr.map(function(item, index) {
      params[item['name']] = item['value'];
    });
    <#if id??>
    	params['id']=${id};
    </#if>
    subAjax(params)  	
  }
}
function subAjax(params) {
 	var url = '${host.base}/hepet/addRecAddr';
 	<#if id??>
 		url = '${host.base}/hepet/editRecAddr';
 	</#if>
	 if($("#submit").hasClass("disable")) {
		  return;
	  }
	  $("#submit").addClass("disable");
	  
  $.ajax({
    url: url,
    type: 'post',
    dataType: 'json',
    data: params,
    success: function(data) {
      if(data.head.code == '0000') {
        if( from && from == 'order') {
					window.location.href = fromUrl;
					
        } else {
          window.location.href = '${host.base}/hepet/addresses';
        }
      } else {
        $.mask({type:'alert', alertTips: data.head.msg, alertTime: 2000})
      }
    }
  })
}
function handelName() {
 	var v;
 	if(!$(this).val()) {
 		v = false;
 	} else {
 		v = true;
 	}
 	updateBtn($(this).attr("id"), v);
}
 function handelTel() {
 	var v;
 	$(this).val($(this).val().replace(/[^\d]/g,'').substr(0,11));
 	if($(this).val().length == 11) {
 		v = true;
 	} else {
 		v = false;
 	}
 	updateBtn($(this).attr("id"), v);
 }
 function handelAddressDetail() {
 	var v;
 	$(this).val($(this).val().substr(0,100));
 	if($(this).val()) {
 		v = true;
 	} else {
 		v = false;
 	}
 	updateBtn($(this).attr("id"), true);
 }
 function checkBtn() {
 	var btn = $("#submit");
 	var disabled = false;
 	for (var key in fieldsCheck) {
 		if (!fieldsCheck[key].checkv) {
 			disabled = true;				
 			break;
 		};
 	}
 	if (disabled) {
 		btn.addClass("disable");
 	} else {
 		btn.removeClass("disable");
 	}
 }
 function updateBtn(checkId, v) {
 	var btn = $("#submit");
 	fieldsCheck[checkId] = {
 		'checkv': v
 	}
 	if (!v) {
 		btn.addClass("disable");
 		return;
 	};
 	checkBtn();
 }

 function initCity(city) {
		//初始化选中的值 - 》 回显示文本框  
		var ProvinceIdStr = "";  
		var CityIdStr     = "";  
		var AreaIdStr     = "";  

    //初始化插件默认选中  
    var ProvinceIdIndex    = "";  
    var CityIdIndex     = "";  
    var AreaIdIndex     = "";  

    //初始化选中隐藏的value -》 提交时使用  
    var ProvinceIdVal    = "";  
    var CityIdVal     = "";  
    var AreaIdVal     = "";  

    var KHHtml = "";  

    //省份  
    for(var i = 0; i < city.length; i++){  
    	var code = city[i].code;  
    	var name = city[i].name;  
      //初始化选中值  
      if(ProvinceId == code){  
      	ProvinceIdStr = name;  
      	ProvinceIdIndex = i;  
      	ProvinceIdVal = code;  
      }  
      KHHtml +="<li>";  
      KHHtml +='<div data-value="'+ code +'">'+ name +'</div>';  
      KHHtml +='<ul>';  
      if(city[i].childs.length == 0) {
				KHHtml +='<li>';  
	      KHHtml +='<div data-value="n"></div>';  
	      KHHtml +='<ul>';  
	      KHHtml +='<li data-value="n"></li>';  
	      KHHtml +='</ul>';  
	      KHHtml += '</li>';  
      } else {
 				//城市  
	      for(var j = 0; j < city[i].childs.length; j++) {  
	      	var subCode = city[i].childs[j].code;  
	      	var subName = city[i].childs[j].name;  
	        //初始化选中值  
	        if(CityId == subCode){  
	        	CityIdStr = subName;  
	        	CityIdIndex= j;  
	        	CityIdVal = subCode;  
	        }  

	        KHHtml +='<li>';  
	        KHHtml +='<div data-value="'+ subCode +'">'+ subName +'</div>';  
	        KHHtml +='<ul>';  
	        if(city[i].childs[j].childs.length == 0) {
             KHHtml +='<li data-value="n"></li>';  
	        } else {
	        	//区  
		        for(var k = 0; k < city[i].childs[j].childs.length; k++) {  
		        	var subSubCode = city[i].childs[j].childs[k].code;  
		        	var subSubName = city[i].childs[j].childs[k].name;  

		          //初始化选中值  
		          if(AreaId == subSubCode){  
		          	AreaIdStr = subSubName;  
		          	AreaIdIndex= k;  
		          	AreaIdVal = subSubCode;  
		          }  

		          KHHtml +='<li data-value="'+ subSubCode  +'">'+ subSubName +'</li>';  
		        }
	        }
	        KHHtml +='</ul>';  
	        KHHtml += '</li>';  
	      }
      }
      KHHtml +='</ul>';  
      KHHtml +='</li>';  
    }
    if(ProvinceIdStr == "" && CityIdStr == "" && AreaIdStr == "" ){  
    	ProvinceIdStr = city[0].name;  
    	CityIdStr = city[0].childs[0].name;  
    	AreaIdStr = city[0].childs[0].childs[0].name;  

    	ProvinceIdIndex = 0;  
    	CityIdIndex = 0;  
    	AreaIdIndex = 0;  

    	ProvinceIdVal = city[0].code;  
    	CityIdVal = city[0].childs[0].code;  
    	AreaIdVal = city[0].childs[0].childs[0].code;  
    } 
		//初始化插件所需html  
		addrMobilscroll = '<div id="AddrMobiscroll"></div>';
		var $addrMobilscroll = $(addrMobilscroll).append(KHHtml);
		$("body").append($addrMobilscroll);


    //初始化插件  
    $("#AddrMobiscroll").mobiscroll().treelist({  
    	theme: "ios",  
    	lang: "zh",  
    	display: 'bottom',  
    	inputClass: 'tmp',  
    	headerText: '请您选择',  
    	width: 50,  
    	defaultValue: [ProvinceIdIndex,CityIdIndex,AreaIdIndex],  
    	onSet: function (valueText, inst) {  
				var n = valueText['valueText'].split(' ');  

				var m1 = $(this).children("li").eq(n[0]).find("div").html();  
				var m1_id = $(this).children("li").eq(n[0]).find("div").attr("data-value");  

				var m2 = $(this).children("li").eq(n[0]).children("ul").find("div").eq(n[1]).html();  
				var m2_id = $(this).children("li").eq(n[0]).children("ul").find("div").eq(n[1]).attr("data-value");  

				var m3 =$(this).children("li").eq(n[0]).children("ul").children("li").eq(n[1]).children("ul").children("li").eq(n[2]).html();  
				var m3_id = $(this).children("li").eq(n[0]).children("ul").children("li").eq(n[1]).children("ul").children("li").eq(n[2]).attr("data-value");  

         console.log("1-name-"+m1);  
         console.log("2-name-"+m2);  
         console.log("3-name-"+m3);  

         console.log("1-val-"+m1_id);  
         console.log("2-val-"+m2_id);  
         console.log("3-val-"+m3_id);  

        //选中后进行赋值  
        $("#area").val(m1 + " " + m2 + " " + m3);
        if(!$("#area").val()) {
        	updateBtn("area", false);
        }else {
  				 updateBtn("area", true);
        }
        ProvinceId = m1_id;  
				CityId = m2_id;  
				AreaId = m3_id;   

      }  
    })
    //隐藏插件自己生成的文本框  
    $("#AddrMobiscroll_dummy").css('display','none');  
  }
</script>
</body>
</html>