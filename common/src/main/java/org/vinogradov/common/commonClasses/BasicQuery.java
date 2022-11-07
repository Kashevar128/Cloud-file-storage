package org.vinogradov.common.commonClasses;

import java.io.Serializable;

public interface BasicQuery extends Serializable {

    String getType();

    default User getUser() {
        return null;
    };
}
