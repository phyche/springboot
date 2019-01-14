/*
package com.example.springboot.controller;

import com.example.springboot.service.production.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

*/
/* X5 API接口入口.*//*


@Slf4j
@RequestMapping("/x5")
@RestController
public class X5ApiController {

    @Autowired
    protected ApplicationContext appContext;

    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> beanMaps = appContext.getBeansWithAnnotation(ApiService.class);
        for (String name : beanMaps.keySet()) {
            Object processorInstance = beanMaps.get(name);
            Class c = processorInstance.getClass();
            if (AopUtils.isAopProxy(processorInstance)) {
                c = AopUtils.getTargetClass(processorInstance);
            }
            for (Method method : c.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Api.class)) {
                    continue;
                }
                Api api = AnnotationUtils.findAnnotation(method, Api.class);
                if (!StringUtils.isBlank(api.apiId())) {
                    ApiSecurityEnum config = ApiSecurityUtil.getSecurityConfigByAppId(api.apiId());
                    List<String> methodList = config.getMethodList();
                    String prefix=name.substring(0,name.length()-10);
                    methodList.add(prefix+"."+method.getName());
                }
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApiResp get() throws X5ApiException {
        // 提示 GET 不可使用
        throw new X5ApiException(RetCode.METHOD_NOT_ALLOWED.getId(), RetCode.METHOD_NOT_ALLOWED.getDescriptionWithParam("GET"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResp post(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return this.process(request, this.genEncodeRequest(request));
    }

    protected ApiResp process(HttpServletRequest request, String encodeRequest) throws Throwable {
        long startTime = System.currentTimeMillis();
        ThreadLocalUtils.Pair<Long, String, String> monitorPair = ThreadLocalUtils.Pair.of(startTime, null, null);
        ThreadLocalUtils.API_MONITOR_THREAD_LOCAL.set(monitorPair);
        ApiReq apiReq = ApiUtil.checkParam(encodeRequest);
        ApiSecurityUtil.check(request, apiReq);
        String reqMethod = apiReq.getHeader().getMethod();
        monitorPair.setMiddle("call_" + reqMethod);
        String reqBody = apiReq.getBody();
        monitorPair.setRight(reqBody);
        // 设置公参
        this.setGlobalParam(request, apiReq);
        Object respBody = this.invokeMethod(reqMethod, reqBody);
        // 空对象在接口返回json的时候body结点就消失了，此处body变为空字符串．
        if(respBody==null){
            respBody="";
        }
        ApiResp apiResp = ApiUtil.genApiResp(ApiConstant.CODE_SUCCESS, String.format("call xms factory api success, method[%s]", reqMethod), respBody);
        ApmClient.recordOne(monitorPair.getMiddle(), System.currentTimeMillis() - startTime);
        return apiResp;
    }

*
     * 设置全局参数/公参


    protected void setGlobalParam(HttpServletRequest request, ApiReq apiReq) {
        Map<String, Object> globalParam = Maps.newHashMap();
        try {
            globalParam.put("operator", apiReq.getHeader().getOperatorId());
            request.setAttribute(ApiConstant.ATTR_GLOBAL_PARAM, globalParam);
        } catch (Exception e) {
            throw Throwables.runtimeException(RetCode.FORBIDDEN_OPERATOR_EMPTY);
        }
    }

    protected String genEncodeRequest(HttpServletRequest request) {
        String base64EncodeReq = request.getParameter("data");
        if (StringUtils.isNotEmpty(base64EncodeReq)) {
            base64EncodeReq = base64EncodeReq.replaceAll("\r|\n", "");
        }
        return base64EncodeReq;
    }

    private Object invokeMethod(String reqMethod, String reqBody) throws Throwable {
        StatWatcher statWatcher = null;
        // watchLog 状态
        int statCode = 500;
        try {
            String[] args = reqMethod.split("\\.");
            String serviceNamePrefix = args[0];
            String methodName = args[1];
            String serviceName = serviceNamePrefix + "ApiService";
            Object serviceBean = appContext.getBean(serviceName);
            Method callMethod = checkBodyEmpty(reqBody) ? serviceBean.getClass().getMethod(methodName) : serviceBean.getClass().getMethod(methodName, String.class);
            if (callMethod != null) {
                statWatcher = WatchLogUtils.begin("X5API_" + serviceNamePrefix.toUpperCase() + "_" + methodName.toUpperCase());
                // statWatcher.begin("X5API_" + serviceName.toUpperCase() + "_" + methodName.toUpperCase());
                Object result = checkBodyEmpty(reqBody) ? callMethod.invoke(serviceBean) : callMethod.invoke(serviceBean, reqBody);
                log.debug("X5ApiController method:{}, reqBody:{}, result:{}", reqMethod, reqBody, result==null ? StringUtils.EMPTY : result.toString());
                statCode = 0;
                return result;
            } else {
                throw new X5ApiException(statCode, RetCode.INTERNAL_SERVER_ERROR.getDescription());
            }
        } catch (InvocationTargetException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            log.error("X5ApiController method:{}, reqBody:{}", reqMethod, reqBody, rootCause);
            if(rootCause instanceof MafactoryRuntimeException){
                statCode = ((MafactoryRuntimeException) rootCause).getCode();
                throw new X5ApiException(statCode, e.getTargetException().getMessage());
            }
            throw new X5ApiException(statCode, Throwables.toUsefulMessage(e.getTargetException()));
        } catch (Exception e) {
            log.error("X5ApiController method:{}, reqBody:{}", reqMethod, reqBody, e);
            throw new X5ApiException(statCode, RetCode.INTERNAL_SERVER_ERROR.getDescription(), e );
        } finally {
            if (statWatcher != null) {
                statWatcher.end(statCode);
            }
        }
    }

    private boolean checkBodyEmpty(String reqBody) {
        if (StringUtils.isBlank(reqBody))
            return true;
        return reqBody.length() <= 2;
    }
}

*/
