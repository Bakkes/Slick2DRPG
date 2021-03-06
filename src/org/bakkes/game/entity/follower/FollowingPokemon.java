package org.bakkes.game.entity.follower;

import org.bakkes.game.entity.Direction;
import org.bakkes.game.entity.Entity;
import org.bakkes.game.entity.NPC;
import org.bakkes.game.entity.Player;
import org.bakkes.game.entity.follower.state.StateMachine;
import org.bakkes.game.math.GridGraphicTranslator;
import org.bakkes.game.math.Vector2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class FollowingPokemon extends NPC implements IFollower {
	/*
	 * Public properties used for states
	 */
	public int stepsTaken = 0;
	public boolean isHealthy = true;
	
	private Player parent;
	private static SpriteSheet _spriteSheet;
	private Animation[] _animation;
	private int facing;
	private StateMachine stateMachine;
	
	public FollowingPokemon(Player parent) {
		this.parent = parent;
		this.position = GridGraphicTranslator.GridToPixels(GridGraphicTranslator
							.PixelsToGrid(parent.getPosition()).minusOperator(
							new Vector2(0f, 2f)));
		this.facing = Direction.SOUTH;
		stateMachine = new StateMachine(this);
	}
	
	@Override
	public void init(GameContainer gc) {
		if(_spriteSheet == null) {
			try {
				_spriteSheet = new SpriteSheet("res/sprites/followers.png", 32, 32, new Color(160, 176, 128));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		_animation = new Animation[4];
		_animation[Direction.NORTH] = new Animation(_spriteSheet, 4, 0, 7, 0, true, 150, true);
		_animation[Direction.EAST]  = new Animation(_spriteSheet, 4, 3, 5, 3, true, 150, true);
		_animation[Direction.SOUTH] = new Animation(_spriteSheet, 4, 1, 7, 1, true, 150, true);
		_animation[Direction.WEST]  = new Animation(_spriteSheet, 4, 2, 7, 2, true, 150, true);
		
		for(int i = 0; i <= _animation.length - 1; i++) {
			_animation[i].setPingPong(true);
			_animation[i].setCurrentFrame(0);
		}
		_animation[facing].setAutoUpdate(true);
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		if(isHealthy) {
			//g.drawString("Rendering " + facing + " - " + position.getX() + ", " + position.getY(), 10, 60);
			_animation[facing].draw(position.getX(), position.getY());
		}
	}

	public void face(int direction) {
		this.facing = direction;
	}
	
	private static final float DISTANCE = 32f;
	
	public void update(int delta) {
		stateMachine.update();
		if(parent.isCurrentlyMoving()) {
			float xDiff = facing == Direction.WEST ? -DISTANCE : facing == Direction.EAST ? DISTANCE : 0;
			float yDiff = facing == Direction.NORTH ? -DISTANCE : facing == Direction.SOUTH ? DISTANCE : 0;
			this.position = parent.getPosition().minusOperator(new Vector2(xDiff, yDiff));
		} else {
			_animation[facing].setCurrentFrame(0);
		}
	}
	
	public StateMachine getStateMachine() {
		return stateMachine;
	}
	
	public Player getParent() {
		return parent;
	}
}
