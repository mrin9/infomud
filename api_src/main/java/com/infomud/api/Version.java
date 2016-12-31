package com.infomud.api;


import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.MediaType.*;

import java.util.List;

import com.infomud.model.VersionModel;

@RestController
@RequestMapping(value = "/version", produces = { "application/json" })
@Api(tags = {"Common"})
public class Version {
  @ApiOperation(value = "Gets the version of the REST API", notes = "", response = VersionModel.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Returns the version info for the REST API.", response = VersionModel.class) })
  @RequestMapping( method = RequestMethod.GET)
  public VersionModel getVersion() throws NotFoundException {
      VersionModel r = new VersionModel();
      r.setVersion("1.0.0");
      r.setMajor(1);
      r.setMinor(0);
      r.setPatch(0);
      // do some magic!
      //return new ResponseEntity<VersionModel>(r,HttpStatus.OK);
      return r;
  }
}
