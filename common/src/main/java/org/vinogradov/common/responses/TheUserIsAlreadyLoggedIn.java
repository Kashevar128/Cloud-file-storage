package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class TheUserIsAlreadyLoggedIn implements BasicQuery {
    @Override
    public String getClassName() {
        return TheUserIsAlreadyLoggedIn.class.getName();
    }
}
