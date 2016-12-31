package com.infomud.model.security;
import io.swagger.annotations.*;
import lombok.*;

@Data
public class SessionResponse {
  @ApiModelProperty(example = "xxx.xxx.xxx", required = true)
  private String token = "";
}
