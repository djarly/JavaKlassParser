# JavaKlassParser
Supports class files up to **Java 25**.

Lightweight parser for Java class files.

Reads the binary `.class` format and exposes constant pool, fields, methods and attributes as plain Java records.

```java
KlassFile file = KlassFile.parse(Path.of("Example.class"));
ConstantPool cp = file.constantPool();

for (Member method : file.methods()) {
    for (Attribute attr : method.attributes().attributes()) {
        if (attr instanceof CodeAttribute(_, _, byte[] code, _, _)) {
            System.out.println(cp.utf8at(method.nameIndex()) + " -> " + Arrays.toString(code));
        }
    }
}
