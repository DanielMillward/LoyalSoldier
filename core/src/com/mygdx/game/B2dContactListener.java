package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Daniel Millward on 12/22/2017.
 */

public class B2dContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture Fa = contact.getFixtureA();
        Fixture Fb = contact.getFixtureB();

        if (Fa.getBody().getType() == BodyDef.BodyType.StaticBody) {
            if (MyGdxGame.upButton.isPressed()) {
                jump(Fa, Fb);
            }

        }
        if (Fb.getBody().getType() == BodyDef.BodyType.StaticBody) {
            if (MyGdxGame.upButton.isPressed()) {
                jump(Fb, Fa);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void jump(Fixture staticFixture, Fixture otherFixture){
        System.out.println("Adding Force");
        otherFixture.getBody().applyLinearImpulse(0, 100, MyActor.body.getPosition().x, MyActor.body.getPosition().y, true);
    }
}
