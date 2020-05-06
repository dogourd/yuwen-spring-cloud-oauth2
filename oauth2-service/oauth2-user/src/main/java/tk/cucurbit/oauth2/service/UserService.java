package tk.cucurbit.oauth2.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.cucurbit.oauth2.dao.UserRepository;
import tk.cucurbit.oauth2.entity.UserEntity;
import tk.cucurbit.oauth2.exceptions.UserNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findUserByUsername(String username) throws UserNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UserNotFoundException("username cannot be blank");
        }
        return userRepository.findOneByUsername(username);
    }
}
