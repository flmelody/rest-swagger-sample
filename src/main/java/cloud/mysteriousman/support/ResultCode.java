package cloud.mysteriousman.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author God
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 操作成功
     */
    OK(HttpStatus.OK.value()),
    /**
     * 操作拒绝
     */
    FORBIDDEN(HttpStatus.FORBIDDEN.value()),
    /**
     * 操作错误
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value()),
    /**
     * 操作失败
     */
    FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value());
    private int code;
}
