package com.zhangben.service;

import java.util.Random;

import org.springframework.stereotype.Service;
import com.zhangben.util.JsonUtil;
import com.alipay.api.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

/**
 * 支付宝登录
 */
@Service
public class AlipayService{
	
	public static String APPID = "2016091800541950";
	public static String CALLBACK_URL = "http://127.0.0.1:8081/webapp/alipay/callback";
	
	// 发起授权地址
	public static String AUTH_URL = "https://openauth.alipaydev.com/oauth2/appToAppAuth.htm";
	
	private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8cMMlwq3SayRV7hvgxMvCU2qT/3eSF7/h2hOD6NldSWQlX5X2kW4xD5cRIi+MECdYkAAUMMv+4T8XpMb3zz86YTdizz7lIA0GE5L1Ao/VopJx//8vbX4IGl3Yl9r0NgH5cqFN8+JNeFZ/fSV3d2qUMJLv1fDxyONd1ji9OSBdt8D+tEUkHVF9pRbyqsMp3D9AjSiKqqdp+uWDKa/9BhMzfXJDEve/3uYeUJZwXGp0FejzVhzCOOAhWo/MWw6zH4dtp6XAp6I8GV5m44EDUXw7chHBO+lheP6yN2d9XKXAoGVfr03Sx6MLL3SJc4sU6zrpae1YgR1x4LLsWP4FnriqwIDAQAB";
	private static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCNKvRxhvOM9ioc3aomPP91UtBR3ZQ+c9tpQy9ywC7HJ5wTznjquTX2mTez3f559woKq5PScQm6CQqfxdkWh/H/M7G6GokfSbR3d3QDTdkxhIsE/G5kDeSwJq4ArmQ3mGViP4XTFn7k/KYx/xX01xWlQov0mmRYv/nv4DS1jwAEu2Vz5R1SK9ZEEnfS+lh+qU2eoEQEdMwsnZws4OkwvTkbCX0YINNhZcVDQd4/m0qBtdinaDbf7UKDmbl+x3kXtG9Hnuf0z+KZ21Xkg3nkNa/LXtZlVevSnYjKakZnZkYX/Bqcr27ZXZoFAaXDYTkOkIldZGlp/EkswUi4+FXS6WaFAgMBAAECggEAMq8beK9FPnUbhaWoc3YvGgaTuPDfStT6D/8JSB/sK6DzDsFw9IVu9IzqTPAFb/DccBxORJ5FKcAZSmgUm1xP7Jq40uK4HL2vx7zhsFYBByBZcX7Ye5/pXb1ld+UlIjjbHb1E5RcMlFaJrSWy0mdu5BjSew37vUmgCRnskss27Ht42soGwqv7Ay6iHYbHTOwqQBnVC6dGqVpiCYW/fAcYEXhIdPWnfp8TyhxLtUXzH96tXo2T4PEcTsZthqFoAo+JBxlDbkq2qFkEUBRDPtKEeri+oygOZTv6C0u8NFiTEZpttFiPnp+i0UlEfYFBN2uUmy+2SDi1wDjSYx+1Z9T7gQKBgQDmN8OIKkeOnurrq2eZYW1/cowUdOGxPWx3aPvKuVj7swvyoXRxWMtT2DGjFogqFyLXScWGMQRcBne8ygtbyv1KPRx60kti1M/QH9jKIO8az6yrkQVKCf/40tmTKfsEhpn9LGJIKJCCmp0RD+hBXpf4NXIabRUNcXqxDKrCx01i8QKBgQCc+isu90BTZ4OIR5fqkjJOM7GaXKHazWjxBbKrbTOr9bpIf0RjulQL9nQb5ZSic+lEofMsd5Yiss4qm1/6WrYxFsHDJ6ElXo+/YBaZXEpw5HywhUFcmKdRhnvWmSBo81mMnv6I2z18MgNiQRX51ZAIx/vd4gqSK/Y015px3f9U1QKBgB4xRAiQmu/ecarUwPKBr42eZTatmYfYKWiDsToGEuB3u+JnQFE5NvlZCjF8XAFBJYWcnDYcKZk0E/IDgXfZaFybJSgKem6MwHBoLNNdfF5EzD13+HycUSC97mD5iX0vdfSZJI6YG1O1mVHKh0hDcqTPxZYnpjIsJs4ffimrX7/RAoGBAI1ySmkWd8rvkjDBOh0uwM+Aev85ispqnq3XUYhwbMf9Mm7S5p/qLINe/n6InybGxSGHwGOIGiuH7Iet24zuIg8Xj163HdhbXbG29oYYCCezartwR1YVY2bthDb5YZ/P7xfryDUvQDbhpfTp6tBk5FVVRhlH7fiKTl6NC+igfctFAoGBAM2yPve2cUThgOjBityVffjmARnf45GUjA5mRqSz+2fxic7YmsB5KNkR4oRI+GGTGKqeSg3X4q5uMjsOx2v1YO9yfKuO2jU6c2FQx4FhwXdpRSii9bNI82n0lKHnWZrJnej0U2LCuUnIUSymcfTco1NG0P0ldC0PEQl8Qzf2dc9G";
	private static String alipayGateway = "https://openapi.alipaydev.com/gateway.do";
	
	
	/**
	 * 获取token
	 * @param code
	 * @return
	 */
	public String getToken(String code) {
		AlipayClient alipayClient = GetAlipayClient();
		AlipayOpenAuthTokenAppRequest requestLogin = new AlipayOpenAuthTokenAppRequest();
		requestLogin.setBizContent("{" +
		        "\"grant_type\":\"authorization_code\"," +
		        "\"code\":\""+ code +"\"" +
		        "}");
		try {
			AlipayOpenAuthTokenAppResponse responseToken = alipayClient.execute(requestLogin);
			if(responseToken.isSuccess()){
				return responseToken.getUserId();
			}else{
				return responseToken.getMsg();
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getErrMsg();
		}
		
	}
	
	private AlipayClient  GetAlipayClient(){
		AlipayClient alipayClient = new DefaultAlipayClient(alipayGateway,APPID,privateKey,"json","UTF-8",publicKey,"RSA2");
		return alipayClient;
	}
	
	public String CertifyInitialize() throws AlipayApiException{
		String biz_no = null;
		AlipayClient alipayClient = GetAlipayClient();
		ZhimaCustomerCertificationInitializeRequest request = new ZhimaCustomerCertificationInitializeRequest();
		long generatedTranid = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append("ZGYD");
		sb.append(String.valueOf(generatedTranid));
		Random rd = new Random(10);
		for(int i=0;i<8;i++){
			sb.append(String.valueOf(rd.nextInt(10)));
		}
		
		String bizContent = "{"
		        + "\"transaction_id\":\""+sb.toString()+"\","
		        + "\"product_code\":\"w1010100000000002978\","
		        + "\"biz_code\":\"FACE\","
		        + "\"identity_param\":\"{\\\"identity_type\\\":\\\"CERT_INFO\\\",\\\"cert_type\\\":\\\"IDENTITY_CARD\\\",\\\"cert_name\\\":\\\"张三\\\",\\\"cert_no\\\":\\\"260104197909275964\\\"}\","
		        + "\"ext_biz_param\":\"{}\"" + "  }";
		request.setBizContent(bizContent);
		ZhimaCustomerCertificationInitializeResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
		    System.out.println("调用成功");
		    biz_no = response.getBizNo();
		} else {
		    System.out.println("调用失败");
		    System.out.println(response.getBody());
		}
		return biz_no;
	}
	
	public String CertifyInvoke(String biz_no) throws AlipayApiException{
		AlipayClient alipayClient = GetAlipayClient();
		ZhimaCustomerCertificationCertifyRequest request = new ZhimaCustomerCertificationCertifyRequest();

		// 设置业务参数,必须要biz_no
		request.setBizContent("{\"biz_no\":\""+biz_no+"\"}");

		// 设置回调地址,必填. 如果需要直接在支付宝APP里面打开回调地址使用alipay协议
		// alipay://www.taobao.com 或者 alipays://www.taobao.com,分别对应http和https请求
		request.setReturnUrl("alipays://www.taobao.com");

		// 这里一定要使用GET模式
		ZhimaCustomerCertificationCertifyResponse response = alipayClient.pageExecute(request, "GET");
		// 从body中获取URL
		String url = response.getBody();
		System.out.println("generateCertifyUrl url:" + url);
		return url;
	}
	
	
	
}