package com.pichillilorenzo.flutter_inappwebview.chrome_custom_tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pichillilorenzo.flutter_inappwebview.Shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class ChromeSafariBrowserManager implements MethodChannel.MethodCallHandler {

  public MethodChannel channel;

  protected static final String LOG_TAG = "ChromeBrowserManager";

  public ChromeSafariBrowserManager(BinaryMessenger messenger) {
    channel = new MethodChannel(messenger, "com.pichillilorenzo/flutter_chromesafaribrowser");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(final MethodCall call, final MethodChannel.Result result) {
    final Activity activity = Shared.activity;
    final String uuid = (String) call.argument("uuid");

    switch (call.method) {
      case "open":
        {
          String url = (String) call.argument("url");
          HashMap<String, Object> options = (HashMap<String, Object>) call.argument("options");
          List<HashMap<String, Object>> menuItemList = (List<HashMap<String, Object>>) call.argument("menuItemList");
          open(activity, uuid, url, options, menuItemList, result);
        }
        break;
      case "isAvailable":
        result.success(CustomTabActivityHelper.isAvailable(activity));
        break;
      default:
        result.notImplemented();
    }
  }

  public void open(Activity activity, String uuid, String url, HashMap<String, Object> options,
                   List<HashMap<String, Object>> menuItemList, MethodChannel.Result result) {

    Intent intent = null;
    Bundle extras = new Bundle();
    extras.putString("fromActivity", activity.getClass().getName());
    extras.putString("url", url);
    extras.putBoolean("isData", false);
    extras.putString("uuid", uuid);
    extras.putSerializable("options", options);
    extras.putSerializable("menuItemList", (Serializable) menuItemList);

    if (CustomTabActivityHelper.isAvailable(activity)) {
      intent = new Intent(activity, ChromeCustomTabsActivity.class);
      intent.putExtras(extras);
      activity.startActivity(intent);
      result.success(true);
      return;
    }

    result.error(LOG_TAG, "ChromeCustomTabs is not available!", null);
  }

  public void dispose() {
    channel.setMethodCallHandler(null);
  }
}
