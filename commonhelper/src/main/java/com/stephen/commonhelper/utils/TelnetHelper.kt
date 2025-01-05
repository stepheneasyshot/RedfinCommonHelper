package com.stephen.commonhelper.utils

import org.apache.commons.net.telnet.TelnetClient
import java.io.InputStream
import java.io.PrintStream

object TelnetHelper {

    private val myTelnetClient = TelnetClient()
    private lateinit var inputStream: InputStream
    private lateinit var outputStream: PrintStream
    private const val prompt = ""

    fun init(server: String, user: String) {
        try {
            myTelnetClient.connect(server, 23)
            inputStream = myTelnetClient.inputStream
            outputStream = PrintStream(myTelnetClient.outputStream)
            readUntil("login:")
            write(user)
        } catch (e: Exception) {
            e.message?.let { errorLog(it) }
        }
    }

    private fun readUntil(pattern: String): String? {
        try {
            val lastChar = pattern[pattern.length - 1]
            val sb = StringBuffer()
            var ch: Char = inputStream.read().toChar()
            while (true) {
                print(ch)
                sb.append(ch)
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString()
                    }
                }
                ch = inputStream.read().toChar()
            }
        } catch (e: java.lang.Exception) {
            e.message?.let { errorLog(it) }
        }
        return null
    }

    private fun write(value: String?) {
        try {
            outputStream.println(value)
            outputStream.flush()
            println(value)
        } catch (e: java.lang.Exception) {
            e.message?.let { errorLog(it) }
        }
    }

    fun sendCommand(command: String) {
        debugLog("execute ：$command")
        try {
            write(command)
            readUntil("$prompt ")
        } catch (e: java.lang.Exception) {
            e.message?.let { errorLog(it) }
        }
    }

    fun disconnect() {
        try {
            myTelnetClient.disconnect()
        } catch (e: java.lang.Exception) {
            e.message?.let { errorLog(it) }
        }
    }

}