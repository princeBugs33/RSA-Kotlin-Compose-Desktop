package edu.kdmk.cipher.implementation

enum class OperationMode {
    ENCRYPT_PRIVATE_DECRYPT_PUBLIC,
    ENCRYPT_PUBLIC_DECRYPT_PRIVATE;

    override fun toString(): String {
        return when (this) {
            ENCRYPT_PRIVATE_DECRYPT_PUBLIC -> "Encrypt with private key \uD83D\uDD04 decrypt with public key"
            ENCRYPT_PUBLIC_DECRYPT_PRIVATE -> "Encrypt with public key \uD83D\uDD04 decrypt with private key"
        }
    }
}