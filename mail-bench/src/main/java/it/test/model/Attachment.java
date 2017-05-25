package it.test.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Attachment {
	
	private String attachmentName;
	
	private File attachedFile;
	private RandomAccessFile defaultAttachment;
	
	private boolean isDefault;
	
	public Attachment(String fileName, String attachmentName) {
		this.attachmentName = attachmentName;
		this.attachedFile = new File(fileName);
		this.isDefault = false;
	}
	
	public Attachment(int fileLength){
		try {
			this.defaultAttachment = new RandomAccessFile(new File("temp"), "rw");
		} catch (FileNotFoundException e) {e.printStackTrace();}
		this.isDefault = true;
	}

	@Override
	public String toString() {
		return "Attachment [attachmentName=" + attachmentName + "]";
	}
}
