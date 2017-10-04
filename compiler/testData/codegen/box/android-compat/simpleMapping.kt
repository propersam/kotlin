// IGNORE_BACKEND: JS, NATIVE

// WITH_RUNTIME
// FILE: Compat.java
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Compat {
    Class value();
}

// FILE: View.java
@Compat(ViewCompat.class)
public class View {
    public boolean noArgs() { return false; }
    public boolean subtype() { return false; }
    public boolean subtypeOverride() { return false; }
    public boolean boxing(int i) { return false; }
    public boolean boxingResult() { return false; }
    public boolean vararg(int... ii) { return false; }
    public boolean varargBoxing(int... ii) { return false; }
    public boolean valueVararg(int i) { return true; }
    public <T> boolean generic(T t) { return false; }
    public boolean samAdapter(Runnable r) { return false; }
    public boolean differentParamType(int i) { return true; }
    public boolean differentReturnType() { return true; }
    public boolean subtypeParam(String s) { return true; }
}

// FILE: SubView.java
@Compat(SubViewCompat.class)
public class SubView extends View {
    @Override public boolean subtypeOverride() { return false; } // todo: do we need to compat this?
    public boolean superInCompat() { return true; }
}

// FILE: AnotherView.java
@Compat(ViewCompat.class)
public class AnotherView {
    public boolean inAnotherView() { return false; }
}

// FILE: ViewCompat.java
public class ViewCompat {
    static public boolean noArgs(View v) { return true; }
    static public boolean subtype(View v) { return true; }
    static public boolean subtypeOverride(View v) { return true; }
    static public boolean boxing(View v, Integer i) { return true; }
    static public Boolean boxingResult(View v) { return true; }
    static public boolean vararg(View v, int... ii) { return true; }
    static public boolean varargBoxing(View v, Integer... ii) { return true; }
    static public boolean valueVararg(View v, int... ii) { return false; }
    static public <K> boolean generic(View v, K k) { return true; }
    static public boolean samAdapter(View v, Runnable r) { return true; }
    static public boolean inAnotherView(AnotherView v) { return true; }
    static public boolean differentParamType(View v, long i) { return false; }
    static public int differentReturnType(View v) { return 0; }
    static public boolean subtypeParam(View v, Object s) { return false; }
}

// FILE: SubViewCompat.java
public class SubViewCompat {
    static boolean superInCompat(View v) { return false; }
}

// FILE: Movable.java
@Compat(MovableCompat.class)
public interface Movable {
    boolean move();
}

// FILE: MovableImpl.java
public class MovableImpl implements Movable {
    public boolean move() { return false; }
}

// FILE: MovableCompat.java
public class MovableCompat {
    public static boolean move(Movable m) { return true; }
}

// FILE: test.kt
fun box(): String {
    if (!View().noArgs()) { println("noArgs"); return "FAIL" }
    if (!SubView().subtype()) { println("subtype"); return "FAIL" }
    if (!SubView().subtypeOverride()) { println("subtypeOverride"); return "FAIL" }
    if (!View().boxing(0)) { println("boxing"); return "FAIL" }
    if (!View().boxingResult()) { println("boxingResult"); return "FAIL" }
    if (!View().vararg(0)) { println("vararg"); return "FAIL" }
//    if (!View().varargBoxing(0)) { println("varargBoxing"); return "FAIL" }
    if (!View().valueVararg(0)) { println("valueVararg"); return "FAIL" }
//    if (!View().generic(0)) { println("generic"); return "FAIL" }
    if (!SubView().superInCompat()) { println("superInCompat"); return "FAIL" }
    if (!View().samAdapter {}) { println("samAdapter"); return "FAIL" }
    if (!MovableImpl().move()) { println("move"); return "FAIL" }
    if (!AnotherView().inAnotherView()) { println("inAnotherView"); return "FAIL" }
    if (!View().differentParamType(0)) { println("differentParamType"); return "FAIL" }
    if (!View().differentReturnType()) { println("differentReturnType"); return "FAIL" }
    if (!View().subtypeParam("")) { println("subtypeParam"); return "FAIL" }
    return "OK"
}
