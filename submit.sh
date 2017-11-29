#!/bin/sh

TOKEN="YOURTOKENHERE"
TIMETESTS="false"
OPTCOUNT="150"

cd bin || exit

if [ "$TOKEN" = "YOURTOKENHERE" ]
then
  echo "Set your token in submit.sh"
else
  java -cp '../bin/:../lib/*' simplec.Submit "$TOKEN" "$OPTCOUNT" "$TIMETESTS"  2> /dev/null
fi

cd ..
exit 0
