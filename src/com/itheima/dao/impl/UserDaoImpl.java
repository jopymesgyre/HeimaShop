package com.itheima.dao.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Created by 13718 on 2017/8/27.
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User findByUsername(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ? ";
        User existUser = queryRunner.query(sql,new BeanHandler<User>(User.class),username);
        return existUser;
    }
}
