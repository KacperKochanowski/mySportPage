CREATE OR REPLACE FUNCTION football.get_team_standings(json_data jsonb)
RETURNS jsonb
LANGUAGE plpgsql
AS $function$

/*
 * Example input:
 * SELECT football.get_team_standings('{"leagueId": 106, "locationType": "all"}'::jsonb) AS standings;
 */

DECLARE
    _location_type text;
    _league_id int;
BEGIN
    _location_type := json_data->>'locationType';
    _league_id := (json_data->>'leagueId')::int;

    RETURN (
        WITH ranked_teams AS (
            SELECT
                s.team,
                r.rounds_played,
                r.wins,
                r.draws,
                r.loses,
                r.goals_for,
                r.goals_against,
                COALESCE(tf.home_results, '') AS home_results,
                COALESCE(af.away_results, '') AS away_results,
                CASE
                    WHEN _location_type = 'home' THEN COALESCE(tf.points, 0)::bigint
                    WHEN _location_type = 'away' THEN COALESCE(af.points, 0)::bigint
                    ELSE s.points::bigint
                END AS points,
                CASE
                    WHEN _location_type = 'home' THEN COALESCE(tf.goals_diff, 0)::bigint
                    WHEN _location_type = 'away' THEN COALESCE(af.goals_diff, 0)::bigint
                    ELSE s.goals_diff::bigint
                END AS goals_diff,
                s.form,
                s.position_description
            FROM football.standing s
            JOIN football.team t ON t.name = s.team AND t.league_id = s.league_id
            JOIN football.results r ON r.team_id = t.team_id AND LOWER(r.description) = _location_type
            LEFT JOIN (
                SELECT
                    f.host AS team,
                    STRING_AGG(
                        CASE
                            WHEN f.winner = f.host THEN 'W'
                            WHEN f.winner = '-' THEN 'D'
                            ELSE 'L'
                        END,
                        '' ORDER BY f.round
                    ) AS home_results,
                    COALESCE(SUM(CASE WHEN f.winner = f.host THEN 3 WHEN f.winner = '-' THEN 1 ELSE 0 END), 0) AS points,
                    COALESCE(SUM(CASE WHEN f.winner = f.host THEN (fr.goals_for - fr.goals_against)::bigint ELSE 0 END), 0) AS goals_diff
                FROM football.fixture f
                JOIN football.results fr ON fr.team_id = (SELECT team_id FROM football.team WHERE name = f.host) AND LOWER(fr.description) = 'home'
                WHERE f.played = true AND f.league_id = _league_id
                GROUP BY f.host
            ) tf ON s.team = tf.team
            LEFT JOIN (
                SELECT
                    f.guest AS team,
                    STRING_AGG(
                        CASE
                            WHEN f.winner = f.guest THEN 'W'
                            WHEN f.winner = '-' THEN 'D'
                            ELSE 'L'
                        END,
                        '' ORDER BY f.round
                    ) AS away_results,
                    COALESCE(SUM(CASE WHEN f.winner = f.guest THEN 3 WHEN f.winner = '-' THEN 1 ELSE 0 END), 0) AS points,
                    COALESCE(SUM(CASE WHEN f.winner = f.guest THEN (fr.goals_for - fr.goals_against)::bigint ELSE 0 END), 0) AS goals_diff
                FROM football.fixture f
                JOIN football.results fr ON fr.team_id = (SELECT team_id FROM football.team WHERE name = f.guest) AND LOWER(fr.description) = 'away'
                WHERE f.played = true AND f.league_id = _league_id
                GROUP BY f.guest
            ) af ON s.team = af.team
            WHERE s.league_id = _league_id
        )
        SELECT
            json_agg(
                json_build_object(
                    'rank', rank,
                    'teamName', team,
                    'roundsPlayed', rounds_played,
                    'wins', wins,
                    'draws', draws,
                    'loses', loses,
                    'goalsRatio', goals_for || ':' || goals_against,
                    'goalsDiff', goals_diff,
                    'points', points,
                    'form',
                    CASE
                        WHEN _location_type = 'home' THEN COALESCE(regexp_split_to_array(home_results, ''), '{}')
                        WHEN _location_type = 'away' THEN COALESCE(regexp_split_to_array(away_results, ''), '{}')
                        ELSE regexp_split_to_array(form, '')
                    END,
                    'positionDescription',
                    CASE
                        WHEN rt.rank = 1 THEN 'UEFA Champions League Qualifiers'
                        WHEN rt.rank IN (2, 3) THEN 'UEFA Conference League Qualifiers'
                        WHEN rt.rank IN (16, 17, 18) THEN 'Relegation'
                        ELSE null
                    END
                )
            )
        FROM (
            SELECT *,
                rank() OVER (ORDER BY points DESC, goals_diff DESC, goals_for DESC, wins DESC, goals_against ASC, team ASC) AS rank
            FROM ranked_teams rt
        ) AS rt
    );
END;
$function$;
