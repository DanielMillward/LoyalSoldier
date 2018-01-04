package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.mygdx.game.MyGdxGame.world;

/**
 * Created by Daniel Millward on 12/22/2017.
 */

public class bullet extends Actor{
static Body body;
static FixtureDef fixtureDef;
static PolygonShape groundBox;
    Sprite bulletThing;

        static BodyDef bodyDef = new BodyDef();

// Set our body's starting position in the world

// Create our body in the world using our body definition

        // Create a fixture definition to apply our shape to



    bullet(){
        body = world.createBody(bodyDef);
        bodyDef.position.set(MyActor.body.getPosition().x, MyActor.body.getPosition().y);
        fixtureDef = new FixtureDef();
        groundBox = new PolygonShape();
        fixtureDef.shape = groundBox;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.1f; // Make it bounce a little bit
// Create a polygon shape
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(1f, 1f);
// Create our fixture and attach it to the body
        body.createFixture(fixtureDef);

    }


public void update() {
        bulletThing.setPosition(bullet.body.getPosition().x, bullet.body.getPosition().y);
    bodyDef.type = DynamicBody;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (MyGdxGame.shootButton.isPressed()) {
          //  bullet.makeBullet(world, MyGdxGame.direction);
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();
            bulletThing = new Sprite(new Texture(pixmap));
            //bulletThing.setBounds(body.getPosition().x - bulletThing.getWidth()/2, body.getPosition().y - bulletThing.getHeight()/2, 1, 1);
            bulletThing.setBounds(bullet.body.getPosition().x - bulletThing.getWidth()/2, bullet.body.getPosition().y - bulletThing.getHeight()/2, 4f, 4f);
            bulletThing.setRotation(MathUtils.radiansToDegrees * bullet.body.getAngle());
            bulletThing.setOriginCenter();
            bulletThing.draw(batch);
            body.applyLinearImpulse(MyGdxGame.direction * 1f,0,body.getWorldCenter().x, body.getWorldCenter().y, true);
        }

    }


}
