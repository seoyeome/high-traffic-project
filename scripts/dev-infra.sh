#!/usr/bin/env bash
set -euo pipefail

echo "[infra] Starting PostgreSQL, Redis, Prometheus, and Grafana via Docker Compose..."
docker compose up -d postgres redis prometheus grafana

echo "[infra] Waiting for services to be healthy..."
docker compose ps
