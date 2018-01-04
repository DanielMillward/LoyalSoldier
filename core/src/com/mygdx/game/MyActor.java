package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;


/**
 * Created by Daniel on 12/17/2017.
 */

public class MyActor extends Actor {
    static BodyDef bodyDef;
    static FixtureDef fixtureDef;
    static Body body;
    static PolygonShape groundBox;

    public MyActor() {

    }

    public static void makeBody(World world, Camera camera) {

        bodyDef = new BodyDef();
        bodyDef.type = DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(100, 300);
// Create our body in the world using our body definition
        body = world.createBody(bodyDef);
        // Create a fixture definition to apply our shape to
        fixtureDef = new FixtureDef();
        groundBox = new PolygonShape();
        fixtureDef.shape = groundBox;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
// Create a polygon shape
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(30.0f, 50.0f);
// Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Texture texture = new Texture(Gdx.files.internal("Player.png"));
        batch.draw(texture,body.getPosition().x, body.getPosition().y);
        setSize(5, 10);
        setBounds(800,480,5,10);
    }
}
