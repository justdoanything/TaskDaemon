sed -i -e 's/\r//g' /usr/src/app/run.sh
sh /usr/src/app/run.sh start
tail -f