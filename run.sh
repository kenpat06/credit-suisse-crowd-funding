#!/bin/bash
# Easily execute the server from the command line.

: ${SBT_CMD:=sbt}

${SBT_CMD} ${SBT_OPTIONS} "run -admin.port=:10000"

 # End.
