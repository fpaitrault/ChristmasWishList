package org.fpaitrault.viewmdl;

import org.fpaitrault.mdl.Wish;

public class WishStatus {
	private Wish wish;
	private boolean editStatus;
	
	public WishStatus(Wish wish, boolean editStatus) {
		this.wish = wish;
		this.editStatus = editStatus;
	}

	public boolean getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(boolean editStatus) {
		this.editStatus = editStatus;
	}

	public Wish getWish() {
		return wish;
	}
	
}
