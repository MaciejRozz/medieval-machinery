/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package com.medievalmachinery.mod.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class RenderTransforms {
    private static final Transform NORMAL = new Transform(0.46, 0.52, 0.5, 72.0f, 7.0f, -1.0f, 0.62f);
    private static final Transform CARRIED = new Transform(0.3, 0.59, 0.54, 237.7f, 254.0f, 3.0f, 0.71f);
    private static final Transform BASKET_GUI = new Transform(0.5, 0.22, 0.5, 30.0f, 225.0f, 0.0f, 0.82f);
    private static final Transform BASKET_FIRST_PERSON = new Transform(1.58, 0.27, -0.33, -8.0f, 180.0f, -5.0f, 1.0f);
    private static final Transform BASKET_CARRY_FIRST_PERSON = new Transform(0.0, -0.38, -1.14, 24.0f, 270.0f, -2.0f, 0.86f);
    private static final Transform ROYAL_GUILLOTINE_GUI = new Transform(0.5, 0.13, 0.5, 15.0f, -160.0f, 0.0f, 0.285f);
    private static final Transform ROYAL_GUILLOTINE_FIRST_PERSON = new Transform(0.62, 0.12, 0.5, 0.0f, 0.0f, 0.0f, 0.235f);
    private static final Transform ROYAL_GUILLOTINE_THIRD_PERSON = new Transform(0.39, 0.4, 0.28, 63.0f, 0.0f, 0.0f, 0.235f);
    private static final Transform WHEEL_GROUND = new Transform(0.93, -0.51, 0.18, 0.0f, 0.0f, 90.0f, 0.52f);
    private static final Transform WHEEL_WALL_WEST = new Transform(-0.1400000000000005, -0.05, 0.5400000000000004, 0.0f, 90.0f, 10.0f, 0.5f);
    private static final Transform WHEEL_WALL_NORTH = new Transform(0.8200000000000001, -0.09, -0.35000000000000014, -10.0f, -90.0f, 0.0f, 0.5f);
    private static final Transform WHEEL_WALL_EAST = new Transform(0.6399999999999999, 0.039999999999999925, -0.019999999999999976, 0.0f, 90.0f, -10.0f, 0.5f);
    private static final Transform WHEEL_WALL_SOUTH = new Transform(0.3099999999999996, 0.01, 0.8000000000000006, 10.0f, 90.0f, 0.0f, 0.5f);
    private static final Transform WHEEL_GUI = new Transform(1.17, 0.14, 0.51, 25.0f, 225.0f, 0.0f, 0.5500002f);
    private static final Transform WHEEL_FIRST_PERSON = new Transform(0.7200000000000001, -0.13999999999999999, 0.55, 11.0f, 87.0f, -12.0f, 0.61f);
    private static final Transform WHEEL_THIRD_PERSON = new Transform(0.22, 1.14, 0.58, -113.0f, 90.0f, -10.0f, 0.45000005f);
    private static final Transform WHEEL_FIXED = new Transform(0.18, 0.030000000000000013, 1.06, 0.0f, 90.0f, 0.0f, 0.55f);
    private static final Transform WHEEL_GROUND_ITEM = new Transform(0.47, 0.2, 0.5, 0.0f, 0.0f, 90.0f, 0.39999998f);
    private static final Transform FIELD_CANNON_GUI = new Transform(0.48, 0.2, 0.5, 18.0f, -130.0f, 0.0f, 0.33f);
    private static final Transform FIELD_CANNON_FIRST_PERSON = new Transform(0.5, 0.05, 0.38, 0.0f, 180.0f, 0.0f, 0.3f);
    private static final Transform FIELD_CANNON_THIRD_PERSON = new Transform(0.5, 0.5400000000000004, 0.10999999999999985, 50.0f, 180.0f, 0.0f, 0.28f);
    private static final Transform FIELD_CANNON_FIXED = new Transform(0.6500000000000001, 0.22, -0.4800000000000006, 0.0f, 90.0f, 0.0f, 0.31f);
    private static final Transform FIELD_CANNON_GROUND = new Transform(0.5, 0.27000000000000013, 0.5, 0.0f, 90.0f, 0.0f, 0.34999993f);
    private static final Transform DECO_FIELD_CANNON_GUI = new Transform(0.6800000000000002, 0.18, 0.5, 21.0f, -72.0f, -2.0f, 0.3000002f);
    private static final Transform DECO_FIELD_CANNON_FIRST_PERSON = new Transform(0.5, 0.08, 0.4, 0.0f, 5.0f, 0.0f, 0.34f);
    private static final Transform DECO_FIELD_CANNON_THIRD_PERSON = new Transform(0.49, 0.7700000000000006, 0.19999999999999984, 72.0f, 0.0f, 0.0f, 0.32f);
    private static final Transform DECO_FIELD_CANNON_FIXED = new Transform(0.3599999999999999, 0.27, -0.5000000000000006, 0.0f, 90.0f, 0.0f, 0.31000003f);
    private static final Transform DECO_FIELD_CANNON_GROUND = new Transform(0.5, 0.28000000000000014, 0.5, 0.0f, 90.0f, 0.0f, 0.34999996f);
    private static final Transform FORTRESS_CANNON_GUI = new Transform(0.5, 0.2, 0.5, 18.0f, -130.0f, 0.0f, 0.4f);
    private static final Transform FORTRESS_CANNON_FIRST_PERSON = new Transform(0.6, 0.1, 0.43, 0.0f, 180.0f, 0.0f, 0.4f);
    private static final Transform FORTRESS_CANNON_THIRD_PERSON = new Transform(0.49, 0.7500000000000004, 0.1899999999999999, 80.0f, 0.0f, 0.0f, 0.39f);
    private static final Transform FORTRESS_CANNON_FIXED = new Transform(-0.5300000000000006, 0.22, 0.5, 0.0f, -90.0f, 0.0f, 0.39f);
    private static final Transform FORTRESS_CANNON_GROUND = new Transform(0.5, 0.2600000000000001, 0.5, 0.0f, 90.0f, 0.0f, 0.39999995f);
    private static final Transform DECO_FORTRESS_CANNON_GUI = new Transform(0.5, 0.18, 0.5, -4.0f, -69.0f, 0.0f, 0.41f);
    private static final Transform DECO_FORTRESS_CANNON_FIRST_PERSON = new Transform(0.6, 0.1, 0.43, 0.0f, 180.0f, 0.0f, 0.4f);
    private static final Transform DECO_FORTRESS_CANNON_THIRD_PERSON = new Transform(0.49, 0.7000000000000004, 0.34, 70.0f, 0.0f, 0.0f, 0.39999998f);
    private static final Transform DECO_FORTRESS_CANNON_FIXED = new Transform(-0.5200000000000006, 0.19999999999999998, 0.53, 0.0f, -90.0f, 0.0f, 0.39f);
    private static final Transform DECO_FORTRESS_CANNON_GROUND = new Transform(0.55, 0.21000000000000008, 0.5, 0.0f, 90.0f, 0.0f, 0.37999997f);
    private static final Transform CATAPULT_GUI = new Transform(0.52, 0.18, 0.5, 17.0f, -138.0f, 0.0f, 0.15999997f);
    private static final Transform CATAPULT_FIRST_PERSON = new Transform(0.7600000000000002, 0.09999999999999999, 0.38, 0.0f, 180.0f, 0.0f, 0.19f);
    private static final Transform CATAPULT_THIRD_PERSON = new Transform(0.49, 0.6, 0.20000000000000007, 67.0f, 180.0f, 0.0f, 0.19f);
    private static final Transform CATAPULT_FIXED = new Transform(0.49, 0.06999999999999998, -0.45, 0.0f, 90.0f, 0.0f, 0.2f);
    private static final Transform CATAPULT_GROUND = new Transform(0.5, 0.2, 0.5, 0.0f, 90.0f, 0.0f, 0.22f);
    private static final Transform CATAPULT_PAYLOAD = new Transform(0.0, 0.0, 0.0, 0.0f, 0.0f, 0.0f, 0.6f);

    private RenderTransforms() {
    }

    public static Transform normal() {
        return NORMAL;
    }

    public static Transform carried() {
        return CARRIED;
    }

    public static Transform basketGui() {
        return BASKET_GUI;
    }

    public static Transform basketFirstPerson() {
        return BASKET_FIRST_PERSON;
    }

    public static Transform basketCarryFirstPerson() {
        return BASKET_CARRY_FIRST_PERSON;
    }

    public static Transform royalGuillotineGui() {
        return ROYAL_GUILLOTINE_GUI;
    }

    public static Transform royalGuillotineFirstPerson() {
        return ROYAL_GUILLOTINE_FIRST_PERSON;
    }

    public static Transform royalGuillotineThirdPerson() {
        return ROYAL_GUILLOTINE_THIRD_PERSON;
    }

    public static Transform wheelGround() {
        return WHEEL_GROUND;
    }

    public static Transform wheelWallWest() {
        return WHEEL_WALL_WEST;
    }

    public static Transform wheelWallNorth() {
        return WHEEL_WALL_NORTH;
    }

    public static Transform wheelWallEast() {
        return WHEEL_WALL_EAST;
    }

    public static Transform wheelWallSouth() {
        return WHEEL_WALL_SOUTH;
    }

    public static Transform wheelGui() {
        return WHEEL_GUI;
    }

    public static Transform wheelFirstPerson() {
        return WHEEL_FIRST_PERSON;
    }

    public static Transform wheelThirdPerson() {
        return WHEEL_THIRD_PERSON;
    }

    public static Transform wheelFixed() {
        return WHEEL_FIXED;
    }

    public static Transform wheelGroundItem() {
        return WHEEL_GROUND_ITEM;
    }

    public static Transform fieldCannonGui() {
        return FIELD_CANNON_GUI;
    }

    public static Transform fieldCannonFirstPerson() {
        return FIELD_CANNON_FIRST_PERSON;
    }

    public static Transform fieldCannonThirdPerson() {
        return FIELD_CANNON_THIRD_PERSON;
    }

    public static Transform fieldCannonFixed() {
        return FIELD_CANNON_FIXED;
    }

    public static Transform fieldCannonGround() {
        return FIELD_CANNON_GROUND;
    }

    public static Transform decoFieldCannonGui() {
        return DECO_FIELD_CANNON_GUI;
    }

    public static Transform decoFieldCannonFirstPerson() {
        return DECO_FIELD_CANNON_FIRST_PERSON;
    }

    public static Transform decoFieldCannonThirdPerson() {
        return DECO_FIELD_CANNON_THIRD_PERSON;
    }

    public static Transform decoFieldCannonFixed() {
        return DECO_FIELD_CANNON_FIXED;
    }

    public static Transform decoFieldCannonGround() {
        return DECO_FIELD_CANNON_GROUND;
    }

    public static Transform fortressCannonGui() {
        return FORTRESS_CANNON_GUI;
    }

    public static Transform fortressCannonFirstPerson() {
        return FORTRESS_CANNON_FIRST_PERSON;
    }

    public static Transform fortressCannonThirdPerson() {
        return FORTRESS_CANNON_THIRD_PERSON;
    }

    public static Transform fortressCannonFixed() {
        return FORTRESS_CANNON_FIXED;
    }

    public static Transform fortressCannonGround() {
        return FORTRESS_CANNON_GROUND;
    }

    public static Transform decoFortressCannonGui() {
        return DECO_FORTRESS_CANNON_GUI;
    }

    public static Transform decoFortressCannonFirstPerson() {
        return DECO_FORTRESS_CANNON_FIRST_PERSON;
    }

    public static Transform decoFortressCannonThirdPerson() {
        return DECO_FORTRESS_CANNON_THIRD_PERSON;
    }

    public static Transform decoFortressCannonFixed() {
        return DECO_FORTRESS_CANNON_FIXED;
    }

    public static Transform decoFortressCannonGround() {
        return DECO_FORTRESS_CANNON_GROUND;
    }

    public static Transform catapultGui() {
        return CATAPULT_GUI;
    }

    public static Transform catapultFirstPerson() {
        return CATAPULT_FIRST_PERSON;
    }

    public static Transform catapultThirdPerson() {
        return CATAPULT_THIRD_PERSON;
    }

    public static Transform catapultFixed() {
        return CATAPULT_FIXED;
    }

    public static Transform catapultGround() {
        return CATAPULT_GROUND;
    }

    public static Transform catapultPayload() {
        return CATAPULT_PAYLOAD;
    }

    public static Transform get(Target target) {
        return switch (target.ordinal()) {
            default -> throw new IncompatibleClassChangeError();
            case 0 -> RenderTransforms.normal();
            case 1 -> RenderTransforms.carried();
            case 2 -> RenderTransforms.basketGui();
            case 3 -> RenderTransforms.basketFirstPerson();
            case 4 -> RenderTransforms.basketCarryFirstPerson();
            case 5 -> RenderTransforms.royalGuillotineGui();
            case 6 -> RenderTransforms.royalGuillotineFirstPerson();
            case 7 -> RenderTransforms.royalGuillotineThirdPerson();
            case 8 -> RenderTransforms.wheelGround();
            case 9 -> RenderTransforms.wheelWallWest();
            case 10 -> RenderTransforms.wheelWallNorth();
            case 11 -> RenderTransforms.wheelWallEast();
            case 12 -> RenderTransforms.wheelWallSouth();
            case 13 -> RenderTransforms.wheelGui();
            case 14 -> RenderTransforms.wheelFirstPerson();
            case 15 -> RenderTransforms.wheelThirdPerson();
            case 16 -> RenderTransforms.wheelFixed();
            case 17 -> RenderTransforms.wheelGroundItem();
            case 18 -> RenderTransforms.fieldCannonGui();
            case 19 -> RenderTransforms.fieldCannonFirstPerson();
            case 20 -> RenderTransforms.fieldCannonThirdPerson();
            case 21 -> RenderTransforms.fieldCannonFixed();
            case 22 -> RenderTransforms.fieldCannonGround();
            case 23 -> RenderTransforms.decoFieldCannonGui();
            case 24 -> RenderTransforms.decoFieldCannonFirstPerson();
            case 25 -> RenderTransforms.decoFieldCannonThirdPerson();
            case 26 -> RenderTransforms.decoFieldCannonFixed();
            case 27 -> RenderTransforms.decoFieldCannonGround();
            case 28 -> RenderTransforms.fortressCannonGui();
            case 29 -> RenderTransforms.fortressCannonFirstPerson();
            case 30 -> RenderTransforms.fortressCannonThirdPerson();
            case 31 -> RenderTransforms.fortressCannonFixed();
            case 32 -> RenderTransforms.fortressCannonGround();
            case 33 -> RenderTransforms.decoFortressCannonGui();
            case 34 -> RenderTransforms.decoFortressCannonFirstPerson();
            case 35 -> RenderTransforms.decoFortressCannonThirdPerson();
            case 36 -> RenderTransforms.decoFortressCannonFixed();
            case 37 -> RenderTransforms.decoFortressCannonGround();
            case 38 -> RenderTransforms.catapultGui();
            case 39 -> RenderTransforms.catapultFirstPerson();
            case 40 -> RenderTransforms.catapultThirdPerson();
            case 41 -> RenderTransforms.catapultFixed();
            case 42 -> RenderTransforms.catapultGround();
            case 43 -> RenderTransforms.catapultPayload();
        };
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Transform {
        public final double translationX;
        public final double translationY;
        public final double translationZ;
        public final float rotationX;
        public final float rotationY;
        public final float rotationZ;
        public final float scale;

        private Transform(double x, double y, double z, float rx, float ry, float rz, float scale) {
            this.translationX = x;
            this.translationY = y;
            this.translationZ = z;
            this.rotationX = rx;
            this.rotationY = ry;
            this.rotationZ = rz;
            this.scale = scale;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Target {
        BASKET_THIRD_PERSON,
        BASKET_CARRIED,
        BASKET_GUI,
        BASKET_FIRST_PERSON,
        BASKET_CARRY_FIRST_PERSON,
        ROYAL_GUILLOTINE_GUI,
        ROYAL_GUILLOTINE_FIRST_PERSON,
        ROYAL_GUILLOTINE_THIRD_PERSON,
        WHEEL_GROUND,
        WHEEL_WALL_WEST,
        WHEEL_WALL_NORTH,
        WHEEL_WALL_EAST,
        WHEEL_WALL_SOUTH,
        WHEEL_GUI,
        WHEEL_FIRST_PERSON,
        WHEEL_THIRD_PERSON,
        WHEEL_FIXED,
        WHEEL_GROUND_ITEM,
        FIELD_CANNON_GUI,
        FIELD_CANNON_FIRST_PERSON,
        FIELD_CANNON_THIRD_PERSON,
        FIELD_CANNON_FIXED,
        FIELD_CANNON_GROUND,
        DECO_FIELD_CANNON_GUI,
        DECO_FIELD_CANNON_FIRST_PERSON,
        DECO_FIELD_CANNON_THIRD_PERSON,
        DECO_FIELD_CANNON_FIXED,
        DECO_FIELD_CANNON_GROUND,
        FORTRESS_CANNON_GUI,
        FORTRESS_CANNON_FIRST_PERSON,
        FORTRESS_CANNON_THIRD_PERSON,
        FORTRESS_CANNON_FIXED,
        FORTRESS_CANNON_GROUND,
        DECO_FORTRESS_CANNON_GUI,
        DECO_FORTRESS_CANNON_FIRST_PERSON,
        DECO_FORTRESS_CANNON_THIRD_PERSON,
        DECO_FORTRESS_CANNON_FIXED,
        DECO_FORTRESS_CANNON_GROUND,
        CATAPULT_GUI,
        CATAPULT_FIRST_PERSON,
        CATAPULT_THIRD_PERSON,
        CATAPULT_FIXED,
        CATAPULT_GROUND,
        CATAPULT_PAYLOAD;

    }
}

