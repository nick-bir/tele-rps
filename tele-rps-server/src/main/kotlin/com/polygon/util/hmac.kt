package com.polygon.util

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@OptIn(ExperimentalStdlibApi::class)
fun signedHash(): String {
    val hmacAlgorithm = "HmacSHA256"
    val data = "baeldung"
    val key = "WebAppData"

    val secretKeySpec = SecretKeySpec(key.toByteArray(), hmacAlgorithm)
    val mac = Mac.getInstance(hmacAlgorithm)
    mac.init(secretKeySpec)
    return mac.doFinal(data.toByteArray()).toHexString()
}