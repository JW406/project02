package org.Foo.Bar.RestObjects;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GitHubAccessToken {
  private String access_token;
  private String token_type;
  private String scope;
}
