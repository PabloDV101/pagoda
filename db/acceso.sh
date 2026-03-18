#!/bin/bash
docker compose up -d
PGPASSWORD=12345 docker compose exec -it postgres psql -U admin -d pagoda

