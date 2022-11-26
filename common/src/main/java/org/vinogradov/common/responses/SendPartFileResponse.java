package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.requests.SendPartFileRequest;

public class SendPartFileResponse implements BasicQuery {

    private final Long id;

    private final byte[] bytes;

    private final long sizePart;

    public SendPartFileResponse(Long id, byte[] bytes) {
        this.id = id;
        this.bytes = bytes;
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

    @Override
    public String getType() {
        return SendPartFileRequest.class.toString();
    }

}
