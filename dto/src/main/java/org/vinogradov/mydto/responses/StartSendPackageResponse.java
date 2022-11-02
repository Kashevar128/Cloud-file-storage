package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.commonClasses.BasicQuery;

public class StartSendPackageResponse implements BasicQuery {


    @Override
    public String getType() {
        return "Начало передачи файла";
    }
}
