package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

public class PickaxeModel {

    private MineralType type;

    private int damage;

    public PickaxeModel(MineralType type) {
        this.type = type;
        /*switch (type) {
            case DIAMOND: damage = 3; break;
            case GOLD: damage = 2; break;
            default: damage = 1; break;
        }*/
    }

    public int getDamage() {
        return damage;
    }

    public MineralType getType() {
        return type;
    }

    private void updateDamage() {
        /*switch (type) {
            case DIAMOND: damage = 3; break;
            case GOLD: damage = 2; break;
            default: damage = 1; break;
        }*/
    }

    public void setType(MineralType type) {
        this.type = type;
        updateDamage();
    }
}
