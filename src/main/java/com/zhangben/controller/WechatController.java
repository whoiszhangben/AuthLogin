package com.zhangben.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zhangben.service.WechatService;;

/**
 * 微信登录
 */
@Controller
@RequestMapping(value = "wechat")
public class WechatController{
	
	@Autowired
	private WechatService WechatService;
	
	
	/**
	 * 登录发起
	 * @return
	 */
	@RequestMapping(value="/auth", method=RequestMethod.GET)
	public String auth() {
		String url = new StringBuilder(WechatService.AUTH_URL)
				.append("?appid=").append(WechatService.APPID)
				.append("&redirect_uri=").append(WechatService.CALLBACK_URL)
				.append("&response_type=code&scope=snsapi_login").toString();
		return "redirect:" + url;
	}	
	
	/**
	 * 登录授权回调
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/callback", method=RequestMethod.GET)
	public String callback(String code, String state) {
		String result = WechatService.getToken(code);
		// 执行登录过程, 然后调整到个人首页
		return "redirect:/my.html?"+result;
	}	
	
}
