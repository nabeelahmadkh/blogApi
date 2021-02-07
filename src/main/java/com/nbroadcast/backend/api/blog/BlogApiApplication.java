package com.nbroadcast.backend.api.blog;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BlogApiApplication extends Application<BlogApiConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BlogApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "BlogApi";
    }

    @Override
    public void initialize(final Bootstrap<BlogApiConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BlogApiConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
