package com.example.mqtt424;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {
    Button bthost,btpub,btback,btstop,btadd;
    EditText edpub,edhost;
    //static String MQTTHost = "tcp://192.168.42.70";
    static String USERNAME = "";
    static String PASSWORD = "";
    private MqttConnectOptions mqttConnectOptions;
    String topicstr = "myname";//主題名子
    private MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edpub = findViewById(R.id.edpub);
        edhost = findViewById(R.id.edhost);
        bthost = findViewById(R.id.bthost);
        btpub = findViewById(R.id.btpub);
        btback = findViewById(R.id.btback);
        btstop = findViewById(R.id.btstop);
        btadd = findViewById(R.id.btadd);
    }
    public  void  host(View v){//Mqtt
        String MQTTHost= edhost.getText().toString();
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(),MQTTHost,clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                                        @Override
                                        public void onSuccess(IMqttToken asyncActionToken) {
                                            Toast.makeText(MainActivity.this,"connectedll",Toast.LENGTH_LONG)
                                                    .show();
                                        }//成功連上host
                                        @Override
                                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                            Toast.makeText(MainActivity.this,"connection failed",Toast.LENGTH_LONG)
                                                    .show();
                                        }//失敗
                                    }

            );
        }
        catch (MqttException e) {
            e.printStackTrace();
        }

    }
    public void pub (View v){
        String topic = topicstr;
        String mp3name = edpub.getText().toString();
        try {
            client.publish(topic, mp3name.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void forward (View v){
        String topic = topicstr;
        String btforward = "+10S";
        try {
            client.publish(topic, btforward.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void rewind (View v){
        String topic = topicstr;
        String btrewind = "-10S";
        try {
            client.publish(topic, btrewind.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void stop (View v){
        String topic = topicstr;
        String stop = "stop";
        try {
            client.publish(topic, stop.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
