package com.nest_lot.config;

public class AppPayConfig {

	// 应用ID，收款账号就是对应的appid
	public static final String app_id = "2018070960579458";
	// 私钥 PKCS8格式RSA2
	public static final String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCaFfamZZixQ75QKu7ZbHMLHCzbc5PCRB+r8KIPdHMnS6j"
			+ "lPoLnG/zWOI2FMWlf2ygBQtlDjPDyRb2LmIuBL/Mr1XWKeDm40KPOUCHJJiGyJ0QUl7E9C+uPVs15dlXNG0RxtrjEU08QyJFPhdyIc1iThuXMVjku+Uo/S8DFuM4r5cAgsnekHh"
			+ "Tqv/2RloaCDY6qjofkBi0eIpbZ3ce0rUpgxd/czq8pAOfbwkccs9Ng4Dsw0jr8j1oGJ4qYoib6zH+tL96/qFT71Ey8QT4LbsRdZ82ykrH64ng8yag1AgBPztKop4ze66aWkBJ"
			+ "39U5/vWCYh9QKVBTQ7wMLRXBz1v3AgMBAAECggEAeuJQRxGLgK8omP+z0/bUQA1wdeP3EMr93CNLMI7wb9t942+y39YeODV9lHCmfnlAptmaDzP+23NWS80Rf0PCaOKgBBuabn"
			+ "egdM4CXFbD096fAo5b90QS2iG/HV7mGyKv1JyKDoZzPWIVnSIRfd8UZporraE2nvPf0WwjLMoYe4SzIrTBIFOhEPlWwPnd//uZtg8Dj0yqDdGDhlq3Z2vK1yRsEGU5wlvUGp"
			+ "fGYdAYH4dtiFALr/VnnFWh/hS0XS15/YV3Nhnyc4DK9eTkq4KYGtUnp/Bo1CaaTlApTVo50xFNfKQD3bWdBGK6AypmwGZLAD4f1HW33bA1xETTE5llAQKBgQD16M06OruK40"
			+ "3GDmeFNFZM/gV7g1CfwHiyBXwoAVKdDt8sONS32UvaVjQRMcfU2aygPD8njL8KrNONZXxnYGt1TFJy6eob7d5itxurjn/59WcTnornjtAQzACgH69QVTqbeIJScDWXq2OeF"
			+ "T2RA6y15wnsE6sa5mWrfFSw9CswIQKBgQCHwjuOU1ey+HAH1kIoiuv9GDHUMJFHh8IGKsgdtd67ODU92QcICc5+Dn/kqLrjcGisZJVbwUH9lLrAIpKVKWJstl2i/Z36fYxr"
			+ "ZZCxE7DS0AakaZqZPi7+48o3uFQHDum+WwyLoIvWDwypC74Qw99KzilseyoKTK7CoJ1qnyDpFwKBgQDQQX48yCy37NSa3OwSxUYpIh/mgEkxYcFVkouZHbWnSUb7no2XvbE"
			+ "dkU+7GBuSSV6GvT8G8Y2BLuhioQYATony5tYUd8FKMeLaWxRV7/K173yCAwzEGhBfiUUmVm8Ua3mc3SwRQPJCJmXRJU/kleYYjze59NY9i82is0LOMZq5wQKBgHxMncT5kB"
			+ "GgoMq8JUUDtYC9d1olwc7xgPeukh2J2M4ob68aF/MHL5aBZh4RU1RNJrm5OnuRXf90/uxOe1GxW5nX+7HxboTcEQaALtPDCKJsr7g3rMJkihj8rbkIv37a669LpBfvvjkW/GuH"
			+ "AdZN0ROn4Yue1WYqwdQXxSnLWxGvAoGAIvLOgncDt1wV/alP+8J1Oi/ZcJXjbqmHRp74DuoeVc2puCGE3LhyY/GSwiRkNmyDl0uRB9TEWdVqmJawrz3N+RUsQ2+WL3ShNhutM"
			+ "g9qTxNntMpIgMnsSmtIQCeBGZ/FIFnnmuDGL382jRVuc1GpFbGyhwVLsFXPyvfaIU1qEmY=";
	// 对应的支付宝公钥
	public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoD5EBDlUu3NYlr3WtobcRJtHYmEH0bbG9q1V0bX1lSDsfjtjw6EMA39OnfAjhhwBcRsJ9iMPu5itOKoBLUKjtlHI68SqEdSa5FJy2lAoSRsM4B9QFkglIJOufbv1s2huWruGKDGohK/ufulf64yJAX7HOkCDoUIGSuCOnrTTLf8sKLzlGW7y0Ot1+1SEuMmFAHMq4nrclku9HMg3lWhgKEVuSXiUZUYZ6l0URO/gLcF0eBLux6idOjpHc0VBXlGZ/4ZUEWwdNls+8xLZv1pMd34HeeEFhffIBIeQwVxjLEDjQppmSh13T8ihLKTwV91q/D++fj0//kXAFjeJB8o8CwIDAQAB";
	// 支付宝网关
	public static final String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	// 异步回调地址
	public static final String notify_url = "	http://chaoluo.free.ngrok.cc";
	public static final String return_url = "";
	// 签名方式
	public static final String sign_type = "RSA2";
	// 字符编码格式
	public static final String chartset = "utf-8";

}
