<!DOCTYPE html>
<html>
<head>
<%String path = request.getContextPath() + "/";%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>this is a test</title>
<script type="text/javascript" src="https://cdn.bootcss.com/zepto/1.2.0/zepto.min.js"></script>
</head>
<body>
<div style="padding:50px 50px;font-size:24px;line-height:24px;">
	Java thirdParty login for wechat and alipay:<a href="wechat/auth" style="text-decoration:none;">
	<img src="<%=path%>img/wx.png" style="height:24px;width:24px;" />
</a>
<a href="alipay/auth" style="text-decoration:none;padding-left:20px;">
	<img src="<%=path%>img/zfb.png" style="height:24px;width:24px;" />
</a>

<a id="btnZhima" href="javascript:void 0;">SesameAuth</a>
</div>

<span>${sanfangUrl}/?brockid=111&&usertype=1</span>


<script type="text/javascript">
	$(function(){
		$("#btnZhima").click(function(){
			$.ajax({
	            type: 'get',
	            url: 'alipay/zhima',
	            success: function(res) {
	                window.location.href = res;
	            },
	            error: function(err){
	            	alert(err);
	            }
	        });
		})
	})
</script>
</body>
</html>