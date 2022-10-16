FILES THAT WERE CHANGED

AndroidManifest.xml

ADD WITH--------------------------------------------------------------------------------------------------------------


 <uses-permission android:name="android.permission.INTERNET"  />
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

----------------------------------------------------------------------------------------------------------------------



build.gradle(:app)

ADD WITH--------------------------------------------------------------------------------------------------------------
dependencies {

    implementation 'com.android.volley:volley:1.2.1'
    
}
----------------------------------------------------------------------------------------------------------------------

MainActivity.java

ADD WITH--------------------------------------------------------------------------------------------------------------
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
----------------------------------------------------------------------------------------------------------------------
