package cloud.mysteriousman.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author God
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = 3455975506787547965L;
    private Integer code;
    private String message;
    private T data;

    /**
     * 操作成功
     *
     * @param <T> 泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> ok() {
        return result(ResultCode.OK);
    }

    /**
     * 操作成功
     *
     * @param data 返回数据
     * @param <T>  泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> ok(T data) {
        return result(ResultCode.OK, data);
    }

    /**
     * 操作成功
     *
     * @param data    返回数据
     * @param message 返回信息
     * @param <T>     泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> ok(T data, String message) {
        return result(ResultCode.OK.getCode(), message, data);
    }

    /**
     * 操作失败
     *
     * @param <T> 泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> failed() {
        return result(ResultCode.FAILED);
    }

    /**
     * 操作失败
     *
     * @param message 返回信息
     * @param <T>     泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> failed(String message) {
        return result(ResultCode.FAILED.getCode(), message);
    }

    /**
     * 操作错误
     *
     * @param <T> 泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> badRequest() {
        return result(ResultCode.BAD_REQUEST);
    }

    /**
     * 操作错误
     *
     * @param message 返回信息
     * @param <T>     泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> badRequest(String message) {
        return result(ResultCode.BAD_REQUEST.getCode(), message);
    }


    /**
     * 拒绝请求
     *
     * @param <T> 泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> forbidden() {
        return result(ResultCode.FORBIDDEN);
    }

    /**
     * 拒绝请求
     *
     * @param message 返回信息
     * @param <T>     泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> forbidden(String message) {
        return result(ResultCode.FORBIDDEN.getCode(), message);
    }

    /**
     * 返回通用返回对象
     *
     * @param resultCode 结果代码
     * @param <T>        泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> result(ResultCode resultCode) {
        return result(resultCode.getCode(), resultCode.name());
    }

    /**
     * 返回通用返回对象
     *
     * @param resultCode 结果代码
     * @param data       返回数据
     * @param <T>        泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> result(ResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.name(), data);
    }

    /**
     * 返回通用返回对象
     *
     * @param code    返回代码
     * @param message 返回信息
     * @param <T>     泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> result(Integer code, String message) {
        return result(code, message, null);
    }

    /**
     * 返回通用返回对象
     *
     * @param code    返回代码
     * @param message 返回信息
     * @param data    返回数据
     * @param <T>     泛型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> result(Integer code, String message, T data) {
        return new CommonResult<>(code, message, data);
    }
}
