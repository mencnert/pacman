package pacman;

import java.util.ArrayList;

public class Ghost {
	private int centerX;
	private int centerY;
	private int SPEED;
	private int speedX;
	private int speedY;
	private int direction;// 0-nothing; 1-up; 2-down; 3-left; 4-right
	private boolean change = false;
	private boolean wait;

	public Ghost(int x, int y) {
		centerX = x;
		centerY = y;
		SPEED = 3;
		speedX = 0;
		speedY = 0;
		direction = 0;
		

	}

	public void update(int pacmanX, int pacmanY) {

		centerX += speedX;
		centerY += speedY;

		if (centerY + speedY >= 451) {
			centerY = 450;
			if (pacmanX < centerX) {
				this.stop();
				setDirection(3);
				moveLeft();
			} else {
				this.stop();
				setDirection(4);
				moveRight();
			}
		}

		if (centerY + speedY <= -1) {
			centerY = 0;
			if (pacmanX < centerX) {
				this.stop();
				setDirection(3);
				moveLeft();
			} else {
				this.stop();
				setDirection(4);
				moveRight();
			}

		}

		if (centerX + speedX <= -1) {
			centerX = 0;
			if (pacmanY < centerY) {
				this.stop();
				setDirection(1);
				moveUp();
			} else {
				this.stop();
				setDirection(2);
				moveDown();
			}
		}

		if (centerX + speedX >= 691) {
			centerX = 690;
			if (pacmanY < centerY) {
				this.stop();
				setDirection(1);
				moveUp();
			} else {
				this.stop();
				setDirection(2);
				moveDown();
			}
		}
	}

	public void blockCollision(ArrayList<Block> blocks, int pacmanX, int pacmanY) {
		if (this.getCenterX() % 30 == 0 && this.getCenterY() % 30 == 0) {
			if(!change){
				switch (getDirection()) {
				case 1:// up
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX() == b.getX()
								&& getCenterY() - 30 == b.getY()) {
							this.stop();
							change = true;
						}
					}
					break;

				case 2:// down
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX() == b.getX()
								&& getCenterY() + 30 == b.getY()) {
							this.stop();
							change = true;
						}
					}
					break;
				case 3:// left
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX() - 30 == b.getX()
								&& getCenterY() == b.getY()) {
							this.stop();
							change = true;
						}
					}
					break;
				case 4:// right
					for (int i = 0; i < blocks.size(); i++) {
						Block b = (Block) blocks.get(i);
						if (getCenterX() + 30 == b.getX()
								&& getCenterY() == b.getY()) {
							this.stop();
							change = true;
						}
					}
					break;
				}
			}
			if(wait && getDirection()<3 ){//change axis direction when he is stop() 
				if(centerX>pacmanX){	//and waiting for change pacmans x or y 
					setDirection(3);	//and ghost can move
				}else {
					setDirection(4);
				}
			}else if(wait && getDirection()>2){
				if(centerY > pacmanY){
					setDirection(1);
				}else{
					setDirection(2);
				}
			}

			if (change) {//change direction from up,dowm to left or right and conversely
				
				switch (getDirection()) {
				case 1:// up
				case 2:// down
					if (centerX > pacmanX) {
						for (int i = 0; i < blocks.size(); i++) {
							Block b = (Block) blocks.get(i);
							if ((getCenterX()- 30 == b.getX() && getCenterY()  == b
									.getY())) {
								wait = true;
							}
						}
						if (!wait) {
							setDirection(3);
							moveLeft();
						}
					} else{

						for (int i = 0; i < blocks.size(); i++) {
							Block b = (Block) blocks.get(i);
							if ((getCenterX()+ 30 == b.getX() && getCenterY()  == b
									.getY())) {
								wait = true;
							}
						}
						if (!wait) {
							setDirection(4);
							moveRight();
						}

					}
					break;

				case 3:// left
				case 4:// right
					if (centerY > pacmanY) {
						for (int i = 0; i < blocks.size(); i++) {
							Block b = (Block) blocks.get(i);
							if ((getCenterX() == b.getX() && getCenterY() - 30 == b
									.getY())) {
								wait = true;
							}
						}
						if (!wait) {
							setDirection(1);
							moveUp();
						}
					} else {

						for (int i = 0; i < blocks.size(); i++) {
							Block b = (Block) blocks.get(i);
							if ((getCenterX() == b.getX() && getCenterY() + 30 == b
									.getY())) {
								wait = true;
							}
						}
						if (!wait) {
							setDirection(2);
							moveDown();
						}

					}
					break;
				}
				change=(wait)?true:false;//optimalization for detection collision with block
			}

		}
	}

	public void ghostCollision(ArrayList<Ghost> gh, ArrayList<Ghost> gh2){

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
		wait = false;
	}

}
