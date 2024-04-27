package edu.kdmk.cipher.implementation

interface KeyPairGen {
    fun getPublicKey(): ByteArray
    fun getPrivateKey(): ByteArray
    fun getKeysAsPair(): Pair<ByteArray, ByteArray>
}