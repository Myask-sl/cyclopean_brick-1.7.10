package invalid.myask.cyclopeanbrick.block;

import invalid.myask.cyclopeanbrick.CyclopeanBrick;
import invalid.myask.cyclopeanbrick.client.YRotatedBlockRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCyclopeanBrick extends Block {

    protected IIcon[] blockIcons = new IIcon[6];
    public static final int[][] yRotationMatrix = new int[][]
        {
            {0,1, 2,3, 4,5, 6},
            {0,1, 4,5, 3,2, 6},
            {0,1, 3,2, 5,4, 6},
            {0,1, 5,4, 2,3, 6}
        };

    public BlockCyclopeanBrick(Material materialIn) {
        super(materialIn);
    }

    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        int turns = MathHelper.floor_double((double) (placer.rotationYaw / 90.0F) + 1.5D) & 3;
        int metaIn = worldIn.getBlockMetadata(x, y, z) & -4;
        worldIn.setBlockMetadataWithNotify(x, y, z, turns | metaIn, 2);
    }

    @Override
    protected String getTextureName() {
        return "giant_brick_side";
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        int rotations = meta & 3;
        if (side < 2 || rotations == 0) return blockIcons[side];
        return blockIcons[yRotationMatrix[rotations][side]];
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        blockIcons[0] = blockIcon = reg.registerIcon(CyclopeanBrick.MODID + ":giant_brick_bottom");
        blockIcons[1] = reg.registerIcon(CyclopeanBrick.MODID + ":giant_brick_top");
        blockIcons[2] = reg.registerIcon(CyclopeanBrick.MODID + ":giant_brick_side_b");
        blockIcons[3] = reg.registerIcon(CyclopeanBrick.MODID + ":giant_brick_side");
        blockIcons[4] = reg.registerIcon(CyclopeanBrick.MODID + ":giant_brick_end");
        blockIcons[5] = reg.registerIcon(CyclopeanBrick.MODID + ":giant_brick_cut");
    }

    @Override
    public int getRenderType() {
        return YRotatedBlockRenderer.RENDER_ID;
    }
}
