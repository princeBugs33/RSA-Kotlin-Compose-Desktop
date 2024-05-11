package edu.kdmk.cipher.implementation.RSAKeyGen

class RSAKeyPairFill(private val publicKey: ByteArray, private val privateKey: ByteArray, private val n: ByteArray) :
    KeyPair {
    override fun getPublicKey(): ByteArray {
        return publicKey
    }

    override fun getPrivateKey(): ByteArray {
        return privateKey
    }

    override fun getN(): ByteArray {
        return n
    }

    override fun getKeysAsPair(): Pair<ByteArray, ByteArray> {
        return Pair(publicKey, privateKey)
    }

    override fun KeysAndNAsTriple(): Triple<ByteArray, ByteArray, ByteArray> {
        return Triple(publicKey, privateKey, n)
    }
}