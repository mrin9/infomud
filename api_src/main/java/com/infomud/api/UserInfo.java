package com.infomud.api;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.*;
import com.infomud.model.*;
import com.infomud.model.security.*;
import javax.servlet.http.HttpServletRequest;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import static com.infomud.model.BaseResponse.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;


@RestController
@Api(tags = {"Common"})
public class UserInfo {

	@Autowired
	private UserInfoService userInfoService;

	@ApiOperation(value = "Gets current user information", response = UserInfoResponse.class)
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = {"application/json"})
	public UserInfoResponse getUserInformation(@RequestParam(value = "name", required = false) String userNameParam, HttpServletRequest req) throws NotFoundException {

		String loggedInUserName = userInfoService.getLoggedInUserName();

		User user;
		boolean provideUserDetails = false;

		if (Strings.isNullOrEmpty(userNameParam)) {
			provideUserDetails = true;
			user = userInfoService.getLoggedInUser();
		}
		else if (loggedInUserName.equals(userNameParam)) {
			provideUserDetails = true;
			user = userInfoService.getLoggedInUser();
		}
		else {
			//Check if the current user is superuser then provide the details of requested user
			provideUserDetails = true;
			user = userInfoService.getUserInfoByUserName(userNameParam);
		}

		UserInfoResponse resp = new UserInfoResponse();
		if (provideUserDetails) {
			//resp.setSuccess(true);
      resp.setMsgType(ResponseStatusEnum.SUCCESS);
		}
		else {
			//resp.setSuccess(false);
      resp.setMsgType(ResponseStatusEnum.ERROR);
			resp.setMsgKey("NO_ACCESS");
		}
		resp.setData(user);
		return resp;
	}


	@ApiOperation(value = "Add new user", response = BaseResponse.class)
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = {"application/json"})
	public BaseResponse addNewUser(@RequestBody User user, HttpServletRequest req) {
		boolean userAddSuccess = userInfoService.addNewUser(user);
		BaseResponse resp = new BaseResponse();
		//resp.setSuccess(userAddSuccess);
		if (userAddSuccess==true){
      resp.setMsgType(ResponseStatusEnum.SUCCESS);
			resp.setMsgKey("SUCCESS");
			resp.setMsg("User Added");
		}
		else{
      resp.setMsgType(ResponseStatusEnum.ERROR);
			resp.setMsgKey("ERROR");
			resp.setMsg("Unable to add user");
		}
		return resp;
	}


}
