package lekkit.scev.render.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;

/*
* Based on:
* https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT/blob/master/src/main/java/com/hbm/render/util/ObjUtil.java
* https://gist.github.com/RainWarrior/9573985
*/

public class ObjTesselator {
    private static int red;
    private static int green;
    private static int blue;
    private static boolean hasColor = false;

    public static void setColor(int color) {
        red = (color & 0xff0000) >> 16;
        green = (color & 0x00ff00) >> 8;
        blue = color & 0x0000ff;
        hasColor = true;
    }

    public static void setColor(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
        hasColor = true;
    }

    public static void clearColor() {
        hasColor = false;
    }

    public static void tesselateWithIcon(WavefrontObject model, IIcon icon, Tessellator tes) {
        tesselateWithIcon(model, icon, tes, 0.f);
    }

    public static void tesselateWithIcon(WavefrontObject model, IIcon icon, Tessellator tes, float rotation) {
        tesselateWithIcon(model, icon, tes, rotation, 1.f);
    }

    public static void tesselateWithIcon(WavefrontObject model, IIcon icon, Tessellator tes, float rotation, float scale) {
        tesselatePartWithIcon(model, null, icon, tes, rotation, scale);
    }

    public static void tesselatePartWithIcon(WavefrontObject model, String part, IIcon icon, Tessellator tes) {
        tesselatePartWithIcon(model, part, icon, tes, 0.f);
    }

    public static void tesselatePartWithIcon(WavefrontObject model, String part, IIcon icon, Tessellator tes, float rotation) {
        tesselatePartWithIcon(model, part, icon, tes, rotation, 1.f);
    }

    public static void tesselatePartWithIcon(WavefrontObject model, String part, IIcon icon, Tessellator tes, float rotation, float scale) {
        boolean isPart = part != null;

        for (GroupObject go : model.groupObjects) {
            if (!isPart || go.name.equals(part)) {
                for (Face f : go.faces) {
                    // Apply normals
                    Vertex n = f.faceNormal;
                    Vec3 normal = Vec3.createVectorHelper(n.x * scale, n.y * scale, n.z * scale);
                    normal.rotateAroundY((float)Math.toRadians(rotation));
                    float nx = (float) normal.xCoord;
                    float ny = (float) normal.yCoord;
                    float nz = (float) normal.zCoord;

                    tes.setNormal(nx, ny, nz);

                    // Calculate face brightness
                    float brightness = (ny + 0.7f) * 0.9f - Math.abs(nx) * 0.1f + Math.abs(nz) * 0.1f;

                    if (brightness < 0.45f) {
                        brightness = 0.45f;
                    }

                    if (isPart && hasColor) {
                        tes.setColorOpaque((int)(red * brightness), (int)(green * brightness), (int)(blue * brightness));
                    } else {
                        tes.setColorOpaque_F(brightness, brightness, brightness);
                    }

                    /*
                     * Fix for weird UV fuckery
                     * See net.minecraftforge.client.model.obj.Face
                     */
                    float averageU = 0.f, averageV = 0.f;

                    for (TextureCoordinate t : f.textureCoordinates) {
                        averageU += t.u;
                        averageV += t.v;
                    }

                    averageU = averageU / f.textureCoordinates.length;
                    averageV = averageV / f.textureCoordinates.length;

                    for (int i = 0; i < f.vertices.length; ++i) {
                        Vertex v = f.vertices[i];
                        Vec3 vec = Vec3.createVectorHelper(v.x * scale, v.y * scale, v.z * scale);
                        vec.rotateAroundY((float)Math.toRadians(rotation));

                        float x = (float) vec.xCoord;
                        float y = (float) vec.yCoord;
                        float z = (float) vec.zCoord;

                        TextureCoordinate t = f.textureCoordinates[i];

                        float tu = t.u;
                        float tv = t.v;

                        if (tu > averageU) {
                            tu -= 0.0005f;
                        } else {
                            tu += 0.0005f;
                        }

                        if (tv > averageV) {
                            tv -= 0.0005f;
                        } else {
                            tv += 0.0005f;
                        }

                        float ao = 1f;//(y + 0.5f) / 2f + 0.5f;

                        tes.setColorOpaque_F(brightness * ao, brightness * ao, brightness * ao);

                        tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(tu * 16.0), icon.getInterpolatedV(tv * 16.0));

                        // Quirky fix for quad tessellator from HBM ObjUtil
                        if(f.vertices.length == 3 && i % 3 == 2) {
                            tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(tu * 16.0), icon.getInterpolatedV(tv * 16.0));
                        }
                    }
                }
            }
        }
    }
}
