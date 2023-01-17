package cn.mgl.purity.model.service.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回结果基本类
 */
@Data
@Accessors(chain = true)
public class JsonResult {
    String msg;
    Object result;
    Boolean success;
    Integer code;

    public JsonResult(Integer code, Boolean success, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.result = data;
    }

    static public JsonResult success(String msg, Object data) {
        return new JsonResult(200, true, msg, data);
    }

    static public JsonResult success(String msg) {
        return new JsonResult(200, true, msg, null);
    }

    static public JsonResult failure(String msg) {
        return new JsonResult(400, false, msg, null);
    }

    static public JsonResult failure(String msg, Integer code) {
        return new JsonResult(code, false, msg, null);
    }

    static public JsonResult failure(String msg, Integer code, Object data) {
        return new JsonResult(code, false, msg, data);
    }

    static public JsonResult failure(String msg, Object data) {
        return new JsonResult(400, false, msg, data);
    }
}
