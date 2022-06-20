package com.pgf;

import lombok.*;

import java.io.Serializable;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/19 14:58
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Hello implements Serializable {
    private String message;
    private String description;
}
