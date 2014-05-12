package org.bakkes.game;

import org.bakkes.game.map.LayerBasedMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World {
	
	private TiledMap map;
	private LayerBasedMap layerMap;

	public World() {
		try {
			map = new TiledMap("res/map/map.tmx");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		layerMap = new LayerBasedMap(map, map.getLayerIndex("objects"));
	}
	
	public TiledMap getMap() {
		return map;
	}

	public LayerBasedMap getLayerMap() {
		return layerMap;
	}
	
	private static World instance;
	
	public static World getWorld() {
		if(instance == null)
			instance = new World();
		return instance;
	}
}
