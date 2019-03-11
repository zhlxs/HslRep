package com.jrwp.core.help;

public class Config {
	public static String softVersion = "config.main.softVersion";
	/**
	 * 系统配置参数个数
	 */
	public static String paramSize = "config.main.paramSize";
	/**
	 * 机器码的key
	 */
	public static String machineCodeKey = "config.main.machineCodeKey";
	/**
	 * 核验对应参数(默认4)
	 */
	public static String checkParam = "config.main.checkParam";
	/**
	 * 人口接口
	 */
	public static String rkApi = "config.sfz.rkApi";
	/**
	 * 丢失补领
	 */
	public static String dsbl = "config.pay.dsbl";

	/**
	 * 到期换领
	 */
	public static String dqhl = "config.pay.dqhl";
	/**
	 * 默认0不用做修改
	 */
	public static String payId = "config.pay.work.id";
	/**
	 * 业务ID 最大长度36位字符串 必填项
	 */
	public static String payWorkId = "config.pay.work.wordId";
	/**
	 * 设备唯一标识
	 */
	public static String payMachineId = "config.pay.work.machineId";
	/**
	 * 设备类型
	 */
	public static String payMachineType = "config.pay.work.machineType";
	/**
	 * 接口ip
	 */
	public static String payUrl = "config.pay.work.url";
	/**
	 * 请求支付
	 */
	public static String payOrder = "config.pay.work.order";
	/**
	 * 支付二维码
	 */
	public static String payQrCode = "config.pay.work.qrCode";
	/**
	 * 支付状态
	 */
	public static String payStatus = "config.pay.work.status";
	/**
	 * 日志地址
	 */
	public static String logUrl = "config.log.logUrl";
	/**
	 * 日志提交路径
	 */
	public static String logRequestUrl = "config.log.logRequestUrl";
	/**
	 * 运行信息地址
	 */
	public static String infoUrl = "config.log.infoUrl";
	/**
	 * 运行信息提交路径
	 */
	public static String infoRequestUrl = "config.log.infoRequestUrl";

}
