package djarly.pss;

import djarly.pss.constants.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record ConstantPool(List<Constant> entries) {

    public static ConstantPool parse(DataInput dis) throws IOException {
        int count = dis.readUnsignedShort();
        List<Constant> entries = new ArrayList<>(count);

        entries.add(null);

        for (int index = 1; index < count; index++) {
            byte tag = dis.readByte();
            entries.add(parseConstant(dis, tag));

            if (tag == ConstantTags.DOUBLE || tag == ConstantTags.LONG) {
                entries.add(null);
                index++;
            }
        }

        return new ConstantPool(entries);
    }

    private static Constant parseConstant(DataInput dis, byte tag) throws IOException {
        return switch (tag) {
            case ConstantTags.UTF8 -> new Utf8Constant(dis.readUTF());
            case ConstantTags.INTEGER -> new IntegerConstant(dis.readInt());
            case ConstantTags.FLOAT -> new FloatConstant(dis.readFloat());
            case ConstantTags.LONG -> new LongConstant(dis.readLong());
            case ConstantTags.DOUBLE -> new DoubleConstant(dis.readDouble());
            case ConstantTags.KLASS, ConstantTags.STRING, ConstantTags.MODULE, ConstantTags.PACKAGE ->
                    new IndexConstant(dis.readUnsignedShort());
            case ConstantTags.FIELD_REF, ConstantTags.METHOD_REF, ConstantTags.INTERFACE_METHOD_REF ->
                    new RefConstant(dis.readUnsignedShort(), dis.readUnsignedShort());
            case ConstantTags.NAME_AND_TYPE -> new NameAndTypeConstant(dis.readUnsignedShort(), dis.readUnsignedShort());
            case ConstantTags.METHOD_HANDLE -> new MethodHandleConstant(dis.readUnsignedByte(), dis.readUnsignedShort());
            case ConstantTags.METHOD_TYPE -> new MethodTypeConstant(dis.readUnsignedShort());
            case ConstantTags.DYNAMIC, ConstantTags.INVOKE_DYNAMIC -> new DynamicConstant(dis.readUnsignedShort(), dis.readUnsignedShort());
            default -> throw new IllegalStateException("Constant " + tag + " not parse");
        };
    }

    public Constant at(int index) {
        return this.entries.get(index);
    }

    public String utf8at(int index) {
        if (this.at(index) instanceof Utf8Constant(String value)) {
            return value;
        }

        throw new IllegalStateException("Constant pool entry at index " + index + " is not a UTF8 constant");
    }
}
