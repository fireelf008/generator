package com.wsf.gen.vo;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResultVo implements Serializable {

    private Integer code;
    private String msg;
    private Long count;
    private Object data;
    private Date systemTime = new Date();

    public static ResultVo success(Object data) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(HttpStatus.OK.value());
        resultVo.setMsg("请求成功");
        if (data instanceof Page) {
            Page page = (Page) data;
            resultVo.setCount(page.getTotalElements());
            resultVo.setData(page.getContent());
        } else {
            resultVo.setData(data);
        }
        return resultVo;
    }

    public static ResultVo error(String msg) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resultVo.setMsg(msg);
        return resultVo;
    }
}
