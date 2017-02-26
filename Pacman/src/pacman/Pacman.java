package pacman;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Pacman {
	private int centerX = 0;
	private int centerY = 0;
	private int startingCenterX, startingCenterY;
	private int SPEED = 3;
	private int speedX = 0;
	private int speedY = 0;
	private int direction = 0;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	private int changeDirection = 0;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	public boolean wait = false;
	private Rectangle rect = new Rectangle(0, 0, 0, 0);
	private boolean canUp, canLeft, canRight, canDown;

	public Pacman(int x, int y) {
		centerX = x;
		centerY = y;
		startingCenterX = x;
		startingCenterY = y;
		speedX = 0;
		speedY = 0;
		direction = 0;
		updateRect();// for canUp,canLeft,... in StaClass.loadMap()
		canUp = true;
		canDown = true;
		canRight = true;
		canLeft = true;
	}

	public void update() {
		centerX += speedX;
		centerY += speedY;

		if (centerY + speedY >= 451) {
			centerY = 450;
			this.stop();
		}

		if (centerY + speedY <= -1) {
			centerY = 0;
			this.stop();
		}

		if (centerX + speedX <= -1) {
			centerX = 0;
			this.stop();
		}

		if (centerX + speedX >= 691) {
			centerX = 690;
			this.stop();
		}

		updateRect();
	}

	public void changeDirection(ArrayList<Block> blocks) {
		if (this.getDirection() != this.getChangeDirection()) {
			if (this.getCenterX() % 30 == 0 && this.getCenterY() % 30 == 0) {
				// if pacman wanna turn and at his way is block
				// he have to wait and continue in direction
				// until his way is clear for change direction
				// which he want
				switch (this.getChangeDirection()) {
				case 1:// up
					wait = false;
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if ((getCenterX() == b.getX() && getCenterY() - 30 == b.getY()) || getCenterY() == 0) {
							wait = true;
						}
					}

					if (!wait) {
						this.stop();
						this.setDirection(1);
						this.moveUp();
					}
					break;
				case 2:// down
					wait = false;
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if ((getCenterX() == b.getX() && getCenterY() + 30 == b.getY()) || getCenterY() == 450) {
							wait = true;
						}
					}

					if (!wait) {
						this.stop();
						this.setDirection(2);
						this.moveDown();
					}
					break;
				case 3:// left
					wait = false;
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if ((getCenterX() - 30 == b.getX() && getCenterY() == b.getY()) || getCenterX() == 0) {
							wait = true;
						}
					}

					if (!wait) {
						this.stop();
						this.setDirection(3);
						this.moveLeft();
					}
					break;
				case 4:// right
					wait = false;
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if ((getCenterX() + 30 == b.getX() && getCenterY() == b.getY()) || getCenterX() == 690) {
							wait = true;
						}
					}

					if (!wait) {
						this.stop();
						this.setDirection(4);
						this.moveRight();
					}
					break;
				}// end switch
			} // end if for x%30 &y%30
		}
	}

	public void blockCollision(ArrayList<Block> blocks) {
		switch (getDirection()) {
		case 1:// up
			for (int i = 0; i < blocks.size(); i++) {
				Block b = (Block) blocks.get(i);
				if (getCenterX() == b.getX() && getCenterY() - 30 == b.getY()) {
					this.stop();
				}
			}
			break;
		case 2:// down
			for (int i = 0; i < blocks.size(); i++) {
				Block b = (Block) blocks.get(i);
				if (getCenterX() == b.getX() && getCenterY() + 30 == b.getY()) {
					this.stop();
				}
			}
			break;
		case 3:// left
			for (int i = 0; i < blocks.size(); i++) {
				Block b = (Block) blocks.get(i);
				if (getCenterX() - 30 == b.getX() && getCenterY() == b.getY()) {
					this.stop();
				}
			}
			break;
		case 4:// right
			for (int i = 0; i < blocks.size(); i++) {
				Block b = (Block) blocks.get(i);
				if (getCenterX() + 30 == b.getX() && getCenterY() == b.getY()) {
					this.stop();
				}
			}
			break;
		}// end switch
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

	public int getChangeDirection() {
		return changeDirection;
	}

	public void setChangeDirection(int changeDirection) {
		this.changeDirection = changeDirection;
	}

	public void updateRect() {
		rect.setRect(centerX + 9, centerY + 9, 12, 12);// (30-12)/2=9
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setPacmanToStartingPosition() {
		centerX = startingCenterX;
		centerY = startingCenterY;
		stop();
		setDirection(0);
		setChangeDirection(0);
	}

	public boolean getCanUp() {
		return canUp;
	}

	public boolean getCanLeft() {
		return canLeft;
	}

	public boolean getCanRight() {
		return canRight;
	}

	public boolean getCanDown() {
		return canDown;
	}

	public void setCanUp(boolean canUp) {
		this.canUp = canUp;
	}

	public void setCanLeft(boolean canLeft) {
		this.canLeft = canLeft;
	}

	public void setCanRight(boolean canRight) {
		this.canRight = canRight;
	}

	public void setCanDown(boolean canDown) {
		this.canDown = canDown;
	}
}