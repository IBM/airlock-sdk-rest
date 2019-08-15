package com.ibm.app.util;

import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;

import java.lang.annotation.Annotation;

public class DynamicApi implements Api {
    private boolean hidden;
    private Api api;

    public DynamicApi(Boolean hidden, Api api) {
        this.api = api;
        this.hidden = hidden;
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return DynamicApi.class;
    }

    @Override
    public String value() {
        return api.value();
    }

    @Override
    public String[] tags() {
        return api.tags();
    }

    @Override
    public String description() {
        return api.description();
    }

    @Override
    public String basePath() {
        return api.basePath();
    }

    @Override
    public int position() {
        return api.position();
    }

    @Override
    public String produces() {
        return api.produces();
    }

    @Override
    public String consumes() {
        return api.consumes();
    }

    @Override
    public String protocols() {
        return api.protocols();
    }

    @Override
    public Authorization[] authorizations() {
        return api.authorizations();
    }

    @Override
    public boolean hidden() {
        return this.hidden;
    }
}
