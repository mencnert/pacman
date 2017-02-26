package pacman;

public class Pacman {
	private int centerX = 0;
	private int centerY = 0;
	private int SPEED = 3;
	private int speedX = 0;
	private int speedY = 0;
	private int direction = 0;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	private int changeDirection = 0;// 0-nothing; 1-up; 2-down; 3-left; 4-right

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

	public void changeDirection() {
		if (this.getDirection() != this.getChangeDirection()) {
			if (this.getCenterX() % 30 == 0 && this.getCenterY() % 30 == 0) {
				switch (this.getChangeDirection()) {
				case 1://up
				if(centerY!=0){
					this.stop();
					this.setDirection(1);
					this.moveUp();
				}
					break;
				case 2://down
				if(centerY!=450){
					this.stop();
					this.setDirection(2);
					this.moveDown();
				}
					break;
				case 3://left
				if(centerX!=0){
					this.stop();
					this.setDirection(3);
					this.moveLeft();
				}
					break;
				case 4://right
				if(centerX!=690){
					this.stop();
					this.setDirection(4);
					this.moveRight();
				}
					break;
				}
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