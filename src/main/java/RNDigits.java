package co.fixt.react.digits; 

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsOAuthSigning;
import com.digits.sdk.android.DigitsSession;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

import java.util.Map;

public final class RNDigits extends ReactContextBaseJavaModule {
  // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
  private static final String FABRIC_KEY = "io.fabric.ApiKey";
  private static final String FABRIC_SECRET = "io.fabric.ApiSecret";
  private static final String TAG = "RNDigits";
  private ReactApplicationContext mContext;

  public RNDigits(ReactApplicationContext reactContext) {
    super(reactContext);
    mContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNDigits";
  }

  @ReactMethod
  public void logout() {
    Digits.getSessionManager().clearActiveSession(); 
  }

  @ReactMethod
  public void view(final Callback cb) {
    TwitterAuthConfig authConfig = getTwitterConfig();
    Fabric.with(mContext, new TwitterCore(authConfig), new Digits());

    AuthCallback callback = new AuthCallback() {
      @Override
      public void success(DigitsSession session, String phoneNumber){
        WritableMap auth = Arguments.createMap();

        Digits digits = Digits.getInstance();

        TwitterAuthConfig config = digits.getAuthConfig();
        auth.putString("consumerKey", config.getConsumerKey());
        auth.putString("consumerSecret", config.getConsumerSecret());

        TwitterAuthToken token = (TwitterAuthToken)session.getAuthToken();

        auth.putString("authToken", token.token);
        auth.putString("authTokenSecret", token.secret);

        Long id = session.getId();
        auth.putString("userId", String.valueOf(id));
        auth.putString("phoneNumber", session.getPhoneNumber());

        cb.invoke(null, auth);
      }

      @Override
      public void failure(DigitsException error) {
        cb.invoke(error.getLocalizedMessage(), null);
      }
    };  

    Digits.authenticate(callback);
  }

  private TwitterAuthConfig getTwitterConfig() {
    String key = getMetaData(FABRIC_KEY);
    String secret = getMetaData(FABRIC_SECRET);

    return new TwitterAuthConfig(key, secret);
  }
  
  private String getMetaData(String name) {
    try {
      ApplicationInfo ai = mContext.getPackageManager().getApplicationInfo(
        mContext.getPackageName(),
        PackageManager.GET_META_DATA
      );

      Bundle metaData = ai.metaData;
      if(metaData == null) {
        Log.w(TAG, "metaData is null. Unable to get meta data for " + name);
      } else {
        String value = metaData.getString(name);
        return value;
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return null; 
  }
}

