package com.damato.brothers.stealtheflag.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.screens.GameScreen;

public class FireBall extends Sprite {
    public World world;
    public Body b2body;

    private float stateTimer;
    private boolean destroy;
    private boolean destroyed;
    private boolean isHit;
    private boolean shot;

    private Vector2 position;

    public FireBall(Player player){
        world = player.getPlayerWorld();
        stateTimer = 0;
        destroy = false;
        destroyed = false;
        //isHit e shot talvez não sejam utilizados (BR-F)
        isHit = false;
        shot = false;
        position = new Vector2(64,96);

        defineFireBall();
        setBounds(0,0,8/ GameMain.PPM, 8/GameMain.PPM);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x- getWidth() / 2,b2body.getPosition().y- getHeight() / 2);
        getRegion(dt);
    }
    public void dispose(){

    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x/ GameMain.PPM,position.y/ GameMain.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fixdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(28/ GameMain.PPM,28/ GameMain.PPM);
        fixdef.filter.categoryBits = GameMain.PLAYER_BIT;
        fixdef.filter.maskBits = GameMain.GROUND_BIT;
        fixdef.shape = shape;

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
    public void hitInBody(){
        //attached
    }
    public float getStateTimer(){
        return  stateTimer;
    }
    public void setToDestroyed(boolean destroyed){
        destroyed = this.destroyed;
    }
}
