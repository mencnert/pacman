package pacman;

import java.util.ArrayList;

public class Pacman {
	private int centerX = 0;
	private int centerY = 0;
	private int SPEED = 3;
	private int speedX = 0;
	private int speedY = 0;
	private int direction = 0;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	private int changeDirection = 0;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	public boolean wait = false;

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

	}

	public void changeDirection(ArrayList<Block> blocks) {
		if (this.getDirection() != this.getChangeDirection()) {
			if (this.getCenterX() % 30 == 0 && this.getCenterY() % 30 == 0) {
				switch (this.getChangeDirection()) {
				case 1:// up
					wait = false;
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if ((getCenterX() == b.getX() && getCenterY() - 30 == b
								.getY()) || getCenterY() == 0) {
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
						if ((getCenterX() == b.getX() && getCenterY() + 30 == b
								.getY()) || getCenterY() == 450) {
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
						if ((getCenterX() - 30 == b.getX() && getCenterY() == b
								.getY()) || getCenterX() == 0) {
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
						if ((getCenterX() + 30 == b.getX() && getCenterY() == b
								.getY()) || getCenterX() == 690) {
							wait = true;
						}
					}
					if (!wait) {
						this.stop();
						this.setDirection(4);
						this.moveRight();
					}
					break;
				}
			}
		}
	}

	public void blockCollision(ArrayList<Block> blocks){
		if (this.getCenterX() % 30 == 0 && this.getCenterY() % 30 == 0) {
			switch(getDirection()){
				case 1://up
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX() == b.getX() && getCenterY()-30 == b
								.getY()) {
							this.stop();
						}
					}
					break;

				case 2://down
				for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX() == b.getX() && getCenterY()+30 == b
								.getY()) {
							this.stop();
						}
					}
					break;
				case 3://left
				for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX()-30 == b.getX() && getCenterY() == b
								.getY()) {
							this.stop();
						}
					}
					break;
				case 4://right
				for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX()+30 == b.getX() && getCenterY() == b
								.getY()) {
							this.stop();
						}
					}
					break;
			}

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

	public int getChangeDirection() {
		return changeDirection;
	}

	public void setChangeDirection(int changeDirection) {
		this.changeDirection = changeDirection;
	}

}