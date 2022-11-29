package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class CreateNewFolderRequest implements BasicQuery {

    User user;

    String pathFolder;

    public CreateNewFolderRequest(User user, String pathFolder) {
        this.user = user;
        this.pathFolder = pathFolder;
    }

    @Override
    public String getClassName() {
        return CreateNewFolderRequest.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getPathFolder() {
        return pathFolder;
    }
}
