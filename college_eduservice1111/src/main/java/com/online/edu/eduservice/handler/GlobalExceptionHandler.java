package com.online.edu.eduservice.handler;

import com.online.edu.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody

     //1对所有的异常进行相同的处理
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("出现了异常");
    }
    //2对特定的异常进行处理ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("0不能为除数，出现了异常");
    }

    @ExceptionHandler(EduExeception.class)
    @ResponseBody
    public R error(EduExeception e){
        e.printStackTrace();
//        return R.error().message(SingleException.EXCEPTION1.getMessage())
//                .code(SingleException.EXCEPTION1.getCode());
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
