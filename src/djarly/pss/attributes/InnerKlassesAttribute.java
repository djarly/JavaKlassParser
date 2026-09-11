package djarly.pss.attributes;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record InnerKlassesAttribute(List<Entry> klasses) implements Attribute {

    public record Entry(int innerKlassInfoIndex, int outerKlassInfoIndex, int innerNameIndex,
                         int innerKlassAccessFlags) {
    }

    public static InnerKlassesAttribute parse(DataInput in) throws IOException {
        int count = in.readUnsignedShort();
        List<Entry> klasses = new ArrayList<>(count);

        for (int index = 0; index < count; index++) {
            klasses.add(new Entry(
                    in.readUnsignedShort(),
                    in.readUnsignedShort(),
                    in.readUnsignedShort(),
                    in.readUnsignedShort()));
        }

        return new InnerKlassesAttribute(List.copyOf(klasses));
    }
}