package com.test.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

 abstract class Ship {

    //характеристики судна
//	скорость движения
    float movementSpeed;  //единицы измерения 
//    щит
    int shield;

    
//    Прямоугольная ограничивающая рамка;
    Rectangle boundingBox;
    
    
    //laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    //graphics
    TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

//конструктор получает данные инцилиацию папраметров каробля и сылки наклас корабль и щит 
    public Ship(float xCentre, float yCentre,
            float width, float height,
            float movementSpeed, int shield,
            float laserWidth, float laserHeight, float laserMovementSpeed,
            float timeBetweenShots,
            TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
            TextureRegion laserTextureRegion) {
    this.movementSpeed = movementSpeed;
    this.shield = shield;

    this.boundingBox = new Rectangle(xCentre - width / 2, yCentre - height / 2, width, height);

    this.laserWidth = laserWidth;
    this.laserHeight = laserHeight;
    this.laserMovementSpeed = laserMovementSpeed;
    this.timeBetweenShots = timeBetweenShots;
    this.shipTextureRegion = shipTextureRegion;
    this.shieldTextureRegion = shieldTextureRegion;
    this.laserTextureRegion = laserTextureRegion;
}

public void update(float deltaTime) {
    timeSinceLastShot += deltaTime;
}

public boolean canFireLaser() {
    return (timeSinceLastShot - timeBetweenShots >= 0);
}

public abstract Laser[] fireLasers();

public boolean intersects(Rectangle otherRectangle) {
    return boundingBox.overlaps(otherRectangle);
}

public void hit(Laser laser) {
    if (shield > 0) {
        shield--;
    }
}

public void translate(float xChange, float yChange) {
    boundingBox.setPosition(boundingBox.x+xChange, boundingBox.y+yChange);
}

public void draw(Batch batch) {
    batch.draw(shipTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    if (shield > 0) {
        batch.draw(shieldTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
}