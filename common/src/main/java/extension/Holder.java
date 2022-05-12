package extension;

import lombok.Getter;
import lombok.Setter;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/12 17:18
 * @description:
 */
public class Holder<T> {
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
