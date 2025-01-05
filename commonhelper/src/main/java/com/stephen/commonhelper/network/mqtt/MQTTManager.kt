package com.stephen.commonhelper.network.mqtt

import com.stephen.commonhelper.utils.infoLog
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

object MQTTManager {

    private lateinit var mqttClient: MqttClient

    // MQTT服务器地址
    private const val serverUri = "tcp://mqtt.eclipse.org:1883"

    // 客户端ID
    private const val clientId = "AndroidDevice"

    // 创建连接选项
    private val options = MqttConnectOptions()

    fun init() {
        mqttClient = MqttClient(serverUri, clientId)
        // 设置用户名和密码
        options.userName = "yourUsername"
        options.password = "yourPassword".toCharArray()
        options.mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1_1
        // 连接
        infoLog("mqtt initial connect")
        mqttClient.connect(options)

        // 回调通知
        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                infoLog("connectionLost cause: ${cause?.message}")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                TODO("Not yet implemented")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                TODO("Not yet implemented")
            }

        })
    }

    /**
     * 上传数据
     */
    fun public(topic: String, message: String) {
        infoLog("public message: $message")
        val mqttMessage = MqttMessage(message.toByteArray())
        mqttClient.publish(topic, mqttMessage)
    }

    private fun handleMessage(topic: String, message: MqttMessage?) {
        infoLog("handleMessage topic:$topic, message:${message?.payload}")
    }
}