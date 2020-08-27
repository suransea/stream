package top.srsea.capture.struct;

import java.util.Locale;

import top.srsea.capture.core.util.net.Packets;

public class IpHeader {
    public static final short IP = 0x0800;
    public static final byte ICMP = 1;
    public static final byte TCP = 6;  //6: TCP协议号
    public static final byte UDP = 17; //17: UDP协议号
    public static final byte offset_proto = 9; //9：8位协议偏移
    public static final int offset_src_ip = 12; //12：源ip地址偏移
    public static final int offset_dest_ip = 16; //16：目标ip地址偏移
    static final byte offset_ver_ihl = 0; //0: 版本号（4bits） + 首部长度（4bits）
    static final byte offset_tos = 1; //1：服务类型偏移
    static final short offset_tlen = 2; //2：总长度偏移
    static final short offset_identification = 4; //4：16位标识符偏移
    static final short offset_flags_fo = 6; //6：标志（3bits）+ 片偏移（13bits）
    static final byte offset_ttl = 8; //8：生存时间偏移
    static final short offset_crc = 10; //10：首部校验和偏移
    static final int offset_op_pad = 20; //20：选项 + 填充

    public byte[] data;
    public int offset;

    public IpHeader(byte[] data, int offset) {
        this.data = data;
        this.offset = offset;
    }

    public int getDataLength() {
        return this.getTotalLength() - this.getHeaderLength();
    }

    public int getHeaderLength() {
        return (data[offset + offset_ver_ihl] & 0x0F) * 4;
    }

    public void setHeaderLength(int value) {
        data[offset + offset_ver_ihl] = (byte) ((4 << 4) | (value / 4));
    }

    public byte getTos() {
        return data[offset + offset_tos];
    }

    public void setTos(byte value) {
        data[offset + offset_tos] = value;
    }

    public int getTotalLength() {
        return Packets.readShort(data, offset + offset_tlen) & 0xFFFF;
    }

    public void setTotalLength(int value) {
        Packets.writeShort(data, offset + offset_tlen, (short) value);
    }

    public int getIdentification() {
        return Packets.readShort(data, offset + offset_identification) & 0xFFFF;
    }

    public void setIdentification(int value) {
        Packets.writeShort(data, offset + offset_identification, (short) value);
    }

    public short getFlagsAndOffset() {
        return Packets.readShort(data, offset + offset_flags_fo);
    }

    public void setFlagsAndOffset(short value) {
        Packets.writeShort(data, offset + offset_flags_fo, value);
    }

    public byte getTTL() {
        return data[offset + offset_ttl];
    }

    public void setTTL(byte value) {
        data[offset + offset_ttl] = value;
    }

    public byte getProtocol() {
        return data[offset + offset_proto];
    }

    public void setProtocol(byte value) {
        data[offset + offset_proto] = value;
    }

    public short getCrc() {
        return Packets.readShort(data, offset + offset_crc);
    }

    public void setCrc(short value) {
        Packets.writeShort(data, offset + offset_crc, value);
    }

    public int getSourceIP() {
        return Packets.readInt(data, offset + offset_src_ip);
    }

    public void setSourceIP(int value) {
        Packets.writeInt(data, offset + offset_src_ip, value);
    }

    public int getDestinationIP() {
        return Packets.readInt(data, offset + offset_dest_ip);
    }

    public void setDestinationIP(int value) {
        Packets.writeInt(data, offset + offset_dest_ip, value);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s->%s Protocol=%s, HeaderLen=%d", Packets.ipToString(getSourceIP()),
                Packets.ipToString(getDestinationIP()), getProtocol(), getHeaderLength());
    }
}
