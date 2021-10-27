package cloud.mysteriousman.pojo;

import cloud.mysteriousman.validation.ValidationGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author God
 */
@Schema(description = "用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 6216240937532973282L;
    @Schema(description = "用户id")
    @NotNull(groups = ValidationGroup.class)
    private Integer userId;

    @Schema(description = "用户姓名")
    @NotNull(groups = ValidationGroup.SubGroup0.class)
    @NotBlank(groups = ValidationGroup.SubGroup1.class,message = "用户名不能为空")
    private String userName;

    @Schema(description = "用户年龄")
    @Max(value = 18,groups = ValidationGroup.SubGroup1.class,message = "年龄不得大于18岁")
    private Integer age;
}
