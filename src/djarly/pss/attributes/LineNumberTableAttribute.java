package djarly.pss.attributes;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record LineNumberTableAttribute(List<Entry> entries) implements Attribute {

    public record Entry(int startPc, int lineNumber) {
    }

    public static LineNumberTableAttribute parse(DataInput in) throws IOException {
        int count = in.readUnsignedShort();
        List<Entry> entries = new ArrayList<>(count);

        for (int index = 0; index < count; index++) {
            entries.add(new Entry(in.readUnsignedShort(), in.readUnsignedShort()));
        }

        return new LineNumberTableAttribute(List.copyOf(entries));
    }
}