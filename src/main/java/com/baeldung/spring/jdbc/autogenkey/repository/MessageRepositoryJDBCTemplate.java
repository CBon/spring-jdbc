package com.baeldung.spring.jdbc.autogenkey.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class MessageRepositoryJDBCTemplate {

    static final String INSERT_MESSAGE_SQL = "insert into sys_message (message) values(?) ";
    static final String SELECT_BY_ID = "select message from sys_message where id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    public long insert(final String message) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, message);
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).orElse(0L);
    }

    public String getMessageById(long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, String.class, id);
    }

}
