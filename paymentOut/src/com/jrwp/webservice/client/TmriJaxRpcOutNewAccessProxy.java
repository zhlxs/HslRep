package com.jrwp.webservice.client;

import com.jrwp.webservice.utils.Constant;

public class TmriJaxRpcOutNewAccessProxy implements TmriJaxRpcOutNewAccess {
	private String _endpoint = null;
	private TmriJaxRpcOutNewAccess tmriJaxRpcOutNewAccess = null;

	public TmriJaxRpcOutNewAccessProxy() {
		_initTmriJaxRpcOutNewAccessProxy();
	}

	public TmriJaxRpcOutNewAccessProxy(String endpoint) {
		_endpoint = endpoint;
		_initTmriJaxRpcOutNewAccessProxy();
	}

	private void _initTmriJaxRpcOutNewAccessProxy() {
		try {
			tmriJaxRpcOutNewAccess = (new TmriJaxRpcOutNewAccessServiceLocator()).getTmriOutNewAccess();
			if (tmriJaxRpcOutNewAccess != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) tmriJaxRpcOutNewAccess)._setProperty("javax.xml.rpc.service.endpoint.address",
							_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) tmriJaxRpcOutNewAccess)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (tmriJaxRpcOutNewAccess != null)
			((javax.xml.rpc.Stub) tmriJaxRpcOutNewAccess)._setProperty("javax.xml.rpc.service.endpoint.address",
					_endpoint);

	}

	public TmriJaxRpcOutNewAccess getTmriJaxRpcOutNewAccess() {
		if (tmriJaxRpcOutNewAccess == null)
			_initTmriJaxRpcOutNewAccessProxy();
		return tmriJaxRpcOutNewAccess;
	}

	public java.lang.String queryObjectOut(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid,
			java.lang.String yhbz, java.lang.String dwmc, java.lang.String dwjgdm, java.lang.String yhxm,
			java.lang.String zdbs, java.lang.String UTF8XmlDoc) throws java.rmi.RemoteException {
		if (tmriJaxRpcOutNewAccess == null)
			_initTmriJaxRpcOutNewAccessProxy();
		return tmriJaxRpcOutNewAccess.queryObjectOut(xtlb, jkxlh, jkid, yhbz, dwmc, dwjgdm, yhxm, zdbs, UTF8XmlDoc);
	}

	public String queryObjectOutNew(String xtlb, String jkxlh, String jkid, String cjsqbh, String dwjgdm, String dwmc,
			String yhbz, String yhxm, String zdbs, String UTF8XmlDoc) throws java.rmi.RemoteException {
		if (tmriJaxRpcOutNewAccess == null)
			_initTmriJaxRpcOutNewAccessProxy();
		return tmriJaxRpcOutNewAccess.queryObjectOutNew(xtlb, jkxlh, jkid, cjsqbh, dwjgdm, dwmc, yhbz, yhxm, zdbs,
				UTF8XmlDoc);
	}

	public java.lang.String writeObjectOut(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid,
			java.lang.String yhbz, java.lang.String dwmc, java.lang.String dwjgdm, java.lang.String yhxm,
			java.lang.String zdbs, java.lang.String UTF8XmlDoc) throws java.rmi.RemoteException {
		if (tmriJaxRpcOutNewAccess == null)
			_initTmriJaxRpcOutNewAccessProxy();
		return tmriJaxRpcOutNewAccess.writeObjectOut(xtlb, jkxlh, jkid, yhbz, dwmc, dwjgdm, yhxm, zdbs, UTF8XmlDoc);
	}

	public java.lang.String writeObjectOutNew(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid,
			java.lang.String cjsqbh, java.lang.String dwjgdm, java.lang.String dwmc, java.lang.String yhbz,
			java.lang.String yhxm, java.lang.String zdbs, java.lang.String UTF8XmlDoc) throws java.rmi.RemoteException {
		if (tmriJaxRpcOutNewAccess == null)
			_initTmriJaxRpcOutNewAccessProxy();
		return tmriJaxRpcOutNewAccess.writeObjectOutNew(xtlb, jkxlh, jkid, cjsqbh, dwjgdm, dwmc, yhbz, yhxm, zdbs,
				UTF8XmlDoc);
	}

	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkid
	 *            接口标识
	 * @param UTF8XmlDoc
	 *            查询条件
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public java.lang.String queryObjectOut(String xtlb, String jkid, String UTF8XmlDoc)
			throws java.rmi.RemoteException {
		return queryObjectOut(xtlb, Constant.jkxlh, jkid, Constant.yhbz, Constant.dwmc, Constant.dwjgdm, Constant.yhxm,
				Constant.zdbs, UTF8XmlDoc);
	}

	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkid
	 *            接口标识
	 * @param cjsqbh
	 *            场景编号
	 * @param UTF8XmlDoc
	 *            查询条件
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public String queryObjectOutNew(String xtlb, String jkid, String cjsqbh, String UTF8XmlDoc)
			throws java.rmi.RemoteException {
		return queryObjectOutNew(xtlb, Constant.jkxlh, jkid, cjsqbh, Constant.dwjgdm, Constant.dwmc, Constant.yhbz,
				Constant.yhxm, Constant.zdbs, UTF8XmlDoc);
	}

	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkid
	 *            接口标识
	 * @param UTF8XmlDoc
	 *            写入数据
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public String writeObjectOut(String xtlb, String jkid, String UTF8XmlDoc) throws java.rmi.RemoteException {
		return writeObjectOut(xtlb, Constant.jkxlh, jkid, Constant.yhbz, Constant.dwmc, Constant.dwjgdm, Constant.yhxm,
				Constant.zdbs, UTF8XmlDoc);
	}

	/**
	 * 
	 * 
	 * @param xtlb
	 *            系统类别
	 * @param jkid
	 *            接口标识
	 * @param cjsqbh
	 *            场景编号
	 * @param UTF8XmlDoc
	 *            写入数据
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public String writeObjectOutNew(String xtlb, String jkid, String cjsqbh, String UTF8XmlDoc)
			throws java.rmi.RemoteException {
		return writeObjectOutNew(xtlb, Constant.jkxlh, jkid, cjsqbh, Constant.dwjgdm, Constant.dwmc, Constant.yhbz,
				Constant.yhxm, Constant.zdbs, UTF8XmlDoc);
	}
}