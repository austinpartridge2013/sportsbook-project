#!/bin/bash
psql -U postgres -c "DROP DATABASE IF EXISTS sportsbook"
psql -U postgres -c "CREATE DATABASE sportsbook WITH owner=sportsbook"

java -jar $LIQUIBASE_HOME/liquibase.jar update

