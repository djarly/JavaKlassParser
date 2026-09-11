package djarly.pss.attributes;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record BootstrapMethodsAttribute(List<Entry> bootstrapMethods) implements Attribute {

    public record Entry(int bootstrapMethodRef, List<Integer> arguments) {
    }

    public static BootstrapMethodsAttribute parse(DataInput in) throws IOException {
        int count = in.readUnsignedShort();
        List<Entry> bootstrapMethods = new ArrayList<>(count);

        for (int index = 0; index < count; index++) {
            int bootstrapMethodRef = in.readUnsignedShort();
            int argumentCount = in.readUnsignedShort();
            List<Integer> arguments = new ArrayList<>(argumentCount);

            for (int argumentIndex = 0; argumentIndex < argumentCount; argumentIndex++) {
                arguments.add(in.readUnsignedShort());
            }

            bootstrapMethods.add(new Entry(bootstrapMethodRef, List.copyOf(arguments)));
        }

        return new BootstrapMethodsAttribute(List.copyOf(bootstrapMethods));
    }
}