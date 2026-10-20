#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_JAR="${1:-/opt/minecraft/data/mods/medieval-machinery-fabric-1.20.1-1.2.0.jar.orig}"
OUTPUT_JAR="${SCRIPT_DIR}/build/medieval-machinery-fabric-1.20.1-1.2.0-patched.jar"
CLASSES_DIR="${SCRIPT_DIR}/build/classes"

if [ ! -d "${CLASSES_DIR}" ]; then
  "${SCRIPT_DIR}/build_patch.sh"
fi

if [ ! -f "${BASE_JAR}" ]; then
  echo "Base JAR not found: ${BASE_JAR}"
  echo "Usage: ./package_jar.sh [path_to_original_jar]"
  exit 1
fi

mkdir -p "${SCRIPT_DIR}/build"
cp "${BASE_JAR}" "${OUTPUT_JAR}"
jar uf "${OUTPUT_JAR}" -C "${CLASSES_DIR}" .

echo "Successfully packaged: ${OUTPUT_JAR}"
