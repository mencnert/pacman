package pacman;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Ghost {
	private int centerX;
	private int centerY;
	private int SPEED;
	private int speedX;
	private int speedY;
	private int direction;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	private Rectangle rect = new Rectangle(0, 0, 0, 0);
	private Rectangle r = new Rectangle();
	private boolean blockStatus = false;

	public Ghost(int x, int y) {
		centerX = x;
		centerY = y;
		SPEED = 3;
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
		rect.setRect(centerX, centerY, 30, 30);
	}

	public void blockCollision(ArrayList<Block> blocks, int pacmanX, int pacmanY) {
		for (int i = 0; i < blocks.size(); i++) {
			Block b = (Block) blocks.get(i);
			r = b.getRect();
			if (rect.intersects(r)) {
				this.stop();
				chooseDirectionAfterBlockCollision(pacmanX, pacmanY);
			}

		}
	}

	public void moveBack(Ghost gh) {
		if (getDirection() == gh.getDirection()) {
			switch (getDirection()) {
			case 1:
				if (getCenterY() > gh.getCenterY()) {
					setCenterY(getCenterY() + 6);
					
				} else {
					gh.setCenterY(getCenterY() + 6);
				}
				break;
			case 2:
				if (getCenterY() < gh.getCenterY()) {
					setCenterY(getCenterY() - 6);
			
				} else {
					gh.setCenterY(getCenterY() - 6);
				}
				break;
			case 3:
				if (getCenterX() > gh.getCenterX()) {
					setCenterX(getCenterX() + 6);
				} else {
					gh.setCenterX(getCenterX() + 6);
				}
				break;
			case 4:
				if (getCenterX() < gh.getCenterX()) {
					setCenterX(getCenterX() + 6);
				} else {
					gh.setCenterX(getCenterX() + 6);
				}
				break;
			}
		}
	}

	public void ghostCollision(ArrayList<Ghost> ghosts, int j, int pacmanX,
			int pacmanY) {
		for (int i = 0; i < ghosts.size(); i++) {
			Ghost gh = (Ghost) ghosts.get(i);
			if (i != j) {
				if (rect.intersects(gh.rect)) {
					setXYAfterGhostCollision(gh);
					System.out.println(centerX + " " + centerY + " | "
							+ gh.getCenterX() + " " + gh.getCenterY());
					chooseDirection(pacmanX, pacmanY);

				}
			}

		}
	}

	public void setXYAfterGhostCollision(Ghost gh) {
		// stop();
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
		// gh.stop();
		/*
		 * switch (gh.getDirection()) { case 1:
		 * gh.setCenterY(gh.getCenterY()+3); break; case 2:
		 * gh.setCenterY(gh.getCenterY()-3); break; case 3:
		 * gh.setCenterX(gh.getCenterX()+3); break; case 4:
		 * gh.setCenterX(gh.getCenterX()-3); break; }
		 */
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
		chooseDirection(pacmanX, pacmanY);
		/*
		 * switch (getDirection()) { case 1: case 2: if (pacmanX < centerX) {
		 * setDirection(3); moveLeft(); } else { setDirection(4); moveRight(); }
		 * break; case 3: case 4: if (pacmanY < centerY) { setDirection(1);
		 * moveUp(); } else { setDirection(2); moveDown(); } break; }
		 */
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

	public void isStoped() {
		if (speedX == 0 && speedY == 0) {
			blockStatus = true;
		} else {
			blockStatus = false;
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

	public boolean getBlockStatus() {
		return blockStatus;
	}
	
	public void setBlockStatus(boolean status) {
		this.blockStatus = status;
	}

}