package com.ipc.netsdk.dh;

import com.sun.jna.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * NetSDK JNA接口封装
 */
public interface NetSDKLib extends Library {

    NetSDKLib NETSDK_INSTANCE = Native.load(LibraryLoad.getLoadLibrary("dhnetsdk"), NetSDKLib.class);

    NetSDKLib CONFIG_INSTANCE = Native.load(LibraryLoad.getLoadLibrary("dhconfigsdk"), NetSDKLib.class);

    //NetSDKLib CONFIG_JNI = (NetSDKLib)Native.loadLibrary(util.getLoadLibrary("JNI1.dll"), INetSDK.class);
    class LLong extends IntegerType {
        private static final long serialVersionUID = 1L;

        /** Size of a native long, in bytes. */
        public static int size;
        static {
            size = Native.LONG_SIZE;
            if (Utils.getOsPrefix().equalsIgnoreCase("linux-amd64")
                    || Utils.getOsPrefix().equalsIgnoreCase("win32-amd64")
                    || Utils.getOsPrefix().equalsIgnoreCase("mac-64")) {
                size = 8;
            } else if (Utils.getOsPrefix().equalsIgnoreCase("linux-i386")
                    || Utils.getOsPrefix().equalsIgnoreCase("win32-x86")) {
                size = 4;
            }
        }

        /** Create a zero-valued LLong. */
        public LLong() {
            this(0);
        }

        /** Create a LLong with the given value. */
        public LLong(long value) {
            super(size, value);
        }
    }
    public static class SdkStructure extends Structure {
        @Override
        protected List<String> getFieldOrder(){
            List<String> fieldOrderList = new ArrayList<String>();
            for (Class<?> cls = getClass();
                 !cls.equals(SdkStructure.class);
                 cls = cls.getSuperclass()) {
                Field[] fields = cls.getDeclaredFields();
                int modifiers;
                for (Field field : fields) {
                    modifiers = field.getModifiers();
                    if (Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
                        continue;
                    }
                    fieldOrderList.add(field.getName());
                }
            }
            //            System.out.println(fieldOrderList);

            return fieldOrderList;
        }

        @Override
        public int fieldOffset(String name){
            return super.fieldOffset(name);
        }
    }

    /************************************************************************
     ** 常量定义
     ***********************************************************************/
    public static final int NET_SERIALNO_LEN                      = 48;             // 设备序列号字符长度
    public static final int MAX_LOG_PATH_LEN                    = 260;  // 日志路径名最大长度

    /************************************************************************
     ** 结构体
     ***********************************************************************/
    // 设置登入时的相关参数
    public static class NET_PARAM  extends SdkStructure
    {
        public int                    nWaittime;                // 等待超时时间(毫秒为单位)，为0默认5000ms
        public int                    nConnectTime;            	// 连接超时时间(毫秒为单位)，为0默认1500ms
        public int                    nConnectTryNum;           // 连接尝试次数，为0默认1次
        public int                    nSubConnectSpaceTime;    	// 子连接之间的等待时间(毫秒为单位)，为0默认10ms
        public int                    nGetDevInfoTime;        	// 获取设备信息超时时间，为0默认1000ms
        public int                    nConnectBufSize;        	// 每个连接接收数据缓冲大小(字节为单位)，为0默认250*1024
        public int                    nGetConnInfoTime;         // 获取子连接信息超时时间(毫秒为单位)，为0默认1000ms
        public int                    nSearchRecordTime;      	// 按时间查询录像文件的超时时间(毫秒为单位),为0默认为3000ms
        public int                    nsubDisconnetTime;      	// 检测子链接断线等待时间(毫秒为单位)，为0默认为60000ms
        public byte                   byNetType;                // 网络类型, 0-LAN, 1-WAN
        public byte                   byPlaybackBufSize;      	// 回放数据接收缓冲大小（M为单位），为0默认为4M
        public byte                   bDetectDisconnTime;       // 心跳检测断线时间(单位为秒),为0默认为60s,最小时间为2s
        public byte                   bKeepLifeInterval;        // 心跳包发送间隔(单位为秒),为0默认为10s,最小间隔为2s
        public int                    nPicBufSize;              // 实时图片接收缓冲大小（字节为单位），为0默认为2*1024*1024
        public byte[]                 bReserved = new byte[4];  // 保留字段字段
    }

    // 设备信息扩展///////////////////////////////////////////////////
    public static class NET_DEVICEINFO_Ex extends SdkStructure {
        public byte[]     sSerialNumber = new byte[NET_SERIALNO_LEN];    // 序列号
        public int        byAlarmInPortNum;                              // DVR报警输入个数
        public int        byAlarmOutPortNum;                             // DVR报警输出个数
        public int        byDiskNum;                                     // DVR硬盘个数
        public int        byDVRType;                                     // DVR类型,见枚举NET_DEVICE_TYPE
        public int        byChanNum;                                     // DVR通道个数
        public byte       byLimitLoginTime;                              // 在线超时时间,为0表示不限制登陆,非0表示限制的分钟数
        public byte       byLeftLogTimes;                                // 当登陆失败原因为密码错误时,通过此参数通知用户,剩余登陆次数,为0时表示此参数无效
        public byte[]     bReserved = new byte[2];                       // 保留字节,字节对齐
        public int        byLockLeftTime;                                // 当登陆失败,用户解锁剩余时间（秒数）, -1表示设备未设置该参数
        public byte[]     Reserved = new byte[24];                       // 保留
    }

    // SDK全局日志打印信息
    public static class LOG_SET_PRINT_INFO extends SdkStructure
    {
        public int 		dwSize;
        public int 		bSetFilePath;//是否重设日志路径, BOOL类型，取值0或1
        public byte[] 	szLogFilePath = new byte[MAX_LOG_PATH_LEN];//日志路径(默认"./sdk_log/sdk_log.log")
        public int 		bSetFileSize;//是否重设日志文件大小, BOOL类型，取值0或1
        public int 		nFileSize;//每个日志文件的大小(默认大小10240),单位:比特, 类型为unsigned int
        public int 		bSetFileNum;//是否重设日志文件个数, BOOL类型，取值0或1
        public int 		nFileNum;//绕接日志文件个数(默认大小10), 类型为unsigned int
        public int 		bSetPrintStrategy;//是否重设日志打印输出策略, BOOL类型，取值0或1
        public int 		nPrintStrategy;//日志输出策略,0:输出到文件(默认); 1:输出到窗口, 类型为unsigned int
        public byte[]	byReserved=new byte[4];							// 字节对齐
        public Pointer	cbSDKLogCallBack;						// 日志回调，需要将sdk日志回调出来时设置，默认为NULL
        public Pointer	dwUser;									// 用户数据
        public LOG_SET_PRINT_INFO()
        {
            this.dwSize = this.size();
        }
    }

    // CLIENT_LoginWithHighLevelSecurity 输入参数
    public static class NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY extends SdkStructure
    {
        public int						    dwSize;				            // 结构体大小
        public byte[]						szIP=new byte[64];	            // IP
        public int							nPort;				            // 端口
        public byte[]						szUserName=new byte[64];		// 用户名
        public byte[]						szPassword=new byte[64];		// 密码
        public int		                    emSpecCap;			            // 登录模式
        public byte[]						byReserved=new byte[4];		    // 字节对齐
        public Pointer pCapParam;			            // 见 CLIENT_LoginEx 接口 pCapParam 与 nSpecCap 关系

        public NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY()
        {
            this.dwSize = this.size();
        }// 此结构体大小
    };

    // CLIENT_LoginWithHighLevelSecurity 输出参数
    public static class NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY extends SdkStructure
    {
        public int						    dwSize;				            // 结构体大小
        public NET_DEVICEINFO_Ex			stuDeviceInfo;		            // 设备信息
        public int							nError;				            // 错误码，见 CLIENT_Login 接口错误码
        public byte[]						byReserved=new byte[132];	    // 预留字段

        public NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY()
        {
            this.dwSize = this.size();
        }// 此结构体大小
    };

    /************************************************************************
     ** 接口
     ***********************************************************************/
    //  JNA直接调用方法定义，cbDisConnect 实际情况并不回调Java代码，仅为定义可以使用如下方式进行定义。 fDisConnect 回调
    public boolean CLIENT_Init(Callback cbDisConnect, Pointer dwUser);

    // 打开日志功能
    // pstLogPrintInfo指向LOG_SET_PRINT_INFO的指针
    public boolean CLIENT_LogOpen(LOG_SET_PRINT_INFO pstLogPrintInfo);

    //  JNA直接调用方法定义，设置断线重连成功回调函数，设置后SDK内部断线自动重连, fHaveReConnect 回调
    public void CLIENT_SetAutoReconnect(Callback cbAutoConnect, Pointer dwUser);

    // 设置连接设备超时时间和尝试次数
    public void CLIENT_SetConnectTime(int nWaitTime, int nTryTimes);

    // 设置登陆网络环境
    public void CLIENT_SetNetworkParam(NET_PARAM pNetParam);

    // 关闭日志功能
    public boolean CLIENT_LogClose();

    //  JNA直接调用方法定义，SDK退出清理
    public void CLIENT_Cleanup();

    //  JNA直接调用方法定义，向设备注销
    public boolean CLIENT_Logout(LLong lLoginID);

    // 高安全级别登陆
    public LLong CLIENT_LoginWithHighLevelSecurity(NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY pstInParam, NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY pstOutParam);

    // 返回函数执行失败代码
    public int CLIENT_GetLastError();

    /***********************************************************************
     ** 回调
     ***********************************************************************/
    //JNA Callback方法定义,断线回调
    public interface fDisConnect extends Callback {
        public void invoke(LLong lLoginID, String pchDVRIP, int nDVRPort, Pointer dwUser);
    }

    // 网络连接恢复回调函数原形
    public interface fHaveReConnect extends Callback {
        public void invoke(LLong lLoginID, String pchDVRIP, int nDVRPort, Pointer dwUser);
    }

}



