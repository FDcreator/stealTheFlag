package com.damato.brothers.stealtheflag.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.screens.GameScreen;

public class Flag extends Sprite {

    public World world;
    public Body b2body;

    private float stateTimer;
    private boolean destroy;
    private boolean setToDestroy;
    private boolean isHit;
    private boolean shot;
    private boolean hability;
    private String color;
    public Flag(GameScreen gameScreen, float x, float y, String color){
        world = gameScreen.getWorld();
        this.color = color;
        stateTimer = 0;
        destroy = false;
        setToDestroy = false;
        //isHit e shot talvez não sejam utilizados (BR-F)
        isHit = false;
        shot = false;

        defineFlag(x,y);
        setBounds(0,0,64/ GameMain.PPM, 64/GameMain.PPM);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x- getWidth() / 2,b2body.getPosition().y- getHeight() / 2);
        getRegion(dt);
        if (setToDestroy && !destroy){
            destroy = true;
            world.destroyBody(b2body);
        }
    }
    public void dispose(){

    }

    public void defineFlag(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/ GameMain.PPM,y/ GameMain.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fixdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / GameMain.PPM, 32 / GameMain.PPM);
        fixdef.filter.categoryBits = GameMain.FLAG_BIT;
        fixdef.filter.maskBits = GameMain.GROUND_BIT | GameMain.PLAYER_BIT | GameMain.WALL_BIT;
        fixdef.shape = shape;
        fixdef.isSensor = true;
        b2body.setGravityScale(0);
        b2body.createFixture(fixdef).setUserData(this);

    }
    public TextureRegion getRegion(float dt){
        //used state here
        //invertendo textura
       /* if (b2body.getLinearVelocity().x < 0 && !region.isFlipX()){
            region.flip(true,false);
        }else if (region.isFlipX()){
            region.flip(true,false);
        }*/
        /*se statetimet = current timer e for igual a previousState
        faça stateTimer + dt, caso não todos ficam iguais a zero*/
        return null;
    }

    public boolean isDestroy(){
        return destroy;
    }
    public float getStateTimer(){
        return  stateTimer;
    }
    public void setToDestroyed(boolean destroyed){
        setToDestroy = destroyed;
    }
    public String getFlagColor(){
        return color;
    }
}
