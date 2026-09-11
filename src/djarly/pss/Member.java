package djarly.pss;

import java.io.DataInput;
import java.io.IOException;

public record Member(int accessFlags, int nameIndex, int descriptorIndex, AttributeTable attributes) {

    public static Member parse(DataInput in, ConstantPool constantPool) throws IOException {
        int accessFlags = in.readUnsignedShort();
        int nameIndex = in.readUnsignedShort();
        int descriptorIndex = in.readUnsignedShort();
        AttributeTable attributes = AttributeTable.parse(in, constantPool);
        return new Member(accessFlags, nameIndex, descriptorIndex, attributes);
    }
}