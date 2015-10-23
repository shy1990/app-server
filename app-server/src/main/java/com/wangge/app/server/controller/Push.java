package com.wangge.app.server.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.util.JPush;

@RestController
public class Push
{
  @RequestMapping(value={"/pushNewOrder"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  @ResponseBody
  public ResponseEntity<JSONObject> pushNewOrder(String state, String info)
  {
    Map map = new HashMap();
    map.put("state", state);
    map.put("info", info);
    JSONObject jsonStr = JSONObject.fromObject(map);

    System.out.println("jsonStr===" + jsonStr);
    return new ResponseEntity(jsonStr, HttpStatus.OK);
  }

  @RequestMapping({"/pushNews"})
  public ResponseEntity<JSONObject> pushNews()
  {
    Map map = new HashMap();
    JSONObject jsonStr = JSONObject.fromObject(map);
    try {
      JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title", "test", map);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("jsonStr===" + jsonStr);
    return new ResponseEntity(jsonStr, HttpStatus.OK);
  }

  @RequestMapping({"/pushActivi"})
  public ResponseEntity<JSONObject> pushActivi()
  {
    Map map = new HashMap();
    JSONObject jsonStr = JSONObject.fromObject(map);
    try {
      JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title", "test", map);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("jsonStr===" + jsonStr);
    return new ResponseEntity(jsonStr, HttpStatus.OK);
  }

  @RequestMapping({"/visitTask"})
  public ResponseEntity<JSONObject> visitTask()
  {
    Map map = new HashMap();
    JSONObject jsonStr = JSONObject.fromObject(map);
    try {
      JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title", "test", map);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("jsonStr===" + jsonStr);
    return new ResponseEntity(jsonStr, HttpStatus.OK);
  }

  @RequestMapping({"/pushTakeMoney"})
  public ResponseEntity<JSONObject> pushTakeMoney()
  {
    Map map = new HashMap();
    JSONObject jsonStr = JSONObject.fromObject(map);
    try {
      JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title", "test", map);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("jsonStr===" + jsonStr);
    return new ResponseEntity(jsonStr, HttpStatus.OK);
  }
}