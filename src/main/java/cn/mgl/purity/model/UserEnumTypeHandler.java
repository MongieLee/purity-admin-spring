package cn.mgl.purity.model;

import cn.mgl.purity.model.persistent.UserTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.Objects;

/**
 * 自定义用户类型枚举处理器
 */
public class UserEnumTypeHandler extends BaseTypeHandler<UserTypeEnum> {

    /**
     * Java类型转换为数据库类型
     *
     * @param preparedStatement
     * @param i
     * @param userTypeEnum
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UserTypeEnum userTypeEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, userTypeEnum.getCode().toString());
    }

    /**
     * 数据库类型转换为Java类型
     *
     * @param resultSet
     * @param s
     * @return
     * @throws SQLException
     */
    @Override
    public UserTypeEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        UserTypeEnum[] values = UserTypeEnum.values();
        String dbValue = resultSet.getString(s);
        if (Objects.isNull(dbValue)){
            return null;
        }
        UserTypeEnum result = null;
        for (UserTypeEnum value : values) {
            if (value.getCode().toString().equals(dbValue)) {
                result = value;
                break;
            }
        }
        return result;
    }

    @Override
    public UserTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        UserTypeEnum[] values = UserTypeEnum.values();
        String dbValue = resultSet.getString(i);
        if (Objects.isNull(dbValue)){
            return null;
        }
        UserTypeEnum result = null;
        for (UserTypeEnum value : values) {
            if (value.getCode().toString().equals(dbValue)) {
                result = value;
                break;
            }
        }
        return result;
    }

    @Override
    public UserTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        UserTypeEnum[] values = UserTypeEnum.values();
        String dbValue = callableStatement.getString(i);
        if (Objects.isNull(dbValue)){
            return null;
        }
        UserTypeEnum result = null;
        for (UserTypeEnum value : values) {
            if (value.getCode().toString().equals(dbValue)) {
                result = value;
                break;
            }
        }
        return result;
    }
}
