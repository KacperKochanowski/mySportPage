CREATE TABLE football.team (
	team_id int4 NOT NULL,
	"name" text NOT NULL,
	league_id int4 NOT NULL,
	shortcut text NULL,
	club_founded int4 NULL,
	country text NULL,
	club_crest text NULL,
	CONSTRAINT team_team_id_key UNIQUE (team_id)
);

CREATE TABLE football.stadium (
	stadium_id int4 NULL,
	stadium text NOT NULL,
	team_id _int4 NOT NULL,
	capacity int4 NULL,
	address text NULL,
	city text NOT NULL,
	CONSTRAINT stadium_stadium_id_key UNIQUE (stadium_id)
);

CREATE TABLE football.league (
	league_id int4 NOT NULL,
	"name" text NOT NULL,
	"type" text NULL,
	country text NOT NULL,
	"year" text NULL,
	"start" date NULL,
	"end" date NULL,
	logo text NULL,
	CONSTRAINT league_league_id_key UNIQUE (league_id)
);

CREATE TABLE public.country (
	"name" text NOT NULL,
	code text NULL,
	flag text NULL,
	CONSTRAINT country_name_key UNIQUE (name)
);

CREATE TABLE football.league_coverage (
	external_league_id int4 NOT NULL,
	events bool NULL,
	lineups bool NULL,
	statistics_fixtures bool NULL,
	statistics_players bool NULL,
	standings bool NULL,
	players bool NULL,
	top_scorers bool NULL,
	top_assists bool NULL,
	top_cards bool NULL,
	injuries bool NULL,
	predictions bool NULL,
	odds bool NULL,
	CONSTRAINT league_coverage_external_league_id_key UNIQUE (external_league_id)
);

CREATE TABLE football.fixture (
	fixture_id int4 NOT NULL,
	"event" text NOT NULL,
	league_id int4 NOT NULL,
	round int4 NOT NULL,
	season text NOT NULL,
	"start" timestamp NULL,
	host text NOT NULL,
	guest text NOT NULL,
	winner text NULL,
	stadium_id int4 NULL,
	referee text NULL,
	"result" text NULL,
	halftime_score text NULL,
	fulltime_score text NULL,
	played bool NULL,
    is_postponed bool NULL DEFAULT false,
	CONSTRAINT fixture_fixture_id_key UNIQUE (fixture_id)
);

CREATE TABLE football.standing (
	"rank" int4 NOT NULL,
	team text NOT NULL,
	points int4 NOT NULL,
	goals_diff text NOT NULL,
	form text NULL,
	league_id int4 NOT NULL,
	season int4 NOT NULL,
	position_description text NULL,
	actual_results_id int4 NULL,
	home_results_id int4 NULL,
	away_results_id int4 NULL,
	updated timestamp NOT NULL,
	CONSTRAINT standing_team_key UNIQUE (team)
);

CREATE TABLE football.results (
	id serial4 NOT NULL,
	team_id int4 NOT NULL,
	rounds_played int4 NOT NULL,
	wins int4 NOT NULL,
	draws int4 NOT NULL,
	loses int4 NOT NULL,
	goals_for int4 NOT NULL,
	goals_against int4 NOT NULL,
	description text NOT NULL
);

CREATE TABLE football.fixture_statistics (
	fixture_id int4 NOT NULL,
	team_id int4 NOT NULL,
	shots_on_goal int4 NULL DEFAULT 0,
	shots_off_goal int4 NULL DEFAULT 0,
	total_shots int4 NULL DEFAULT 0,
	blocked_shots int4 NULL DEFAULT 0,
	shots_inside_box int4 NULL DEFAULT 0,
	shots_outside_box int4 NULL DEFAULT 0,
	fouls int4 NULL DEFAULT 0,
	corner_kicks int4 NULL DEFAULT 0,
	offsides int4 NULL DEFAULT 0,
	ball_possession text NULL,
	yellow_cards int4 NULL DEFAULT 0,
	red_cards int4 NULL DEFAULT 0,
	goalkeeper_saves int4 NULL DEFAULT 0,
	total_passes int4 NULL DEFAULT 0,
	passes_accurate int4 NULL DEFAULT 0,
	passes_percent text NULL,
	expected_goals float8 NULL
);

CREATE TABLE football.coach (
	external_id int4 NOT NULL,
	"name" text NOT NULL,
	first_name text NOT NULL,
	last_name text NOT NULL,
	age int4 NULL,
	birth_date date NULL,
	birth_country text NULL,
	nationality text NULL,
	height int4 NULL,
	weight int4 NULL,
	photo text NULL,
	CONSTRAINT pk_coach_external_id PRIMARY KEY (external_id)
);

CREATE TABLE football.coach_career (
	coach_id int4 NOT NULL,
	team_id int4 NOT NULL,
	"start" date NOT NULL,
	"end" date NULL
);