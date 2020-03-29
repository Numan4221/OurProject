package es.urjc.computadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


public class MidSessionAuthenticationWrapper implements Authentication {

    private Authentication wrapped;
    private List<GrantedAuthority> authorities;

    public MidSessionAuthenticationWrapper(
            Authentication wrapped, Collection<GrantedAuthority> extraAuths) {
        this.wrapped = wrapped;
        this.authorities = new ArrayList<GrantedAuthority>(wrapped.getAuthorities());
        this.authorities.addAll(extraAuths);
    }

    public Authentication getWrapped() {
        return this.wrapped;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

    // delegate all the other methods of Authentication interace to this.wrapped
}