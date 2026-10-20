/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3542
 */
package com.medievalmachinery.mod.block.architecture;

import net.minecraft.class_3542;

public enum QuaderPattern implements class_3542
{
    SINGLE("single", true, true, true, true),
    H_LEFT("h_left", true, false, true, true),
    H_RIGHT("h_right", false, true, true, true),
    V_BOTTOM("v_bottom", true, true, true, false),
    V_TOP("v_top", true, true, false, true),
    QUAD_BOTTOM_LEFT("quad_bottom_left", true, false, true, false),
    QUAD_BOTTOM_RIGHT("quad_bottom_right", false, true, true, false),
    QUAD_TOP_LEFT("quad_top_left", true, false, false, true),
    QUAD_TOP_RIGHT("quad_top_right", false, true, false, true),
    SMALL_A("small_a", true, true, true, true),
    SMALL_B("small_b", true, true, true, true);

    private final String id;
    private final boolean seamLeft;
    private final boolean seamRight;
    private final boolean seamBottom;
    private final boolean seamTop;

    private QuaderPattern(String id, boolean seamLeft, boolean seamRight, boolean seamBottom, boolean seamTop) {
        this.id = id;
        this.seamLeft = seamLeft;
        this.seamRight = seamRight;
        this.seamBottom = seamBottom;
        this.seamTop = seamTop;
    }

    public String method_15434() {
        return this.id;
    }

    public boolean seamLeft() {
        return this.seamLeft;
    }

    public boolean seamRight() {
        return this.seamRight;
    }

    public boolean seamBottom() {
        return this.seamBottom;
    }

    public boolean seamTop() {
        return this.seamTop;
    }
}

