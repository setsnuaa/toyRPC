package github.setsnuaa.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/28 20:17
 * @description:
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCodeEnum {
    SUCCESS(200,"The remote call succeed"),
    FAIL(500,"The remote call failed");

    private final int code;
    private final String message;
}
