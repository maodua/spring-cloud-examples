package io.github.maodua.user.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class LoginVO {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空。")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空。")
    private String password;
}
