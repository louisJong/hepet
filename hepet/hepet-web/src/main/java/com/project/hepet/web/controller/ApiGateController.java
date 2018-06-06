package com.project.hepet.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.PayConfig;
import com.project.hepet.common.utils.RSADatagram;
import com.project.hepet.service.GoodsService;
import com.project.hepet.web.utils.WebUtil;
@Controller
public class ApiGateController {
	private static final Logger logger = Logger.getLogger(GoodsController.class);
	@Autowired
	private GoodsService goodsService;
	/**
	 * 申请开通二维码支付
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/mall/gateway", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String payGateway(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String reqParam =WebUtil.getReqData(request);
		JSONObject result = JsonUtils.commonJsonReturn();
		logger.info("商城网关请求,请求参数：reqParam:"+reqParam);
		if(StringUtils.isEmpty(reqParam)){
			logger.warn("商城网关请求，非法请求，请求参数为空");
			return JsonUtils.commonJsonReturn("0100", "非法请求，请求参数为空").toJSONString();
		}
		RSADatagram datagram = RSADatagram.buildDatagram(reqParam, PayConfig.clientPrivateKey, PayConfig.serverPublicKey);
		//签名验证 与 解密
		if(! datagram.verifySign(reqParam)){
			logger.warn("商城网关 签名|解密失败,reqData:"+reqParam);
			return JsonUtils.commonJsonReturn("0200", "签名验证失败").toJSONString();
		}
		logger.info("商城网关请求，请求参数:"+datagram.getPlaintext());
		JSONObject params = JSON.parseObject(datagram.getPlaintext());
		if(logger.isDebugEnabled()) logger.debug("商城网关请求参数："+params);
		//TODO 时间戳校验
		switch (datagram.getServerCode()) {
		case "MALL0001": 
			result = goodsService.appIndexProList();
			break;
		default:
			result = JsonUtils.commonJsonReturn("0900", "交易不存在");
			logger.warn("商城网关请求，请求参数:"+datagram.getPlaintext());
			break;
		}
		logger.info("商城网关请求，执行完成，请求参数:"+datagram.getPlaintext()+",执行结果："+result);
		datagram.setReqText(result.toJSONString());
		return datagram.encrypt();
	}
	
}
