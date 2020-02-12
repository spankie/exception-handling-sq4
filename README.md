# General overview:

you need the devtools dependencies for live reload

it can do browser reload with plugins

you can set additional-paths to watch or not to watch.

## TOPICS

1. TESTING
   Why test:
   - Verifiy correctness of code.
   - Document behaviour (How system behaves when the there are errors as well)
   - Detect regression (We are not breaking existing functionality)

- Ways of making tests easier:

  - Following SOLID principles.
  - Using dependency injection.
    - better ways of doing D.I for good testing.
      - Constructor injection.
      - Setter injection (When dependecy is optional)
      - Using builder patterns

## Unit tests runs in isolation

- Mocks:
  - types of mock:
    - dummy: when dependency is not checked
    - stub: when dependecy needs to fulfill a condition

## Integration test

---

2. CONFIGURATION

3. SECURITY

Spring provides various authentication filters/managers

- e.g Openidauthfilter, basicauthfilter, digestauthfilter
- auth manager returns token to the authfilter. it delegates to one or more providers e.g openidauthprovider, daoauthprovider, ldapauthprovider (mostly use one, but big organizations use more than one.)
- the providers use a userdetailsservice to retreive the credentials from and identity store

different kinds of authentication:

- form login
- digest auth
- basic auth

---

## Security Protocols:

- Oauth2: is not authentication or authorization. It is "delegated authorization" and has its flaws, which include impersonation.
- Open ID Connect (OIDC)

  - features of oidc is identity token: is a jwt(json web token), contains claims of how the authentication occured by the authorization server

    ## features:

    - must contain a unique subject which assert the identity of the user (non reassignable)
    - issuer: issuing authority (client can verify the user was authenticated by the expected source)
    - audience: client that was authenticate (client id).
    - expiration time:
    - issued at:

    - scope: this contains specific claims
      - openid, email, profile, offline access, custom scopes

4. DEPLOYMENT

5. SPRING CLOUD

# sort orders and contacts.

# search orders and contacts

# show company names on the orders or contacts table list
