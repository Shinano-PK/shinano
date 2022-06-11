#!/bin/bash

if [ $# -ne 5 ]
  then
    echo "No arguments supplied: command host port username password"
    exit 1
fi

export shinano_email_host=$2
export shinano_email_port=$3
export shinano_email_username=$4
export shinano_email_password=$5

# echo "$2"
# echo "$3"
# echo "$4"
# echo "$5"

mvn "$1"
unset shinano_email_host
unset shinano_email_port
unset shinano_email_username
unset shinano_email_password
