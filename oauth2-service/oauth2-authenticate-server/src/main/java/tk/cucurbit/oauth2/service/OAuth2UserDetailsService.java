package tk.cucurbit.oauth2.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.cucurbit.oauth2.entity.OAuth2User;
import tk.cucurbit.oauth2.entity.UserEntity;
import tk.cucurbit.oauth2.feign.OAuth2UserFeignClient;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class OAuth2UserDetailsService implements UserDetailsService {

    private final OAuth2UserFeignClient userFeignClient;

    public OAuth2UserDetailsService(OAuth2UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userFeignClient.findUserByUsername(username).getData();
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException(username);
        }
        OAuth2User userDetails = new OAuth2User();
        Set<String> permissionCodes = userFeignClient.findPermissionsByUserId(userEntity.getId()).getData();
        String[] codes = permissionCodes.toArray(new String[0]);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(codes);


        BeanUtils.copyProperties(userEntity, userDetails);
        userDetails.setAuthorities(authorityList);
        return userDetails;
    }
}
