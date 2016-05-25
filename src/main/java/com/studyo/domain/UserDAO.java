package com.studyo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public UserInfo getUserInfo(String username){
        String sql = "SELECT u.username name, u.password pass, r.role role FROM "+
                "users u INNER JOIN roles r on u.username=r.username WHERE "+
                "u.enabled = 1 and u.username = ?";
        UserInfo userInfo = jdbcTemplate.queryForObject(sql, new Object[]{username},
                (rs, rowNum) -> {
                    String name = rs.getString("name");
                    String pass = rs.getString("pass");
                    String role = rs.getString("role");
                    GrantedAuthority auth = new SimpleGrantedAuthority(role);
                    UserInfo user = new UserInfo(name, pass,
                            new ArrayList<GrantedAuthority>(){{add(auth);}});
                    return user;
                });
        return userInfo;
    }
}