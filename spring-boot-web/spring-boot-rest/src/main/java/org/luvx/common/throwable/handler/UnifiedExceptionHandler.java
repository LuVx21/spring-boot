package org.luvx.common.throwable.handler;

import lombok.extern.slf4j.Slf4j;
import org.luvx.common.api.R;
import org.luvx.common.throwable.base.BaseException;
import org.luvx.common.throwable.exception.BusinessException;
import org.luvx.common.throwable.assertion.ArgumentAssert;
import org.luvx.common.throwable.assertion.CommonAssert;
import org.luvx.common.throwable.assertion.ServletAssert;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.MessageFormat;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Slf4j
@Component
@ControllerAdvice
@ConditionalOnWebApplication
@ConditionalOnMissingBean(UnifiedExceptionHandler.class)
public class UnifiedExceptionHandler {
    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 自定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public R handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return R.failed(e.getBaseAssert().getCode(), getMessage(e));
    }

    /**
     * 业务异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public R handleBusinessException(BaseException e) {
        log.error(e.getMessage(), e);
        return R.failed(e.getBaseAssert().getCode(), getMessage(e));
    }

    /**
     * Controller上一层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            // BindException.class,
            // MethodArgumentNotValidException.class
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    @ResponseBody
    public R handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        String code = CommonAssert.SERVER_ERROR.getCode();
        try {
            ServletAssert sa = ServletAssert.valueOf(e.getClass().getSimpleName());
            code = sa.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletAssert.class.getName());
        }

        if (ENV_PROD.equals(profile)) {
            code = CommonAssert.SERVER_ERROR.getCode();
            BaseException ee = new BaseException(CommonAssert.SERVER_ERROR);
            String message = getMessage(ee);
            return R.failed(code, message);
        }

        return R.failed(code, e.getMessage());
    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public R handleBindException(BindException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param result 绑定结果
     * @return 异常结果
     */
    private R wrapperBindingResult(BindingResult result) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }

        return R.failed(ArgumentAssert.VALID_ERROR.getCode(), msg.substring(2));
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
            String code = CommonAssert.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonAssert.SERVER_ERROR);
            String message = getMessage(baseException);
            return R.failed(code, message);
        }

        return R.failed(CommonAssert.SERVER_ERROR.getCode(), e.getMessage());
    }


    /**
     * 获取国际化消息
     *
     * @param e 异常
     * @return
     */
    public String getMessage(BaseException e) {
        String message = "response." + e.getBaseAssert().getMessage();
        message = MessageFormat.format(message, e.getArgs());
        if (StringUtils.isEmpty(message)) {
            return e.getMessage();
        }
        return message;
    }
}