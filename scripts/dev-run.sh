#!/usr/bin/env bash
set -euo pipefail

PORT=8000
if lsof -ti:${PORT} >/dev/null 2>&1; then
  echo "[dev] Port ${PORT} is in use. Killing process..."
  lsof -ti:${PORT} | xargs kill -9 || true
fi

./gradlew bootRun
