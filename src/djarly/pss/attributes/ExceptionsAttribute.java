package djarly.pss.attributes;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record ExceptionsAttribute(List<Integer> exceptionIndexTable) implements Attribute {

    public static ExceptionsAttribute parse(DataInput in) throws IOException {
        int count = in.readUnsignedShort();
        List<Integer> exceptionIndexTable = new ArrayList<>(count);

        for (int index = 0; index < count; index++) {
            exceptionIndexTable.add(in.readUnsignedShort());
        }

        return new ExceptionsAttribute(List.copyOf(exceptionIndexTable));
    }
}