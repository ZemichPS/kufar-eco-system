package by.zemich.userservice.domain.policy;

import org.apache.catalina.User;

public interface Policy<T> {

    T apply(T applicable);
}
