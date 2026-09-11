package djarly.pss.constants;

public interface ConstantTags {

    byte KLASS = 0x07;
    byte FIELD_REF = 0x09;
    byte METHOD_REF = 0x0A;
    byte INTERFACE_METHOD_REF = 0x0B;
    byte STRING = 0x08;
    byte INTEGER = 0x03;
    byte FLOAT = 0x04;
    byte LONG = 0x05;
    byte DOUBLE = 0x06;
    byte NAME_AND_TYPE = 0x0C;
    byte UTF8 = 0x01;
    byte METHOD_HANDLE = 0x0F;
    byte METHOD_TYPE = 0x10;
    byte DYNAMIC = 0x11;
    byte INVOKE_DYNAMIC = 0x12;
    byte MODULE = 0x13;
    byte PACKAGE = 0x14;
}