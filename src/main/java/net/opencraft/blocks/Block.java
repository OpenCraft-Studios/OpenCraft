
package net.opencraft.blocks;

import java.util.List;
import java.util.Random;

import net.opencraft.blocks.material.Material;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.client.sound.*;
import net.opencraft.entity.*;
import net.opencraft.item.*;
import net.opencraft.physics.AABB;
import net.opencraft.tileentity.TileEntitySign;
import net.opencraft.util.Vec3;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class Block {

    public static final StepSound soundPowderFootstep;
    public static final StepSound soundWoodFootstep;
    public static final StepSound soundGravelFootstep;
    public static final StepSound soundGrassFootstep;
    public static final StepSound soundStoneFootstep;
    public static final StepSound soundMetalFootstep;
    public static final StepSound soundGlassFootstep;
    public static final StepSound soundClothFootstep;
    public static final StepSound soundSandFootstep;
    public static final Block[] blocksList;
    public static final boolean[] tickOnLoad;
    public static final boolean[] opaqueCubeLookup;
    public static final int[] lightOpacity;
    public static final boolean[] canBlockGrass;
    public static final int[] lightValue;
    public static final Block stone;
    public static final GrassBlock grass;
    public static final Block dirt;
    public static final Block cobblestone;
    public static final Block planks;
    public static final Block sapling;
    public static final Block bedrock;
    public static final Block waterMoving;
    public static final Block waterStill;
    public static final Block lavaMoving;
    public static final Block lavaStill;
    public static final Block sand;
    public static final Block gravel;
    public static final Block oreGold;
    public static final Block oreIron;
    public static final Block oreCoal;
    public static final Block wood;
    public static final LeavesBlock leaves;
    public static final Block sponge;
    public static final Block glass;
    public static final Block woolRed;
    public static final Block woolOrange;
    public static final Block woolYellow;
    public static final Block woolLime;
    public static final Block woolGreen;
    public static final Block woolAquaGreen;
    public static final Block woolCyan;
    public static final Block woolBlue;
    public static final Block woolPurple;
    public static final Block woolIndigo;
    public static final Block woolViolet;
    public static final Block woolMagenta;
    public static final Block woolPink;
    public static final Block woolDarkGray;
    public static final Block woolGray;
    public static final Block woolWhite;
    public static final FlowerBlock plantYellow;
    public static final FlowerBlock plantRed;
    public static final FlowerBlock mushroomBrown;
    public static final FlowerBlock mushroomRed;
    public static final Block blockGold;
    public static final Block blockSteel;
    public static final Block slabDouble;
    public static final Block slabSingle;
    public static final Block brick;
    public static final Block tnt;
    public static final Block bookshelf;
    public static final Block mossyCobblestone;
    public static final Block obsidian;
    public static final Block torch;
    public static final FireBlock fire;
    public static final Block spawner;
    public static final Block stairPlanks;
    public static final Block chest;
    public static final Block gears;
    public static final Block oreDiamond;
    public static final Block blockDiamond;
    public static final Block workbench;
    public static final Block crops;
    public static final Block tilledField;
    public static final Block stoneOvenIdle;
    public static final Block stoneOvenActive;
    public static final Block signPost;
    public static final Block door;
    public static final Block ladder;
    public static final Block rail;
    public static final Block stairCobblestone;
    public int blockIndexInTexture;
    public final int blockID;
    protected float blockHardness;
    protected float blockResistance;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    public StepSound stepSound;
    public float blockParticleGravity;
    public final Material blockMaterial;

    static {
        soundPowderFootstep = new StepSound("stone", 1.0f, 1.0f);
        soundWoodFootstep = new StepSound("wood", 1.0f, 1.0f);
        soundGravelFootstep = new StepSound("gravel", 1.0f, 1.0f);
        soundGrassFootstep = new StepSound("grass", 1.0f, 1.0f);
        soundStoneFootstep = new StepSound("stone", 1.0f, 1.0f);
        soundMetalFootstep = new StepSound("stone", 1.0f, 1.5f);
        soundGlassFootstep = new StepSoundStone("stone", 1.0f, 1.0f);
        soundClothFootstep = new StepSound("cloth", 1.0f, 1.0f);
        soundSandFootstep = new StepSoundSand("sand", 1.0f, 1.0f);
        blocksList = new Block[256];
        tickOnLoad = new boolean[256];
        opaqueCubeLookup = new boolean[256];
        lightOpacity = new int[256];
        canBlockGrass = new boolean[256];
        lightValue = new int[256];
        stone = new StoneBlock(1, 1).setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep);
        grass = (GrassBlock) new GrassBlock(2).setHardness(0.6f).setStepSound(Block.soundGrassFootstep);
        dirt = new DirtBlock(3, 2).setHardness(0.5f).setStepSound(Block.soundGravelFootstep);
        cobblestone = new Block(4, 16, Material.ROCK).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep);
        planks = new Block(5, 4, Material.WOOD).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundWoodFootstep);
        sapling = new SaplingBlock(6, 15).setHardness(0.0f).setStepSound(Block.soundGrassFootstep);
        bedrock = new Block(7, 17, Material.ROCK).setHardness(-1.0f).setResistance(6000000.0f).setStepSound(Block.soundStoneFootstep);
        waterMoving = new MovingLiquidBlock(8, Material.WATER).setHardness(100.0f).setLightOpacity(3);
        waterStill = new StaticLiquidBlock(9, Material.WATER).setHardness(100.0f).setLightOpacity(3);
        lavaMoving = new MovingLiquidBlock(10, Material.LAVA).setHardness(0.0f).setLightValue(1.0f).setLightOpacity(255);
        lavaStill = new StaticLiquidBlock(11, Material.LAVA).setHardness(100.0f).setLightValue(1.0f).setLightOpacity(255);
        sand = new SandBlock(12, 18).setHardness(0.5f).setStepSound(Block.soundSandFootstep);
        gravel = new GravelBlock(13, 19).setHardness(0.6f).setStepSound(Block.soundGravelFootstep);
        oreGold = new OreBlock(14, 32).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep);
        oreIron = new OreBlock(15, 33).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep);
        oreCoal = new OreBlock(16, 34).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep);
        wood = new LogBlock(17).setHardness(2.0f).setStepSound(Block.soundWoodFootstep);
        leaves = (LeavesBlock) new LeavesBlock(18, 52).setHardness(0.2f).setLightOpacity(1).setStepSound(Block.soundGrassFootstep);
        sponge = new SpongeBlock(19).setHardness(0.6f).setStepSound(Block.soundGrassFootstep);
        glass = new GlassBlock(20, 49, Material.GLASS, false).setHardness(0.3f).setStepSound(Block.soundGlassFootstep);
        woolRed = null;
        woolOrange = null;
        woolYellow = null;
        woolLime = null;
        woolGreen = null;
        woolAquaGreen = null;
        woolCyan = null;
        woolBlue = null;
        woolPurple = null;
        woolIndigo = null;
        woolViolet = null;
        woolMagenta = null;
        woolPink = null;
        woolDarkGray = null;
        woolGray = new Block(35, 64, Material.CLOTH).setHardness(0.8f).setStepSound(Block.soundClothFootstep);
        woolWhite = null;
        plantYellow = (FlowerBlock) new FlowerBlock(37, 13).setHardness(0.0f).setStepSound(Block.soundGrassFootstep);
        plantRed = (FlowerBlock) new FlowerBlock(38, 12).setHardness(0.0f).setStepSound(Block.soundGrassFootstep);
        mushroomBrown = (FlowerBlock) new MushroomBlock(39, 29).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setLightValue(0.125f);
        mushroomRed = (FlowerBlock) new MushroomBlock(40, 28).setHardness(0.0f).setStepSound(Block.soundGrassFootstep);
        blockGold = new OreStorageBlock(41, 39).setHardness(3.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep);
        blockSteel = new OreStorageBlock(42, 38).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep);
        slabDouble = new SlabBlock(43, true).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep);
        slabSingle = new SlabBlock(44, false).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep);
        brick = new Block(45, 7, Material.ROCK).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep);
        tnt = new TNTBlock(46, 8).setHardness(0.0f).setStepSound(Block.soundGrassFootstep);
        bookshelf = new BookshelfBlock(47, 35).setHardness(1.5f).setStepSound(Block.soundWoodFootstep);
        mossyCobblestone = new Block(48, 36, Material.ROCK).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep);
        obsidian = new ObsidianBlock(49, 37).setHardness(10.0f).setResistance(20.0f).setStepSound(Block.soundStoneFootstep);
        torch = new TorchBlock(50, 80).setHardness(0.0f).setLightValue(0.9375f).setStepSound(Block.soundWoodFootstep);
        fire = (FireBlock) new FireBlock(51, 31).setHardness(0.0f).setLightValue(1.0f).setStepSound(Block.soundWoodFootstep);
        spawner = new SpawnerBlock(52, 65).setHardness(5.0f).setStepSound(Block.soundMetalFootstep);
        stairPlanks = new StairBlock(53, Block.planks);
        chest = new ChestBlock(54).setHardness(2.5f).setStepSound(Block.soundWoodFootstep);
        gears = new GearsBlock(55, 62).setHardness(0.5f).setStepSound(Block.soundMetalFootstep);
        oreDiamond = new OreBlock(56, 50).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep);
        blockDiamond = new OreStorageBlock(57, 40).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep);
        workbench = new CraftingTableBlock(58).setHardness(2.5f).setStepSound(Block.soundWoodFootstep);
        crops = new CropsBlock(59, 88).setHardness(0.0f).setStepSound(Block.soundGrassFootstep);
        tilledField = new FarmlandBlock(60).setHardness(0.6f).setStepSound(Block.soundGravelFootstep);
        stoneOvenIdle = new FurnaceBlock(61, false).setHardness(3.5f).setStepSound(Block.soundStoneFootstep);
        stoneOvenActive = new FurnaceBlock(62, true).setHardness(3.5f).setStepSound(Block.soundStoneFootstep).setLightValue(0.875f);
        signPost = new SignBlock(63, TileEntitySign.class, Item.sign.shiftedIndex).setHardness(1.0f).setStepSound(Block.soundWoodFootstep);
        door = new DoorBlock(64).setHardness(3.0f).setStepSound(Block.soundWoodFootstep);
        ladder = new LadderBlock(65, 83).setHardness(0.4f).setStepSound(Block.soundWoodFootstep);
        rail = new RailBlock(66, 128).setHardness(1.0f).setStepSound(Block.soundMetalFootstep);
        stairCobblestone = new StairBlock(67, Block.cobblestone);
        for (int i = 0; i < 256; ++i) {
            if (Block.blocksList[i] != null) {
                Item.itemsList[i] = new ItemBlock(i - 256);
            }
        }
    }

    protected Block(final int blockid, final Material material) {
        this.stepSound = Block.soundPowderFootstep;
        this.blockParticleGravity = 1.0f;
        if (Block.blocksList[blockid] != null) {
            throw new IllegalArgumentException(new StringBuilder().append("Slot ").append(blockid).append(" is already occupied by ").append(Block.blocksList[blockid]).append(" when adding ").append(this).toString());
        }
        this.blockMaterial = material;
        Block.blocksList[blockid] = this;
        this.blockID = blockid;
        this.setShape(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        Block.opaqueCubeLookup[blockid] = this.isOpaqueCube();
        Block.lightOpacity[blockid] = (this.isOpaqueCube() ? 255 : 0);
        Block.canBlockGrass[blockid] = this.getCanBlockGrass();
    }

    protected Block(final int blockid, final int textureIndexSlot, final Material material) {
        this(blockid, material);
        this.blockIndexInTexture = textureIndexSlot;
    }

    protected Block setStepSound(final StepSound soundName) {
        this.stepSound = soundName;
        return this;
    }

    protected Block setLightOpacity(final int opacity) {
        Block.lightOpacity[this.blockID] = opacity;
        return this;
    }

    protected Block setLightValue(final float maxLightLevel) {
        Block.lightValue[this.blockID] = (int) (15.0f * maxLightLevel);
        return this;
    }

    protected Block setResistance(final float explosionResistance) {
        this.blockResistance = explosionResistance * 3.0f;
        return this;
    }

    private boolean getCanBlockGrass() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public int getRenderType() {
        return 0;
    }

    protected Block setHardness(final float blockHardness) {
        this.blockHardness = blockHardness;
        if (this.blockResistance < blockHardness * 5.0f) {
            this.blockResistance = blockHardness * 5.0f;
        }
        return this;
    }

    protected void setTickOnLoad(final boolean tick) {
        Block.tickOnLoad[this.blockID] = tick;
    }

    public void setShape(final float minXBound, final float minYBound, final float minZBound, final float maxXBound, final float maxYBound, final float maxZBound) {
        this.minX = minXBound;
        this.minY = minYBound;
        this.minZ = minZBound;
        this.maxX = maxXBound;
        this.maxY = maxYBound;
        this.maxZ = maxZBound;
    }

    public float getBlockBrightness(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        return blockAccess.getLightBrightness(xCoord, yCoord, zCoord);
    }

    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        return (nya4 == 0 && this.minY > 0.0) || (nya4 == 1 && this.maxY < 1.0) || (nya4 == 2 && this.minZ > 0.0) || (nya4 == 3 && this.maxZ < 1.0) || (nya4 == 4 && this.minX > 0.0) || (nya4 == 5 && this.maxX < 1.0) || !blockAccess.isBlockNormalCube(xCoord, yCoord, zCoord);
    }

    public int getBlockTextureGeneric(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int metadataValue) {
        return this.getBlockTextureFromSideAndMetadata(metadataValue, blockAccess.getBlockMetadata(xCoord, yCoord, zCoord));
    }

    public int getBlockTextureFromSideAndMetadata(final int textureIndexSlot, final int metadataValue) {
        return this.getBlockTextureFromSide(textureIndexSlot);
    }

    public int getBlockTextureFromSide(final int textureIndexSlot) {
        return this.blockIndexInTexture;
    }

    public AABB getSelectedBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return AABB.getBoundingBoxFromPool(xCoord + this.minX, yCoord + this.minY, zCoord + this.minZ, xCoord + this.maxX, yCoord + this.maxY, zCoord + this.maxZ);
    }

    public void getCollidingBoundingBoxes(World world, int xCoord, int yCoord, int zCoord, AABB aabb, List<AABB> list) {
        AABB aabb1 = this.getCollisionBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
        if (aabb1 != null && aabb.intersectsWith(aabb1)) {
            list.add(aabb1);
        }
    }

    public AABB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return AABB.getBoundingBoxFromPool(xCoord + this.minX, yCoord + this.minY, zCoord + this.minZ, xCoord + this.maxX, yCoord + this.maxY, zCoord + this.maxZ);
    }

    public boolean isOpaqueCube() {
        return true;
    }

    public boolean canCollideCheck(final int nya1, final boolean boolean2) {
        return this.isCollidable();
    }

    public boolean isCollidable() {
        return true;
    }

    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
    }

    public void randomDisplayTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
    }

    public void onBlockDestroyedByPlayer(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
    }

    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
    }

    public int tickRate() {
        return 10;
    }

    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
    }

    public void onBlockRemoval(final World world, final int xCoord, final int yCoord, final int zCoord) {
    }

    public int quantityDropped(final Random random) {
        return 1;
    }

    public int idDropped(final int blockid, final Random random) {
        return this.blockID;
    }

    public float blockStrength(final EntityPlayer entityPlayer) {
        if (this.blockHardness < 0.0f) {
            return 0.0f;
        }
        if (!entityPlayer.canHarvestBlock(this)) {
            return 1.0f / this.blockHardness / 100.0f;
        }
        return entityPlayer.getCurrentPlayerStrVsBlock(this) / this.blockHardness / 30.0f;
    }

    public void dropBlockAsItem(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        this.dropBlockAsItemWithChance(world, xCoord, yCoord, zCoord, nya4, 1.0f);
    }

    public void dropBlockAsItemWithChance(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4, final float nya5) {
        for (int quantityDropped = this.quantityDropped(world.rand), i = 0; i < quantityDropped; ++i) {
            if (world.rand.nextFloat() <= nya5) {
                final int idDropped = this.idDropped(nya4, world.rand);
                if (idDropped > 0) {
                    final float n = 0.7f;
                    final EntityItem entity = new EntityItem(world, xCoord + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), yCoord + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), zCoord + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), new ItemStack(idDropped));
                    entity.delayBeforeCanPickup = 10;
                    world.entityJoinedWorld(entity);
                }
            }
        }
    }

    public float getExplosionResistance(final Entity entity) {
        return this.blockResistance / 5.0f;
    }

    public MovingObjectPosition collisionRayTrace(final World world, final int xCoord, final int yCoord, final int zCoord, Vec3 var1, Vec3 var2) {
        var1 = var1.addVector(-xCoord, -yCoord, -zCoord);
        var2 = var2.addVector(-xCoord, -yCoord, -zCoord);
        Vec3 intermediateWithXValue = var1.getIntermediateWithXValue(var2, this.minX);
        Vec3 intermediateWithXValue2 = var1.getIntermediateWithXValue(var2, this.maxX);
        Vec3 intermediateWithYValue = var1.getIntermediateWithYValue(var2, this.minY);
        Vec3 intermediateWithYValue2 = var1.getIntermediateWithYValue(var2, this.maxY);
        Vec3 intermediateWithZValue = var1.getIntermediateWithZValue(var2, this.minZ);
        Vec3 intermediateWithZValue2 = var1.getIntermediateWithZValue(var2, this.maxZ);
        if (!this.isVecInsideYZBounds(intermediateWithXValue)) {
            intermediateWithXValue = null;
        }
        if (!this.isVecInsideYZBounds(intermediateWithXValue2)) {
            intermediateWithXValue2 = null;
        }
        if (!this.isVecInsideXZBounds(intermediateWithYValue)) {
            intermediateWithYValue = null;
        }
        if (!this.isVecInsideXZBounds(intermediateWithYValue2)) {
            intermediateWithYValue2 = null;
        }
        if (!this.isVecInsideXYBounds(intermediateWithZValue)) {
            intermediateWithZValue = null;
        }
        if (!this.isVecInsideXYBounds(intermediateWithZValue2)) {
            intermediateWithZValue2 = null;
        }
        Vec3 vec3D = null;
        if (intermediateWithXValue != null && (vec3D == null || var1.distanceTo(intermediateWithXValue) < var1.distanceTo(vec3D))) {
            vec3D = intermediateWithXValue;
        }
        if (intermediateWithXValue2 != null && (vec3D == null || var1.distanceTo(intermediateWithXValue2) < var1.distanceTo(vec3D))) {
            vec3D = intermediateWithXValue2;
        }
        if (intermediateWithYValue != null && (vec3D == null || var1.distanceTo(intermediateWithYValue) < var1.distanceTo(vec3D))) {
            vec3D = intermediateWithYValue;
        }
        if (intermediateWithYValue2 != null && (vec3D == null || var1.distanceTo(intermediateWithYValue2) < var1.distanceTo(vec3D))) {
            vec3D = intermediateWithYValue2;
        }
        if (intermediateWithZValue != null && (vec3D == null || var1.distanceTo(intermediateWithZValue) < var1.distanceTo(vec3D))) {
            vec3D = intermediateWithZValue;
        }
        if (intermediateWithZValue2 != null && (vec3D == null || var1.distanceTo(intermediateWithZValue2) < var1.distanceTo(vec3D))) {
            vec3D = intermediateWithZValue2;
        }
        if (vec3D == null) {
            return null;
        }
        int integer4 = -1;
        if (vec3D == intermediateWithXValue) {
            integer4 = 4;
        }
        if (vec3D == intermediateWithXValue2) {
            integer4 = 5;
        }
        if (vec3D == intermediateWithYValue) {
            integer4 = 0;
        }
        if (vec3D == intermediateWithYValue2) {
            integer4 = 1;
        }
        if (vec3D == intermediateWithZValue) {
            integer4 = 2;
        }
        if (vec3D == intermediateWithZValue2) {
            integer4 = 3;
        }
        return new MovingObjectPosition(xCoord, yCoord, zCoord, integer4, vec3D.addVector(xCoord, yCoord, zCoord));
    }

    private boolean isVecInsideYZBounds(final Vec3 var1) {
        return var1 != null && var1.yCoord >= this.minY && var1.yCoord <= this.maxY && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
    }

    private boolean isVecInsideXZBounds(final Vec3 var1) {
        return var1 != null && var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
    }

    private boolean isVecInsideXYBounds(final Vec3 var1) {
        return var1 != null && var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.yCoord >= this.minY && var1.yCoord <= this.maxY;
    }

    public void onBlockDestroyedByExplosion(final World world, final int xCoord, final int yCoord, final int zCoord) {
    }

    public int getRenderBlockPass() {
        return 0;
    }

    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return true;
    }

    public boolean blockActivated(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
        return false;
    }

    public void onEntityWalking(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity) {
    }

    public void onBlockPlaced(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
    }

    public void onBlockClicked(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
    }

    public void velocityToAddToEntity(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity, final Vec3 var1) {
    }

    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
    }

    public int getRenderColor(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        return 16777215;
    }

}
