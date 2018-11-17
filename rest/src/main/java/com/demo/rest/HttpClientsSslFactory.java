package com.demo.rest;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

/**
 *
 *  HTTPClient的工厂类。 可以接收https请求
 *  http clients support http
 */
public class HttpClientsSslFactory implements FactoryBean<CloseableHttpClient>,InitializingBean{

	private int connectionRequestTimeout;
	private int connectionTimeout;
	private int socketTimeout;
	
	private CloseableHttpClient closeableHttpClient;
	
	/**
	 * 最大总数
	 */
	private int maxTotal=200;
	
	/**
	 * 默认并发数
	 */
	private int defaultMaxPerRoute=100;
	
	@Override
	public CloseableHttpClient getObject() throws Exception {

		return closeableHttpClient;
	}

	@Override
	public Class<CloseableHttpClient> getObjectType() {
		return CloseableHttpClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		HttpClientBuilder b = HttpClients.custom();

		InputStream inputStream = HttpClientsSslFactory.class.getResourceAsStream("/wuzhong_passtest.p12");
		SSLContext sslContext;
		try {
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			//p12证书保护口令
			String str="passtest";
			char[] partnerId2charArray=str.toCharArray();
			keystore.load(inputStream, partnerId2charArray);
			sslContext = SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		b.setSSLContext(sslContext);

		HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				new String[]{"TLSv1"}, null, hostnameVerifier);

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http",
						PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslsf).build();

		// now, we create connection-manager using our Registry.
		// -- allows multi-threaded use
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		connMgr.setMaxTotal(maxTotal);
		connMgr.setDefaultMaxPerRoute(defaultMaxPerRoute);
		b.setConnectionManager(connMgr);

		//request config
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectionTimeout)
				.setSocketTimeout(socketTimeout)
				.build();
		b.setDefaultRequestConfig(requestConfig);


		// finally, build the HttpClient;
		// -- done!
		closeableHttpClient = b.build();
	}
}