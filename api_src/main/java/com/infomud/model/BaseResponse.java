package com.infomud.model;

import io.swagger.annotations.*;
import lombok.*;



@Data //for getters and setters
public class BaseResponse  {
  public enum ResponseStatusEnum {SUCCESS, ERROR, WARNING};

  @ApiModelProperty(required = true)
  private ResponseStatusEnum  msgType;

  private String  msgKey;
  private String  msg;
}
