package org.luvx.boot.web.exchange;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/v1/user")
public interface UserClient {
    @GetExchange("/users/{userId}")
    String select(@PathVariable("userId") long userId);
}
