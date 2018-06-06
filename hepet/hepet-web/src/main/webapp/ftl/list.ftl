<#import "/common/host.ftl" as host>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>分期商城</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<!-- 引入css -->
	<link rel="stylesheet" type="text/css" href="${host.css}/mescroll.min.css">
	<link rel="stylesheet" type="text/css" href="${host.css}/base.css?v=${host.version}">
	<link rel="stylesheet" type="text/css" href="${host.css}/mall.css?v=${host.version}">
	<!-- 引入js -->
  <script src="${host.js}/jquery-1.11.3.min.js"></script>
  <script src="${host.js}/mescroll.min.js"></script>
	<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1273825318'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s13.cnzz.com/z_stat.php%3Fid%3D1273825318' type='text/javascript'%3E%3C/script%3E"));var _czc = _czc || [];_czc.push(["_setAccount", "3D1273825318"]);</script>
  <script src="${host.js}/common.js?v=${host.version}1"></script>
  <script type="text/javascript">
  </script>
</head>
<body style="padding-top: 44px; padding-bottom: 60px;">
	<header>
		
	</header>
    <footer></footer>
	<script type="text/javascript">
		var categorys = ${categorys};
		var curNavIndex = categorys[${cCindex}]['code']; //默认初始选中的nav
		var mescrollArr = new Array(categorys.length);
		var categorysMap = {}
		categorys.forEach(function(item, index) {
			categorysMap[item['code']] = index;
		})
    $(function() {
    	initNav();
    	new Footer([{text:'精选推荐', code:'jxtj', link:'${host.base}/hepet/index'},{text: '商品分类', code:'spfl', link:'${host.base}/hepet/mall'}, {text: '我的', code:'wode', link:'${host.base}/hepet/my'}], 1).init();	
			//初始化
		  mescrollArr[categorysMap[curNavIndex]] = initMescroll("mescroll"+curNavIndex, "dataList"+curNavIndex);
		  $("#mescroll"+curNavIndex).show();
		  /*初始化菜单*/
			$(".nav-item").click(function(){
				var i= $(this).attr("i");
				if(curNavIndex != i) {
					//更改列表条件
					$(".nav-item.actived").removeClass("actived");
					$(this).addClass("actived");
					//隐藏当前列表
					$("#mescroll" + curNavIndex).hide();
					//显示对应的列表
					curNavIndex = i;
					$("#mescroll" + curNavIndex).show();
					//取出菜单所对应的mescroll对象,如果未初始化则初始化
					if(mescrollArr[categorysMap[curNavIndex]] == null) mescrollArr[categorysMap[curNavIndex]] = initMescroll("mescroll"+curNavIndex, "dataList"+curNavIndex);
				}
			})
    })		

		
    function initNav() {
    	var str = '';
    	var listContent = '';
      categorys.map(function(item, index) {
      	str += '<div i="'+ item['code'] +'" class="nav-item '+ (item['code'] === curNavIndex ? 'actived ': '') +'" i="0">'+ item['shotName'] +'</div>';	
      	listContent += '<div id="mescroll'+ item['code'] +'" class="mescroll hide"><ul id="dataList'+ item['code'] +'" class="data-list"></ul></div>';
      })
      $("header").append($(str));
      $("body").append($(listContent));
    }
		/*创建MeScroll对象*/
		function initMescroll(mescrollId,clearEmptyId){
			//创建MeScroll对象,内部已默认开启下拉刷新,自动执行up.callback,刷新列表数据;
			var mescroll = new MeScroll(mescrollId, {
				//上拉加载的配置项
				up: {
					callback: getListData,
					noMoreSize: 2, 
					empty: {
						icon: "${host.img}/mescroll-empty.png", 
						tip: "暂无相关数据~", 
						btntext: "去首页 >", 
						btnClick: function(){
							window.location.href = '${host.base}/hepet/index';
						} 
					},
					htmlNodata: '<p class="upwarp-nodata">-- 没有更多 --</p>',
					clearEmptyId: clearEmptyId, //相当于同时设置了clearId和empty.warpId; 简化写法;默认null					
				}
			});
			return mescroll;
		}

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
		function getListData(page){
			//联网加载数据
			var dataIndex = curNavIndex; //记录当前联网的nav下标,防止快速切换时,联网回来curNavIndex已经改变的情况;
			getListDataFromNet(dataIndex, page.num, page.size, function(pageData){
				//后台接口有返回列表的总数据量 totalSize
			  mescrollArr[categorysMap[dataIndex]].endBySize(pageData.listData.length, pageData.count); 
			  //设置列表数据
				setListData(pageData,dataIndex);
			}, function(){
				//联网失败的回调,隐藏下拉刷新和上拉加载的状态;
        mescrollArr[categorysMap[dataIndex]].endErr();
			});
		}

		/* 设置列表数据
		 * pageData 当前页的数据
		 * dataIndex 数据属于哪个nav */
		function setListData(pageData,dataIndex){
			var listDom=$("#dataList"+dataIndex);
			var dataList = pageData['listData'];
			for (var i = 0; i < dataList.length; i++) {
				var pd= dataList[i];	
				var str = '<li onClick="window.location.href=\'${host.base}/hepet/goodsInfo?goodsId='+ pd.id +'\'">'	;	
				str+='<img class="pd-img" src="'+pd.listImgUrl+'"/>';
				str+= '<div class="pd-right">';
				str+='<p class="pd-name">'+pd.goodsName+'</p>';
				str+='<p class="pd-sub">'+pd.subDesc+'</p>';
				str+='<p class="prod-item_price">¥'+pd.price+'<span class="pd-market">¥'+pd.marketPrice+'</span></p>';
				str+='<p class="pd-price">¥'+pd.price+'<span class="small-font">/每期</span></p>';
				str+='</div>';
				str+='</li>';
				listDom.append($(str));
			}
		}

   
		/*联网加载列表数据*/
		function getListDataFromNet(curNavIndex,pageNum,pageSize,successCallback,errorCallback) {
			$.ajax({
        type: 'GET',
        url: '${host.base}/hepet/goodsList',
        data: {
        	pageIndex: pageNum-1,
        	limit: pageSize,
          categoryCode: curNavIndex
        },
        dataType: 'json',
        success: function(data){
        	if(data.head.code == '0000') {
						var listData=[];
	          var _data = {
	          	listData: listData,
	          	count: data.body.count
	          }
	          categorys.map(function(item, index) {
	          	if(curNavIndex == categorys[index]['code']){
								for (var i = 0; i < data.body.goodsList.length; i++) {
	            		_data.listData.push(data.body.goodsList[i]);
	          		}							
		          }
	          })	  
	          console.log('ajax:', _data)       
	        	//回调
	        	successCallback(_data);
        	}else {
        		errorCallback
        	}
          
        },
        error: errorCallback
      });
		}
	</script>

	
</body>
</html>