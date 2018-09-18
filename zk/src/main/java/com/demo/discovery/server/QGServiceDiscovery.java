package com.demo.discovery.server;

import com.demo.discovery.server.annotation.ServiceDesc;
import com.demo.discovery.server.register.IRegister;
import com.demo.discovery.util.LocalIp;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务注册
 *
 *  找出所有标记了 #{ServiceDesc}的类，注册到zookeeper中
 *
 */
public class QGServiceDiscovery implements ApplicationContextAware, InitializingBean {

	private final static Logger logger = LoggerFactory.getLogger(QGServiceDiscovery.class);


    //setter
	private IRegister register;

	private String context;
	
	private String port;

	/**
	 * 标记了 ServiceDesc.
	 */
	private final Map<String /* bean name*/,Object /*bean instance*/> serviceBeanMap = new HashMap<>();


	//监听地址： 127.0.0.1:8081
	private String listenAddress;


	/**
	 *  get all beans
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		serviceBeanMap.putAll(applicationContext.getBeansWithAnnotation(Controller.class));
		serviceBeanMap.putAll(applicationContext.getBeansWithAnnotation(RestController.class));

		logger.info("End to init service discovery application context, found controller or restcontroller class: {}", this.serviceBeanMap);

	}


	@Override
	public void afterPropertiesSet() throws Exception {

		this.listenAddress = LocalIp.getLocalIp() + ":" + getRealPort();

		for (Object bean: serviceBeanMap.values()) {

			//获取service serviceType 如果有YHService的标签，则取标签的值，否则取context
			ServiceDesc annotation = AnnotationUtils.findAnnotation(bean.getClass(), ServiceDesc.class);
			String serviceType =  getServiceDescValue(annotation, this.context);

			//获取controller上的request mapping. @RequestMapping("/value")
			RequestMapping classRequestMappingAnno = AnnotationUtils.findAnnotation(bean.getClass(), RequestMapping.class);
			String class_path = getPath(classRequestMappingAnno);

			//获取每一个方法上的request mapping的value，可能为空
			Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
			for(Method method : methods){
				RequestMapping method_requestMappingAnno = method.getAnnotation(RequestMapping.class);
				   if(method_requestMappingAnno != null) {
					   String method_path = getPath(method_requestMappingAnno);

					   //method name. 如果有YHService的标签，则取标签的值，否则取方法名字
					   ServiceDesc method_anno = method.getAnnotation(ServiceDesc.class);
					   String methodName = getServiceDescValue(method_anno, method.getName());

					   this.register.register(this.context, this.listenAddress, serviceType, methodName, method_path, class_path, method_anno);
				   }

			}

			logger.info("Register all service for controller:{} success",bean );

		}

	}


	private static String getServiceDescValue(ServiceDesc serviceDesc, String defaultValue){
		if(serviceDesc == null) {
			return defaultValue;
		}

		String value= serviceDesc.value();
		if(StringUtils.isNotEmpty(value)){
			return value;
		}

		return defaultValue;
	}





	private final static String getPath(RequestMapping requestMapping){

		if(requestMapping == null){
			return null;
		}

		String[] path = requestMapping.path();
		if( ArrayUtils.isNotEmpty(path)){
			return  path[0];
		}

		String[] value = requestMapping.value();
		if( ArrayUtils.isNotEmpty(value)){
			return  value[0];
		}

		return null;
	}


	public void setContext(String context) {
		this.context = context;
	}
	
	private Integer getRealPort(){
		return  NumberUtils.toInt(port);
	}
	
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public void setRegister(IRegister register) {
		this.register = register;
	}
}
