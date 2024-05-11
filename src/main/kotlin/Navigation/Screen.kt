package Navigation

sealed class Screen(val route : String) {

    object RSACipher : Screen(route = "rsa_cipher")
    object RSABlindSignature : Screen(route = "rsa_blind_signature")
}