import test.*

fun box(): String {
    foo {
        try {
            return "OK"
        } catch(e: Exception) {
            return "fail 1"
        }
    }

    return "fail 2"
}