package cloud.mysteriousman.controller;

import cloud.mysteriousman.pojo.User;
import cloud.mysteriousman.support.CommonResult;
import cloud.mysteriousman.validation.ValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author God
 */
@Tag(name = "样例服务接口",description = "服务接口样例")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/sample")
public class SampleController {

    @Operation(summary = "传入id",tags = "样例服务接口")
    @GetMapping(value = "/get")
    public CommonResult<Integer> get(@Parameter(description = "传入id") @NotNull @Min(value = 18)@RequestParam(value = "id") Integer id) {
        return CommonResult.ok(id);
    }

    @Operation(summary = "添加用户",tags = "样例服务接口")
    @PutMapping(value = "/put")
    public CommonResult<User> put(@io.swagger.v3.oas.annotations.parameters.RequestBody @Validated(value = ValidationGroup.SubGroup1.class)@RequestBody User user) {
        return CommonResult.ok(user);
    }
}
