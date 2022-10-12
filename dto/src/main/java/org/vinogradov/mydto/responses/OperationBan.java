package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

public class OperationBan implements BasicQuery {

    @Override
    public String getType() {
        return "operation ban";
    }

    @Override
    public User getUser() {
        return null;
    }
}
