package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

import java.util.List;

public class GetListResponse implements BasicQuery {

    private List<String> currentList;

    public GetListResponse(List<String> currentList) {
        this.currentList = currentList;
    }

    public List<String> getCurrentList() {
        return currentList;
    }

    @Override
    public String getType() {
        return "this new list";
    }



}
