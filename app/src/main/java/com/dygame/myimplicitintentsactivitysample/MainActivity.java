package com.dygame.myimplicitintentsactivitysample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Todo:
 * 隱式Intent和Intent Filter進行比較時的三要素是Intent的動作、數據以及類別。
 * Explicit明確（Explicit Intents）Explicit intents specify the component to start by name (the fully-qualified class name).For example, start a new activity in response to a user action or start a service to download a file in the background.
 * Implicit含蓄（Implicit Intents）do not name a specific component, but instead declare a general action to perform,which allows a component from another app to handle it.
 * 顯式Intent直接用組件的名稱定義目標組件，這種方式很直接。但是由於開發人員往往並不清楚別的應用程序的組件名稱，因此，顯示Intent更多用於在應用程序內部傳遞消息。比如在某應用程序內，一個Activity啟動一個Service。
 * 隱式Intent恰恰相反，它更廣泛地用於在不同應用程序之間傳遞消息，所以必須由Android系統尋找與Intent請求意圖最匹配的組件具體的選擇方法 是：Android將Intent的請求內容和一個叫做IntentFilter的過濾器比較，IntentFilter中包含系統中所有可能的待選組件。
 * Todo:
 * Android不建議在AndroidManifest.xml的<service>標籤中使用<intent-filter>的，即不建議使用隱式Intent啟動Service；
 * 而且常用的隱式Intent（比如android.intent.action.VIEW）也無法將Intent喚醒，這些Intent通常都是Activity Action，即只能用於Activity。
 * 若要直接使用這些Activity Action的Intent開啟Service而不影響到前台程序，
 * 首先建立一個Activity，將其Theme設置為android:theme="@android:style/Theme.NoDisplay"。
 * 當外部的隱式Intent到達的時候就會啟動這個Activity，我們只需要在其onCreate()方法中開啟Service並傳遞相應的參數，然後立即finish()掉這個Activity就OK。
 * 採用上述方法可以立即開啟一個Service而不影響到當前正在運行的前台程序，效果非常不錯。
 * 20150519@
 * AndroidStudio的Activity，繼承自 ActionBarActivity的類必須指定固定的集中Theme風格，而這些 Theme 風格是需要導入V7中的 appcompat LIB庫工程，
 * 編譯後再引用才能引用使用。然後不能再用@android:style/Theme.NoDisplay這個了。要改成@style/Theme.AppCompat。然後隱式Intent時,畫面會一閃....
 * 還有一種方法 把ActionBarActivity換成Activity...
 * Todo:
 * Final Result:
 * ImplicitIntentActivity Implicit Intent ImplicitIntentService and start Service , it is work.
 * ImplicitIntentActivity broadcast to ImplicitIntentService and start Service , ImplicitIntentService must at background and it is work .
 */
public class MainActivity extends ActionBarActivity
{
    protected Button pBroadcastButton ;//跨Activity廣播
    protected Button pImplicitIntentButton ;//隱式意圖開啟Activity
    protected Button pImplicitIntentViewButton ;//隱式意圖開啟Activity
    protected Button pQuitButton ;//隱式意圖開啟Activity
    protected static String TAG = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Crash Handler
        MyCrashHandler pCrashHandler = MyCrashHandler.getInstance();
        pCrashHandler.init(getApplicationContext());
        TAG = pCrashHandler.getTag() ;
        //
        pBroadcastButton = (Button)findViewById(R.id.button1) ;
        pImplicitIntentButton = (Button)findViewById(R.id.button2) ;
        pImplicitIntentViewButton = (Button)findViewById(R.id.button3) ;
        pQuitButton = (Button)findViewById(R.id.button4) ;
        //跨Activity廣播 , 傳出 action= "com.dygame.myimplicitintentservicesample.broadcast" 的廣播
        pBroadcastButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String sAction = "com.dygame.myimplicitintentservicesample.broadcast" ;
                Intent intent = new Intent();
                intent.setAction(sAction);
                intent.putExtra("MyCrashHandler", "POI~");
                sendBroadcast(intent);
                Log.d(TAG, "send Broadcast=" + sAction);
            }
        });
        //自定義隱式意圖跨進程通信;//如果找不到"com.dygame.myimplicitintent"的activity,呼叫的activity就會當
        pImplicitIntentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction("com.dygame.myimplicitintent");
                startActivity(intent);
                Log.d(TAG, "Implicit Intent=" + "com.dygame.myimplicitintent");
            }
        });
        //測試隱式意圖;//it is work.
        pImplicitIntentViewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                Log.d(TAG, "Implicit Intent=" + "ACTION_VIEW");
            }
        });
        //
        pQuitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish() ;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
