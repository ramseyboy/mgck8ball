#!/usr/bin/env bash

mkdir resources/db
sqlite3 resources/db/qa.db < migrations/create.sql