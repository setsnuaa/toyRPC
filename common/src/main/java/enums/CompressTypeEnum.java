package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/11 15:44
 * @description:
 */
@AllArgsConstructor
@Getter
public enum CompressTypeEnum {
    GZIP((byte) 0x01, "gzip"),
    SNAPPY((byte) 0x02, "snappy");

    private final byte code;
    private final String name;
}
