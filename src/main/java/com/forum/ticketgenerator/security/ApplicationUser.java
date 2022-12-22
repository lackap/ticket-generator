package com.forum.ticketgenerator.security;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.TicketUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class ApplicationUser implements UserDetails {

    private TicketUser ticketUser;

    private Evenement evenement;

    public ApplicationUser(TicketUser ticketUser) {
        this.ticketUser = ticketUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        GrantedAuthority grantedAuthority = () -> ticketUser.getRole();
        grantedAuthorityList.add(grantedAuthority);
        return grantedAuthorityList;
    }

    @Override
    public String getPassword () {
        return ticketUser.getPassword();
    }

    @Override
    public String getUsername () {
        return ticketUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired () {
        return false;
    }

    @Override
    public boolean isAccountNonLocked () {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return false;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }
}
