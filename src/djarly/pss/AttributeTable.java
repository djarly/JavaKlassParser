package djarly.pss;

import djarly.pss.attributes.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record AttributeTable(List<Attribute> attributes) {

    public static AttributeTable parse(DataInput dis, ConstantPool cp) throws IOException {
        int count = dis.readUnsignedShort();
        List<Attribute> attributes = new ArrayList<>(count);

        for (int index = 0; index < count; index++) {
            attributes.add(parseAttribute(dis, cp));
        }

        return new AttributeTable(List.copyOf(attributes));
    }

    private static Attribute parseAttribute(DataInput dis, ConstantPool cp) throws IOException {
        int nameIndex = dis.readUnsignedShort();
        String name = cp.utf8at(nameIndex);
        int length = dis.readInt();

        return switch (name) {
            case AttributeNames.CONSTANT_VALUE -> new ConstantValueAttribute(dis.readUnsignedShort());
            case AttributeNames.CODE -> CodeAttribute.parse(dis, cp);
            case AttributeNames.EXCEPTIONS -> ExceptionsAttribute.parse(dis);
            case AttributeNames.SYNTHETIC -> new SyntheticAttribute();
            case AttributeNames.DEPRECATED -> new DeprecatedAttribute();
            case AttributeNames.SIGNATURE -> new SignatureAttribute(dis.readUnsignedShort());
            case AttributeNames.SOURCE_FILE -> new SourceFileAttribute(dis.readUnsignedShort());
            case AttributeNames.ENCLOSING_METHOD ->
                    new EnclosingMethodAttribute(dis.readUnsignedShort(), dis.readUnsignedShort());
            case AttributeNames.INNER_CLASSES -> InnerKlassesAttribute.parse(dis);
            case AttributeNames.LINE_NUMBER_TABLE -> LineNumberTableAttribute.parse(dis);
            case AttributeNames.LOCAL_VARIABLE_TABLE -> LocalVariableTableAttribute.parse(dis);
            case AttributeNames.LOCAL_VARIABLE_TYPE_TABLE -> LocalVariableTypeTableAttribute.parse(dis);
            case AttributeNames.BOOTSTRAP_METHODS -> BootstrapMethodsAttribute.parse(dis);
            default -> RawAttribute.parse(dis, name, length);
        };
    }
}
