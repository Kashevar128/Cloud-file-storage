package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.commonClasses.BasicQuery;

public class StopSendFileResponse implements BasicQuery {
    @Override
    public String getType() {
        return "Файл полностью передан";
    }
}
