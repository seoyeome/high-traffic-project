#!/usr/bin/env bash
set -euo pipefail

echo "[infra] Starting PostgreSQL and Redis via Docker Compose..."
docker compose up -d postgres redis

echo "[infra] Waiting for services to be healthy..."
docker compose ps
