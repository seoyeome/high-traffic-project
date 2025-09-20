#!/usr/bin/env bash
set -euo pipefail

PORT=8000
if lsof -ti:${PORT} >/dev/null 2>&1; then
  echo "[dev] Port ${PORT} is in use. Killing process..."
  lsof -ti:${PORT} | xargs kill -9 || true
fi

# Ensure Postgres is ready before starting the app (helps avoid boot failures)
echo "[dev] Checking Postgres readiness..."
# If docker service exists, wait for container health
if command -v docker >/dev/null 2>&1 && docker ps --format '{{.Names}}' | grep -q '^ht_postgres$'; then
  echo "[dev] Waiting for Docker container ht_postgres to be healthy..."
  for i in {1..60}; do
    STATUS=$(docker inspect -f '{{.State.Health.Status}}' ht_postgres 2>/dev/null || echo "unknown")
    if [ "$STATUS" = "healthy" ]; then
      echo "[dev] ht_postgres is healthy."
      break
    fi
    sleep 2
  done
fi

# Fallback: wait until localhost:5432 accepts TCP connections
for i in {1..30}; do
  if (echo > /dev/tcp/localhost/5432) >/dev/null 2>&1; then
    echo "[dev] Postgres port 5432 is accepting connections."
    break
  fi
  sleep 1
done

./gradlew bootRun
