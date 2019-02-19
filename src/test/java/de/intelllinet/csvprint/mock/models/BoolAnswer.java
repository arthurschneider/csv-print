package de.intelllinet.csvprint.mock.models;

public class BoolAnswer {

	private boolean fullAged;
	private boolean child;
	private boolean living;
	private boolean working;

	public BoolAnswer(boolean fullAged, boolean child, boolean living, boolean working) {
		this.fullAged = fullAged;
		this.child = child;
		this.living = living;
		this.working = working;
	}

	public boolean isFullAged() {
		return fullAged;
	}

	public void setFullAged(boolean fullAged) {
		this.fullAged = fullAged;
	}

	public boolean isChild() {
		return child;
	}

	public void setChild(boolean child) {
		this.child = child;
	}

	public boolean isLiving() {
		return living;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

}
