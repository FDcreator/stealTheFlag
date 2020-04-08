package com.damato.brothers.stealtheflag.game.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.damato.brothers.stealtheflag.game.GameMain;

public class WorldMapManagement {
        private TmxMapLoader maploader;
        private TiledMap tileMap;
        private int level;
        public WorldMapManagement(int map, GameMain gameMain){
            this.level = map;
            maploader = new TmxMapLoader();
            switch (map){
                case 1:
                    tileMap = maploader.load("maps/map01.tmx");
                    break;
               /* case 2:
                    map = maploader.load("covid_instance_map/map02.tmx");
                    break;
                case 3:
                    map = maploader.load("covid_instance_map/map03.tmx");
                    break;
                case 4:
                    break;*/
            }
        }
        public TiledMap getWorldMap(){
            return tileMap;
        }
    }
