package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkCache implements IBlockAccess {

   protected int field_72818_a;
   protected int field_72816_b;
   protected Chunk[][] field_72817_c;
   protected boolean field_72814_d;
   protected World field_72815_e;
   private static final String __OBFID = "CL_00000155";


   public ChunkCache(World p_i45746_1_, BlockPos p_i45746_2_, BlockPos p_i45746_3_, int p_i45746_4_) {
      this.field_72815_e = p_i45746_1_;
      this.field_72818_a = p_i45746_2_.func_177958_n() - p_i45746_4_ >> 4;
      this.field_72816_b = p_i45746_2_.func_177952_p() - p_i45746_4_ >> 4;
      int var5 = p_i45746_3_.func_177958_n() + p_i45746_4_ >> 4;
      int var6 = p_i45746_3_.func_177952_p() + p_i45746_4_ >> 4;
      this.field_72817_c = new Chunk[var5 - this.field_72818_a + 1][var6 - this.field_72816_b + 1];
      this.field_72814_d = true;

      int var7;
      int var8;
      for(var7 = this.field_72818_a; var7 <= var5; ++var7) {
         for(var8 = this.field_72816_b; var8 <= var6; ++var8) {
            this.field_72817_c[var7 - this.field_72818_a][var8 - this.field_72816_b] = p_i45746_1_.func_72964_e(var7, var8);
         }
      }

      for(var7 = p_i45746_2_.func_177958_n() >> 4; var7 <= p_i45746_3_.func_177958_n() >> 4; ++var7) {
         for(var8 = p_i45746_2_.func_177952_p() >> 4; var8 <= p_i45746_3_.func_177952_p() >> 4; ++var8) {
            Chunk var9 = this.field_72817_c[var7 - this.field_72818_a][var8 - this.field_72816_b];
            if(var9 != null && !var9.func_76606_c(p_i45746_2_.func_177956_o(), p_i45746_3_.func_177956_o())) {
               this.field_72814_d = false;
            }
         }
      }

   }

   public TileEntity func_175625_s(BlockPos p_175625_1_) {
      int var2 = (p_175625_1_.func_177958_n() >> 4) - this.field_72818_a;
      int var3 = (p_175625_1_.func_177952_p() >> 4) - this.field_72816_b;
      return this.field_72817_c[var2][var3].func_177424_a(p_175625_1_, Chunk.EnumCreateEntityType.IMMEDIATE);
   }

   public IBlockState func_180495_p(BlockPos p_180495_1_) {
      if(p_180495_1_.func_177956_o() >= 0 && p_180495_1_.func_177956_o() < 256) {
         int var2 = (p_180495_1_.func_177958_n() >> 4) - this.field_72818_a;
         int var3 = (p_180495_1_.func_177952_p() >> 4) - this.field_72816_b;
         if(var2 >= 0 && var2 < this.field_72817_c.length && var3 >= 0 && var3 < this.field_72817_c[var2].length) {
            Chunk var4 = this.field_72817_c[var2][var3];
            if(var4 != null) {
               return var4.func_177435_g(p_180495_1_);
            }
         }
      }

      return Blocks.field_150350_a.func_176223_P();
   }

   public boolean func_175623_d(BlockPos p_175623_1_) {
      return this.func_180495_p(p_175623_1_).func_177230_c().func_149688_o() == Material.field_151579_a;
   }

   public int func_175627_a(BlockPos p_175627_1_, EnumFacing p_175627_2_) {
      IBlockState var3 = this.func_180495_p(p_175627_1_);
      return var3.func_177230_c().func_176211_b(this, p_175627_1_, var3, p_175627_2_);
   }
}
