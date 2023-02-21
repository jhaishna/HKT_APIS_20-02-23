package com.jaishna.wrapperframework.model.subscriptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class Service {

    List<Alias> aliasList;

    public List<Alias> getAliasList() {
        return aliasList;
    }

    public void setAliasList(List<Alias> aliasList) {
        this.aliasList = aliasList;
    }
}
