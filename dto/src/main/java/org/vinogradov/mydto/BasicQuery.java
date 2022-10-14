package org.vinogradov.mydto;

import java.io.Serializable;

public interface BasicQuery extends Serializable {

    String getType();

    default User getUser() {
        return null;
    };
}
