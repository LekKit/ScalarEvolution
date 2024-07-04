package lekkit.rvvm;

import java.nio.ByteBuffer;

public class Framebuffer extends MMIODevice {
    protected int width;
    protected int height;
    protected int bpp;
    protected ByteBuffer buff;

    public static final long BPP_R5G6B5 = 16;
    public static final long BPP_R8G8B8 = 24;
    public static final long BPP_A8R8G8B8 = 32;

    public Framebuffer(RVVMMachine machine, int x, int y, int bpp) {
        this.width = x;
        this.height = y;
        this.bpp = bpp;
        this.buff = ByteBuffer.allocateDirect(x * y * (bpp / 8));
        if (machine.isValid()) {
            this.machine = machine;
            this.mmio_handle = RVVMNative.framebuffer_init_auto(machine.machine, this.buff, x, y, bpp);

            machine.fb_refs.add(this);
        }
    }

    public ByteBuffer getBuffer() {
        return buff;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getBpp() {
        return bpp;
    }
}
