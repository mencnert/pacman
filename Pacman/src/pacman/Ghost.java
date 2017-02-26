package pacman;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Ghost {
	private int centerX;
	private int centerY;
	private int SPEED=3;
	private int speedX;
	private int speedY;
	private int direction;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	private Rectangle rect = new Rectangle(0, 0, 0, 0);

	public Ghost(int x, int y) {
		centerX = x;
		centerY = y;
		speedX = 0;
		speedY = 0;
		direction = 0;
		rect.setRect(x, y, 30, 30);
	}

	public void update(int pacmanX, int pacmanY) {
			centerX += speedX;
			centerY += speedY;
			if (centerY + speedY >= 451) {
				centerY = 450;
				chooseDirection(pacmanX, pacmanY);

			} else if (centerY + speedY <= -1) {
				centerY = 0;
				chooseDirection(pacmanX, pacmanY);

			} else if (centerX + speedX <= -1) {
				centerX = 0;
				chooseDirection(pacmanX, pacmanY);

			} else if (centerX + speedX >= 691) {
				centerX = 690;
				chooseDirection(pacmanX, pacmanY);
			}
			
			updateRect();
	}

	public void blockCollision(ArrayList<Block> blocks, int pacmanX, int pacmanY) {
		for (int i = 0; i < blocks.size(); i++) {
			Block b = (Block) blocks.get(i);
			if (rect.intersects(b.getRect())) {
				chooseDirectionAfterBlockCollision(pacmanX, pacmanY);
				
			}

		}
	}

	

	public void ghostCollision(ArrayList<Ghost> ghosts, ArrayList<Block> blocks, int j, int pacmanX,
			int pacmanY) {
		for (int i = 0; i < ghosts.size(); i++) {
			Ghost gh = (Ghost) ghosts.get(i);
			if (i != j) {
				if (rect.intersects(gh.rect)) {
					setXYAfterGhostCollision();
					chooseDirection(pacmanX, pacmanY);
				}
			}
		}
		
		blockCollision(blocks, pacmanX, pacmanY);
	}

	public void setXYAfterGhostCollision() {
		switch (getDirection()) {
		case 1:
			centerY += 3;
			break;
		case 2:
			centerY -= 3;
			break;
		case 3:
			centerX += 3;
			break;
		case 4:
			centerX -= 3;
			break;
		}
		updateRect();
	}

	public void chooseDirection(int pacmanX, int pacmanY) {
		stop();
		switch (getDirection()) {
		case 1:
		case 2:
			if (pacmanX < centerX) {
				setDirection(3);
				moveLeft();
			} else {
				setDirection(4);
				moveRight();
			}
			break;
		case 3:
		case 4:
			if (pacmanY < centerY) {
				setDirection(1);
				moveUp();
			} else {
				setDirection(2);
				moveDown();
			}
			break;
		}
	}

	public void chooseDirectionAfterBlockCollision(int pacmanX, int pacmanY) {

		switch (getDirection()) {
		case 1:
			centerY += 3;
			break;
		case 2:
			centerY -= 3;
			break;
		case 3:
			centerX += 3;
			break;
		case 4:
			centerX -= 3;
			break;
		}
		updateRect();
		chooseDirection(pacmanX, pacmanY);
	}

	public void chooseStartingDirection(int pacmanX, int pacmanY) {
		if (pacmanY == centerY) {// up
			setDirection(1);
		} else if (pacmanX >= centerX) {// right
			setDirection(4);
		} else if (pacmanX < centerX) {// left
			setDirection(3);
		}
	}

	public void moveRight() {
		speedX = SPEED;
	}

	public void moveLeft() {
		speedX = -1 * SPEED;
	}

	public void moveUp() {
		speedY = -1 * SPEED;
	}

	public void moveDown() {
		speedY = SPEED;
	}

	public void stop() {
		speedX = 0;
		speedY = 0;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void updateRect(){
		rect.setRect(centerX, centerY, 30, 30);
	}

}