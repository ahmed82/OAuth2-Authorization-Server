# oauth2 Authorization server

## configuration
Create class the 
```java
implements AuthorizationServerConfigurer
```

To Override the 3 methods:

```java
@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	}
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	}
```
