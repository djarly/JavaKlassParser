package djarly.pss.constants;

public sealed interface Constant permits Utf8Constant, IntegerConstant, FloatConstant, LongConstant, DoubleConstant,
        IndexConstant, RefConstant, NameAndTypeConstant, MethodHandleConstant, MethodTypeConstant, DynamicConstant {
}
