package com.powernode.settings;

import com.powernode.common.Code;
import com.powernode.resp.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({Exception.class})    //捕捉某异常
    public Result handlerException(){
        Result result = Result.FAIL();
        result.setCodeEnum(Code.SERVER_RESPONESE_ERROR);
        return result;
    }

    /*细化不同异常不同响应*/
    /* @ExceptionHandler({NullPointerException.class})//捕捉某异常
    public Result handlerNullPointerException(){
        Result result = Result.FAIL();
        result.setMsg("无法获取目标对象");
        return result;
    }*/
}
