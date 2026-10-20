#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
OUTPUT_DIR="${SCRIPT_DIR}/build/classes"

mkdir -p "${OUTPUT_DIR}"

echo "Compiling patched classes..."
javac -proc:none -encoding UTF-8 -d "${OUTPUT_DIR}" @"${SCRIPT_DIR}/javac_cp.txt" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/block/garrison/WatchpostBlockEntity.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/network/SoldierCommandNetworking.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/network/SoldierNetworking.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/npc/entity/MedievalSoldierEntity.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/npc/entity/MedievalSoldierOrderController.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/npc/organization/CastleAreaService.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/npc/organization/GarrisonData.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/npc/organization/GarrisonManager.java" \
  "${SCRIPT_DIR}/src/main/java/com/medievalmachinery/mod/screen/npc/SoldierInventoryScreenHandler.java"

echo "Build successful! Classes written to ${OUTPUT_DIR}"
