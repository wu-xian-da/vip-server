

package com.jianfei.frame.spring;


import com.jianfei.frame.Constants;
import com.jianfei.frame.error.*;
import com.jianfei.frame.error.support.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author libinsong1204@gmail.com
 */
public class DefaultHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Map<String, SubErrorType> JSR303_SUB_ERR_MAPPING = new LinkedHashMap<>();

    private static final ModelAndView ERROR_MODEL_AND_VIEW = new ModelAndView();

    static {
        JSR303_SUB_ERR_MAPPING.put("typeMismatch", SubErrorType.ISV_PARAMETERS_MISMATCH);
        JSR303_SUB_ERR_MAPPING.put("NotNull", SubErrorType.ISV_MISSING_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("NotEmpty", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Size", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Range", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Pattern", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Min", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Max", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("DecimalMin", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("DecimalMax", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Digits", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Past", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("Future", SubErrorType.ISV_INVALID_PARAMETER);
        JSR303_SUB_ERR_MAPPING.put("AssertFalse", SubErrorType.ISV_INVALID_PARAMETER);
    }

    private ErrorRequestMessageConverter messageConverter;

    public DefaultHandlerExceptionResolver() {
        setOrder(Ordered.LOWEST_PRECEDENCE);
    }

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        MainError mainError;

        Locale locale = (Locale) request.getAttribute(Constants.SYS_PARAM_KEY_LOCALE);
        try {
            if (ex instanceof ErrorCodeException) {
                ErrorCodeException ec = (ErrorCodeException) ex;
                mainError = new SimpleMainError(ec.getErrorCode(), ec.getMessage(), ec.getSolution());
            } else if (ex instanceof ExtraSubErrorException) {
                ExtraSubErrorException exception = (ExtraSubErrorException) ex;
                mainError = MainErrors.getErrorExt(exception.getMainErrorType(), exception.getData(), locale);
                for (Map.Entry<String, Object[]> entry : exception.getSubErrors().entrySet()) {
                    SubError subError;
                    try {
                        subError = SubErrors.getSubError(entry.getKey(), locale, entry.getValue());
                    } catch (NoSuchMessageException e) {
                        subError = SubErrors.getSubError(SubErrorType.ISV_UNKNOWN_SUB_ERROR.value(), locale, entry.getKey());
                    }
                    mainError.addSubError(subError);
                }
            } else if (ex instanceof BindException) {
                mainError = handleBindException((BindException) ex, request, response, handler);
            } else if (ex instanceof MissingServletRequestParameterException) {

                mainError = SubErrors.getMainError(SubErrorType.ISV_MISSING_PARAMETER, locale);
                MissingParamException exception = new MissingParamException ( ((MissingServletRequestParameterException) ex).getParameterName(), ex.getMessage() );
                SubError subError = SubErrors.getSubError(
                        SubErrorType.ISV_MISSING_PARAMETER.value(),
                        locale, exception.getParamName(), exception.getMessage());
                mainError.addSubError(subError);
            } else if (ex instanceof MissingParamException) {
                MissingParamException exception = (MissingParamException) ex;
                mainError = SubErrors.getMainError(SubErrorType.ISV_MISSING_PARAMETER, locale);

                SubError subError = SubErrors.getSubError(
                        SubErrorType.ISV_MISSING_PARAMETER.value(),
                        locale, exception.getParamName(), exception.getMessage());
                mainError.addSubError(subError);

            } else if (ex instanceof InvalidParamException) {
                InvalidParamException exception = (InvalidParamException) ex;
                mainError = SubErrors.getMainError(SubErrorType.ISV_INVALID_PARAMETER, locale);

                SubError subError = SubErrors.getSubError(
                        SubErrorType.ISV_INVALID_PARAMETER.value(), locale,
                        exception.getParamName(), exception.getValue(), exception.getMessage());
                mainError.addSubError(subError);

            } else if (ex instanceof CycoreErrorException) {
                CycoreErrorException exception = (CycoreErrorException) ex;
                mainError = SubErrors.getMainError(SubErrorType.ISV_CYCORE_ERROR, locale);

                SubError subError = SubErrors.getSubError(SubErrorType.ISV_CYCORE_ERROR.value(),
                        locale, exception.getErrorMessage(), exception.getMessage());
                mainError.addSubError(subError);

            } else if (ex instanceof TypeMismatchException) {
                TypeMismatchException exception = (TypeMismatchException) ex;

                mainError = SubErrors.getMainError(SubErrorType.ISV_PARAMETERS_MISMATCH, locale);
                SubError subError = SubErrors.getSubError(SubErrorType.ISV_PARAMETERS_MISMATCH.value(), locale,
                        exception.getValue(), exception.getRequiredType().getSimpleName());
                mainError.addSubError(subError);
            }  else if(ex instanceof ReturnValueConverterException) {
                mainError = SubErrors.getMainError(SubErrorType.ISV_RETURN_VALUE_CONVERT, locale);

                SubError subError = SubErrors.getSubError(SubErrorType.ISV_RETURN_VALUE_CONVERT.value(), locale,
                        ((ReturnValueConverterException) ex).getReturnValueType());
                mainError.addSubError(subError);
            } else {
                SubError subError = SubErrors.getSubError(
                        SubErrorType.ISP_SERVICE_UNAVAILABLE.value(),
                        (Locale) request.getAttribute(Constants.SYS_PARAM_KEY_LOCALE),
                        request.getParameter(Constants.SYS_PARAM_KEY_METHOD),
                        NestedExceptionUtils.buildMessage(ex.getMessage(), ex));

                mainError = MainErrors.getError(MainErrorType.SERVICE_CURRENTLY_UNAVAILABLE,
                        (Locale) request.getAttribute(Constants.SYS_PARAM_KEY_LOCALE));
                mainError.addSubError(subError);
            }

            request.setAttribute(Constants.MAIN_ERROR_CODE, mainError.getCode());
            messageConverter.convertData(request, response, mainError);

            logger.warn(ex.getMessage(), ex);
        } catch (Exception handlerException) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception",
                    handlerException);
        }

        return ERROR_MODEL_AND_VIEW;
    }

    protected MainError handleBindException(BindException ex, HttpServletRequest request,
                                            HttpServletResponse response, Object handler)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

        //将Bean数据绑定时产生的错误转换为Rop的错误
        MainError mainError = null;
        if (errorList != null && errorList.size() > 0) {
            mainError = toMainErrorOfSpringValidateErrors(errorList,
                    (Locale) request.getAttribute(Constants.SYS_PARAM_KEY_LOCALE));
        }

        return mainError;
    }

    /**
     * 将通过JSR 303框架校验的错误转换为服务框架的错误体系.
     */
    private MainError toMainErrorOfSpringValidateErrors(List<ObjectError> allErrors,
                                                        Locale locale) {
        if (hastSubErrorType(allErrors, SubErrorType.ISV_MISSING_PARAMETER)) {
            return getBusinessParameterMainError(allErrors, locale,
                    SubErrorType.ISV_MISSING_PARAMETER);
        } else if (hastSubErrorType(allErrors, SubErrorType.ISV_PARAMETERS_MISMATCH)) {
            return getBusinessParameterMainError(allErrors, locale,
                    SubErrorType.ISV_PARAMETERS_MISMATCH);
        } else {
            return getBusinessParameterMainError(allErrors, locale,
                    SubErrorType.ISV_INVALID_PARAMETER);
        }
    }

    /**
     * 判断错误列表中是否包括指定的子错误.
     */
    private boolean hastSubErrorType(List<ObjectError> allErrors, SubErrorType subErrorType) {
        for (ObjectError objectError : allErrors) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                if (JSR303_SUB_ERR_MAPPING.containsKey(fieldError.getCode())) {
                    SubErrorType tempSubErrorType = JSR303_SUB_ERR_MAPPING.get(fieldError.getCode());
                    if (tempSubErrorType == subErrorType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 生成对应子错误的错误类.
     */
    private MainError getBusinessParameterMainError(
            List<ObjectError> allErrors, Locale locale, SubErrorType subErrorType) {
        MainError mainError = SubErrors.getMainError(subErrorType, locale);
        for (ObjectError objectError : allErrors) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                SubErrorType tempSubErrorType = JSR303_SUB_ERR_MAPPING.get(fieldError.getCode());
                if (tempSubErrorType == subErrorType) {
                    SubError subError = SubErrors.getSubError(tempSubErrorType.value(), locale,
                            fieldError.getField(), fieldError.getRejectedValue(),
                            fieldError.getDefaultMessage());
                    mainError.addSubError(subError);
                }
            }
        }
        return mainError;
    }

    public ErrorRequestMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(ErrorRequestMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

}
