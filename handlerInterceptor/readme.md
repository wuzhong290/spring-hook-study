21:28:37.923 [http-nio-8080-exec-9] DEBUG org.springframework.web.servlet.DispatcherServlet - DispatcherServlet with name 'spring' processing POST request for [/spring-hook/arg/person]
21:28:37.925 [http-nio-8080-exec-9] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Looking up handler method for path /arg/person
21:28:37.929 [http-nio-8080-exec-9] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Returning handler method [public com.demo.converters.model.Person com.demo.resolvers.controller.argController.getStudentInfo(com.demo.converters.model.Person)]
21:28:37.929 [http-nio-8080-exec-9] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Returning cached instance of singleton bean 'argController'
21:28:37.930 [http-nio-8080-exec-9] INFO com.demo.handlerInterceptor.DemoHandlerInterceptor - preHandle
21:28:37.988 [http-nio-8080-exec-9] DEBUG com.demo.resolvers.argumentResolver.JsonMethodArgumentResolver - Read request method is POST,value is {"name":"wuzhong","age":100}
21:28:38.081 [http-nio-8080-exec-9] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor - Written [com.demo.converters.model.Person@41e96798] as "application/json" using [com.demo.converters.converter.JsonDemoConverter@204e6dbb]
21:28:38.082 [http-nio-8080-exec-9] INFO com.demo.handlerInterceptor.DemoHandlerInterceptor - postHandle
21:28:38.082 [http-nio-8080-exec-9] DEBUG org.springframework.web.servlet.DispatcherServlet - Null ModelAndView returned to DispatcherServlet with name 'spring': assuming HandlerAdapter completed request handling
21:28:38.082 [http-nio-8080-exec-9] INFO com.demo.handlerInterceptor.DemoHandlerInterceptor - afterCompletion
21:28:38.082 [http-nio-8080-exec-9] DEBUG org.springframework.web.servlet.DispatcherServlet - Successfully completed request



21:32:20.932 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.DispatcherServlet - DispatcherServlet with name 'spring' processing GET request for [/spring-hook/student-module/getStudentInfo]
21:32:20.933 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Looking up handler method for path /student-module/getStudentInfo
21:32:20.935 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Returning handler method [public java.lang.String com.demo.controller.StudentController.getStudentInfo(org.springframework.ui.Model)]
21:32:20.935 [http-nio-8080-exec-2] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Returning cached instance of singleton bean 'studentController'
21:32:20.935 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.DispatcherServlet - Last-Modified value for [/spring-hook/student-module/getStudentInfo] is: -1
21:32:20.935 [http-nio-8080-exec-2] INFO com.demo.handlerInterceptor.DemoHandlerInterceptor - preHandle
21:32:20.935 [http-nio-8080-exec-2] INFO com.demo.handlerInterceptor.DemoHandlerInterceptor - postHandle
21:32:20.935 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.DispatcherServlet - Rendering view [org.springframework.web.servlet.view.JstlView: name 'showStudentInfo'; URL [/WEB-INF/views/showStudentInfo.jsp]] in DispatcherServlet with name 'spring'
21:32:20.936 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.view.JstlView - Added model object 'students' of type [java.util.ArrayList] to request in view with name 'showStudentInfo'
21:32:20.936 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.view.JstlView - Forwarding to resource [/WEB-INF/views/showStudentInfo.jsp] in InternalResourceView 'showStudentInfo'
21:32:20.940 [http-nio-8080-exec-2] INFO com.demo.handlerInterceptor.DemoHandlerInterceptor - afterCompletion
21:32:20.940 [http-nio-8080-exec-2] DEBUG org.springframework.web.servlet.DispatcherServlet - Successfully completed request
