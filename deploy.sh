#!/bin/bash
rm -rf $JBOSS_HOME/standalone/deployments/*.ear
cp java/easybooks-core/easybooks-core-ear/target/*.ear $JBOSS_HOME/standalone/deployments
