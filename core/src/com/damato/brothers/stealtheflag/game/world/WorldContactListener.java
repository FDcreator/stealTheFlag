package com.damato.brothers.stealtheflag.game.world;

import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){

         /*   case GameMain.PLAYER_BIT | GameMain.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == GameMain.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).setHit(true);
                }else{
                    ((Player)fixB.getUserData()).setHit(true);
                }
                break;*/
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){

         /*   case GameMain.PLAYER_BIT | GameMain.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == GameMain.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).setHit(true);
                }else{
                    ((Player)fixB.getUserData()).setHit(true);
                }
                break;*/
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
