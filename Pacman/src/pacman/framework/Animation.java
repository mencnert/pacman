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
		// totalDuration is only for one animation
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
		// AnimFrame is class where is image and respective duration
		// declared like endTime
	}

	public synchronized void update(long elapsedTime) {
		// time which have to elapse(uplynout)
		// frames is ArrayList of AnimFrames
		if (frames.size() > 1) {
			animTime += elapsedTime;
			// animation have to loop
			if (animTime >= totalDuration) {
				// for better synchronizing if duration of last frame is over
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}

			while (animTime > getFrame(currentFrame).endTime) {
				// if duration of currentFrame is over
				// it set next frame like currentFrame
				currentFrame++;
			}
		}
	}

	// overloaded method if I wanna current image of animation
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	// overloaded method if I wanna specific image
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
	// image with endTime of totalDuration in animation
	public class AnimFrame {
		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}