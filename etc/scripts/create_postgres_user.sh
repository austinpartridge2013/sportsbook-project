#!/bin/bash
psql -U postgres -c "CREATE USER easybooks WITH PASSWORD 'fightpredictor'"
psql -U postgres -c "DROP SCHEMA IF EXISTS easybooks"
psql -U postgres -c "CREATE SCHEMA easybooks"
psql -U postgres -c "ALTER USER easybooks SET search_path TO public"
