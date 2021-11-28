# ssh_tunneling

This program open SSH tunneling and execute linux command or a specific query to database repeatedly.\
You can run the below types of tasks repeatedly if you change application.xml.\
And also, run more complex task if you put your logic into DatabaseService class.
  - Type1 : Open SSH tunneling and reopen ssh tunnel if the local port is not opened.
  - Type2 : Open channel and execute linux command repeatedly.
  - Type3 : Open SSH tunneling and execute a specific query repeatedly.

Project Type : maven\
Refo : https://github.com/justdoanything/WatchDB
