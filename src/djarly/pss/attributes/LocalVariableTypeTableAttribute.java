package djarly.pss.attributes;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record LocalVariableTypeTableAttribute(List<Entry> entries) implements Attribute {

    public record Entry(int startPc, int length, int nameIndex, int signatureIndex, int index) {
    }

    public static LocalVariableTypeTableAttribute parse(DataInput in) throws IOException {
        int count = in.readUnsignedShort();
        List<Entry> entries = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            entries.add(new Entry(
                    in.readUnsignedShort(),
                    in.readUnsignedShort(),
                    in.readUnsignedShort(),
                    in.readUnsignedShort(),
                    in.readUnsignedShort()));
        }

        return new LocalVariableTypeTableAttribute(List.copyOf(entries));
    }
}