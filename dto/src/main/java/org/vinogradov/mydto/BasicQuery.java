package org.vinogradov.mydto;

import java.io.Serializable;

public interface BasicQuery extends Serializable {

    String getType();

    User getUser();
}
