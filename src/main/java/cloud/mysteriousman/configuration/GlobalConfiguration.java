package cloud.mysteriousman.configuration;

import cloud.mysteriousman.support.CommonResult;
import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeFormatter;

/**
 * @author God
 */
@Configuration(proxyBeanMethods = false)
public class GlobalConfiguration {

    /**
     * Jackson序列化/反序列化定制
     *
     * @return 自定义信息
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DatePattern.NORM_DATE_PATTERN);
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        };
    }

    /**
     * 全局异常处理
     */
    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class, BindException.class,
                MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
                MissingServletRequestPartException.class})
        @ResponseBody
        public <T> CommonResult<T> handleValidationException(Exception e) {
            if (e instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
                for (ConstraintViolation<?> constraintViolation : constraintViolationException.getConstraintViolations()) {
                    return CommonResult.badRequest(constraintViolation.getMessage());
                }
            } else if (e instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
                BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
                for (ObjectError error : bindingResult.getAllErrors()) {
                    return CommonResult.badRequest(error.getDefaultMessage());
                }
            } else if (e instanceof BindException) {
                BindException bindException = (BindException) e;
                BindingResult bindingResult = bindException.getBindingResult();
                for (ObjectError error : bindingResult.getAllErrors()) {
                    return CommonResult.badRequest(error.getDefaultMessage());
                }
            } else if (e instanceof MissingServletRequestParameterException) {
                MissingServletRequestParameterException missingServletRequestParameterException = (MissingServletRequestParameterException) e;
                return CommonResult.badRequest(missingServletRequestParameterException.getParameterName() + "\nmiss");
            }
            return CommonResult.badRequest();
        }

        @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
        @ResponseBody
        public <T> CommonResult<T> handleNotSupportedException() {
            return CommonResult.result(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.name());
        }

        @ExceptionHandler(value = Exception.class)
        @ResponseBody
        public <T> CommonResult<T> handleUnknownException() {
            return CommonResult.failed();
        }
    }
}
