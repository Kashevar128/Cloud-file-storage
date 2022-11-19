package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class SendPartFileRequest implements BasicQuery {

    private final User user;

    private final Long id;

    private final String fileName;

    private final byte[] bytes;

    private final long sizePart;

    public SendPartFileRequest(User user, Long id, String fileName, byte[] bytes) {
        this.user = user;
        this.bytes = bytes;
        this.id = id;
        this.fileName = fileName;
        this.sizePart = bytes.length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Long getId() {
        return id;
    }

    public long getSizePart() {
        return sizePart;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String getType() {
        return SendPartFileRequest.class.toString();
    }

    @Override
    public User getUser() {
        return user;
    }
}
