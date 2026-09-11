package djarly.pss.attributes;

import java.io.DataInput;
import java.io.IOException;

public record RawAttribute(String name, byte[] info) implements Attribute {

    public static RawAttribute parse(DataInput in, String name, int length) throws IOException {
        byte[] info = new byte[length];
        in.readFully(info);
        return new RawAttribute(name, info);
    }
}