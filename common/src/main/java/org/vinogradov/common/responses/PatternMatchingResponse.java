package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.Field;
import org.vinogradov.common.commonClasses.StatusUser;
import org.vinogradov.common.commonClasses.User;

public class PatternMatchingResponse implements BasicQuery {

    private User user;

    private final Field field;

    private StatusUser statusUser;

    public PatternMatchingResponse(Field field) {
        this.field = field;
    }

    public PatternMatchingResponse(Field field, User user, StatusUser statusUser) {
        this(field);
        this.user = user;
        this.statusUser = statusUser;
    }

    public Field getField() {
        return field;
    }

    public StatusUser getStatusUser() {
        return statusUser;
    }

    @Override
    public String getClassName() {
        return PatternMatchingResponse.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }
}
