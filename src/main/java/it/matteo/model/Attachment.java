package it.matteo.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Attachment {

    private String attachmentName;
    private long fileSize;

    private File attachedFile;
    private RandomAccessFile defaultAttachment;

    //Generate attachment
    public Attachment(String attachmentName, double attachmentSize) {
        this.attachmentName = attachmentName;
        this.attachedFile = new File(attachmentName);
        this.fileSize = (int) (attachmentSize * 1024 * 1024);
        try {
            this.defaultAttachment = new RandomAccessFile(attachedFile, "rw");
            defaultAttachment.setLength(fileSize);
            defaultAttachment.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public RandomAccessFile getDefaultAttachment() {
        return defaultAttachment;
    }

    public void setDefaultAttachment(RandomAccessFile defaultAttachment) {
        this.defaultAttachment = defaultAttachment;
    }

    public void delete() {
        this.attachedFile.delete();
    }

    @Override
    public String toString() {
        return "Attachment [attachmentName=" + attachmentName + ", fileSize=" + fileSize + "]";
    }
}
