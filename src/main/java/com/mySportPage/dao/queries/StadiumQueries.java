package com.mySportPage.dao.queries;

public class StadiumQueries {

    public static final String STADIUM_BY_CITY =
            "SELECT stadium_id, stadium, capacity, address, city " +
                    "FROM football.stadium " +
                    "WHERE city = :city";

    public static final String STADIUM_BY_ADDRESS =
            "SELECT stadium_id, stadium, capacity, address, city " +
                    "FROM football.stadium " +
                    "WHERE " +
                    "  CASE " +
                    "    WHEN :address LIKE '%ul.%' THEN address = :address " +
                    "    ELSE REPLACE(address, 'ul. ', '') = :address " +
                    "  END";

    public static final String STADIUM_BY_TEAM_ID =
            "SELECT stadium_id, stadium, capacity, address, city " +
                    "FROM football.stadium " +
                    "WHERE :teamId = ANY(team_id)";

    public static final String STADIUM_BY_TEAM_NAME =
            "SELECT s.stadium_id, s.stadium, s.capacity, s.address, s.city " +
                    "FROM football.stadium s " +
                    "JOIN football.team t ON t.team_id = ANY(s.team_id) " +
                    "WHERE t.name ILIKE '%' || :teamName ||'%'";

    public static final String STADIUM_BY_NAME =
            "SELECT stadium_id, stadium, capacity, address, city " +
                    "FROM football.stadium " +
                    "WHERE " +
                    "  CASE " +
                    "    WHEN :name LIKE '%Stadion%' THEN stadium = :name " +
                    "    ELSE REPLACE(stadium, 'Stadion ', '') = :name " +
                    "  END";
}
