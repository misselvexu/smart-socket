package org.smartboot.socket.buffer;

import java.nio.ByteBuffer;

/**
 * 虚拟ByteBuffer缓冲区
 *
 * @author 三刀
 * @version V1.0 , 2018/10/31
 */
public final class VirtualBuffer {

    /**
     * 当前虚拟buffer的归属内存页
     */
    private final BufferPage bufferPage;
    /**
     * 通过ByteBuffer.slice()隐射出来的虚拟ByteBuffer
     *
     * @see ByteBuffer#slice()
     */
    private ByteBuffer buffer;
    private boolean clean = false;
    /**
     * 当前虚拟buffer映射的实际buffer.position
     */
    private int parentPosition;

    /**
     * 当前虚拟buffer映射的实际buffer.limit
     */
    private int parentLimit;

    VirtualBuffer(BufferPage bufferPage, ByteBuffer buffer, int parentPosition, int parentLimit) {
        this.bufferPage = bufferPage;
        this.buffer = buffer;
        this.parentPosition = parentPosition;
        this.parentLimit = parentLimit;
    }

    int getParentPosition() {
        return parentPosition;
    }

    void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    int getParentLimit() {
        return parentLimit;
    }

    void setParentLimit(int parentLimit) {
        this.parentLimit = parentLimit;
    }

    public ByteBuffer buffer() {
        return buffer;
    }

    void buffer(ByteBuffer buffer) {
        this.buffer = buffer;
        clean = false;
    }

    public void clean() {
        if (clean) {
            System.err.println("buffer has cleaned");
            throw new RuntimeException();
        }
//        buffer = null;
        clean = true;
        if (bufferPage != null) {
            bufferPage.clean(this);
        }
    }

    @Override
    public String toString() {
        return "VirtualBuffer{" +
                "parentPosition=" + parentPosition +
                ", parentLimit=" + parentLimit +
                '}';
    }
}
