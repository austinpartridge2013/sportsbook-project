#!/bin/bash
psql -U postgres -c "DROP DATABASE IF EXISTS easybooks"
psql -U postgres -c "CREATE DATABASE easybooks WITH owner=easybooks"

java -jar $LIQUIBASE_HOME/liquibase.jar update

