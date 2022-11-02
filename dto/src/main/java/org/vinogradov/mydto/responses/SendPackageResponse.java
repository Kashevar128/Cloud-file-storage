package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.commonClasses.BasicQuery;

public class SendPackageResponse implements BasicQuery {
    @Override
    public String getType() {
        return "Пакет успешно доставлен";
    }
}
