/**
 * TmriJaxRpcOutNewAccessServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jrwp.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

import com.jrwp.webservice.utils.Constant;

public class TmriJaxRpcOutNewAccessServiceLocator extends Service implements TmriJaxRpcOutNewAccessService {

	private static final long serialVersionUID = 965199323953019271L;

	public TmriJaxRpcOutNewAccessServiceLocator() {
	}

	public TmriJaxRpcOutNewAccessServiceLocator(EngineConfiguration config) {
		super(config);
	}

	public TmriJaxRpcOutNewAccessServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for TmriOutNewAccess
	private String TmriOutNewAccess_address = Constant.tmriOutNewAccessAddress;

	public String getTmriOutNewAccessAddress() {
		return TmriOutNewAccess_address;
	}

	// The WSDD service name defaults to the port name.
	private String TmriOutNewAccessWSDDServiceName = "TmriOutNewAccess";

	public String getTmriOutNewAccessWSDDServiceName() {
		return TmriOutNewAccessWSDDServiceName;
	}

	public void setTmriOutNewAccessWSDDServiceName(String name) {
		TmriOutNewAccessWSDDServiceName = name;
	}

	public TmriJaxRpcOutNewAccess getTmriOutNewAccess() throws ServiceException {
		URL endpoint;
		try {
			endpoint = new URL(TmriOutNewAccess_address);
		} catch (MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getTmriOutNewAccess(endpoint);
	}

	public TmriJaxRpcOutNewAccess getTmriOutNewAccess(URL portAddress) throws ServiceException {
		try {
			TmriOutNewAccessSoapBindingStub _stub = new TmriOutNewAccessSoapBindingStub(portAddress, this);
			_stub.setPortName(getTmriOutNewAccessWSDDServiceName());
			return _stub;
		} catch (AxisFault e) {
			return null;
		}
	}

	public void setTmriOutNewAccessEndpointAddress(String address) {
		TmriOutNewAccess_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	@SuppressWarnings("rawtypes")
	public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
		try {
			if (TmriJaxRpcOutNewAccess.class.isAssignableFrom(serviceEndpointInterface)) {
				TmriOutNewAccessSoapBindingStub _stub = new TmriOutNewAccessSoapBindingStub(
						new URL(TmriOutNewAccess_address), this);
				_stub.setPortName(getTmriOutNewAccessWSDDServiceName());
				return _stub;
			}
		} catch (Throwable t) {
			throw new ServiceException(t);
		}
		throw new ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public Remote getPort(QName portName, @SuppressWarnings("rawtypes") Class serviceEndpointInterface)
			throws ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("TmriOutNewAccess".equals(inputPortName)) {
			return getTmriOutNewAccess();
		} else {
			Remote _stub = getPort(serviceEndpointInterface);
			((Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName() {
		return new QName(Constant.TmriOutNewAccessNamespace, "TmriJaxRpcOutNewAccessService");
	}

	@SuppressWarnings("rawtypes")
	private HashSet ports = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator getPorts() {
		if (ports == null) {
			ports = new HashSet();
			ports.add(new QName(Constant.TmriOutNewAccessNamespace, "TmriOutNewAccess"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName, String address) throws ServiceException {

		if ("TmriOutNewAccess".equals(portName)) {
			setTmriOutNewAccessEndpointAddress(address);
		} else { // Unknown Port Name
			throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(QName portName, String address) throws ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
