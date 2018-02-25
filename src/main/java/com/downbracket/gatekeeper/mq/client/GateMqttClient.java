package com.downbracket.gatekeeper.mq.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class GateMqttClient {
    public static final String BROKER_URL = "tcp://localhost:1883";

    private MqttClient client;

    
    public GateMqttClient( )
    {
    	connect() ;
    }

	
	private MqttClient getMqttClient()
	{
		if( client == null )
		{
	        String clientId = "" + hashCode() + "-publisher";
	        try
	        {
	             client = new MqttClient(BROKER_URL, clientId);
	        }
	        catch (MqttException e)
	       {
	            e.printStackTrace();
	            System.exit(1);
	        }		 
		}
        return client ;
	}
	
	private void connect()
	{

		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		 
		try {
			getMqttClient().connect(options);
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void sendMessageToTopic( String message, String topic ) {

		final MqttTopic temperatureTopic = client.getTopic(topic);
     try {
		temperatureTopic.publish(new MqttMessage(message.getBytes()));
	} catch (MqttPersistenceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
