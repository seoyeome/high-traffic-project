#!/usr/bin/env bash
set -euo pipefail

echo "[infra] Stopping Docker Compose services..."
docker compose down -v
