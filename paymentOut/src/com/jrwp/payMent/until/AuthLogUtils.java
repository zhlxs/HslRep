package com.jrwp.payMent.until;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;
import com.jrwp.payMent.entity.AuthLog;
import com.jrwp.payMent.service.IAuthLogService;
import com.jrwp.webservice.help.SpringContextHolder;

public class AuthLogUtils {

	// 以cpu核心数为参数创建一个定长线程池
	private static ExecutorService fixedThreadPool = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	// 从容器中取出接口日志服务层对象
	private static IAuthLogService authLogService = SpringContextHolder
			.getBean(IAuthLogService.class);

	public static void saveLog(AuthLog log, JSONObject jsonParams) {
		saveLog(log);
	}

	public static void saveLog(AuthLog log) {
		// 设置日志主键
		fixedThreadPool.execute(new SaveLogThread(log));
	}

	/**
	 * 保存日志线程
	 */
	private static class SaveLogThread extends Thread {
		AuthLog log;

		/**
		 * @param log
		 *            日志对象
		 */
		public SaveLogThread(AuthLog log) {
			super();
			this.log = log;
		}

		@Override
		public void run() {
			try {
				authLogService.save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
