package com.jrwp.webservice.help;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring ApplicationContext, 以备程序中调取spring容器中的bean.
 * 
 */
@Component(value = "springContextHolder")
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);
	// 持有spring容器的静态变量
	private static ApplicationContext applicationContext = null;

	/**
	 * 取得spring容器
	 * 
	 * @return spring容器
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 根据<bean>标签的id调取spring容器中的bean, 自动转型为所赋值对象的类型
	 * 
	 * @param name
	 *            <bean>标签的id
	 * @return spring容器中的bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 根据类型调取spring容器中的bean, 自动转型为所赋值对象的类型
	 * 
	 * @param requiredType
	 *            类型
	 * @return spring容器中的bean
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清除SpringContextHolder
	 */
	public static void clearHolder() {
		applicationContext = null;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.info("注入spring容器");
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy() throws Exception {
		logger.info("销毁spring容器");
		SpringContextHolder.clearHolder();
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		Validate.validState(applicationContext != null,
				"applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder");
	}
}