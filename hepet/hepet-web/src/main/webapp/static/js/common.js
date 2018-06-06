var Footer = function(footerArr, current) {
	this.footerArr = footerArr || [];
	this.current = current || 0;
	var _this = this;
	this.init = function() {
		var str = '<div class="footer-nav">';
		this.footerArr.map(function(item, index) {
			var actived = (index == _this.current) ? 'actived' : '';
			str += '<div data-href="'+ item['link'] +'" class="footeritem nav-item ' + actived + '">' 
			str += '<span class="icon '+ item['code'] +'"></span>'
			str += '<span>'+ item['text'] +'</span>' 
			str +=  '</div>';
		})
		str += '</div>';
		if ($('body').find('footer').length > 0) {
			$("footer").empty();
		}

		$("footer").append($(str));
		console.log(_czc)
		$("footer .footeritem").on("click", function() {
			_czc.push(["_trackEvent",'底部tab切换', '底部tab切换', '', '', '']);
			window.location.href = $(this).data('href');
		})
	}
}

function ZlMask(opts) {
	this.defaults = {
		title: '',
		text: '',
		type: 'content', //[normal|alert|content|image|loading]
		buttons: [{
			buttonTxt: '立即开启'
		}],
	};
	this.options = $.extend({}, this.defaults, opts);

	var _this = this;

	this.init = function() {
		if (_this.options.type == 'alert') {
			_this.initAlert();
		} else if (_this.options.type == 'normal') {
			_this.initNormal();
		} else if (_this.options.type == 'content') {
			_this.initContent();
		} else if (_this.options.type == 'image') {
			_this.initImage();
		} else if (_this.options.type == 'loading') {
			if (_this.options.loadingStatus == 'show') {
				_this.initLoading();
			} else {
				$(".bgmask").remove();
			}
		}
	}
	this.bgmask = $('<div class="bgmask"></div>');
	this.bgmaskWrap = $('<div class="bgmask-wrap"></div>');
	this.bgmaskBody = $('<div class="bgmask-body"></div>');
	this.bgmaskFooter = $('<div class="bgmask-footer"></div>');

	this.footerBtn = [];
	if (_this.options.buttons.length == 1) {
		_this.footerBtn.push($('<div class="bgmask-button">' + _this.options.buttons[0].buttonTxt + '</div>'));
	} else {
		_this.options.buttons.map(function(item, index) {
			_this.footerBtn.push($('<div class="bgmask-button">' + item.buttonTxt + '</div>'));
		})
	}
	this.footerBtn.map(function(item, index) {
		_this.bgmaskFooter.append(item);
	})



	this.initAlert = function() {
		this.bgmask.addClass('alert');
		var alert = $('<div class="bgmask-alert">' + this.options.alertTips + '</div>');
		this.bgmask.append(alert);
		$('body').append(_this.bgmask);
		setTimeout(function() {
			_this.bgmask.remove();
		}, _this.options.alertTime ? _this.options.alertTime : 1500);
	}

	this.initNormal = function() {
		var title = _this.options.title ? $('<div class="title">' + _this.options.title + '</div>') : '';
		var text = $('<div class="text">' + _this.options.text + '</div>');
		_this.bgmaskBody.append(title).append(text);
		_this.bgmaskWrap.append(_this.bgmaskBody).append(_this.bgmaskFooter);
		_this.bgmask.append(_this.bgmaskWrap);
		$('body').append(_this.bgmask);
		_this.bindEvent();
	}

	this.initContent = function() {
		_this.bgmaskBody.addClass('content');
		_this.close = _this.options.close ? $('<div class="close"></div>') : '';
		var title = $('<p class="content-title">' + _this.options.title + '</p>');
		var text = $('<p class="content-text"></p>');
		Array.isArray(_this.options.text) ? _this.options.text.map(function(item, index) {
			text.append('<p>' + item + '</p>');
		}) : '';
		var tips = $('<p class="content-tips"></p>');
		Array.isArray(_this.options.tips) ? _this.options.tips.map(function(item, index) {
			tips.append('<p>' + item + '</p>');
		}) : '';
		if (!_this.text) {
			text = "";
		}
		_this.bgmaskBody.append(_this.close).append(title).append(text).append(tips);
		_this.bgmaskWrap.append(_this.bgmaskBody).append(_this.bgmaskFooter);
		_this.bgmask.append(_this.bgmaskWrap);
		$('body').append(_this.bgmask);
		_this.bindEvent();
	}

	this.initImage = function() {
		_this.bgmaskWrap.addClass('image');
		var image = $('<img src="' + _this.options.imageSrc + '">');
		_this.bgmaskBody.append(image);
		_this.bgmaskWrap.append(_this.bgmaskBody).append(_this.bgmaskFooter);
		_this.bgmask.append(_this.bgmaskWrap);
		$('body').append(_this.bgmask);
		_this.bindEvent();
	}

	this.initLoading = function() {
		this.bgmask.addClass('alert');
		var loading = $('<div class="bgmask-loading"><img src="' + this.options.imageSrc + '"/></div>');
		this.bgmask.append(loading);
		$('body').append(_this.bgmask);
	}

	this.bindEvent = function() {
		var _this = this;
		this.bgmask.on("click", function() {
			if (!_this.options.onlyTouchCardClose) {
				_this.bgmask.remove();
			}
		});
		//禁止滚动
		this.bgmask.on("touchmove", function(e) {
			e.preventDefault();
		});
		this.bgmaskWrap.on("click", function(e) {
			e.stopPropagation();
		});
		this.footerBtn.map(function(item, index) {
			item.on('click', function(e) {
				if (!!_this.options.buttons[index].buttonFn && typeof _this.options.buttons[index].buttonFn == 'function') {
					_this.options.buttons[index].buttonFn();
					if (!_this.options.buttons[index].buttonShow) {
						setTimeout(function() {
							_this.bgmask.remove();
						}, 300)
					}
				} else {
					_this.bgmask.remove();
				}
			})
		});
		this.close ? this.close.on("click", function() {
			_this.bgmask.remove();
		}) : ''
	}
}

var CommonUtils = function() {
	this.baseUrl = 'http://101.132.166.7/hepet-web/';
	this.getUrlParam = function (name) {
    var url = window.location.href;
    var params = url.substr(url.indexOf("?") + 1);
    var paramsarr = params.split("&");
    var paramsobj = {};
    for (var i = 0; i < paramsarr.length; i++) {
      var list = paramsarr[i].split("=");
      paramsobj[list[0]] = list[1];
    }
    return paramsobj[name];
  }
  this.changeURLArg = function(url,arg,arg_val){ 
		var pattern=arg+'=([^&]*)'; 
		var replaceText=arg+'='+arg_val; 
		if(url.match(pattern)){ 
			var tmp='/('+ arg+'=)([^&]*)/gi'; 
			tmp=url.replace(eval(tmp),replaceText); 
			return tmp; 
		}else{ 
			if(url.match('[\?]')){ 
				return url+'&'+replaceText; 
			}else{ 
				return url+'?'+replaceText; 
			} 
		} 
		return url+'\n'+arg+'\n'+arg_val; 
	} 
}
var commonUtils = new CommonUtils();
(function($) {
	$.extend({
		mask: function(opts) {
			if (!this.instance) {
				this.instance = new ZlMask(opts).init();
			}
			return this.instance;
		}
	});
})(jQuery);