package djarly;

import djarly.pss.AttributeTable;
import djarly.pss.ConstantPool;
import djarly.pss.Member;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record KlassFile(int minorVersion, int majorVersion, ConstantPool constantPool, int accessFlags, int thisKlass,
                        int superKlass, List<Integer> interfaces, List<Member> fields, List<Member> methods, AttributeTable attributes) {

    public static final int MAGIC = 0xCAFEBABE;

    public static KlassFile parse(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path);
             DataInputStream dis = new DataInputStream(is)) {
            return parse(dis);
        }
    }

    public static KlassFile parse(DataInput in) throws IOException {
        int magic = in.readInt();
        if (magic != MAGIC) {
            throw new IOException("Invalid klass file magic: 0x" + Integer.toHexString(magic));
        }

        int minorVersion = in.readUnsignedShort();
        int majorVersion = in.readUnsignedShort();

        ConstantPool constantPool = ConstantPool.parse(in);

        int accessFlags = in.readUnsignedShort();
        int thisKlass = in.readUnsignedShort();
        int superKlass = in.readUnsignedShort();

        int interfacesCount = in.readUnsignedShort();
        List<Integer> interfaces = new ArrayList<>(interfacesCount);
        for (int i = 0; i < interfacesCount; i++) {
            interfaces.add(in.readUnsignedShort());
        }

        int fieldsCount = in.readUnsignedShort();
        List<Member> fields = new ArrayList<>(fieldsCount);
        for (int i = 0; i < fieldsCount; i++) {
            fields.add(Member.parse(in, constantPool));
        }

        int methodsCount = in.readUnsignedShort();
        List<Member> methods = new ArrayList<>(methodsCount);
        for (int i = 0; i < methodsCount; i++) {
            methods.add(Member.parse(in, constantPool));
        }

        AttributeTable attributes = AttributeTable.parse(in, constantPool);

        return new KlassFile(minorVersion, majorVersion, constantPool, accessFlags, thisKlass, superKlass,
                List.copyOf(interfaces), List.copyOf(fields), List.copyOf(methods), attributes);
    }
}
