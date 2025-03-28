package edu.kdmk.cipher.implementation.RSAKeyGen

interface KeyPair {
    fun getPublicKey(): ByteArray
    fun getPrivateKey(): ByteArray
    fun getN(): ByteArray
    fun getKeysAsPair(): Pair<ByteArray, ByteArray>
    fun KeysAndNAsTriple(): Triple<ByteArray, ByteArray, ByteArray>
}