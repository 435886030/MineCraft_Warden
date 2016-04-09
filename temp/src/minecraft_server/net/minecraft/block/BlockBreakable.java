package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBreakable extends Block {

   private boolean field_149996_a;
   private static final String __OBFID = "CL_00000254";


   protected BlockBreakable(Material p_i45712_1_, boolean p_i45712_2_) {
      super(p_i45712_1_);
      this.field_149996_a = p_i45712_2_;
   }

   public boolean func_149662_c() {
      return false;
   }
}
