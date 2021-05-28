package com.test.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class Ship {

    //характеристики судна
//	скорость движения
    float movementSpeed;  //единицы измерения 
//    щит
    int shield;

    //положение и размер
    float xPosition, yPosition; //lower-left corner
    float width, height;

    //graphics
    TextureRegion shipTexture, shieldTexture;
//конструктор получает данные инцилиацию папраметров каробля и сылки наклас корабль и щит 
    public Ship(float movementSpeed, int shield,
                float width, float height,
                float xCentre, float yCentre,
                TextureRegion shipTexture, TextureRegion shieldTexture) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
    }
//рисуем получаем ссылку длЯ метода 
    public void draw(Batch batch) {
        batch.draw(shipTexture, xPosition, yPosition, width, height);
//        если щит меньше нуля то защита то ресуем снова 
        if (shield > 0) {
            batch.draw(shieldTexture, xPosition, yPosition, width, height);
        }
    }
}
