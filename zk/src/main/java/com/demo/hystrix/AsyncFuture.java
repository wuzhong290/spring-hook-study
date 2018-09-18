package com.demo.hystrix;

import com.demo.aop.monitor.ThreadProfile;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 包装了一下对异常的处理和调用统计
 */
public class AsyncFuture<T> {
    private final static Logger log = LoggerFactory.getLogger(AsyncFuture.class);


    private final Future<T> future;
    private final String serviceName;
    private final T fallbackRsp;


    /**
     *  constructor
     */
    public AsyncFuture(Future<T> future, String serviceName,  T fallback) {
        this.future = future;
        this.serviceName = serviceName;
        this.fallbackRsp = fallback;
    }


    /**
     * 获取服务调用结果
     *
     * @return 获取服务调用结果
     */
    public T get(int seconds) throws Exception{

        ThreadProfile.asynCall(future,this.getClass().getName(),serviceName );

        T t;
        try {
            t = this.innerGet(seconds,TimeUnit.SECONDS);
        } catch (Exception e) {
            if (this.fallbackRsp != null) {
                t = this.fallbackRsp;
            } else {
                throw e;
            }
        } finally {
            ThreadProfile.asynReturn(future);
        }

        return t;

    }
    
    
    public T get(int seconds,TimeUnit unit) throws Exception{

        ThreadProfile.asynCall(future,this.getClass().getName(),serviceName );

        T t;
        try {
            t = this.innerGet(seconds,unit);
        } catch (Exception e) {
            if (this.fallbackRsp != null) {
                t = this.fallbackRsp;
            } else {
                throw e;
            }
        } finally {
            ThreadProfile.asynReturn(future);
        }
        return t;
    }
    
    
    /**
     * 获取服务调用结果
     *
     * @return 获取服务调用结果
     */
    private T innerGet(int waitingSeconds,TimeUnit unit) throws Exception{
        T t;
        try {
            t = this.future.get(waitingSeconds, unit);
            
            //避免LogUtils.shotter  先执行
        }catch(TimeoutException te){//增加超时异常
        	log.warn("call service: {} failed:{} with TimeoutException.", serviceName,te);
            throw new Exception(serviceName);
        }catch(InterruptedException ie){//增加线程中断异常
        	log.warn("call service: {} failed:{} with InterruptedException.", serviceName,ie);
            throw new Exception(serviceName);
        } catch (Exception ex) {
            // 异步场景下，会抛出 ExecutionException
            if (ex instanceof ExecutionException) {

                //hystrix 场景 HystrixBadRequestException 异常，不需要进行fallback
                if (ex.getCause() instanceof HystrixBadRequestException ) {
                    Exception se = (Exception) (ex.getCause().getCause());
                    log.info("call service: {}, return: {} success.", serviceName, se.getMessage());
                    throw se;
                }
                //native 场景
                else if (ex.getCause() instanceof Exception) {
                    Exception se = (Exception) (ex.getCause());
                    log.info("call service: {}, return: {} success.", serviceName, se.getMessage());
                    throw se;
                }
                //不是service exception
                else {
                    log.warn("call service: {} failed with exception.", serviceName, ex);
                    throw new Exception(serviceName);
                }
            } else {
                log.warn("call service: {} failed with exception.", serviceName);
                throw new Exception(serviceName);
            }
        }
        return t;
    }




    /**
     * 获取服务调用结果
     *
     * @return 获取服务调用结果
     */
    public T get() throws Exception{

        // 默认future的超时时间.
        return this.get(2);
    }
}
