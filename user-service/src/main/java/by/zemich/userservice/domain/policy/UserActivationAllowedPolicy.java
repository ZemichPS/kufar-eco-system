package by.zemich.userservice.domain.policy;

import by.zemich.userservice.domain.model.user.entity.User;
import by.zemich.userservice.domain.model.user.vo.Role;

public class UserActivationAllowedPolicy implements Policy<User> {

    @Override
    public User apply(User user) {
        user.setEnabled(!user.getRole().equals(Role.ADMIN));
        return user;
    }
}
