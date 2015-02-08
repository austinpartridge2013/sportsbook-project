#!/bin/bash
rm $JBOSS_HOME/standalone/deployments/*.ear
cp java/sportsbook-core/sportsbook-core-ear/target/*.ear $JBOSS_HOME/standalone/deployments
