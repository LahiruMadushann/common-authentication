package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.util.enums.NoticeCategory;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NoticeAccessManager {
    private static final Map<NoticeCategory, Set<String>> CATEGORY_ACCESS_MAP = new HashMap<>();

    static {
        CATEGORY_ACCESS_MAP.put(NoticeCategory.GENERAL, new HashSet<>(Arrays.asList("ROLE_ADMIN", "ROLE_BUYER", "ROLE_USER", "buyer")));
        CATEGORY_ACCESS_MAP.put(NoticeCategory.BUYER, new HashSet<>(Arrays.asList("ROLE_ADMIN", "ROLE_BUYER", "buyer")));
        CATEGORY_ACCESS_MAP.put(NoticeCategory.SELLER, new HashSet<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
    }

    private UserDetails userDetails;
    private Collection<? extends GrantedAuthority> authorities;

    public NoticeAccessManager() {

        try{
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            authorities = userDetails.getAuthorities();
        }catch (ClassCastException e){
            throw new RuntimeException("User is not authenticated");
        }

    }


    public boolean hasAccessToCategory(NoticeCategory category) {
        return authorities.stream().anyMatch(grantedAuthority -> CATEGORY_ACCESS_MAP.get(category).contains(grantedAuthority.getAuthority()));
    }

    public List<NoticeCategory> getAccessibleCategories() {
        List<NoticeCategory> categories = new ArrayList<>();
        for (NoticeCategory category : NoticeCategory.values()) {
            if (hasAccessToCategory(category)) {
                categories.add(category);
            }
        }
        return categories;
    }

    public List<String> getAccessibleCategoriesString() {
        List<String> categories = new ArrayList<>();
        for (NoticeCategory category : NoticeCategory.values()) {
            if (hasAccessToCategory(category)) {
                categories.add(category.name());
            }
        }
        return categories;
    }

}
