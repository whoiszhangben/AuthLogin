package com.zhangben.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.zhangben.service.AlipayService;

/**
 * 支付宝登录
 */
@Controller
@RequestMapping(value = "alipay")
public class AlipayController{
	
	@Autowired
	private AlipayService AlipayService;
	
	
	/**
	 * 登录发起
	 * @return
	 */
	@RequestMapping(value="/auth", method=RequestMethod.GET)
	public String auth() {
		String url = new StringBuilder(AlipayService.AUTH_URL)
				.append("?app_id=").append(AlipayService.APPID)
				.append("&redirect_uri=").append(URLEncoder.encode(AlipayService.CALLBACK_URL)).toString();
		return "redirect:" + url;
	}	
	
	/**
	 * 登录授权回调
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/callback", method=RequestMethod.GET)
	public String callback(String app_auth_code, String app_id,String source) {
		String result = AlipayService.getToken(app_auth_code);
		// 执行登录过程, 然后调整到个人首页
		return "redirect:http://127.0.0.1?param="+result;
	}
	
	@RequestMapping(value="/zhima", method=RequestMethod.GET)
	public @ResponseBody String zhima() throws AlipayApiException{
		String biz_no = AlipayService.CertifyInitialize();
		String url = AlipayService.CertifyInvoke(biz_no);
		return url;
	}
}
