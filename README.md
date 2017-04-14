# DataSync
Project contains code to:
  * define the mysql database table
  * android client code synchronizing a table with web server
  * server side code 
    * initial build is to a executable jar
    * written as a spring boot application
    * supports a limited number of simultaneous client connections
  
## Getting Started
  * mySQL version information
    * MySQL Workbench Community (GPL) for Windows version 6.3.6 CE build 511
    * exported schema in db folder
  * Android version and db information
    * Tested on android v 6.0 Marshmalloiw and v7.0 Nougat
    * database error table from sqlite3 

## Contents
  * server folder - contains server side code, resources and pom file
  * database folder - contains client and server database schemas for table
  * client folder - contains client code 
  * code folders - contains client code to test synchronization
## Reason
  1. Developed due to lack of working/complete server and client samples on the net.
  1. Poor man's implementation of firebase without the fees
  1. Error entries removed from client if entries uploaded to server
 ## Updates
  1. Modified to support both windows and linux servers
  1. tested and verified that enough space has been allocated for error message content
  1. add support for sending emails when database updated to dev team
    * //localhost:8080/error_table/getAll - crude plain text dump of error table records to browser screen
    * //localhost:8080/error_table/send-mail - sends email containing error table entries.
 ## Planned
  
  1. add support for running as war based web service with increased connection count support
  1. override for whitelabel error displays
