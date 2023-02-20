package com.powernode.settings;

import com.powernode.resp.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({Exception.class})    //捕捉某异常
    public Result handlerException(){
        Result result = Result.FAIL();

        return result;
    }
}
