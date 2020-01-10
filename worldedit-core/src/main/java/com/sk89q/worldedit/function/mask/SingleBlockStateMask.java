package com.sk89q.worldedit.function.mask;

import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;

public class SingleBlockStateMask extends ABlockMask {
    private final char ordinal;

    public BlockState getBlockState() {
        return BlockState.getFromOrdinal(ordinal);
    }

    public SingleBlockStateMask(Extent extent, BlockState state) {
        super(extent);
        this.ordinal = state.getOrdinalChar();
    }

    @Override
    public boolean test(Extent extent, BlockVector3 vector) {
        return ordinal == vector.getOrdinal(extent);
    }

    @Override
    public final boolean test(BlockState state) {
        return state.getOrdinalChar() == ordinal;
    }

    @Override
    public Mask inverse() {
        return new InverseSingleBlockStateMask(getExtent(), BlockState.getFromOrdinal(ordinal));
    }

    @Override
    public Mask tryCombine(Mask mask) {
        if (mask instanceof ABlockMask) {
            ABlockMask other = (ABlockMask) mask;
            if (other.test(BlockState.getFromOrdinal(ordinal))) {
                return this;
            }
            return Masks.alwaysFalse();
        }
        return null;
    }
}
