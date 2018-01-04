package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by David on 12/20/2017.
 */

public class theGround extends Actor {

    static BodyDef groundBodyDef;
    static Body groundBody;
    static PolygonShape groundBox;

    static void makeTheGround(World world, Camera camera) {
        // Create our body definition
        groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
// Set its world position
        groundBodyDef.position.set(new Vector2(0, 10));

        // Create a body from the defintion and add it to the world
        groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(camera.viewportWidth, 10.0f);
// Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Image happy = new Image(texture);
        batch.draw(texture,groundBody.getPosition().x, groundBody.getPosition().y);
        setSize(5, 10);

    }
}
