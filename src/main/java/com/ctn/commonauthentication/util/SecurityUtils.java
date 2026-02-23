package com.ctn.commonauthentication.util;

import com.ctn.commonauthentication.repository.BranchMapRepo;
import com.ctn.commonauthentication.repository.ShopInvoiceRepo;
import com.ctn.commonauthentication.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class SecurityUtils {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ShopInvoiceRepo shopInvoiceRepo;
    @Autowired
    BranchMapRepo branchMapRepo;

    public static boolean hasUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }

    public boolean hasRole(String role, int userId) {
        return userMapper.findById((long) userId).getRole().equals(role);
    }

    public boolean hasRole(String role, String email) {
        return userMapper.find(email).getRole().equals(role);
    }


    public static boolean hasAnyRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        List<String> roleList = List.of(roles);
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> roleList.contains(authority.getAuthority()));
    }

    public static List<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return List.of();
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public List<Long> getUserShopIds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return List.of();
        }

        List<Long> userAccsessibleShopIds = getUserHeadShopIds();

        userAccsessibleShopIds.addAll(
                userAccsessibleShopIds.stream()
                        .flatMap(shopId -> branchMapRepo.findBranchesByHeadBranchId(toInteger(shopId)).stream())
                        .map(this::toLong)
                        .collect(Collectors.toList())
        );

        return userAccsessibleShopIds;
    }

    public List<Long> getUserHeadShopIds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return List.of();
        }
        return shopInvoiceRepo.getShopIdsByUserID(getUserId());
    }

    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (long) userMapper.getId(authentication.getName());
    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }
    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return null;
        }

        return authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
    }

    private Long toLong(Integer number) {
        return number == null ? null : number.longValue();
    }

    private Integer toInteger(Long number) {
        return number == null ? null : number.intValue();
    }

}
