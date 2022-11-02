package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.commonClasses.BasicQuery;

public class StopSendPackageResponse implements BasicQuery {
    @Override
    public String getType() {
        return "Файл полностью передан";
    }
}
