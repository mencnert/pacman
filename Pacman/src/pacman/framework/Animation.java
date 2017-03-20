package pacman.framework;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {

	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;

	public Animation() {
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;

		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}

	public synchronized void addFrame(Image image, long duration) {
		// each image may have different duration(trvani)
		// each animation has its own totalDuration
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
		// AnimFrame is a class that contains an image and its respective
		// duration
		// declared as endTime
	}

	public synchronized void update(long elapsedTime) {
		// time which has to elapse(uplynout)
		// variable frames is ArrayList of AnimFrames
		if (frames.size() > 1) {
			animTime += elapsedTime;
			// animation has to loop
			if (animTime >= totalDuration) {
				// for better synchronizing if the duration of last frame is
				// over
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}

			while (animTime > getFrame(currentFrame).endTime) {
				// if duration of currentFrame is over
				// it sets next frame as currentFrame
				currentFrame++;
			}
		}
	}

	// overloaded method get current animation image
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	// overloaded method get specific image
	public synchronized Image getImage(int frame) {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(frame).image;
		}
	}

	private AnimFrame getFrame(int i) {
		return (AnimFrame) frames.get(i);
	}

	// image with duration
	// image with endTime of totalDuration of animation
	public class AnimFrame {
		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}