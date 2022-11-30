package io.github.rothschil.base.persistence.mybatis.safe;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 对属性进行加密的密文逻辑
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2022/1/19 - 16:30
 * @since 1.0.0
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Encrypt.class)
public class EncryptTypeHandler extends BaseTypeHandler<Encrypt> {

    private static final byte[] KEYS = "12345678abcdefgh".getBytes(StandardCharsets.UTF_8);

    /**
     * 设置参数
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Encrypt parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null || parameter.getValue() == null) {
            ps.setString(i, null);
            return;
        }
        AES aes = SecureUtil.aes(KEYS);
        String encrypt = aes.encryptHex(parameter.getValue());
        ps.setString(i, encrypt);
    }


    /**
     * 根据列名称来获取属性值
     *
     * @param rs         存储查询结果的对象
     * @param columnName 根据列的名称
     * @return Encrypt
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    @Override
    public Encrypt getNullableResult(ResultSet rs, String columnName) throws SQLException {

        return decrypt(rs.getString(columnName));
    }

    /**
     * 根据在查询结果的位置来获取属性值
     *
     * @param rs         存储查询结果的对象
     * @param columnIndex 列在返回结果的位置
     * @return Encrypt
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    @Override
    public Encrypt getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return decrypt(rs.getString(columnIndex));
    }


    /**
     * 根据在查询结果的位置来获取属性值
     *
     * @param cs         调用已储存过程的方法
     * @param columnIndex 列在返回结果的位置
     * @return Encrypt
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    @Override
    public Encrypt getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return decrypt(cs.getString(columnIndex));
    }

    public Encrypt decrypt(String value) {
        if (null == value) {
            return null;
        }
        return new Encrypt(SecureUtil.aes(KEYS).decryptStr(value));
    }
}
