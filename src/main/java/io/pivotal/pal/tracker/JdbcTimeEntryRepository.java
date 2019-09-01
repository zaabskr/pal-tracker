package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;
    private final String insertSql = "INSERT INTO time_entries ( project_id, user_id, date, hours) VALUES ( ?, ?, ?, ?)";
    private final String selectSql = "SELECT * FROM time_entries where id = ?";
    private final String deleteSql = "DELETE FROM time_entries where id = ?";
    private final String listSql = "SELECT * FROM time_entries";
    private final String updateSql = "UPDATE time_entries set project_id=?, user_id=?, date=?, hours=? WHERE id=?";

    @Autowired
    public JdbcTimeEntryRepository( DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate( dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement( insertSql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(1, timeEntry.getProjectId());
                preparedStatement.setLong(2, timeEntry.getUserId());
                preparedStatement.setDate(3, Date.valueOf(timeEntry.getDate()));
                preparedStatement.setInt(4, timeEntry.getHours());
                return preparedStatement;
            }
        };
        jdbcTemplate.update( preparedStatementCreator, keyHolder);
        return find( keyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {
        try
        {
            TimeEntry timeEntry = jdbcTemplate.queryForObject( selectSql, new Object[]{id}, new BeanPropertyRowMapper<>( TimeEntry.class));
            return timeEntry;
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> timeEntry = jdbcTemplate.query(listSql, new BeanPropertyRowMapper<>( TimeEntry.class));
        return timeEntry;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement( updateSql);
                preparedStatement.setLong(1, timeEntry.getProjectId());
                preparedStatement.setLong(2, timeEntry.getUserId());
                preparedStatement.setDate(3, Date.valueOf(timeEntry.getDate()));
                preparedStatement.setInt(4, timeEntry.getHours());
                preparedStatement.setLong(5, id);
                return preparedStatement;
            }
        };
        jdbcTemplate.update( preparedStatementCreator);
        return find( id);
    }

    @Override
    public TimeEntry delete(long id) {
        TimeEntry timeEntry = find(id);
        jdbcTemplate.update(deleteSql, id);
        return timeEntry;
    }
}
