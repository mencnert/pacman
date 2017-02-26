package pacman;

import pacman.framework.Animation;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private Pacman pacman;
	private Image image, currentPacman;
	private Graphics second;
	private URL base;
	private Animation animRight, animUp, animLeft, animDown;
	private int animSpeed = 10;

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Pacman");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups

		animRight = new Animation();
		animUp = new Animation();
		animLeft = new Animation();
		animDown = new Animation();
		// load images for right direction
		animRight.addFrame(getImage(base, "data/pstop.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/pright1.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/pright2.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/pright3.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/pright4.png"), animSpeed);

		// load images for left direction
		animLeft.addFrame(getImage(base, "data/pstop.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/pleft1.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/pleft2.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/pleft3.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/pleft4.png"), animSpeed);

		// load images for up direction
		animUp.addFrame(getImage(base, "data/pstop.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/pup1.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/pup2.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/pup3.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/pup4.png"), animSpeed);

		// load images for down direction
		animDown.addFrame(getImage(base, "data/pstop.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/pdown1.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/pdown2.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/pdown3.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/pdown4.png"), animSpeed);

		currentPacman = animRight.getImage();

	}

	@Override
	public void start() {
		pacman = new Pacman();

		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			pacman.update();
			switch (pacman.getDirection()) {
			case 1:
				currentPacman = animUp.getImage();
				break;
			case 2:
				currentPacman = animDown.getImage();
				break;
			case 3:
				currentPacman = animLeft.getImage();
				break;
			case 4:
				currentPacman = animRight.getImage();
				break;
			}
			animate();
			
			
			repaint();

			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void animate() {
		animRight.update(2);
		animLeft.update(2);
		animUp.update(2);
		animDown.update(2);
		if(pacman.getSpeedX()==0 && pacman.getSpeedY()==0){
			currentPacman = animRight.getImage(0);
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {

		g.drawImage(currentPacman, pacman.getCenterX() - 61,
				pacman.getCenterY() - 63, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			pacman.stop();
			pacman.setDirection(1);
			pacman.moveUp();
			break;

		case KeyEvent.VK_DOWN:
			pacman.stop();
			pacman.setDirection(2);
			pacman.moveDown();
			break;

		case KeyEvent.VK_LEFT:
			pacman.stop();
			pacman.setDirection(3);
			pacman.moveLeft();
			break;

		case KeyEvent.VK_RIGHT:
			pacman.stop();
			pacman.setDirection(4);
			pacman.moveRight();
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			// pacman.stop();
			break;

		case KeyEvent.VK_DOWN:
			// pacman.stop();
			break;

		case KeyEvent.VK_LEFT:
			// pacman.stop();
			break;

		case KeyEvent.VK_RIGHT:
			// pacman.stop();
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}