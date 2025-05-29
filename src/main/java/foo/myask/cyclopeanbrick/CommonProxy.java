package foo.myask.cyclopeanbrick;

import static foo.myask.cyclopeanbrick.CyclopeanBrick.THEBLOCK;

import foo.myask.cyclopeanbrick.block.BlockCyclopeanBrick;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        CyclopeanBrick.LOG.info(Config.greeting);
        CyclopeanBrick.LOG.info("I am " + CyclopeanBrick.MODNAME + " at version " + Tags.VERSION);

        THEBLOCK = new BlockCyclopeanBrick(Material.rock).setHardness(3F)
            .setBlockName("cyclopean_brick")
            .setCreativeTab(CreativeTabs.tabBlock);
        THEBLOCK.setHarvestLevel("pickaxe", 1);
        GameRegistry.registerBlock(THEBLOCK, "cyclopean_brick");
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        GameRegistry.addShapedRecipe(new ItemStack(THEBLOCK, 4), "BB", "BB", 'B', Blocks.stonebrick);
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
