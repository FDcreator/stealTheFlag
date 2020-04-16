package com.damato.brothers.stealtheflag.game.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.screens.GameScreen;
import com.damato.brothers.stealtheflag.game.sprites.Flag;

public class B2dWorldCreator {

    private Array<Flag> flags;
    public B2dWorldCreator(GameScreen gameScreen){
        World world = gameScreen.getWorld();
        TiledMap map = gameScreen.getMap();
        //colisores
        //configurações do corpo
        BodyDef bdef = new BodyDef();
        //verificar a disposição dos conteiners
        PolygonShape polyshap = new PolygonShape();
        //configurações dos objetos montados ouu imóveis
        FixtureDef fixdef = new FixtureDef();
        //corpo à ser adicionado ao mundo
        Body body;

        flags = new Array<Flag>();
        //para todos object no layer (chão) que tenha tipo de retângulo faça
        for (MapObject object : map.getLayers().get("grounds").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            //tipo de corpo estático
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth()/2)/GameMain.PPM,
                    (rectangle.getY() + rectangle.getHeight()/2)/GameMain.PPM);

            //adicionando objeto ao mundo
            body = world.createBody(bdef);
            polyshap.setAsBox((rectangle.getWidth()/2)/GameMain.PPM, (rectangle.getHeight()/2)/GameMain.PPM);
            fixdef.shape = polyshap;
            fixdef.filter.categoryBits = GameMain.GROUND_BIT;
            body.createFixture(fixdef);
        }
        for (MapObject object : map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            //tipo de corpo estático
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth()/2)/GameMain.PPM,
                    (rectangle.getY() + rectangle.getHeight()/2)/GameMain.PPM);

            //adicionando objeto ao mundo
            body = world.createBody(bdef);
            polyshap.setAsBox((rectangle.getWidth()/2)/GameMain.PPM, (rectangle.getHeight()/2)/GameMain.PPM);
            fixdef.shape = polyshap;
            fixdef.filter.categoryBits = GameMain.WALL_BIT;
            body.createFixture(fixdef);
        }
        for (MapObject object : map.getLayers().get("flags").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            flags.add(new Flag(gameScreen,
                    (rectangle.getX() + rectangle.getWidth())/2,
                    (rectangle.getY() + rectangle.getHeight()/2),
                    object.getName()));
        }
    }

    public Array<Flag> getFlags(){
        return flags;
    }
}
