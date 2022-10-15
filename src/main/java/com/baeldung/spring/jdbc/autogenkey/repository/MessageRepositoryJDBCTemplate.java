package com.baeldung.spring.jdbc.autogenkey.repository;

import java.sql.PreparedStatement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepositoryJDBCTemplate {

    @Autowired
    JdbcTemplate jdbcTemplate;

    static final String INSERT_MESSAGE_SQL = "insert into sys_message (message) values(?) ";

    public long insert(final String message) {
        
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_SQL);
            ps.setString(1, message);
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey()).map(long.class::cast).orElse(0L);
    }

    static final String SELECT_BY_ID = "select message from sys_message where id = ?";

    public String getMessageById(long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, String.class, id);
    }

}
