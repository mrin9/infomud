package com.infomud.model.security;

import com.infomud.model.*;
import lombok.*;

@Data
public class UserInfoResponse extends BaseResponse {
    private User data = new User();
}
