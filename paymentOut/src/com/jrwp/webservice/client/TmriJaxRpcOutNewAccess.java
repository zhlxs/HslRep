/**
 * TmriJaxRpcOutNewAccess.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jrwp.webservice.client;

public interface TmriJaxRpcOutNewAccess extends java.rmi.Remote {
	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkxlh
	 *            接口序列号
	 * @param jkid
	 *            接口标识
	 * @param yhbz
	 *            用户标识
	 * @param dwmc
	 *            单位名称
	 * @param dwjgdm
	 *            单位机构代码
	 * @param yhxm
	 *            用户姓名
	 * @param zdbs
	 *            终端标识
	 * @param UTF8XmlDoc
	 *            查询条件
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public java.lang.String queryObjectOut(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid,
			java.lang.String yhbz, java.lang.String dwmc, java.lang.String dwjgdm, java.lang.String yhxm,
			java.lang.String zdbs, java.lang.String UTF8XmlDoc) throws java.rmi.RemoteException;

	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkxlh
	 *            接口序列号
	 * @param jkid
	 *            接口标识
	 * @param cjsqbh
	 *            场景编号
	 * @param dwjgdm
	 *            单位机构代码
	 * @param dwmc
	 *            单位名称
	 * @param yhbz
	 *            用户标识
	 * @param yhxm
	 *            用户姓名
	 * @param zdbs
	 *            终端标识
	 * @param UTF8XmlDoc
	 *            查询条件
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public java.lang.String queryObjectOutNew(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid,
			java.lang.String cjsqbh, java.lang.String dwjgdm, java.lang.String dwmc, java.lang.String yhbz,
			java.lang.String yhxm, java.lang.String zdbs, java.lang.String UTF8XmlDoc) throws java.rmi.RemoteException;

	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkxlh
	 *            接口序列号
	 * @param jkid
	 *            接口标识
	 * @param yhbz
	 *            用户标识
	 * @param dwmc
	 *            单位名称
	 * @param dwjgdm
	 *            单位机构代码
	 * @param yhxm
	 *            用户姓名
	 * @param zdbs
	 *            终端标识
	 * @param UTF8XmlDoc
	 *            写入数据
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public java.lang.String writeObjectOut(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid,
			java.lang.String yhbz, java.lang.String dwmc, java.lang.String dwjgdm, java.lang.String yhxm,
			java.lang.String zdbs, java.lang.String UTF8XmlDoc) throws java.rmi.RemoteException;

	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkxlh
	 *            接口序列号
	 * @param jkid
	 *            接口标识
	 * @param cjsqbh
	 *            查询条件
	 * @param dwjgdm
	 *            单位机构代码
	 * @param dwmc
	 *            单位名称
	 * @param yhbz
	 *            用户标识
	 * @param yhxm
	 *            用户姓名
	 * @param zdbs
	 *            终端标识
	 * @param UTF8XmlDoc
	 *            写入数据
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public java.lang.String writeObjectOutNew(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid,
			java.lang.String cjsqbh, java.lang.String dwjgdm, java.lang.String dwmc, java.lang.String yhbz,
			java.lang.String yhxm, java.lang.String zdbs, java.lang.String UTF8XmlDoc) throws java.rmi.RemoteException;
}
