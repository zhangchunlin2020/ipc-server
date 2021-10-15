package com.ipc.netsdk.dh;

import java.text.SimpleDateFormat;

public class ToolKits {

	static NetSDKLib netsdkapi = NetSDKLib.NETSDK_INSTANCE;
	static NetSDKLib configapi = NetSDKLib.CONFIG_INSTANCE;

	// 获取当前时间
	public static String getDate() {
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleDate.format(new java.util.Date()).replace(" ", "_").replace(":", "-");

		return date;
	}

	/**
	 * 获取接口错误码和错误信息，用于打印
	 * @return
	 */
	public static String getErrorCodePrint() {
		return "\n{error code: (0x80000000|" + (netsdkapi.CLIENT_GetLastError() & 0x7fffffff) +").参考  NetSDKLib.java }"
				+ " - {error info:" + ErrorCode.getErrorCode(netsdkapi.CLIENT_GetLastError()) + "}\n";
	}

}
