package cn.irving.zhao.platform.core.shiro.authenInfo;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

public class OauthAuthenticationInfo implements AuthenticationInfo {

    public OauthAuthenticationInfo(Object principal, String realmName) {
        this.principalCollection = new SimplePrincipalCollection(principal, realmName);
    }

    private final PrincipalCollection principalCollection;

    /**
     * Returns all principals associated with the corresponding Subject.  Each principal is an identifying piece of
     * information useful to the application such as a username, or user id, a given name, etc - anything useful
     * to the application to identify the current <code>Subject</code>.
     * <p>
     * The returned PrincipalCollection should <em>not</em> contain any credentials used to verify principals, such
     * as passwords, private keys, etc.  Those should be instead returned by {@link #getCredentials() getCredentials()}.
     *</p>
     * @return all principals associated with the corresponding Subject.
     */
    @Override
    public PrincipalCollection getPrincipals() {
        return principalCollection;
    }

    /**
     * Returns the credentials associated with the corresponding Subject.  A credential verifies one or more of the
     * {@link #getPrincipals() principals} associated with the Subject, such as a password or private key.  Credentials
     * are used by Shiro particularly during the authentication process to ensure that submitted credentials
     * during a login attempt match exactly the credentials here in the <code>AuthenticationInfo</code> instance.
     *
     * @return the credentials associated with the corresponding Subject.
     */
    @Override
    public Object getCredentials() {
        return null;
    }
}
