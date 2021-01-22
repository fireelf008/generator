package com.wsf.gen.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldColumn implements Serializable {

    private String type;
    private String name;
    private String comment;
    private String methodName;
}
