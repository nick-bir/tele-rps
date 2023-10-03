package com.polygon.util

import com.polygon.ConfigLoader
import java.security.SecureRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@OptIn(ExperimentalStdlibApi::class)
fun signedHash(): String {
    if (ConfigLoader.config.tgHash != null) {
        return ConfigLoader.config.tgHash
    }
    val hmacAlgorithm = "HmacSHA256"
    val data = ConfigLoader.config.tgToken
    val key = "WebAppData"

    val secretKeySpec = SecretKeySpec(key.toByteArray(), hmacAlgorithm)
    val mac = Mac.getInstance(hmacAlgorithm)
    mac.init(secretKeySpec)
    return mac.doFinal(data.toByteArray()).toHexString()
}
val signedHash = signedHash()

val random = SecureRandom()
@OptIn(ExperimentalStdlibApi::class)
fun generateToken(): String {
    val values = ByteArray(128)
    random.nextBytes(values)
    return values.toHexString()
}