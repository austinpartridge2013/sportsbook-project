#!/bin/bash
psql -U postgres -c "CREATE USER sportsbook WITH PASSWORD 'fightpredictor'"
psql -U postgres -c "DROP SCHEMA IF EXISTS sportsbook"
psql -U postgres -c "CREATE SCHEMA sportsbook"
psql -U postgres -c "ALTER USER sportsbook SET search_path TO public"
