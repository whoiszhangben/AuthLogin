package com.zhangben.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.zhangben.util.HttpUtil;
import com.zhangben.util.JsonUtil;

/**
 * 微信登录
 */
@Service
public class WechatService{
	
	public static String APPID = "wx60000b0b25f9d6da";
	public static String SECRET = "a34084812fbf6736ac56cd5c86b8aaf0";
	public static String CALLBACK_URL = "http://otc.muchinfo.cn/ThirdParty/login";
	
	// 发起授权地址
	public static String AUTH_URL = "https://open.weixin.qq.com/connect/qrconnect";
	// 获取token地址
	public static String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	// 获取用户信息地址
	public static String INFO_URL = "https://api/weixin.qq.com/sns/userinfo";
	
	
	/**
	 * 获取token
	 * @param code
	 * @return
	 */
	public String getToken(String code) {
		String url = new StringBuilder(TOKEN_URL)
				.append("?appid=").append(APPID)
				.append("&secret=").append(SECRET)
				.append("&grant_type=").append("authorization_code")
				.append("&code=").append(code).toString();
		String result = HttpUtil.get(url);
		return result;
	}
	
	
	/**
	 * 获取用户信息
	 * @param openid
	 * @param token
	 */
	public Map<String, String> getInfo(String openid, String token) {
		String url = new StringBuilder(INFO_URL)
				.append("?oauth_consumer_key=").append(APPID)
				.append("&openid=").append(openid)
				.append("&access_token=").append(token).toString();
		String result = HttpUtil.get(url);
		return JsonUtil.toObject(result, HashMap.class, Map.class, String.class, String.class);
	}	
	
}