1、org.springframework.format.support.FormattingConversionServiceFactoryBean 可以将自定义的转化器注册进去

ConversionServiceFactoryBean的
注册代码如下：
	public void afterPropertiesSet() {
		this.conversionService = new DefaultFormattingConversionService(this.embeddedValueResolver, this.registerDefaultFormatters);
		ConversionServiceFactory.registerConverters(this.converters, this.conversionService);
		registerFormatters();
	}
2、mvc的使用

    <!-- 第二步： 创建convertion-Service ，并注入dateConvert-->
    <bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <ref bean="dateConvert"/>
            </set>
        </property>
    </bean>
    <!-- 第一步:  创建自定义日期转换规则 -->
    <bean id="dateConvert" class="com.demo.lifecycle.converter.DateConvert"/>


<mvc:annotation-driven conversion-service="conversionService">

    @RequestMapping("/test")
    @ResponseBody
    public String test(Date birthday){
        System.out.println(birthday);
        return "index";
    }