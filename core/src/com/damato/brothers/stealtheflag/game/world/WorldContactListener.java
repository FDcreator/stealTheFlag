package com.damato.brothers.stealtheflag.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.sprites.FireBall;
import com.damato.brothers.stealtheflag.game.sprites.Player;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case GameMain.FIREBALL_BIT | GameMain.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == GameMain.FIREBALL_BIT) {
                    ((FireBall) fixA.getUserData()).setToDestroyed(true);
                }else{
                    ((FireBall)fixB.getUserData()).setToDestroyed(true);
                }
                break;
            case GameMain.FIREBALL_BIT | GameMain.WALL_BIT:
                if(fixA.getFilterData().categoryBits == GameMain.FIREBALL_BIT) {
                    ((FireBall) fixA.getUserData()).setToDestroyed(true);
                }else{
                    ((FireBall)fixB.getUserData()).setToDestroyed(true);
                }
                break;

            case GameMain.FIREBALL_BIT | GameMain.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == GameMain.FIREBALL_BIT) {
                    FireBall fireBall = ((FireBall) fixA.getUserData());
                    fireBall.setToDestroyed(true);
                    ((Player) fixB.getUserData()).hitInBody(fireBall.getPlayer().getDamage());
                    System.out.println("damage: " + fireBall.getPlayer().getDamage());
                }else{
                	FireBall fireBall = ((FireBall)fixB.getUserData());
                    fireBall.setToDestroyed(true);
                    ((Player) fixA.getUserData()).hitInBody(fireBall.getPlayer().getDamage());
                    System.out.println("damage: " + fireBall.getPlayer().getDamage());
                }
                break;
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
