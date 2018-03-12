package qq.game.object;

import java.awt.Graphics;

import qq.game.frame.RaidenFrame;
import qq.game.values.Direction;

public abstract class Bullet extends GameObject{

	public Bullet(int x, int y, int width, int height,Direction d) {
		super(x, y, width, height,d);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(showImage, x-this.width/2, y, null);
		
	}
	
	@Override
	public void checkBound() {
		// TODO Auto-generated method stub
		if(this.x < 0||this.y < 0 ||this.x > RaidenFrame.WINDOW_WIDTH ||this.y > RaidenFrame.WINDOW_HEIGHT){
			this.die();
		}
	}
	
	public void move(){
		switch(this.d){
		case U:this.y -= speed;break;
		case D:this.y += speed;break;
		case L:this.x -= speed;break;
		case R:this.x += speed;break;
		case LU:this.x -= speed;this.y -= speed;break;
		case LD:this.x -= speed;this.y += speed;break;
		case LUU:this.x -= 0.1*speed;this.y -= speed;break;
		case RU:this.x += speed;this.y -= speed;break;
		case RUU:this.x += 0.1*speed;this.y -= speed;break;		
		case RD:this.x += speed;this.y += speed;break;
		}
		checkBound();
	}

}
