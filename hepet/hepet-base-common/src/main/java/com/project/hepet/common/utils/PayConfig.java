package com.project.hepet.common.utils;

public class PayConfig {
	/** 商户清算机构号 */
	public static String merchantCode="N04020100000010";
	/** 贴息标记 000000：不贴息  000001：贴息 */
	public static String midProdId="000000";
	/** 商户贴息   贴息时移入贴息金额，否则移入空*/
	public static String midFee="";
	/** 折扣优惠代码   默认值：000001*/
	public static String comCouCode="000001";
	
	/** 商户秘钥 */
	public static String clientPrivateKey = ResourceUtils.getValue("hepet-core", "mall.clientPrivateKey");
	public static String serverPublicKey = ResourceUtils.getValue("hepet-core", "mall.serverPublicKey");	
	/** 服务端秘钥 */
	public static String serverPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCV2DPNKCCYse6evbnCwdEj/5ZY42gasFKcDVrIWKCm1fU6ztCYF+blRmQ59fKLfjMVEhwd1/4N45aW5++5daOj9ratgCrzglhS9q0AvxwHau8gXxVod7XlWcNUZAGroOA0OyFcVmIpTKxEAvacJlfgWQSAsobrEc8RBW3oKy/mJ33q3XUGf0uS88i0JiNZPoGUDvSXzv9dMRUV61T1fSK3kFitZR7feaQvUBEloRZAKXEHWC1XzxzSktdAWTPolkn/LOkqGwGCxvnl4MsjhgzKEEv1OHA/JBR4JVn3TbZFyhdlvqWE4pboNlyVUmkOqvRF+2EhiyNzEgCzg3l1CZSpAgMBAAECggEADeNjT7tNpCFvwq5Gip7QrdO5YWU4Z6raiUSympmeUEBJ8aisDEDA4ZS/4uBhGd05SlVqnjh//1UOBtAZ97hH0cFi5ptXv8ifu5bJ66UK6cQXREqTzyIzTX3fnRGE1x260hR979NA11+YIticd1YVdIGDqZMCbsSwir0UVf20u5HeqqumFH44ai8DpGF44jO5N32BSQdgi/2Rq6cTBzggrHWeOqP8cDzjEutcGr5nsrPOBR6ywUjLi4WIKQxhivWo+LOUdUnQCXItAvgRTCIR/6g2HeAtV8PRE6MLBlvXlPFhwfnS5IJPCpX1tZIhdSmwpPZU+V0eaYO9YYc4dYAN4QKBgQDF3SQBoX0mE3tLFMxERYtEPvBqzELNWllKO/m13+arGBr3ESrkZv3yA+ZUd+v/Gn0bHLT9uj1xu9+6RMNcssi5McxJBuAlRoP/Om0Qct6NuoC2U72QA9zet4zILW6OlAUMU1LNEM4XXdRNL+0SP1QxPADIZ10LHMNAnK7pIvaRUQKBgQDB3yluKZ+wVqqpSgtlheiu2hadKMwjCLpTf1/Kq8wEZPr74dIwmWfn/z9sHSDOJbRlo82pu1JEpvLZnEMIPgCQaxJgW0WCYbLYOycut7gbQxhzJFFSd+Wuqyuw+DSQjMynC+gKzYZMZuLipa834lRg1Z1VBf7GidLZHLHbl9A32QKBgQDEApV31B2hSa1bLKhKfcY//kW21tDak8Vep9lxFfRxy/hyhxxBldG+HaTgSsiMOBZ1dFPMAuJ5uA8jfJk4TJhR9gYU1e3Yn2mFvsBqihD0wCxf8scYUutL6vNZHsCmF/aERksBgMNZHIeDS5YD/J6QA4wW+2aN6pl2eYFAzXULcQKBgBJDkcWdCjw0jfNQ1G4GBXujdAnuM/0A73y6PS38rUWktbhQ99o4OX+znifzPontJ2eD3PYmfYOWLr6tZ+s43Bi+3YZSUuQLjCuutuxBxoha88y1GSwpu2uPGJidaWZMvWLnTj7mScac0rcDYNRk8AoHVcESxYOyrDhj6aWEUctRAoGBAIj0vaOJmHW3GuygMmKrZux7LdlYZ5inP9UC0OKzHpG5arRjRd9qrZZP5nwno+SyW2eXJkQVIvr7aZuqKDLKhjQTNKHA7AvIanOsFXLYnEc3pjCllAccfaKclbnQQqsyid71DJWPAF1THj14dlaS2bDQb6dLgCj6ryGV4ey+HWCr";
	public static String clientPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlz2EIr7g45mRho283y1WlP+qmxURxX912DCSGnwF0akCbMSdicFUoZv+BFv58IukwF3qh6A6UPzHbQi8VQRNzm2C4AKHf+PHv6Km9KVYswTSsIS0UG7HnSSZX4teN+9Mai973sp+4Es4RaeCqHzqPnggfZiQDqR5e921twekq+MAG722tvd3woWaKUayGhGSQpEfzpF8l3gV7oxFtg9BHlCk5K8A7pQ4bwAajSpW0F3xHlfUquTa1PzKLOEfCSyp4s/QzZ3/uzIaikhsSDiNcsLniJZ7lO2DM+ooMPE0nXj7fMMwd61h83+Qo96F29/6XnYw0cHvqUbPl1uCNOGgmQIDAQAB";
	
	/** 支付请求提交地址 */
	public static String pay_gateway_url = ResourceUtils.getValue("hepet-core", "mall.payGatewayUrl");
	
	public static String input_charset = "utf-8";
	
	/**商城host配置*/
	public static String baseUrl = "http://wallet.iok.la:11208";
	public static String projectName = "/hepet-web";
}
