package com.eforce21.libraries.security.tokenbased;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Extract information from the current authentication.
 * <p>
 * This class heavily relies on the principal and credentials provided by
 * {@link TokenFilter} and {@link UserDetailsServiceImpl} and will
 * probably not work as expected without those two classes in place.
 */
@Component
public class AuthenticationAccessor {

    /**
     * Determines the currently authenticated user's id.
     *
     * @return The id of the authenticated user, or <code>null</code> if there is none.
     */
    public String getAuthenticatedUserId() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Determines the user details of the currently authenticated user.
     *
     * @return The {@link User} representing the authenticated user, or
     * <code>null</code> if there is none.
     */
    public User getAuthenticatedUserDetails() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            return null;
        }
        return (User) principal;
    }

    private Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof PreAuthenticatedAuthenticationToken) || !(auth.isAuthenticated())) {
            return null;
        }
        return auth;
    }
}
