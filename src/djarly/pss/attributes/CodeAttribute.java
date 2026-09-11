package djarly.pss.attributes;

import djarly.pss.AttributeTable;
import djarly.pss.ConstantPool;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record CodeAttribute(int maxStack, int maxLocals, byte[] code, List<ExceptionHandler> exceptionTable,
                             AttributeTable attributes) implements Attribute {

    public record ExceptionHandler(int startPc, int endPc, int handlerPc, int catchType) {
    }

    public static CodeAttribute parse(DataInput in, ConstantPool constantPool) throws IOException {
        int maxStack = in.readUnsignedShort();
        int maxLocals = in.readUnsignedShort();

        int codeLength = in.readInt();
        byte[] code = new byte[codeLength];
        in.readFully(code);

        int exceptionTableLength = in.readUnsignedShort();
        List<ExceptionHandler> exceptionTable = new ArrayList<>(exceptionTableLength);

        for (int index = 0; index < exceptionTableLength; index++) {
            exceptionTable.add(new ExceptionHandler(
                    in.readUnsignedShort(),
                    in.readUnsignedShort(),
                    in.readUnsignedShort(),
                    in.readUnsignedShort()));
        }

        AttributeTable attributes = AttributeTable.parse(in, constantPool);

        return new CodeAttribute(maxStack, maxLocals, code, List.copyOf(exceptionTable), attributes);
    }
}