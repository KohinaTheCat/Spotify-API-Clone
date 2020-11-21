#!/bin/bash

echo "----------------------------------------------------------------------------------------------------"
echo "****************************************************************************************************"
echo "----------------------------------------------------------------------------------------------------"

echo "-----BEGIN IMPORTING TEST DATA-----"

[ -d "song_db_backup" ] || mkdir "song_db_backup"

echo "-----Backing up existing CSC301 DB-----"
mongodump -d csc301-test -o ./"song_db_backup"/`date +%Y-%m-%d_%H-%M-%S` || (echo "[ERROR] Could not create DB backup" && exit 1)
echo "-----Deleting existing CSC301 DB-----" 
mongo csc301-test --eval "db.dropDatabase()" || (echo "[ERROR] Could not delete CSC301 DB" && exit 1)
echo "-----Importing test data from ./MOCK_DATA to CSC301 DB-----"
mongoimport --db csc301-test --jsonArray --collection songs --file "./MOCK_DATA.json" || (echo "[ERROR] Could not import test data" && exit 1)

echo "-----END IMPORTING TEST DATA-----"