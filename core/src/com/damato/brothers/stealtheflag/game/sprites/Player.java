package com.damato.brothers.stealtheflag.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.screens.GameScreen;

public class Player extends Sprite {
    //for animation
    public enum  State {  STANDING, JUMPING,FALLING, WALKING, SHOOTING, DEAD };

    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private float stateTimer;
    private float jumpTime;
    private boolean isDead;
    private boolean destroyed;
    private boolean isHit;
    private boolean shot;

    private Vector2 position;

    private boolean walkRight;
    private boolean walkLeft;
    private Array<FireBall> fireBalls;

    public Player(GameScreen gameScreen){
        world = gameScreen.getWorld();
        walkLeft = false;
        walkRight = false;
        stateTimer = 0;
        jumpTime = 0;
        isDead = false;
        destroyed = false;
        //isHit e shot talvez não sejam utilizados (BR-F)
        isHit = false;
        shot = false;
        position = new Vector2(64,96);

        fireBalls = new Array<FireBall>();

        definePlayer();
        setBounds(0,0,60/GameMain.PPM, 60/GameMain.PPM);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x- getWidth() / 2,
                b2body.getPosition().y- getHeight() / 2);
        getRegion(dt);

        for (FireBall fireBall: fireBalls){
            fireBall.update(dt);
        }
    }
    public void dispose(){

    }
    public void jump(float dt){
        jumpTime +=dt;
        if ( currentState != State.JUMPING && currentState != State.FALLING) {
            b2body.applyLinearImpulse(new Vector2(0, 6.5f), b2body.getWorldCenter(), true);
            jumpTime = 0;
            // currentState = State.JUMPING;
        }
        if (currentState == State.JUMPING && jumpTime >=0.3f){
            b2body.applyLinearImpulse(new Vector2(0, 0.5f), b2body.getWorldCenter(), true);
            jumpTime = 0;
        }
    }
    public void fire(){
        fireBalls.add(new FireBall(this));
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x/ GameMain.PPM,position.y/ GameMain.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fixdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(28/ GameMain.PPM,28/ GameMain.PPM);
        fixdef.filter.categoryBits = GameMain.PLAYER_BIT;
        fixdef.filter.maskBits = GameMain.GROUND_BIT | GameMain.WALL_BIT;
        fixdef.shape = shape;

        b2body.createFixture(fixdef).setUserData(this);

    }
    public TextureRegion getRegion(float dt){
        //used state here
        //invertendo textura
       /* if (walkLeft && !region.isFlipX()){
            region.flip(true,false);
        }else if (walkRight && region.isFlipX()){
            region.flip(true,false);
        }*/

        /*se statetimet = current timer e for igual a previousState
        faça stateTimer + dt, caso não todos ficam iguais a zero*/
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        currentState = getState();
        return null;
    }
    public State getState() {

        if (isDead) {
            return State.DEAD;
        } else if (shot) {
            return State.SHOOTING;
        } else if ((b2body.getLinearVelocity().y > 0)) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0 ) {
            return State.FALLING;
        } else if (walkLeft || walkRight) {
            return State.WALKING;
        } else {
            return State.STANDING;
        }
    }

    public boolean isDead(){
        return  isDead;
    }
    public World getPlayerWorld(){
        return world;
    }
    public boolean getDirectionR(){
        //retorna verdadeiro se anda para direita e falso se anda  para esquerda
        return walkRight;
    }

    public float getStateTimer(){
        return  stateTimer;
    }
    public Array<FireBall> getFireBalls(){
        return fireBalls;
    }
    public void setToDestroyed(boolean destroyed){
        destroyed = this.destroyed;
    }
    public void setWalkDirection(boolean walkR,boolean walkL){
        walkRight = walkR;
        walkLeft = walkL;
    }
    public void hitInBody(){
        //attached
    }


}
