package org.Foo.Bar.RestObjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenQueryResponse {
  private String email;
  private Long pokeToken;
}
