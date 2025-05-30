package invalid.myask.cyclopeanbrick.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class YRotatedBlockRenderer implements ISimpleBlockRenderingHandler {

    public static final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
    public static final byte[][] ROTATIONS_TO_UVROTATE = new byte[][]{
        {0,1,3,2},
        {0,2,3,1}
    };

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();

        int turns = metadata % 4;
        int unturnedMeta = metadata - turns;

        renderer.uvRotateTop = ROTATIONS_TO_UVROTATE[0][turns];//BlockCyclopeanBrick.yRotationMatrix[metadata % 4][3];
        renderer.uvRotateBottom = ROTATIONS_TO_UVROTATE[1][turns];//BlockCyclopeanBrick.yRotationMatrix[3-(metadata % 4)][3];
        renderer.uvRotateWest = renderer.uvRotateEast = renderer.uvRotateNorth = renderer.uvRotateSouth = 0;

        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);

        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, unturnedMeta));

        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, unturnedMeta));

        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, unturnedMeta));

        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, unturnedMeta));

        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, unturnedMeta));

        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, unturnedMeta));

        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        renderer.uvRotateTop = renderer.uvRotateBottom = 0;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        int metadata = world.getBlockMetadata(x, y, z);

        renderer.uvRotateTop = ROTATIONS_TO_UVROTATE[0][metadata % 4];//BlockCyclopeanBrick.yRotationMatrix[metadata % 4][3];
        renderer.uvRotateBottom = ROTATIONS_TO_UVROTATE[1][metadata % 4];//BlockCyclopeanBrick.yRotationMatrix[3-(metadata % 4)][3];
        renderer.uvRotateWest = renderer.uvRotateEast = renderer.uvRotateNorth = renderer.uvRotateSouth = 0;

        boolean succeeded = renderer.renderStandardBlock(block, x, y, z);

        renderer.uvRotateTop = renderer.uvRotateBottom = 0;

        return succeeded;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RENDER_ID;
    }
}
