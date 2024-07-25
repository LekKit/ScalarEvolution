package lekkit.scev.client;

import java.util.UUID;
import java.nio.ByteBuffer;

import lekkit.scev.server.MachineState;
import lekkit.rvvm.RVVMMachine;

import org.lwjgl.opengl.GL11;

public class DisplayState {
    protected final UUID uuid;
    protected final ByteBuffer buffer;
    protected final MachineState singleplayerMachine;

    protected final int width;
    protected final int height;

    protected int textureID = -1;
    protected boolean dirty = true;

    public DisplayState(UUID machineUUID, int width, int height) {
        this.uuid = machineUUID;
        this.buffer = ByteBuffer.allocateDirect(getWidth() * getHeight() * getBytesPerPixel());
        this.singleplayerMachine = null;

        this.width = width;
        this.height = height;
    }

    public DisplayState(MachineState state) {
        this.uuid = state.getUUID();
        this.singleplayerMachine = state;
        this.buffer = null;

        this.width = state.getDisplay().getWidth();
        this.height = state.getDisplay().getHeight();
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isDirty() {
        return dirty || singleplayerMachine != null;
    }

    public synchronized void markDirty() {
        dirty = true;
    }

    public ByteBuffer getBuffer() {
        if (singleplayerMachine != null && singleplayerMachine.getDisplay() != null) {
            return singleplayerMachine.getDisplay().getBuffer();
        }
        return buffer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBytesPerPixel() {
        return 4;
    }

    public synchronized void bindTexture() {
        // Gnerate a texture if there isn't one yet
        if (textureID == -1) {
            textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        } else {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        }

        // Upload dirty framebuffer to VRAM
        if (isDirty()) {
            getBuffer().rewind();
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, getWidth(), getHeight(), 0, 0x80E1, GL11.GL_UNSIGNED_BYTE, getBuffer());
        }
    }

    public synchronized void destroy() {
        GL11.glDeleteTextures(textureID);
    }
}
