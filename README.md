# Medieval Machinery — Community Bugfix Fork (Fabric 1.20.1)

A maintenance and bugfix fork of **Medieval Machinery 1.2.0** for Minecraft Fabric 1.20.1.

> [!NOTE]
> **Credits & License**: Original mod created by **TWeber90** on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/medieval-machinery). All original assets, models, textures, animations, and designs remain the intellectual property of TWeber90. This repository contains decompiled source and targeted bugfixes for stability, multiplayer servers, and long-running worlds.

---

## 🎯 Patches & Fixes in This Fork

### 1. Garrison Ghost Records & Watchpost Census Discrepancy
- **Problem**: When a soldier dies in battle or is despawned, `MedievalSoldierEntity.cleanupOrganizationAfterRemoval()` only cleaned up `CommandGroupManager` and `SoldierRegistryManager`. It never called `GarrisonManager.removeSoldier(...)`. As a result, deceased soldier UUIDs accumulated indefinitely in `medieval-machinery_garrisons.dat`, causing the Watchpost Table count to grow unboundedly and display ghost soldiers.
- **Fix**:
  - Implemented `GarrisonManager.removeSoldierEverywhere(UUID soldierId)` with proper `markDirty()`.
  - Added death cleanup hook in `MedievalSoldierEntity.cleanupOrganizationAfterRemoval()`.
  - Implemented automatic pruning (`GarrisonManager.pruneDeadSoldiers`) on world load, reconciling garrison records against active living soldiers in `SoldierRegistryManager`.

### 2. Quartermaster Work Order in Quick RMB (PPM) Rotation
- **Problem**: Right-clicking (PPM) a soldier cycles their field orders via `MedievalSoldierOrderController.cycleFieldOrder()`. The cycle was hardcoded to `IDLE -> FOLLOW -> HOLD_POSITION -> IDLE/PATROL`, while `WORK` was only treated as an exit transition (`WORK -> IDLE`). Players could not toggle Quartermasters to farm work without opening the full GUI.
- **Fix**:
  - For worker roles (`NpcRole.QUARTERMASTER`), `cycleFieldOrder()` now rotates through `HOLD_POSITION -> WORK -> PATROL / IDLE`.

### 3. Entity ID Overflow Above 32767 (`2^15 - 1`)
- **Problem**: Vanilla Minecraft's `ScreenHandlerPropertyUpdateS2CPacket` synchronizes `PropertyDelegate` slots as signed 16-bit integers (`short`, max value `32767`). On servers running continuously, `Entity.CURRENT_ID` easily exceeds `32767`, wrapping into negative numbers (e.g. `35000` becomes `-30536`).
  - Client-side: `world.getEntityById(negativeId)` returned `null`, resulting in blank GUI screens and missing 3D model previews.
  - Server-side: Closing the GUI or issuing orders returned the overflowed ID back to `SoldierCommandNetworking` and `SoldierNetworking`, causing `world.getEntityById()` to fail and drop player commands silently.
- **Fix**:
  - **32-Bit Property Splitting**: Split entity ID across delegate slot 0 (low 16 bits) and slot 7 (high 16 bits), reconstructing the full 32-bit ID in `SoldierInventoryScreenHandler.getSoldierEntityId()`.
  - **Multi-Tier Server Fallback**: Enhanced `SoldierCommandNetworking.findSoldier()` and `SoldierNetworking.ownedSoldier()`:
    1. Direct lookup by 32-bit entity ID.
    2. Open `SoldierInventoryScreenHandler` lookup matching low 16 bits or signed short.
    3. Spatial lookup within 8-block reach (`squaredDistanceTo <= 64.0`) matching `(soldier.getId() & 0xFFFF) == (networkId & 0xFFFF)`.
    4. Safe fallback to the player's currently open soldier GUI if within 8 blocks.
- **Zero Client Update Required**: **100% backward compatible**. Unmodified clients connecting to a patched server will no longer lose orders or have GUI desyncs when entity IDs exceed 32767.

---

## 🛠️ Building & Packaging

The mod uses standard Fabric 1.20.1 intermediary mappings.

To compile patched classes:
```bash
./build_patch.sh
```

To package a standalone patched `.jar`:
```bash
./package_jar.sh
```
