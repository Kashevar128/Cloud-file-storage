package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.HelperMethods;

import java.nio.file.Path;
import java.util.List;

public class GetListResponse implements BasicQuery {

    private List<String> currentList;

    public GetListResponse(Path path) {
        this.currentList = HelperMethods.generateStringList(path);
    }

    public List<String> getCurrentList() {
        return currentList;
    }

    @Override
    public String getType() {
        return "this new list";
    }



}
