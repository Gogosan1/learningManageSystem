package ru.zelenev.learning.manage.system.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.client.RestTestClient;

@Import(TestConfig.class)
@AutoConfigureRestTestClient
public class AbstractIt {

    @Autowired
    protected RestTestClient restTestClient;

}
