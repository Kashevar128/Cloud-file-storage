package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class SendPackageResponse implements BasicQuery {
    @Override
    public String getType() {
        return "Пакет успешно доставлен";
    }
}
