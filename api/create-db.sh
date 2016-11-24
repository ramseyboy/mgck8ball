#!/usr/bin/env bash

mkdir -p resources/db
sqlite3 resources/db/qa.db < migrations/create.sql