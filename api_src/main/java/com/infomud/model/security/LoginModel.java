package com.infomud.model;

import io.swagger.annotations.*;
import lombok.*;

@Data
public class LoginModel  {

  @ApiModelProperty(example = "demo", required = true)
  private String username = "";

  @ApiModelProperty(example = "demo", required = true)
  private String password = "";

}
