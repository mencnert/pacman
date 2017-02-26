package pacman;

public class Pacman {
	private int centerX = 0;
	private int centerY = 0;
	private int SPEED = 3;
	private int speedX = 0;
	private int speedY = 0;
	private int direction = 0;//0-nothing; 1-up; 2-down; 3-left; 4-right

	public void update() {

		centerX += speedX;
		centerY += speedY;

		if (centerY + speedY >= 512) {
			centerY = 511;
			this.stop();
		}
		if (centerY + speedY <= 63) {
			centerY = 64;
			this.stop();
		}
		if (centerX + speedX <= 62) {
			centerX = 63;
			this.stop();
		}
		if (centerX + speedX >= 830) {
			centerX = 829;
			this.stop();
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

}