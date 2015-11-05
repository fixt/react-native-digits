package co.fixt.react.digits; 

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthToken;
import io.fabric.sdk.android.Fabric;

public final class RNDigits extends ReactContextBaseJavaModule {
  // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
  private static final String TWITTER_KEY = "fakeTwitterKey";
  private static final String TWITTER_SECRET = "fakeTwitterSecret";
  private AuthCallback callback;

  public RNDigits(ReactApplicationContext reactContext) {
    // This probably needs to go in an init method?
    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new TwitterCore(authConfig), new Digits());

    super(reactContext);
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
  public void view(Callback cb) {
    callback = new AuthCallback() {
      @Override
      public void success(DigitsSession session, String phoneNumber){
        WritableMap auth = Arguments.createMap();

        TwitterAuthConfig config = Digits.getAuthConfig();
        auth.putString("consumerKey", config.getConsumerKey());
        auth.putString("consumerSecret", config.getConsumerSecret());

        TwitterAuthToken token = session.getAuthToken();
        auth.putString("authToken", token.token);
        auth.putString("authTokenSecret", token.secret);

        auth.putString("userId", session.getId());
        auth.putString("phoneNumber", phoneNumber);
        // consumerKey, consumerSecret 
        cb.invoke(null, auth);
      }

      @Override
      public void failure(DigitsException error) {
        cb.invoke(error.getLocalizedMessage(), null);
      }
    };  

    Digits.authenticate(callback);
  }
 
}

