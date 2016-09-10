#!/bin/bash
# Pack up the source distribution

DistDir=target/distribution
JarFile=credit-suisse-crowdfunding-loans-peterp-src.zip
mkdir -p ${DistDir}
jar cvf ${DistDir}/${JarFile}  .gitignore *.sh *.txt *.md *.sbt  src
# End.
