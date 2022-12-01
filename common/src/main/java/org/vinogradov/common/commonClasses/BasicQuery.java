package org.vinogradov.common.commonClasses;

import java.io.Serializable;

public interface BasicQuery extends Serializable {

    String getClassName();

    default User getUser() {
        return null;
    };
}
