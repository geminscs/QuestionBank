package indi.tammy.qb.annotation;

  
import org.aspectj.lang.JoinPoint;  
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;  
import org.json.JSONObject;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;  
import org.springframework.web.context.request.RequestContextHolder;  
import org.springframework.web.context.request.ServletRequestAttributes;  

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpSession;  

import java.lang.reflect.Method;
import java.util.Arrays;

  
/** 
 * 切点类 
 * @author tiangai 
 * @since 2014-08-05 Pm 20:35 
 * @version 1.0 
 */  
@Aspect  
@Component
@Configuration  
public  class SystemLogAspect {  
    
    //本地异常日志记录对象  
     private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);  
  
    //Service层切点  
    @Pointcut("@annotation(indi.tammy.qb.annotation.SystemServiceLog)")  
     public  void serviceAspect() {  
    }  
  
    //Controller层切点  
    @Pointcut("@annotation(indi.tammy.qb.annotation.SystemControllerLog)")  
     public  void controllerAspect() {  
    }  
  
    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     */  
    
   /* @Before("controllerAspect()")  
     public  void doBefore(JoinPoint joinPoint) {  
  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        HttpSession session = request.getSession();  
        //请求的IP  
        String ip = request.getRemoteAddr();  
        
         try {  
            System.out.println("=====前置通知开始=====");  
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));  
            System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));  
            System.out.println("请求IP:" + ip);  
            System.out.println("=====前置通知结束=====");  
            System.out.println("参数"+Arrays.toString(joinPoint.getArgs()));
        }  catch (Exception e) {  
            //记录本地异常日志  
            logger.error("==前置通知异常==");  
            logger.error("异常信息:{}", e.getMessage());  
        }  
    }  */
    
    /*@AfterReturning(value="controllerAspect()", argNames="rtv", returning="rtv")
    public void doAfter(JoinPoint jp, Object rtv){
    	System.out.println("结束了");
    	System.out.println(rtv);
    }*/
    
   
    @Around(value = "controllerAspect()")  
    public  Object  doAfterThrowing2(ProceedingJoinPoint  pjp){
    	System.out.println("====around=====start");
    	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
         String ip = request.getRemoteAddr();  
    	try  
        {   
             Object[] args           = pjp.getArgs(); 
             System.out.println("参数"+Arrays.toString(args));
             System.out.println("ip"+ip);
             System.out.println("请求方法:" + (pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() + "()"));  
             System.out.println("方法描述:" + getControllerMethodDescription(pjp)); 
             Object result = pjp.proceed( args ); 
             System.out.println("返回值"+result);
             System.out.println("====around=====end");
             return result;  
             
               
         }catch(Throwable e)  
         {  
             logger.error(e.toString(), e);  
         }  
         return null;  
    }
    /** 
     * 异常通知 用于拦截service层记录异常日志 
     * 
     * @param joinPoint 
     * @param e 
     */  
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")  
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        HttpSession session = request.getSession();  
        //读取session中的用户  
        //获取请求ip  
        String ip = request.getRemoteAddr();  
        //获取用户请求方法的参数并序列化为JSON格式字符串  
        String params = "";  
         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {  
             for ( int i = 0; i < joinPoint.getArgs().length; i++) {
            	 
                params += JSONObject.valueToString(joinPoint.getArgs()[i]) + ";";  
            }  
        }  
         try {  
              /*========控制台输出=========*/  
            System.out.println("=====异常通知开始=====");  
            System.out.println("异常代码:" + e.getClass().getName());  
            System.out.println("异常信息:" + e.getMessage());  
            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));  
            System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));  
            System.out.println("请求IP:" + ip);  
            System.out.println("请求参数:" + params);  
            System.out.println("=====异常通知结束=====");  
        }  catch (Exception ex) {  
            //记录本地异常日志  
            logger.error("==异常通知异常==");  
            logger.error("异常信息:{}", ex.getMessage());  
        }  
         /*==========记录本地异常日志==========*/  
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);  
  
    }  
  
  
    /** 
     * 获取注解中对方法的描述信息 用于service层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
     public  static String getServiceMthodDescription(JoinPoint joinPoint)  
             throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        String description = "";  
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {  
                    description = method.getAnnotation(SystemServiceLog. class).description();  
                     break;  
                }  
            }  
        }  
         return description;  
    }  
  
    /** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
     public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        String description = "";  
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {  
                    description = method.getAnnotation(SystemControllerLog. class).description();  
                     break;  
                }  
            }  
        }  
         return description;  
    }  
}  
