package com.aaa.lee.es.base;

import com.aaa.lee.es.status.StatusEnum;
import org.springframework.stereotype.Controller;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/8/10 16:33
 * @Description
 *      如果架构是分模块搭建的，这个BaseController的注解就会失效，需要使用AOP自己写！
 **/
@Controller
public class BaseController {

    /**
     * @author Seven Lee
     * @description
     *      如果不需要返回数据，则使用这个方法
     * @param
     * @date 2019/8/10
     * @return com.aaa.lee.es.base.ResultData
     * @throws 
    **/
    protected ResultData success(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      需要返回前端页面数据，则使用这个方法
     * @param [msg, data]
     * @date 2019/8/10
     * @return com.aaa.lee.es.base.ResultData
     * @throws 
    **/
    protected ResultData success(String msg, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

    /**
     *
     * 失败的情况
     *
     *
     */

}
