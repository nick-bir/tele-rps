package com.polygon.util

import com.polygon.ConfigLoader
import java.net.URLDecoder
import java.security.SecureRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private fun botSignedHash(): ByteArray {
    val hmacAlgorithm = "HmacSHA256"
    val data = ConfigLoader.config.tgToken
    val key = "WebAppData"

    val secretKeySpec = SecretKeySpec(key.toByteArray(), hmacAlgorithm)
    val mac = Mac.getInstance(hmacAlgorithm)
    mac.init(secretKeySpec)
    return mac.doFinal(data.toByteArray())
}
val botSignedHash = botSignedHash()

@OptIn(ExperimentalStdlibApi::class)
fun validateTgInitData(initData: String): Boolean {
    if (ConfigLoader.config.tgHash != null) {
        return initData == ConfigLoader.config.tgHash
    }
    val decoded = URLDecoder.decode(initData, "utf-8")
    val arr = decoded.split('&')
    val hashAndData = arr.partition { it.startsWith("hash=") }
    val messageHash = hashAndData.first.firstOrNull()?.split("=")?.lastOrNull() ?: return false
    val data = hashAndData.second.sorted().joinToString("\n")

    val hmacAlgorithm = "HmacSHA256"
    val key = botSignedHash
    val secretKeySpec = SecretKeySpec(key, hmacAlgorithm)
    val mac = Mac.getInstance(hmacAlgorithm)
    mac.init(secretKeySpec)
    val hash = mac.doFinal(data.toByteArray()).toHexString()
    return hash == messageHash
}

val random = SecureRandom()
@OptIn(ExperimentalStdlibApi::class)
fun generateToken(): String {
    val values = ByteArray(128)
    random.nextBytes(values)
    return values.toHexString()
}