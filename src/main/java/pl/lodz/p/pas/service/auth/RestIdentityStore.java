package pl.lodz.p.pas.service.auth;

import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.HashSet;

@ApplicationScoped
public class RestIdentityStore implements IdentityStore {

    @Inject
    private UserManager userManager;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            String givenPassword = usernamePasswordCredential.getPasswordAsString();
            User user = userManager.get(usernamePasswordCredential.getCaller());
            if(user.getPassword().equals(givenPassword) && user.isActive()) {
                return new CredentialValidationResult(user.getLogin(), new HashSet<>(Collections.singletonList(user.getClass().getSimpleName())));
            }
            return CredentialValidationResult.INVALID_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
