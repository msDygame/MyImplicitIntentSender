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
 * ����Intent�MIntent Filter�i�����ɪ��T�n���OIntent���ʧ@�B�ƾڥH�����O�C
 * Explicit���T�]Explicit Intents�^Explicit intents specify the component to start by name (the fully-qualified class name).For example, start a new activity in response to a user action or start a service to download a file in the background.
 * Implicit�t�W�]Implicit Intents�^do not name a specific component, but instead declare a general action to perform,which allows a component from another app to handle it.
 * �㦡Intent�����βե󪺦W�٩w�q�ؼвե�A�o�ؤ覡�ܪ����C���O�ѩ�}�o�H�������ä��M���O�����ε{�Ǫ��ե�W�١A�]���A���Intent��h�Ω�b���ε{�Ǥ����ǻ������C��p�b�Y���ε{�Ǥ��A�@��Activity�Ұʤ@��Service�C
 * ����Intent���ۤϡA����s�x�a�Ω�b���P���ε{�Ǥ����ǻ������A�ҥH������Android�t�δM��PIntent�ШD�N�ϳ̤ǰt���ե���骺��ܤ�k �O�GAndroid�NIntent���ШD���e�M�@�ӥs��IntentFilter���L�o������AIntentFilter���]�t�t�Τ��Ҧ��i�઺�ݿ�ե�C
 * Todo:
 * Android����ĳ�bAndroidManifest.xml��<service>���Ҥ��ϥ�<intent-filter>���A�Y����ĳ�ϥ�����Intent�Ұ�Service�F
 * �ӥB�`�Ϊ�����Intent�]��pandroid.intent.action.VIEW�^�]�L�k�NIntent����A�o��Intent�q�`���OActivity Action�A�Y�u��Ω�Activity�C
 * �Y�n�����ϥγo��Activity Action��Intent�}��Service�Ӥ��v�T��e�x�{�ǡA
 * �����إߤ@��Activity�A�N��Theme�]�m��android:theme="@android:style/Theme.NoDisplay"�C
 * ��~��������Intent��F���ɭԴN�|�Ұʳo��Activity�A�ڭ̥u�ݭn�b��onCreate()��k���}��Service�öǻ��������ѼơA�M��ߧYfinish()���o��Activity�NOK�C
 * �ĥΤW�z��k�i�H�ߧY�}�Ҥ@��Service�Ӥ��v�T���e���b�B�檺�e�x�{�ǡA�ĪG�D�`�����C
 * 20150519@
 * AndroidStudio��Activity�A�~�Ӧ� ActionBarActivity�����������w�T�w������Theme����A�ӳo�� Theme ����O�ݭn�ɤJV7���� appcompat LIB�w�u�{�A
 * �sĶ��A�ޥΤ~��ޥΨϥΡC�M�ᤣ��A��@android:style/Theme.NoDisplay�o�ӤF�C�n�令@style/Theme.AppCompat�C�M������Intent��,�e���|�@�{....
 * �٦��@�ؤ�k ��ActionBarActivity����Activity...
 * Todo:
 * Final Result:
 * ImplicitIntentActivity Implicit Intent ImplicitIntentService and start Service , it is work.
 * ImplicitIntentActivity broadcast to ImplicitIntentService and start Service , ImplicitIntentService must at background and it is work .
 */
public class MainActivity extends ActionBarActivity
{
    protected Button pBroadcastButton ;//��Activity�s��
    protected Button pImplicitIntentButton ;//�����N�϶}��Activity
    protected Button pImplicitIntentViewButton ;//�����N�϶}��Activity
    protected Button pQuitButton ;//�����N�϶}��Activity
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
        //��Activity�s�� , �ǥX action= "com.dygame.myimplicitintentservicesample.broadcast" ���s��
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
        //�۩w�q�����N�ϸ�i�{�q�H;//�p�G�䤣��"com.dygame.myimplicitintent"��activity,�I�s��activity�N�|��
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
        //���������N��;//it is work.
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
