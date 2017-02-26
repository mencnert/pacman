package pacman;

import pacman.framework.Animation;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private Pacman pacman;
	private Image image, currentPacman, point, ghost, block;
	private Graphics second;
	private URL base;
	private Animation animRight, animUp, animLeft, animDown;
	private int animSpeed = 5, score = 0;
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

	@Override
	public void init() {

		setSize(720, 500);// 24[720px]_16[480px]|
		setBackground(Color.white);
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
		animRight.addFrame(getImage(base, "data/rightstop.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/right1.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/right2.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/right3.png"), animSpeed);
		animRight.addFrame(getImage(base, "data/right4.png"), animSpeed);

		// load images for left direction
		animLeft.addFrame(getImage(base, "data/leftstop.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/left1.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/left2.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/left3.png"), animSpeed);
		animLeft.addFrame(getImage(base, "data/left4.png"), animSpeed);

		// load images for up direction
		animUp.addFrame(getImage(base, "data/upstop.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/up1.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/up2.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/up3.png"), animSpeed);
		animUp.addFrame(getImage(base, "data/up4.png"), animSpeed);

		// load images for down direction
		animDown.addFrame(getImage(base, "data/downstop.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/down1.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/down2.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/down3.png"), animSpeed);
		animDown.addFrame(getImage(base, "data/down4.png"), animSpeed);

		currentPacman = animRight.getImage();

		point = getImage(base, "data/point.png");
		block = getImage(base, "data/block.png");
		ghost = getImage(base, "data/ghost.png");

		try {
			BufferedReader fo = new BufferedReader(new FileReader("maps/1.txt"));
			String a;
			char b;
			for (int i = 0; i < 16; i++) {// i is for axis x
				String mapLine = fo.readLine();
				for (int j = 0; j < 24; j++) {// j is for axis y
					a = mapLine.substring(j, j + 1);
					b = a.charAt(0);
					switch (b) {
					case '.':// point
						addPoint(j * 30, i * 30);
						break;

					case 'B':// block
						addBlock(j * 30, i * 30);

						break;

					case '-':// nothing
						break;

					case 'G':// ghost
						addGhost(j * 30, i * 30);
						addPoint(j * 30, i * 30);
						break;

					case 'P':// pacman
						pacman = new Pacman();
						pacman.setCenterX(j * 30);
						pacman.setCenterY(i * 30);
						break;

					}
				}
			}
			fo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < ghosts.size(); i++) {
			Ghost gh = (Ghost) ghosts.get(i);
			gh.chooseStartingDirection(pacman.getCenterX(), pacman.getCenterY());

		}

	}

	@Override
	public void start() {

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
			for (int i = 0; i < ghosts.size(); i++) {
				Ghost gh = (Ghost) ghosts.get(i);
				//gh.isStoped();
				gh.update(pacman.getCenterX(),// collision
						pacman.getCenterY());// with border
				
				gh.ghostCollision(ghosts, i, pacman.getCenterX(),
						pacman.getCenterY());
				gh.blockCollision(blocks, pacman.getCenterX(),
						pacman.getCenterY());
				
			}

			animate();// set currentPacman and give shut pacman when he stoped
			pacmanPointColision();
			pacmanBlockColision();
			repaint();

			// System.out.println(score);
			// System.out.println(pacman.getCenterX() + " "
			// +pacman.getCenterY());
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void animate() {// set currentPacman and give shut pacman when he
							// stoped
		animRight.update(2);
		animLeft.update(2);
		animUp.update(2);
		animDown.update(2);
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
		if (pacman.getSpeedX() == 0 && pacman.getSpeedY() == 0) {

			switch (pacman.getDirection()) {
			case 1:
				currentPacman = animUp.getImage(0);
				break;
			case 2:
				currentPacman = animDown.getImage(0);
				break;
			case 3:
				currentPacman = animLeft.getImage(0);
				break;
			case 4:
				currentPacman = animRight.getImage(0);
				break;
			}
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

		for (int i = 0; i < points.size(); i++) {
			Point p = (Point) points.get(i);
			g.drawImage(point, p.getX() + 7, p.getY() + 7, this);
		}

		for (int i = 0; i < blocks.size(); i++) {
			Block b = (Block) blocks.get(i);
			g.drawImage(block, b.getX(), b.getY(), this);
		}

		g.drawImage(currentPacman, pacman.getCenterX(), pacman.getCenterY(),
				this);

		for (int i = 0; i < ghosts.size(); i++) {
			Ghost gh = (Ghost) ghosts.get(i);
			g.drawImage(ghost, gh.getCenterX(), gh.getCenterY(), this);
			g.drawRect(gh.getCenterX(), gh.getCenterY(), 30, 30);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (pacman.getDirection() == 0) {// for start game
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:

				pacman.setDirection(1);
				pacman.moveUp();
				break;

			case KeyEvent.VK_DOWN:

				pacman.setDirection(2);
				pacman.moveDown();
				break;

			case KeyEvent.VK_LEFT:

				pacman.setDirection(3);
				pacman.moveLeft();
				break;

			case KeyEvent.VK_RIGHT:

				pacman.setDirection(4);
				pacman.moveRight();
				break;

			}

			for (int i = 0; i < ghosts.size(); i++) {
				Ghost gh = (Ghost) ghosts.get(i);

				switch (gh.getDirection()) {
				case 1:
					gh.moveUp();
					break;
				case 2:
					gh.moveDown();
					break;
				case 3:
					gh.moveLeft();
					break;
				case 4:
					gh.moveRight();
				}

			}

		} else {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				pacman.setChangeDirection(1);
				break;

			case KeyEvent.VK_DOWN:
				pacman.setChangeDirection(2);
				break;

			case KeyEvent.VK_LEFT:
				pacman.setChangeDirection(3);
				break;

			case KeyEvent.VK_RIGHT:
				pacman.setChangeDirection(4);
				break;

			}
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

	public void addPoint(int x, int y) {
		Point p = new Point(x, y);
		points.add(p);
	}

	public void addBlock(int x, int y) {
		Block b = new Block(x, y);
		blocks.add(b);
	}

	public void addGhost(int x, int y) {
		Ghost gh = new Ghost(x, y);
		ghosts.add(gh);
	}

	public void pacmanPointColision() {

		for (int i = 0; i < points.size(); i++) {
			Point p = (Point) points.get(i);
			if (pacman.getCenterX() == p.getX()
					&& pacman.getCenterY() == p.getY()) {
				points.remove(i);
				score += 1;
			}
		}
	}

	public void pacmanBlockColision() {
		pacman.changeDirection(blocks);
		pacman.blockCollision(blocks);
	}
}
