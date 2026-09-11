package djarly.pss.attributes;

public sealed interface Attribute permits ConstantValueAttribute, CodeAttribute, ExceptionsAttribute, SyntheticAttribute,
        DeprecatedAttribute, SignatureAttribute, SourceFileAttribute, EnclosingMethodAttribute, InnerKlassesAttribute,
        LineNumberTableAttribute, LocalVariableTableAttribute, LocalVariableTypeTableAttribute, BootstrapMethodsAttribute,
        RawAttribute {
}